package TimeTableGeneratorDesktopApp.DatabaseHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseHelper {

// ===================== DATABASE PART - STARTS HERE =============================================================================

  /*  *//** get the database connection here
     */
    public Connection getConnection(){
        return DatabaseConnection.getInstance().getConnection();
    }


    /** execute the query string
     * @param query string is passed here
     * this query will execute by this method
     */
    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
