package TimeTableGeneratorDesktopApp.Departments.DepartmentsPopUps;

import TimeTableGeneratorDesktopApp.DatabaseHelper.BuildingDatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.FacultyDepartments.Faculty;
import TimeTableGeneratorDesktopApp.LocationsLabsHalls.Building;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddDeptPopUpController implements Initializable {

    // holds values
    public int facultyID;
    public String facultyName;

    @FXML
    private TextField txtDepartmentName;

    @FXML
    private TextField txtDepartmentShortName;

    @FXML
    private TextField txtDepartmentFloorNo;

    @FXML
    private Label departmentFacultyLabel;

    @FXML
    private ComboBox<String> departmentBuildingComboBox;

    @FXML
    private ComboBox<String> departmentSpecializedForComboBox;

    @FXML
    private ComboBox<String> departmentHeadComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializationOfCombobox();
    }

    private void initializationOfCombobox() {

        /** ============== COMBO BOXES ====================================================================
         */
        // facultySpecializedForComboBox combobox
        departmentSpecializedForComboBox.getItems().addAll(
                "Software Engineering",
                "Data Science",
                "UI / UX Engineering",
                "IT"
                /*
                "Faculty of Computing",
                "Faculty of Business Management",
                "Faculty of Engineering"
                */
        );
        // prompt text
        departmentSpecializedForComboBox.setPromptText("Select Specialized For"); // selects the first one in the dropdown


        departmentHeadComboBox.getItems().addAll(
                "Dr. Nuwan",
                "Dr.Asela",
                "Dr. Nimali",
                "Dr. Kumaraswami",
                "John Doe",
                "Oliver Cruise",
                "Max Born",
                "Lise Meitner",
                "Jane Goodall",
                "Jacqueline K. Barton",
                "Dorothy Hodgkin",
                "Melissa Franklin",
                "Sarah Boysen"

        );
        // prompt text
        departmentHeadComboBox.setPromptText("Select head ff the department"); // selects the first one in the dropdown


        departmentBuildingComboBox.setPromptText("Select building:");


    }


    public void getNecessaryDetails(int facultyID, String facultyName) {
        this.facultyID = facultyID;
        this.facultyName = facultyName;

        departmentFacultyLabel.setText(this.facultyName);

        // buildings
        ObservableList<Building> buildingList = getBuildingList();

        if (buildingList.isEmpty() == true) {
            departmentBuildingComboBox.getItems().add("");
        } else {
            for (Building building : buildingList) {
                // sysout check
                departmentBuildingComboBox.getItems().add(building.getBuildingName());
                System.out.println("building table rec: " + building.toString());
            }
        }

    }

    public void ActionEventAddDepartmentPopUp(ActionEvent actionEvent) {

        // ADD BUTTON add a new department
        System.out.println("clicked pop up - add a department action event on ADD BUTTON");
        getPermissionToAddTheRecordFromConfirmBox();

    }


    public void getPermissionToAddTheRecordFromConfirmBox() {
        System.out.println("Clicked - Open Confirmation AlertBOX before adding a Department Record");

        Alert addDepartmentAlert = new Alert(Alert.AlertType.CONFIRMATION);
        addDepartmentAlert.setTitle("Confirmation");
        addDepartmentAlert.setHeaderText("Add a new Department");
        addDepartmentAlert.setContentText("Do you want to add the Department?\nClick add to add the Department,\nOtherwise click Cancel");

        ButtonType AddBtn = new ButtonType("ADD");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        addDepartmentAlert.getButtonTypes().setAll(AddBtn, CancelBtn);

        Optional<ButtonType> result = addDepartmentAlert.showAndWait();
        if (result.get() == AddBtn) {
            insertRecord();
            //System.out.println("Department is added successfully");
        } else {
            System.out.println("Clicked Cancel Button - (Adding a Department)");
        }
    }


    /**
     * This method is used to insert a new Department
     */
    BuildingDatabaseHelper buildingDatabaseHelper = new BuildingDatabaseHelper();

    public void insertRecord() {

        try {
            // get user input
            //int lecturer_emp_id = 1;
            String department_name = txtDepartmentName.getText();
            String department_short_name = txtDepartmentShortName.getText();
            int department_floor_no = Integer.parseInt(txtDepartmentFloorNo.getText());
            String department_specialized_for = departmentSpecializedForComboBox.getValue();
            String department_head = departmentHeadComboBox.getValue();

            String building = departmentBuildingComboBox.getValue();
            int department_building_id = buildingDatabaseHelper.getBuildingInstance(building).getBuildingID();


            int faculty_faculty_id = this.facultyID;
            String department_delete_status = "N";

            if (department_name.equals("") || department_short_name.equals("") || department_specialized_for.equals("") || department_head.equals("")) {
                new Alert(Alert.AlertType.ERROR, "Error: Empty / Not selected field found.\nAll fields are required!").show();
            } else if (txtDepartmentName.getText() == null || txtDepartmentShortName.getText() == null || departmentSpecializedForComboBox.getValue() == null || departmentHeadComboBox.getValue() == null || departmentBuildingComboBox.getValue() == null || department_building_id == 0) {
                new Alert(Alert.AlertType.ERROR, "Error: Empty / Not selected field found.\nAll fields are required!").show();
            } else {
                try {
                    // insert query
                    String query = "INSERT INTO `department` (`department_name`,`department_short_name`,`department_floor_no`,`department_specialized_for`,`department_head`,`department_building_id`,`department_delete_status`,`faculty_faculty_id`) VALUES ('" + department_name + "', '" + department_short_name + "'," + department_floor_no + ", '" + department_specialized_for + "','" + department_head + "'," + department_building_id + ",'" + department_delete_status + "'," + faculty_faculty_id + ")";

                    // execute the insert query
                    databaseHelper.executeQuery(query);
                    closeAddFacultyPopUpForm();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Error: Something went wrong when inserting data").show();
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.ERROR, "Error NullPointerException: Empty / Not selected field found.\nAll fields are required!").show();
            e.printStackTrace();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: Something wrong with selected data,\nEmpty / Not selected field found.\nAll fields are required!").show();
            e.printStackTrace();
        }

    }// insert record

    private void closeAddFacultyPopUpForm() {
        // just used the txtFacultyName here to close the pop up when the record insertion is successfully done.

        Stage stage = (Stage) txtDepartmentName.getScene().getWindow();
        System.out.println("Succeed insertion of a new faculty - closing pop up form");
        stage.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Insertion successfully!");
        alert.setContentText("Record Insertion successfully!\nPlease refresh the screen to view changes");
        alert.show();
    }


    // ===================== DATABASE PART - STARTS HERE =============================================================================

    DatabaseHelper databaseHelper = new DatabaseHelper();

    /**
     * this method is to get all the faculties in the faculty table...
     * returns departmentList;
     */
    public ObservableList<Faculty> getFacultyList() {
        ObservableList<Faculty> facultyList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();

        String query = "SELECT * FROM faculty WHERE faculty_delete_status = 'N' ORDER BY faculty_name";

/*        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

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

        } catch (SQLException ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return facultyList;
    }


    /**
     * this method is to get the buildings relevant to the faculty id in the building table...
     * returns getBuildingList;
     */
    public ObservableList<Building> getBuildingList() {

        ObservableList<Building> buildingList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();

        // get the buildings relevant to the faculty id
        String query;
        query = "SELECT * FROM building WHERE faculty_faculty_id = " + this.facultyID + " AND building_delete_status = 'N' ORDER BY building_name";

/*        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {


            Building building;
            while (rs.next()) {
                building = new Building(
                        rs.getInt("building_id"),
                        rs.getString("building_name"),
                        rs.getInt("building_no_of_floors"),
                        rs.getInt("building_capacity"),
                        rs.getString("building_center"),
                        rs.getString("building_condition"),
                        rs.getString("building_specialized_for"),
                        rs.getInt("building_no_of_lecture_halls"),
                        rs.getInt("building_no_of_tutorial_halls"),
                        rs.getInt("building_no_of_labs"),
                        rs.getInt("faculty_faculty_id")
                );
                buildingList.add(building);
            }

        } catch (SQLException ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return buildingList;
    }


}
