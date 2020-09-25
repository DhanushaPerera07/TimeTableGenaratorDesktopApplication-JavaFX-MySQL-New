package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LecturerDatabaseHelper extends DatabaseHelper{


    // ------------------------------------------------------------------------------

    public int getLecturerCount() {

        Connection conn = getConnection();

        String count = "";
        String query = "SELECT COUNT(lid) AS NumberOfLecturer " +
                "FROM lecturer;";


        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            StudentBatches studentBatch;

            if (rs.next()) {
                count = rs.getString("NumberOfLecturer");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Integer.parseInt(count);
    }
}
