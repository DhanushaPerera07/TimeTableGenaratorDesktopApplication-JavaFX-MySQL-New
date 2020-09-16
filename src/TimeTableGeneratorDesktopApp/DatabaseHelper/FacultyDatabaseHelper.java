package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.FacultyDepartments.Faculty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FacultyDatabaseHelper extends DatabaseHelper {


    /**
     * Method when faculty name is given as parameter , returns that faculty instance
     */
    public Faculty getFacultyInstance(int facultyID) {

        // create a faculty object
        Faculty faculty = new Faculty();

        // get database connection
        Connection conn = getConnection();

        String query = "SELECT * FROM faculty WHERE faculty_delete_status = 'N' AND faculty_id = '" + facultyID + "' ORDER BY faculty_name";

        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                faculty.setId(rs.getInt("faculty_id"));
                faculty.setName(rs.getString("faculty_name"));
                faculty.setShortName(rs.getString("faculty_short_name"));
                faculty.setSpecializedFor(rs.getString("faculty_specialized_for"));
                faculty.setStatus(rs.getString("faculty_status"));
                faculty.setHead(rs.getString("faculty_head_name"));
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }

        return faculty;

    }


    /**
     * Method when faculty name is given as parameter , returns that faculty instance
     */
    public Faculty getFacultyInstance(String facultyName) {

        // create a faculty object
        Faculty faculty = new Faculty();

        // get database connection
        Connection conn = getConnection();

        String query = "SELECT * FROM faculty WHERE faculty_delete_status = 'N' AND faculty_name = '" + facultyName + "' ORDER BY faculty_name";

        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                faculty.setId(rs.getInt("faculty_id"));
                faculty.setName(rs.getString("faculty_name"));
                faculty.setShortName(rs.getString("faculty_short_name"));
                faculty.setSpecializedFor(rs.getString("faculty_specialized_for"));
                faculty.setStatus(rs.getString("faculty_status"));
                faculty.setHead(rs.getString("faculty_head_name"));
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }

        return faculty;

    }


    // =================================================================================

    /**
     * this method is to get all the faculties in the faculty table...
     * returns departmentList;
     */
    public ObservableList<Faculty> getFacultyList() {
        ObservableList<Faculty> facultyList = FXCollections.observableArrayList();
        //Connection conn = getConnection();
        Connection conn = getConnection();

        String query = "SELECT * FROM faculty WHERE faculty_delete_status = 'N' ORDER BY faculty_name";


//        String query = "SELECT * FROM department";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Faculty faculty;
            while (rs.next()) {
                faculty = new Faculty(
                        rs.getInt("faculty_id"),
                        rs.getString("faculty_name"),
                        rs.getString("faculty_short_name"),
                        rs.getString("faculty_specialized_for"),
                        rs.getString("faculty_status"),
                        rs.getString("faculty_head_name")
                );
                facultyList.add(faculty);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return facultyList;
    }
}
