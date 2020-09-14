package TimeTableGeneratorDesktopApp.LocationsLabsHalls.LocationsBuildingPopUps;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.FacultyDatabaseHelper;
import TimeTableGeneratorDesktopApp.FacultyDepartments.Faculty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class AddLocationsBuildingPopUpController implements Initializable {

    @FXML
    private TextField txtBuildingName;

    @FXML
    private TextField txtBuildingNoOfFloors;

    @FXML   // button
    private Button btnAddBuildingPopUp;

    @FXML
    private TextField txtBuildingCapacity;

    @FXML
    private ComboBox<String> specializedForBuildingComboBox;

    @FXML
    private ComboBox<String> conditionBuildingComboBox;

    @FXML
    private ComboBox<String> centerBuildingComboBox;

    @FXML
    private ComboBox<String> facultyForBuildingComboBox;

    public String facultyName;
    public int facultyIdDB,facultyID;



    // ==============================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializationComboBox();

    }


    // ==============================================================================================


    // method for the pop up combobox content should be initialized here
    public void initializationComboBox(){

        ObservableList<Faculty> facultyList = new FacultyDatabaseHelper().getFacultyList();


        if (facultyList.isEmpty() != true){
            // faculties are there, more than one

            for (Faculty faculty : facultyList){
                facultyForBuildingComboBox.getItems().add(faculty.getName());
            }
            facultyForBuildingComboBox.setPromptText("Select Faculty");
        } else {
            facultyForBuildingComboBox.setPromptText("Add faculty first");
        }


        centerBuildingComboBox.getItems().addAll("Malabe",
                "Metro",
                "Matara"
        );
        centerBuildingComboBox.setPromptText("Select Center");

        conditionBuildingComboBox.getItems().addAll("OK",
                "Construction"
        );
        conditionBuildingComboBox.setPromptText("Select Condition");

        specializedForBuildingComboBox.getItems().addAll("Computing",
                "Engineering",
                "Business",
                "Humanities",
                "Science",
                "etc"
        );
        specializedForBuildingComboBox.setPromptText("Select Specialized For");

    }


    public void ActionEventBtnAddBuildingPopUp(ActionEvent actionEvent) {
        // pop up - add a building action event on ADD BUTTON
        System.out.println("clicked pop up - add a building action event on ADD BUTTON");
        insertBuilding();
    }


    // ===================== DATABASE PART - STARTS HERE =============================================================================

    /** get the database connection here
     */
    DatabaseHelper databaseHelper = new DatabaseHelper();

    /**
     * This method is used to insert a new faculty
     */
    public void insertBuilding(){
        // building insertion method

        // get user input
        //int lecturer_emp_id = 1;
        String building_name = txtBuildingName.getText();
        String building_no_of_floors = txtBuildingNoOfFloors.getText();
        String building_capacity = txtBuildingCapacity.getText();
        String building_center = centerBuildingComboBox.getValue();
        String building_condition = conditionBuildingComboBox.getValue();
        String building_specialized_for = specializedForBuildingComboBox.getValue();
        String fac_name = facultyForBuildingComboBox.getValue();

        FacultyDatabaseHelper facultyDatabaseHelper = new FacultyDatabaseHelper();

        // faculty id is retrieved from database
        int faculty_faculty_id = facultyDatabaseHelper.getFacultyInstance(fac_name).getId();
        String building_delete_status = "N";

        // insert query
        String query = "INSERT INTO `building` (`building_name`,`building_no_of_floors`,`building_capacity`,`building_center`,`building_condition`,`building_specialized_for`,`faculty_faculty_id`,`building_delete_status`) VALUES ('"+building_name+"',"+building_no_of_floors+","+building_capacity+", '"+building_center+"','"+building_condition+"','"+building_specialized_for+"',"+faculty_faculty_id+",'"+building_delete_status+"')";

        // execute the insert query
        databaseHelper.executeQuery(query);
        closeAddFacultyPopUpForm();

    }

    private void closeAddFacultyPopUpForm() {
        // just used the txtBuildingName here to close the pop up when the record insertion is successfully done.

        Stage stage = (Stage) txtBuildingName.getScene().getWindow();
        System.out.println("Succeed insertion of a new building - closing pop up form");
        stage.close();
    }



}
