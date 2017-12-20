package WebBrowser;

import static WebBrowser.WebBrowserTypes.*;

/**
 * Created by santiago on 11/21/17.
 */
public class WebBrowserFactory {


    private static WebBrowser browser=null;


    public static WebBrowser createBrowser(WebBrowserTypes type){

        if(browser==null) {
            switch (type) {
                case CHROME:
                    browser = new LocalChromeDriver();
                    break;
                case FIREFOX:
                    browser = new FireFoxDriver();
                    break;
                default:
                    browser = new LocalChromeDriver();
                    break;
            }
        }

        return browser;
    }

}
