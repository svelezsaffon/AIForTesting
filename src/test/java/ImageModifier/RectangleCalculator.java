package ImageModifier;

import com.google.cloud.vision.v1.Vertex;

import java.awt.Point;

/**
 * Created by santiago on 11/22/17.
 */
public class RectangleCalculator {
    private Vertex upCornerLeft;
    private Vertex upCornerRight;
    private Vertex downCornerRight;
    private Vertex downCornerLeft;

    public RectangleCalculator(Vertex upCornerLeft,Vertex upCornerRight ,Vertex downCornerRight,Vertex downCornerLeft){
        this.upCornerLeft=upCornerLeft;
        this.upCornerRight=upCornerRight;
        this.downCornerLeft=downCornerLeft;
        this.downCornerRight=downCornerRight;
    }

    public Point calculateCenter(){
        return new Point( (this.upCornerLeft.getX()+this.upCornerRight.getX() )/2, (this.downCornerLeft.getY()+this.downCornerRight.getY())/2);
    }

    public int heigth(){
        return this.downCornerRight.getY()-this.upCornerRight.getY();
    }

    public int width(){
        return this.downCornerRight.getX()-this.downCornerLeft.getX();
    }

    public Vertex getUpCornerLeft(){
        return this.upCornerLeft;
    }
}
