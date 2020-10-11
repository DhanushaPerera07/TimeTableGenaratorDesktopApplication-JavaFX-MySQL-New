package TimeTableGeneratorDesktopApp.DatabaseHelper;

import java.sql.Connection;
import java.sql.SQLException;
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
    Statement st;
    public void executeQuery(String query) {
        Connection conn = getConnection();

        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (SQLException ex){
            ex.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            try {
                st.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
