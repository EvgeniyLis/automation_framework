import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static com.utils.TestUtils.initchromeDriverWithProxy;

public class CheggNotFoundTest {

    //TestUtils testUtils = new TestUtils(); // создание экземпляра класса TestUtils для вызова метода testDataForNotFoundFromMySql() - считывания данных из MySql

    WebDriver driver;
    String ISBN13 = "9781111129514";


    @BeforeClass
    public void setUp(){
        //RecordDataForVitalsourceNoData.getTestData();
        driver = initchromeDriverWithProxy();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @Test
    public void notFoundTestChegg(){
        String urlPart1 = "https://www.chegg.com/search/";
        String urlFull = urlPart1 + ISBN13;
        //String isbn13Block = getNodeText(getRequestWithUSProxyToPage(urlFull), NO_SEARCH_RESULTS);


    }

    /*public static Document getRequestWithUSProxyToPage(String url) {
        Response rsp = null;
        System.setProperty("https.proxyHost", "us.proxy.nixsolutions.com");
        System.setProperty("https.proxyPort", "3128");
        for (int i = 0; i < 50; i++) {
            rsp = given().header("Connection", "keep-alive")
                    .header("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36")
                    .get(url);
            int statusCode = rsp.getStatusCode();
            if (statusCode == 200 || statusCode == 404)
                break;
        }
        return getDocument(rsp.asInputStream());
    }*/



}
