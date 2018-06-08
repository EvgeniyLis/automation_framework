import com.utils.MysqlConnectStatic;

import java.sql.*;
import java.util.ArrayList;

public class WorkWithDatabase {

    private static final String url = "jdbc:mysql://localhost:3306/user_test";
    private static final String user = "root";
    private static final String password = "Lke568xr";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String[] args) {

        String queryIsbnsNotAvailable = "SELECT distinct(ISBN13) from user_test.duty_vitalsource_data;";
        ArrayList<String> myData = new ArrayList<>(); // list with the distinct of isbns from duty_vitalsource_data table
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            //PreparedStatement statement = MysqlConnectStatic.connect().prepareStatement(queryIsbnsNorAvailable);
            rs = stmt.executeQuery(queryIsbnsNotAvailable);
            while (rs.next()){
                String isbn=rs.getString("ISBN13");
                myData.add(isbn);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MysqlConnectStatic.disconnect();
        }

        System.out.println("list is created");


        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        try {
            stmt = con.createStatement();
            for (int i=0; i<myData.size(); i++) {
                stmt.addBatch("INSERT INTO user_test.cases (offer, url, price) VALUES ('"+ myData.get(i) +"', null, null);");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        try {
            stmt.executeBatch();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}