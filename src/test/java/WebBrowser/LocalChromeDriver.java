package WebBrowser;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by santiago on 11/21/17.
 */
class LocalChromeDriver extends ChromeDriver implements WebBrowser{

    static {
        System.setProperty("webdriver.chrome.driver", "/home/santiago/bin/CHROMEDRIVER");
    }

    public LocalChromeDriver(){
        super();
    }
}
