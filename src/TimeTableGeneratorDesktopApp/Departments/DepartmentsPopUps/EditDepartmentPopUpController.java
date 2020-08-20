package TimeTableGeneratorDesktopApp.Departments.DepartmentsPopUps;

import TimeTableGeneratorDesktopApp.Departments.Department;
import TimeTableGeneratorDesktopApp.FacultyDepartments.Faculty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditDepartmentPopUpController implements Initializable {

    // holds values
    public int facultyID;
    public String facultyName;
    public Department department;

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

        initializeCombobox();

    }

    private void initializeCombobox() {

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
                "Dr. Kumaraswami"

        );
        // prompt text
        departmentHeadComboBox.setPromptText("Select head ff the department"); // selects the first one in the dropdown


        departmentBuildingComboBox.setPromptText("Select building:");
    }

    public void ActionEventEditDepartmentPopUp(ActionEvent actionEvent) {
        // EDIT BUTTON

        getPermissionToEditTheRecordFromConfirmBox(this.facultyID,this.department.getId());
        System.out.println("clicked pop up - edit a department action event on EDIT BUTTON");
        //System.out.println("clicked pop up - EDIT BUTTON : " + this.department.getId());
    }



    /**
     * here we are getting the faculty instance which is selected by the user to edit
     * @param department
     */
    public void getNecessaryFacultyDetails(Department department, int facultyID, String facultyName){
        this.department = department;
        this.facultyID = facultyID;
        this.facultyName = facultyName;
        //this.facultyIdToUpdate = faculty.getId();
        setFormFieldsToExistingValues();
    }

    /**
     *  set the form fields to existing values
     */
    public void setFormFieldsToExistingValues(){
        // set the form fields to existing values
        txtDepartmentName.setText(department.getName());
        txtDepartmentShortName.setText(department.getShortName());
        txtDepartmentFloorNo.setText(Integer.toString(department.getFloor()));
        departmentFacultyLabel.setText(this.facultyName);

        // building
        int building_id = department.getBuildingID();
        if (building_id == 1) {
            departmentBuildingComboBox.getSelectionModel().select("FOC - Main");
        } else {
            departmentBuildingComboBox.getSelectionModel().select("FOC - New Building");
        }

        // specialized for
        departmentSpecializedForComboBox.getSelectionModel().select(department.getSpecializedFor());
        departmentHeadComboBox.getSelectionModel().select(department.getHead());

    }



    // ===================== DATABASE PART - STARTS HERE =============================================================================

    /** get the database connection here
     */
    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetabledb", "root","root");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: getConnection() :::: " + ex.getMessage());
            return null;
        }
    }

    /** execute the query string
     * @param query string is passed here
     * this query will execute by this method
     */
    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    /**
     * Get the permission from the user to EDIT OR UPDATE the record.
     * confirmation dialog box is used here.
     * if confirmation dialog: UPDATE button clicked by the user then, record should be updated,
     * otherwise it should not be
     */

    private void getPermissionToEditTheRecordFromConfirmBox(int facultyID, int departmentID) {

        System.out.println("Clicked - Open Confirmation AlertBOX before UPDATING a Faculty Record");

        Alert editFacultyAlert = new Alert(Alert.AlertType.CONFIRMATION);
        editFacultyAlert.setTitle("Confirmation");
        editFacultyAlert.setHeaderText("Edit / Update a new Faculty");
        editFacultyAlert.setContentText("Do you want to Edit/Update the faculty?\nClick Update to Edit/Update the faculty,\nOtherwise click Cancel");

        ButtonType EditBtn = new ButtonType("CONFIRM UPDATE");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        editFacultyAlert.getButtonTypes().setAll(EditBtn,CancelBtn);

        Optional<ButtonType> result = editFacultyAlert.showAndWait();
        if (result.get() == EditBtn){
            editUpdateRecord(facultyID, departmentID); // parameter: facultyID is passed here, it is used to update the record.
            System.out.println("Faculty is edited/updated successfully");
        } else {
            System.out.println("Clicked Cancel Button - (edit/update a faculty)");
        }
    }


    /**
     * This method is used to insert a new faculty
     */
    public void editUpdateRecord(int facultyID, int departmentID){

        // local variables created to get the user input from the pop up form

        int department_id = departmentID;
        String department_name = txtDepartmentName.getText();
        String department_short_name = txtDepartmentShortName.getText();
        int department_floor_no = Integer.parseInt(txtDepartmentFloorNo.getText());
        String department_specialized_for = departmentSpecializedForComboBox.getValue();
        String department_head = departmentHeadComboBox.getValue();

        String building = departmentBuildingComboBox.getValue();
        int department_building_id;
        if (building == "FOC - Main"){
            department_building_id = 1;
        } else {
            department_building_id = 2;
        }

        int faculty_faculty_id = this.facultyID;
        // String department_delete_status = "N";

        // update query
        String query = "UPDATE `department` SET department_name = '" +department_name+ "', department_short_name = '" +department_short_name+
                "', department_floor_no = " +department_floor_no+ ", department_specialized_for = '" +department_specialized_for+ "', department_head = '"
                +department_head+
                "', department_building_id = "+department_building_id+", faculty_faculty_id = "+faculty_faculty_id+" WHERE department_id = " +department_id+ "";


        // execute the insert query
        executeQuery(query);
        closeEditFacultyPopUpForm();

    }

    private void closeEditFacultyPopUpForm() {
        // just used the txtDepartmentName here to close the pop up when the record editing/update is successfully done.

        Stage stage = (Stage) txtDepartmentName.getScene().getWindow();
        System.out.println("Succeed edit/update of the department - closing pop up form");
        stage.close();
    }

}
