package AI.Actions;

import AI.Body.Body;
import com.google.cloud.language.v1.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by santiago on 11/30/17.
 */
public class Visit extends Action {

    private String valueToVisit;

    public Visit(String lema) {
        super(lema);
    }

    public void execute(Body body){super.execute(body);
        body.visitWebPage(this.valueToVisit);

    }


    public void create(AnnotateTextResponse response){
        // Print the response
        for (Entity entity : response.getEntitiesList()) {
            for (EntityMention mention : entity.getMentionsList()) {

                String content=mention.getText().getContent();

                try {
                    URL url=new URL(content);
                    this.valueToVisit=url.toString();
                } catch (MalformedURLException e) {

                }
            }
        }

    }
}
