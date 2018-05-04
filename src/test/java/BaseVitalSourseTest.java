
import com.utils.ExcelToDataProvider;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BaseVitalSourseTest {

    ExcelToDataProvider excelToDataProvider = new ExcelToDataProvider();

    public WebElement getFindingBlock(List<WebElement> blockList, String rental_term){
        WebElement findingBlock = null;
        for (int i = 0; i < blockList.size(); i++){
            if (blockList.get(i).getText().contains(rental_term)){
                findingBlock = blockList.get(i);
            }
        }
        return findingBlock;
    }
}
