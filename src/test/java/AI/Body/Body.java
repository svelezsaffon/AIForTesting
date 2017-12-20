package AI.Body;

import AI.Actions.Action;
import AI.Sensors.NaturalLanguage.NaturalLanguage;
import AI.Sensors.Vision.Vision;
import ImageModifier.RectangleCalculator;
import WebBrowser.WebBrowser;
import WebBrowser.WebBrowserFactory;
import WebBrowser.WebBrowserTypes;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.Vertex;

import java.awt.*;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santiago on 11/30/17.
 */

public class Body {

    private Vision vision;
    private NaturalLanguage language;
    private Todos<String> actions;
    private WebBrowser browser;


    public Body(){
        this.vision=new Vision();
        this.language=new NaturalLanguage();
        this.actions=new Todos<String>();
        this.browser= WebBrowserFactory.createBrowser(WebBrowserTypes.CHROME);
        this.browser.manage().window().maximize();
    }

    public void loadAction(String text) throws Exception {
        this.actions.addAction(text);
    }

    public void ExecuteAllActions(){
        while(this.actions.isEmpty()==false){
            try {
                Action actio= null;
                actio = language.buildActionUpponText(this.actions.getNextAction());
                actio.execute(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void clickOnStringOnPage(String text){
        try {
            Image image=this.browser.takeScreenShotToVisionImage();

            List<EntityAnnotation> anotation=vision.getTextDetectionObject(image, text);

            assert anotation.isEmpty()==false:BodyUtils.createMessage("Element ",text," not found on page");

            String [] words=text.split(" ");

            Vertex vertex=null;

            Point center=new Point();

            if(words.length > 1 && anotation.size()>1){
                assert false : "We are still working on this feature";

                List<RectangleCalculator> rectangles=new ArrayList<>();

                anotation.forEach(anot -> {
                    List<Vertex> vertices = anot.getBoundingPoly().getVerticesList();
                    rectangles.add(new RectangleCalculator(vertices.get(0), vertices.get(1), vertices.get(2), vertices.get(3)));
                });

                List<DistRectangle> places=new ArrayList<>(words.length);

                places.forEach(distRectangle -> {
                    distRectangle.distance = 0.0;
                    rectangles.forEach(rectA -> {
                        rectangles.forEach(rectB -> {
                            Point centerA = rectA.calculateCenter();
                            Point centerB = rectB.calculateCenter();
                            double newDistance = Math.sqrt(Math.pow(centerB.getX()-centerA.getX(), 2) + Math.pow(centerB.getY()-centerA.getY(), 2));
                            if (newDistance > distRectangle.distance) {
                                distRectangle.a = rectA;
                                distRectangle.b = rectB;
                                distRectangle.distance = newDistance;

                            }
                        });
                    });
                });

            }else if(anotation.size()==1){

                List<Vertex> vertices = anotation.get(0).getBoundingPoly().getVerticesList();

                RectangleCalculator aux=new RectangleCalculator(vertices.get(0),vertices.get(1),vertices.get(2),vertices.get(3));

                center=aux.calculateCenter();

            }else{

                assert false : "User tried to click in a place that appears more than once on the page";

            }



            browser.clickCoordinates(center);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void visitWebPage(String page){
        this.browser.get(page);
    }

}
