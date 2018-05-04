package com.utils;

// in this class is created method which returns Object[][]. This method is used in test class with @Data Provider to send test data to test
public class ExcelToDataProvider {

    ExcelApiTest eat = null;

    public Object[][] testData(String xlFilePath, String sheetName) throws Exception
    {
        Object[][] excelData = null;
        eat = new ExcelApiTest(xlFilePath);
        int rows = eat.getRoCount(sheetName);
        int columns = eat.getColCount(sheetName);

        excelData = new Object[rows-1][columns];

        for(int i=1; i<rows; i++)
        {
            for(int j=0; j<columns; j++)
            {
                excelData[i-1][j] = eat.getCellData(sheetName, j, i);
            }

        }
        return excelData;
    }
}
