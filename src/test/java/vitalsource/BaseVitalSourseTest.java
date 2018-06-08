package vitalsource;

import com.utils.ExcelToDataProvider;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BaseVitalSourseTest {

    public ExcelToDataProvider excelToDataProvider = new ExcelToDataProvider();

    public WebElement getFindingBlock(List<WebElement> blockList, String rentalTerm_or_isbn){
        WebElement findingBlock = null;
        for (int i = 0; i < blockList.size(); i++){
            if (blockList.get(i).getText().contains(rentalTerm_or_isbn)){
                findingBlock = blockList.get(i);
            }
        }
        return findingBlock;
    }


    public String correctIsbn(String isbn){
        StringBuffer sb = new StringBuffer(isbn);
        sb.insert(3,"-");
        sb.insert(5,"-");
        sb.insert(8,"-");
        sb.insert(15,"-");
        String correctIsbn = sb.toString();
        return correctIsbn;
    }

    public static String convertedPriceNet(String price_net) {
        String convertedPriceNet = null;
        int isbnDotIndex = price_net.indexOf('.');
        if (isbnDotIndex == -1){
            if (price_net.length() > 3){
                StringBuffer sb = new StringBuffer(price_net);
                sb.insert(price_net.length()-3, ",");
                convertedPriceNet = sb.toString();
                return convertedPriceNet;
            } else { return price_net;}
        }else{
            if (price_net.subSequence(0, isbnDotIndex).length() > 3) {
                StringBuffer sb = new StringBuffer(price_net);
                sb.insert(isbnDotIndex - 3, ",");
                convertedPriceNet = sb.toString();
                return convertedPriceNet;
            } else { return price_net; }
        }
    }

    /*public String convertedForTest(String rental_term) {
        if (rental_term.equalsIgnoreCase("180")){
            rental_term = "180 Day";
        } else if (rental_term.equalsIgnoreCase("365")){
            rental_term = "1 Year";
        } else if (rental_term.equalsIgnoreCase("")){
            rental_term = "Lifetime";
        }
        return rental_term;
    }*/


}
