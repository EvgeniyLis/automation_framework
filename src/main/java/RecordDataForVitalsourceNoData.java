import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecordDataForVitalsourceNoData {

    private static final String url = "jdbc:mysql://localhost:3306/user_test";
    private static final String user = "root";
    private static final String password = "Lke568xr";

    private static Connection con;
    private static java.sql.Statement stmt;
    private static ResultSet rs;

    public static void getTestData() {

        String queryIsbnsNorAvailable = "SELECT distinct(ISBN13) FROM user_test.duty_vitalsource_data;";
        ArrayList<String> myData = new ArrayList<>(); // list with the distinct of isbns from duty_vitalsource_data table
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(queryIsbnsNorAvailable);
            while (rs.next()) {
                String isbn=rs.getString("ISBN13");
                myData.add(isbn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("List with test data is created");


            try {
                stmt = con.createStatement();
                for (int i=0; i<myData.size(); i++){
                    stmt.executeUpdate("INSERT INTO user_test.cases (offer, url, rent_term) VALUES ("+ myData.get(i) +", null, null);"); // record isbns from the list to testcases table
                }

            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }

        try {
            stmt.executeBatch();
        } catch (SQLException e){
            e.printStackTrace();
        }

        System.out.println("Test data are recorded to cengage.cases");

    }

}
