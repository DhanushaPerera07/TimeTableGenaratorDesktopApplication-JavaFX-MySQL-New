package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class StudentBatchesDatabaseHelper extends DatabaseHelper {

    /**
     * Get all the batches from the database
     * @return observableList<StudentBatches> // all the batches
     */
    public ObservableList<StudentBatches> getBatchesList() {
        ObservableList<StudentBatches> studentBatchesList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM studentBatches ORDER BY year";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            StudentBatches studentBatch;
            while (rs.next()) {
                studentBatch = new StudentBatches(rs.getInt("id"), rs.getString("year"), rs.getString("semester"),
                        rs.getString("intake"), rs.getString("faculty"),
                        rs.getString("programme"),
                        rs.getString("center"),
                        rs.getInt("noofstd"), rs.getString("batchID"));
                studentBatchesList.add(studentBatch);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentBatchesList;
    }


}