import com.utils.ConfigProperties;
import com.utils.MysqlConnectStatic;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ArrayListFromMySql {



    public static ArrayList<String> isbnForNoDataTest(){
        String queryIsbn = "SELECT ISBN13 FROM user_test.duty_vitalsource_data where Duration_Rental_Term=180;";
        ArrayList<String> isbnList = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement statement = MysqlConnectStatic.connect().prepareStatement(queryIsbn);
            rs = statement.executeQuery(queryIsbn);
            while (rs.next()) { // executing of query
                String isbn = rs.getString("ISBN13");
                isbnList.add(isbn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MysqlConnectStatic.disconnect();
        }
        return isbnList;
    }

    public static ArrayList<String> urlForNoDataTest(){
        String queryIsbn = "SELECT URL FROM user_test.duty_vitalsource_data where Duration_Rental_Term=180;";
        ArrayList<String> urlList = new ArrayList<>();
        ResultSet rs;
        try {
            PreparedStatement statement = MysqlConnectStatic.connect().prepareStatement(queryIsbn);
            rs = statement.executeQuery(queryIsbn);
            while (rs.next()) { // executing of query
                String url = rs.getString("URL");
                urlList.add(url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MysqlConnectStatic.disconnect();
        }
        return urlList;
    }

    public static void main(String[] args) throws IOException, SQLException {

        Connection con;
        Statement stmt;
        ResultSet rs;




        try {
            con = DriverManager.getConnection(ConfigProperties.getTestProperty("mysqlLocalUrl"), ConfigProperties.getTestProperty("mysqlLocalLogin"), ConfigProperties.getTestProperty("mysqlLocalPassword"));
            stmt=con.createStatement();
            for (int i=0; i<isbnForNoDataTest().size(); i++){
                for (int j=0; j<urlForNoDataTest().size(); j++){
                    stmt.addBatch("INSERT INTO `user_test`.`cases` (`offer`, `url`) VALUES ('"+isbnForNoDataTest().get(i)+"', '"+urlForNoDataTest().get(i)+"');");
                    break;
                }
            }
            stmt.executeBatch();
            //INSERT INTO `user_test`.`cases` (`offer`, `url`) VALUES ('9781305686298', 'https://www.vitalsource.com/products/introduction-to-paralegalism-perspectives-william-p-statsky-v9781305686298');
        } catch (SQLException e){
            e.printStackTrace();
        }



    }
}
