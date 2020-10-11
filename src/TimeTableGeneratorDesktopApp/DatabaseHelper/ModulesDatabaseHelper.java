package TimeTableGeneratorDesktopApp.DatabaseHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModulesDatabaseHelper extends DatabaseHelper{

    // ------------------------------------------------------------------------------

    public int getSubjectCount() {

        Connection conn = getConnection();

        String count = "";
        String query = "SELECT COUNT(idmodule) AS NumberOfModules " +
                "FROM module;";


/*        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {


            if (rs.next()) {
                count = rs.getString("NumberOfModules");
            }

        }catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Integer.parseInt(count);
    }

    // ------------------------------------------------------------------------------


}
