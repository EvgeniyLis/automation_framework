import com.utils.ConfigProperties;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class GeckoProxyTest {

    WebDriver driver;

    @BeforeClass
    public void setUp(){
        driver = initGeckoDriverWithProxy();
    }

    @Test
    public void geskoProxyTest(){
        driver.get("https://www.amazon.com/Introduction-Paralegalism-Perspectives-Problems-Skills-ebook/dp/B00W5ZVG8S");
    }

    public static WebDriver initGeckoDriverWithProxy() {
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(ConfigProperties.getTestProperty("proxy"))
                .setFtpProxy(ConfigProperties.getTestProperty("proxy"))
                .setSslProxy(ConfigProperties.getTestProperty("proxy"));
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(CapabilityType.PROXY, proxy);
        WebDriver driver = new FirefoxDriver(cap);
        return driver;
    }

}
