package ImageModifier;

import LogSystem.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by santiago on 11/21/17.
 */
public class ImageModifier {

    public BufferedImage cropImage(Image image,int initX,int initY,int width,int height){
        BufferedImage bufferImage=this.imageToBufferedImage(image);
        return bufferImage.getSubimage(initX,initY,width,height);
    }

    public BufferedImage cropImage(Image image,RectangleCalculator rect){
        BufferedImage bufferImage=this.imageToBufferedImage(image);
        return bufferImage.getSubimage(rect.getUpCornerLeft().getX(),rect.getUpCornerLeft().getX(),rect.width(),rect.heigth());
    }


    public void saveImageToFile(BufferedImage image,String file) throws IOException {
        File outputfile = new File(file);
        outputfile.createNewFile();
        Log.Log(outputfile.getAbsolutePath());
        ImageIO.write(image, "png", outputfile);
    }

    public  BufferedImage imageToBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }


}
