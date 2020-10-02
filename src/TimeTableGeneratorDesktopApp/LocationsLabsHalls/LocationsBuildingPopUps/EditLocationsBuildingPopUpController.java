package TimeTableGeneratorDesktopApp.LocationsLabsHalls.LocationsBuildingPopUps;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.FacultyDatabaseHelper;
import TimeTableGeneratorDesktopApp.FacultyDepartments.Faculty;
import TimeTableGeneratorDesktopApp.LocationsLabsHalls.Building;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

public class EditLocationsBuildingPopUpController implements Initializable {

    // variables are used to hold values
    public String facultyName;
    public int building_id,facultyIdDB,facultyID;
    public int buildingIdToUpdate;
    public Building buildingInstance;

    Map<Integer,String> facultyHashMap= new HashMap<>();


    @FXML
    private TextField txtBuildingName;

    @FXML
    private TextField txtBuildingNoOfFloors;

    @FXML
    private Button btnEditBuildingPopUp;

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



    // ======================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializationComboBox();
    }


    // ======================================================================

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


    /**
     * here we are getting the building instance which is selected by the user to edit
     * @param building
     */
    public void getBuildingInstance(Building building){
        this.buildingInstance = building;
        this.buildingIdToUpdate = building.getBuildingID();
        setFormFieldsToExistingValues();
    }

    /**
     *  set the form fields to existing values
     */
    public void setFormFieldsToExistingValues(){
        // set the form fields to existing values
        txtBuildingName.setText(this.buildingInstance.getBuildingName());
        txtBuildingCapacity.setText(Integer.toString(this.buildingInstance.getBuildingCapacity()));
        txtBuildingNoOfFloors.setText(Integer.toString(this.buildingInstance.getBuildingNoOfFloors()));
        specializedForBuildingComboBox.getSelectionModel().select(this.buildingInstance.getBuildingSpecializedFor());
        conditionBuildingComboBox.getSelectionModel().select(this.buildingInstance.getBuildingCondition());
        centerBuildingComboBox.getSelectionModel().select(this.buildingInstance.getBuildingCenter());
        //facultyForBuildingComboBox.getSelectionModel().select(Integer.toString(this.buildingInstance.getFacultyFacultyId())); //  this should be changed
        facultyForBuildingComboBox.getSelectionModel().select(new FacultyDatabaseHelper().getFacultyInstance(this.buildingInstance.getFacultyFacultyId()).getName()); // pass facultyID, and get facultyName

    }



    public void ActionEventEditBuildingPopUp(ActionEvent actionEvent) {

        // pop up - edit a building action event on ADD BUTTON
        System.out.println("clicked pop up - edit/update a building action event on EDIT/UPDATE BUTTON");
        editBuilding(this.buildingInstance.getBuildingID()); // should pass the building ID that should be updated
    }




    // ===================== DATABASE PART - STARTS HERE =============================================================================

    DatabaseHelper databaseHelper = new DatabaseHelper();

    /**
     * This method is used to insert a new faculty
     */
    public void editBuilding(int buildingID){
        // building insertion method

        try {
            // get user input
            this.building_id = buildingID;
            String building_name = txtBuildingName.getText();
            int building_no_of_floors = Integer.parseInt(txtBuildingNoOfFloors.getText());
            int building_capacity = Integer.parseInt(txtBuildingCapacity.getText());
            String building_center = centerBuildingComboBox.getValue();
            String building_condition = conditionBuildingComboBox.getValue();
            String building_specialized_for = specializedForBuildingComboBox.getValue();
            String fac_name = facultyForBuildingComboBox.getValue();

            FacultyDatabaseHelper facultyDatabaseHelper = new FacultyDatabaseHelper();

            // faculty id is retrieved from database
            int faculty_faculty_id = facultyDatabaseHelper.getFacultyInstance(fac_name).getId();
            String building_delete_status = "N";

            try {
                // insert query
                String query = "UPDATE `building` SET `building_name` = '"+building_name+"',`building_no_of_floors` = "+building_no_of_floors+",`building_capacity` = "+building_capacity+",`building_center` = '"+building_center+"',`building_condition` = '"+building_condition+"',`building_specialized_for` = '"+building_specialized_for+"',`faculty_faculty_id` = "+faculty_faculty_id+",`building_delete_status` = '"+building_delete_status+"' WHERE `building_id` = "+this.building_id+"";

                // UPDATE `members` SET `contact_number` = '0759 253 542' WHERE `membership_number` = 1;

                // execute the insert query
                databaseHelper.executeQuery(query);
                new Alert(Alert.AlertType.INFORMATION,"Update successful !").show();
                closeAddBuildingPopUpForm();

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR,"Error: Something went wrong when updating data").show();
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR,"Error: Invalid inputs and\nNumberFormatException,\nNo of floors and capacity\nshould be only numbers.\nAll fields are required!").show();
            e.printStackTrace();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Error: Something went wrong,\nenter valid inputs,\ncheck inputs again.\nAll fields are required!").show();
            e.printStackTrace();
        }

    }

    private void closeAddBuildingPopUpForm() {
        // just used the txtBuildingName here to close the pop up when the record insertion is successfully done.

        Stage stage = (Stage) txtBuildingName.getScene().getWindow();
        System.out.println("Succeed editing of the building - closing pop up form");
        stage.close();
    }





}
