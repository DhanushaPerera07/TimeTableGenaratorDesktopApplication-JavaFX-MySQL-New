package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.Lecturers.Lecturers;
import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LecturerDatabaseHelper extends DatabaseHelper {


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


    // ----------------------------------------------------------------------------------------------------------------


    public ObservableList<Lecturers> getLecturersList() {
        ObservableList<Lecturers> lecturerList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String query = "SELECT * FROM lecturer";

        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Lecturers lecturers;
            while (rs.next()) {
                lecturers = new Lecturers(
                        rs.getInt("lid"),
                        rs.getString("lecturerID"),
                        rs.getString("lecturerName"),
                        rs.getString("lecturerFaculty"),
                        rs.getString("lecturerDepartment"),
                        rs.getString("lecturerCenter"),
                        rs.getString("lecturerBuilding"),
                        rs.getInt("lecturerLevel"),
                        rs.getString("lecturerRank")
                );
                lecturerList.add(lecturers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lecturerList;
    }

    // ----------------------------------------------------------------------------------------------------------------
}
