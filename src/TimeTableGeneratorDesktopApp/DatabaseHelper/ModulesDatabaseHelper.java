package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import TimeTableGeneratorDesktopApp.Subjects.Subjects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ModulesDatabaseHelper extends DatabaseHelper{

    // ------------------------------------------------------------------------------

    public int getSubjectCount() {

        Connection conn = getConnection();

        String count = "";
        String query = "SELECT COUNT(idmodule) AS NumberOfModules " +
                "FROM module;";


        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);


            if (rs.next()) {
                count = rs.getString("NumberOfModules");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Integer.parseInt(count);
    }

    // ------------------------------------------------------------------------------


}
