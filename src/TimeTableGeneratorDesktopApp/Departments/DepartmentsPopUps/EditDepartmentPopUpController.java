package TimeTableGeneratorDesktopApp.Departments.DepartmentsPopUps;

import TimeTableGeneratorDesktopApp.DatabaseHelper.BuildingDatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.Departments.Department;
import TimeTableGeneratorDesktopApp.LocationsLabsHalls.Building;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
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
    public void getNecessaryFacultyDetails(Department department, int facultyID, String facultyName) throws SQLException {
        this.department = department;
        this.facultyID = facultyID;
        this.facultyName = facultyName;
        //this.facultyIdToUpdate = faculty.getId();
        setFormFieldsToExistingValues();
    }

    /**
     *  set the form fields to existing values
     */
    public void setFormFieldsToExistingValues() throws SQLException {
        // set the form fields to existing values
        txtDepartmentName.setText(department.getName());
        txtDepartmentShortName.setText(department.getShortName());
        txtDepartmentFloorNo.setText(Integer.toString(department.getFloor()));
        departmentFacultyLabel.setText(this.facultyName);

        // building list should be displayed in the dropdown menu
        // get building list from the database
        ObservableList<Building> buildingList = new BuildingDatabaseHelper().getBuildingList();

        if (buildingList.isEmpty()==true) {
            departmentBuildingComboBox.getItems().add("");
        } else {
            for (Building building : buildingList){
                // sysout check
                departmentBuildingComboBox.getItems().add(building.getBuildingName());
                System.out.println("building table rec: " + building.toString());
            }
        }
        departmentBuildingComboBox.getSelectionModel().select(new BuildingDatabaseHelper().getBuildingInstance(department.getBuildingID()).getBuildingName());


        // specialized for
        departmentSpecializedForComboBox.getSelectionModel().select(department.getSpecializedFor());
        departmentHeadComboBox.getSelectionModel().select(department.getHead());

    }



    // ===================== DATABASE PART - STARTS HERE =============================================================================

    /** get the database connection here
     */
    DatabaseHelper databaseHelper = new DatabaseHelper();


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
            //System.out.println("Faculty is edited/updated successfully");
        } else {
            System.out.println("Clicked Cancel Button - (edit/update a faculty)");
        }
    }


    /**
     * This method is used to insert a new faculty
     */
    BuildingDatabaseHelper buildingDatabaseHelper = new BuildingDatabaseHelper();
    public void editUpdateRecord(int facultyID, int departmentID){

        // local variables created to get the user input from the pop up form

        try {
            int department_id = departmentID;
            String department_name = txtDepartmentName.getText();
            String department_short_name = txtDepartmentShortName.getText();
            int department_floor_no = Integer.parseInt(txtDepartmentFloorNo.getText());
            String department_specialized_for = departmentSpecializedForComboBox.getValue();
            String department_head = departmentHeadComboBox.getValue();

            String building = departmentBuildingComboBox.getValue();
            int department_building_id = buildingDatabaseHelper.getBuildingInstance(building).getBuildingID();


            int faculty_faculty_id = this.facultyID;
            // String department_delete_status = "N";

            if (department_name.equals("") || department_short_name.equals("") || department_specialized_for.equals("") || department_head.equals("")) {
                new Alert(Alert.AlertType.ERROR, "Error: Empty / Not selected field found.\nAll fields are required!").show();
            } else if (txtDepartmentName.getText() == null || txtDepartmentShortName.getText() == null || departmentSpecializedForComboBox.getValue() == null || departmentHeadComboBox.getValue() == null || departmentBuildingComboBox.getValue() == null || department_building_id == 0) {
                new Alert(Alert.AlertType.ERROR, "Error: Empty / Not selected field found.\nAll fields are required!").show();
            } else {
                try {
                    // update query
                    String query = "UPDATE `department` SET department_name = '" +department_name+ "', department_short_name = '" +department_short_name+
                            "', department_floor_no = " +department_floor_no+ ", department_specialized_for = '" +department_specialized_for+ "', department_head = '"
                            +department_head+
                            "', department_building_id = "+department_building_id+", faculty_faculty_id = "+faculty_faculty_id+" WHERE department_id = " +department_id+ "";


                    // execute the insert query
                    databaseHelper.executeQuery(query);
                    closeEditFacultyPopUpForm();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Error: Something went wrong when inserting data").show();
                    e.printStackTrace();
                }
            }//else
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.ERROR, "Error NullPointerException: Empty / Not selected field found.\nAll fields are required!").show();
            e.printStackTrace();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: Invalid inputs\nDepartment floor no should be only a number,\nPlease check again given inputs.\nAll fields are required!").show();
            e.printStackTrace();
        }

    }

    private void closeEditFacultyPopUpForm() {
        // just used the txtDepartmentName here to close the pop up when the record editing/update is successfully done.

        Stage stage = (Stage) txtDepartmentName.getScene().getWindow();
        System.out.println("Succeed edit/update of the department - closing pop up form");
        stage.close();
    }

}
