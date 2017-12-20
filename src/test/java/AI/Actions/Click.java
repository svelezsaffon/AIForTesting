package AI.Actions;

import AI.Body.Body;
import com.google.cloud.language.v1.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Created by santiago on 11/30/17.
 */
public class Click  extends Action{

    private Token tokenFound;
    private Entity entityFound;

    public Click(String lema) {
        super(lema);
    }

    public void execute(Body body){
        body.clickOnStringOnPage(this.entityFound.getName());
    }


    public boolean isRootReachable(List<Token> syntax,Token syntaxFound){
        boolean found=false;
        Queue<Token> recursion=new LinkedList<>();
        recursion.add(syntaxFound);

        while(recursion.isEmpty()==false){
            Token look=recursion.remove();
            if(look.getPartOfSpeech().getTag()==PartOfSpeech.Tag.VERB && look.getDependencyEdge().getLabel()== DependencyEdge.Label.ROOT){
                found=true;
            }else{

                recursion.add(syntax.get(look.getDependencyEdge().getHeadTokenIndex()));
            }
        }


        return found;
    }


    public void create(AnnotateTextResponse response){
        Token syntaxFound=null;
        Entity entityFound=null;
        boolean found=false;
        for (Token token : response.getTokensList()) {
            for(Entity entity:response.getEntitiesList()) {
                for (EntityMention mention : entity.getMentionsList()) {
                    if(mention.getText().getContent().contains(token.getText().getContent())){
                       found=true;
                        syntaxFound=token;
                        entityFound=entity;
                    }
                }
            }
        }

        if(found) {
            if (this.isRootReachable(response.getTokensList(), syntaxFound)) {
                this.tokenFound = syntaxFound;
                this.entityFound=entityFound;
            } else {
                assert false : "Test case is written incorrect, could not found a place to click";
            }
        }
    }
}
