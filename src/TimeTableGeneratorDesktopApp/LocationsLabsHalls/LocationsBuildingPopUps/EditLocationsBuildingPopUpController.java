package TimeTableGeneratorDesktopApp.LocationsLabsHalls.LocationsBuildingPopUps;

import TimeTableGeneratorDesktopApp.FacultyDepartments.Faculty;
import TimeTableGeneratorDesktopApp.LocationsLabsHalls.Building;
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

        ObservableList<Faculty> facultyList = getFacultyList();


        for (Faculty faculty : facultyList){
            // sysout check
            facultyHashMap.put(faculty.getId(),faculty.getName());

        }

        Set<Map.Entry<Integer, String>> entries = facultyHashMap.entrySet();

        if(facultyHashMap.isEmpty() != true) {
            for (Map.Entry<Integer, String> entry : entries) {
                facultyID = entry.getKey();
                facultyName = entry.getValue();
                facultyForBuildingComboBox.getItems().add(facultyName);

            }
        } else {
            facultyForBuildingComboBox.setPromptText("Add faculty first");
        }

        facultyForBuildingComboBox.setPromptText("Select Faculty");

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
        facultyForBuildingComboBox.getSelectionModel().select(getFacultyNameFromTheFacultyHashMap(this.buildingInstance.getFacultyFacultyId())); //  this should be changed

    }



    public void ActionEventEditBuildingPopUp(ActionEvent actionEvent) {

        // pop up - edit a building action event on ADD BUTTON
        System.out.println("clicked pop up - edit/update a building action event on EDIT/UPDATE BUTTON");
        editBuilding(this.buildingInstance.getBuildingID()); // should pass the building ID that should be updated
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
     * Get faculty name when we pass the faculty ID as the parameter
     */
    public String getFacultyNameFromTheFacultyHashMap(int facultyID){

        return facultyHashMap.get(facultyID);
    }


    /**
     * this method is to get all the faculties in the faculty table...
     * returns departmentList;
     * */
    public ObservableList<Faculty> getFacultyList() {
        ObservableList<Faculty> facultyList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String query = "SELECT * FROM faculty WHERE faculty_delete_status = 'N' ORDER BY faculty_name";

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


    /**
     * This method is used to insert a new faculty
     */
    public void editBuilding(int buildingID){
        // building insertion method

        // get user input
        this.building_id = buildingID;
        String building_name = txtBuildingName.getText();
        String building_no_of_floors = txtBuildingNoOfFloors.getText();
        String building_capacity = txtBuildingCapacity.getText();
        String building_center = centerBuildingComboBox.getValue();
        String building_condition = conditionBuildingComboBox.getValue();
        String building_specialized_for = specializedForBuildingComboBox.getValue();
        String fac_name = facultyForBuildingComboBox.getValue();

        Set<Map.Entry<Integer, String>> entries = facultyHashMap.entrySet();

        if(facultyHashMap.isEmpty() != true) {
            for (Map.Entry<Integer, String> entry : entries) {
                facultyID = entry.getKey();
                facultyName = entry.getValue();

                if (fac_name == facultyName){
                    facultyIdDB = facultyID;
                    break;
                }
            } // for loop
        }

        //String faculty_faculty_id = facultyForBuildingComboBox.getValue();
        int faculty_faculty_id = facultyIdDB;
        String building_delete_status = "N";

        // insert query
        String query = "UPDATE `building` SET `building_name` = '"+building_name+"',`building_no_of_floors` = "+building_no_of_floors+",`building_capacity` = "+building_capacity+",`building_center` = '"+building_center+"',`building_condition` = '"+building_condition+"',`building_specialized_for` = '"+building_specialized_for+"',`faculty_faculty_id` = "+faculty_faculty_id+",`building_delete_status` = '"+building_delete_status+"' WHERE `building_id` = "+this.building_id+"";

        // UPDATE `members` SET `contact_number` = '0759 253 542' WHERE `membership_number` = 1;

        // execute the insert query
        executeQuery(query);
        closeAddBuildingPopUpForm();

    }

    private void closeAddBuildingPopUpForm() {
        // just used the txtBuildingName here to close the pop up when the record insertion is successfully done.

        Stage stage = (Stage) txtBuildingName.getScene().getWindow();
        System.out.println("Succeed editing of the building - closing pop up form");
        stage.close();
    }





}
