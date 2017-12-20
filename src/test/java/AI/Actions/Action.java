package AI.Actions;

import AI.Body.Body;
import com.google.cloud.language.v1.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by santiago on 11/30/17.
 */
public abstract class Action {

    private String lema;

    public Action(String lema){
        this.lema=lema;
    }

    public String getLema(){
        return this.lema;
    }

    public void execute(Body body){
        System.out.println(this.lema);
    }

    public abstract void create(AnnotateTextResponse response);
}
