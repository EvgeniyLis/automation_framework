import java.io.IOException;
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

    static ArrayList<String> myData = new ArrayList<>();
    static ArrayList<String> myDataWithComma = new ArrayList<>();

    public static void getTestData() throws IOException {

        String queryIsbnsNorAvailable = "SELECT distinct(ISBN13) FROM user_test.duty_vitalsource_data;";
         // list with the distinct of isbns from duty_vitalsource_data table
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
    }

    public static void myDataWithComma(ArrayList<String> arr) {
        for (int i = 0; i < arr.size(); i++) {
            if (i < arr.size()-1){
            StringBuffer sb = new StringBuffer(arr.get(i));
            myDataWithComma.add(sb.insert(13, ",").toString());
            }else if (i == arr.size()-1){
                myDataWithComma.add(arr.get(arr.size()-1));
            }
        }
    }

    public static void main(String[] args)throws IOException, ClassNotFoundException {

            getTestData();
            myDataWithComma(myData);
        for (String el:myDataWithComma
             ) {
            System.out.println(el);
        }
        }


}
