package WebBrowser;

import LogSystem.Log;
import com.google.protobuf.ByteString;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Created by santiago on 11/21/17.
 */
public interface WebBrowser extends WebDriver {
    default void safeClick() {

    }


    default void takeScreenShotWriteFile(String filePath) throws IOException {
        try {
            File src = ((TakesScreenshot) this).getScreenshotAs(OutputType.FILE);
            File aux = new File(filePath);
            FileUtils.copyFile(src, aux);
            Log.Log(aux.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default void shutDown() {
        this.close();
        this.quit();
    }


    default File takeScreenShotToFile(String filePath) throws IOException {
        File src = ((TakesScreenshot) this).getScreenshotAs(OutputType.FILE);

        return src;
    }

    default com.google.cloud.vision.v1.Image takeScreenShotToVisionImage() throws IOException {
        File pathToFile = ((TakesScreenshot) this).getScreenshotAs(OutputType.FILE);

        Path path = Paths.get(pathToFile.getPath());

        byte[] data = Files.readAllBytes(path);

        ByteString imgBytes = ByteString.copyFrom(data);

        return com.google.cloud.vision.v1.Image.newBuilder().setContent(imgBytes).build();
    }

    default java.awt.Image takeScreenShotAsAwtImage() throws IOException {
        File pathToFile = ((TakesScreenshot) this).getScreenshotAs(OutputType.FILE);
        Image image = ImageIO.read(pathToFile);
        return image;
    }

    default void clickCoordinates(int x, int y) {
        JavascriptExecutor executor = (JavascriptExecutor) this;
        executor.executeScript("document.elementFromPoint(" + x + "," + y + ").click();");
    }

    default WebElement findElemebtByCorrdinate(Point p){
        JavascriptExecutor executor = (JavascriptExecutor) this;
        String script = "return  document.elementFromPoint(" + (int) p.getX() + "," + (int) p.getY() + ");";
        return (WebElement) executor.executeScript(script);
    }


    default void sendKeysToElementWithCorrdinates(Point p, String sequence) {
        WebElement elem=this.findElemebtByCorrdinate(p);
        elem.sendKeys(sequence);
    }

    default void clickCoordinates(Point p) {
        JavascriptExecutor executor = (JavascriptExecutor) this;
        executor.executeScript("document.elementFromPoint(" + (int) p.getX() + "," + (int) p.getY() + ").click();");
    }
}
