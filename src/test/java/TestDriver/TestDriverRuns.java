package TestDriver;

import AI.Actions.Action;
import AI.Actions.NoRootActionDetected;
import AI.Body.Body;
import AI.Sensors.NaturalLanguage.NaturalLanguage;
import org.testng.annotations.Test;


import java.io.IOException;


/**
 * Created by santiago on 11/21/17.
 * ~/Documents/fromwindows
 */
public class TestDriverRuns {


    @Test
    public void testDriverIsRunning(){

        Body body=new Body();

        try {

            //body.loadAction("Write 'anything that the user wants' in search field");

            //body.loadAction("Type any word you like in username field");

                body.loadAction("Visit web page https://www.nike.com");
                body.loadAction("Click on NORTH");
            /*    body.loadAction("Click on UNITED");
                body.loadAction("Click on MEN");
                body.loadAction("Click on Running");
            */


        } catch (Exception e) {
            e.printStackTrace();
        }

        body.ExecuteAllActions();

    }

}
