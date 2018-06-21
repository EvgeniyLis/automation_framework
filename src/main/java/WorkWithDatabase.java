import com.Book;

import java.sql.*;
import java.util.ArrayList;

public class WorkWithDatabase {

    private static final String urlDB = "jdbc:mysql://localhost:3306/user_test";
    private static final String user = "root";
    private static final String password = "Lke568xr";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String[] args) {

        ArrayList<Book> bookList = new ArrayList<>();
        try {
            con = DriverManager.getConnection(urlDB, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery("select ISBN13,RETAIL_PRICE,URL from user_test.duty_vitalsource_data;");
            while (rs.next()){
                bookList.add(new Book(rs.getString("ISBN13"), rs.getString("RETAIL_PRICE"), rs.getString("URL")));
            }
            stmt = con.createStatement();
            for (Book el:bookList) {
                stmt.addBatch("INSERT INTO user_test.cases (offer, url, price) VALUES ('"+el.getIsbn()+"', '"+el.getUrl()+"', '"+el.getPrice_net()+"');");
            }
            stmt.executeBatch();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
