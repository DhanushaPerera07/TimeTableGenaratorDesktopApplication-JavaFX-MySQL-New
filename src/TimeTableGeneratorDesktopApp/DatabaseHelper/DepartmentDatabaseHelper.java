package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.Departments.Department;
import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DepartmentDatabaseHelper extends DatabaseHelper {

    /**
     * Method when faculty name is given as parameter , returns that faculty instance
     */
    public Department getDepartmentInstance(String departmentName) {

        // create a faculty object
        Department department = new Department(); // default constructor is called

        // get database connection
        Connection conn = getConnection();

        String query = "SELECT * FROM faculty WHERE faculty_delete_status = 'N' AND faculty_name = '" + departmentName + "' ORDER BY faculty_name";

/*        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                // set values to the created department obj by fetching from the database
                department.setId(rs.getInt("department_id"));
                department.setName(rs.getString("department_name"));
                department.setShortName(rs.getString("department_short_name"));
                department.setFloor(rs.getInt("department_floor_no"));
                department.setSpecializedFor(rs.getString("department_specialized_for"));
                department.setHead(rs.getString("department_head"));
                department.setBuildingID(rs.getInt("department_building_id"));
                department.setFacultyID(rs.getInt("faculty_faculty_id"));
            }


        } catch (SQLException ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }

        return department;

    }


    // ====================================================================================

    /**
     * @param facultyID is used to get the department records for a particular faculty
     *                  this method is to get all the departments in the department table...
     *                  returns departmentList;
     */
    public ObservableList<Department> getDepartmentsList(int facultyID) {
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String query = "SELECT * FROM department WHERE department_delete_status = 'N' AND faculty_faculty_id = " + facultyID + " ORDER BY department_name";

/*        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            Department department;
            while (rs.next()) {
                department = new Department(
                        rs.getInt("department_id"),
                        rs.getString("department_name"),
                        rs.getString("department_short_name"),
                        rs.getInt("department_floor_no"),
                        rs.getString("department_specialized_for"),
                        rs.getString("department_head"),
                        rs.getInt("department_building_id"),
                        rs.getInt("faculty_faculty_id")
                );
                departmentList.add(department);
            }

        } catch (SQLException ex) {
            // if an error occurs print an error...
            System.out.println("Error SQLException - When department data retrieving ");
            ex.printStackTrace();
        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }
        return departmentList;
    }


    // ------------------------------------------------------------------------------


    /**
     * Method when faculty name is given as parameter , returns that faculty instance
     */

    public ObservableList<Department> getDepartmentListByDepartmentName(String departmentName, int facultyID) {
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String query = "SELECT * FROM department WHERE department_delete_status = 'N' AND department_name LIKE '%" + departmentName + "%' AND faculty_faculty_id = " + facultyID + " ORDER BY department_name";

/*        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            Department department;
            while (rs.next()) {
                department = new Department(
                        rs.getInt("department_id"),
                        rs.getString("department_name"),
                        rs.getString("department_short_name"),
                        rs.getInt("department_floor_no"),
                        rs.getString("department_specialized_for"),
                        rs.getString("department_head"),
                        rs.getInt("department_building_id"),
                        rs.getInt("faculty_faculty_id")
                );
                departmentList.add(department);
            }

        } catch (SQLException ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }

        return departmentList;
    }


    // ------------------------------------------------------------------------------

    public int getDepartmentCount() {

        Connection conn = getConnection();

        String count = "";
        String query = "SELECT COUNT(department_id) AS NumberOfDepartment " +
                "FROM department;";


/*        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            StudentBatches studentBatch;

            if (rs.next()) {
                count = rs.getString("NumberOfDepartment");
            }

        } catch (SQLException ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Integer.parseInt(count);
    }

}
