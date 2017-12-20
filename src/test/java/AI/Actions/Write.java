package AI.Actions;

import AI.Body.Body;
import com.google.cloud.language.v1.*;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by santiago on 12/1/17.
 */
public class Write extends Action{

    private String valueToVisit;

    public Write(String lema) {
        super(lema);
    }

    public void execute(Body body){super.execute(body);
        super.execute(body);
    }


    public void create(AnnotateTextResponse response){

        List<Token> rootVerbs=response.getTokensList().parallelStream().filter(token -> token.getPartOfSpeech().getTag() == PartOfSpeech.Tag.VERB).collect(Collectors.toList()).
                stream().filter(token -> token.getDependencyEdge().getLabel() == DependencyEdge.Label.ROOT).collect(Collectors.toList());

        assert  rootVerbs.size()==1:"More than one root action, we are working on this";

        List<Entity>  listOutput= new ArrayList<>();

        response.getEntitiesList().parallelStream()
                .forEach(
                        entity -> entity.getMentionsList().forEach(
                                entityMention -> {
                                    if (entityMention.getText().getContent().matches("(\\S(\\s)*)+(input|field)")) {
                                        listOutput.add(entity);
                                        String[] writeOn = entityMention.getText().getContent().split("(\\S(\\s)*)+(input|field)");
                                    }
                                }
                        )
                );


        assert listOutput.size()==1:"More than one field to write on, we are working on that";


        /*
        for (Entity entity : listOutput) {
            System.out.printf("Entity: %s", entity.getName());
            System.out.printf("Salience: %.3f\n", entity.getSalience());
            System.out.println("Metadata: ");
            for (Map.Entry<String, String> entry : entity.getMetadataMap().entrySet()) {
                System.out.printf("%s : %s", entry.getKey(), entry.getValue());
            }
            for (EntityMention mention : entity.getMentionsList()) {
                System.out.printf("Begin offset: %d\n", mention.getText().getBeginOffset());
                System.out.printf("Content: %s\n", mention.getText().getContent());
                System.out.printf("Type: %s\n\n", mention.getType());
            }
        }

        for (Token token : rootVerbs) {
            System.out.printf("\tText: %s\n", token.getText().getContent());
            System.out.printf("\tBeginOffset: %d\n", token.getText().getBeginOffset());
            System.out.printf("Lemma: %s\n", token.getLemma());
            System.out.printf("PartOfSpeechTag: %s\n", token.getPartOfSpeech().getTag());
            System.out.printf("\tAspect: %s\n", token.getPartOfSpeech().getAspect());
            System.out.printf("\tCase: %s\n", token.getPartOfSpeech().getCase());
            System.out.printf("\tForm: %s\n", token.getPartOfSpeech().getForm());
            System.out.printf("\tGender: %s\n", token.getPartOfSpeech().getGender());
            System.out.printf("\tMood: %s\n", token.getPartOfSpeech().getMood());
            System.out.printf("\tNumber: %s\n", token.getPartOfSpeech().getNumber());
            System.out.printf("\tPerson: %s\n", token.getPartOfSpeech().getPerson());
            System.out.printf("\tProper: %s\n", token.getPartOfSpeech().getProper());
            System.out.printf("\tReciprocity: %s\n", token.getPartOfSpeech().getReciprocity());
            System.out.printf("\tTense: %s\n", token.getPartOfSpeech().getTense());
            System.out.printf("\tVoice: %s\n", token.getPartOfSpeech().getVoice());
            System.out.println("DependencyEdge");
            System.out.printf("\tHeadTokenIndex: %d\n", token.getDependencyEdge().getHeadTokenIndex());
            System.out.printf("\tLabel: %s\n\n", token.getDependencyEdge().getLabel());
        }
        */

    }

}
