package TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsPopUps;

import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditLocationsHallsPopUpController implements Initializable {

    // holder buidling ID
    public int buildingID, moduleID,tagID;
    public String condition, buildingName;

    @FXML // button
    private Button btnAddHallLabPopUp;

    @FXML
    private TextField txtHallLabName;

    @FXML
    private TextField txtHallLabCapacity;

    @FXML
    private TextField txtHallLabFloor;

    @FXML
    private Text textTheBuilding;

    @FXML
    private ComboBox<String> specializedForHallLabComboBox;

    @FXML
    private ComboBox<String> conditionHallLabComboBox;

    @FXML
    private ComboBox<String> tagHallLabComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCombobox();
    }

    private void initializeCombobox() {
        specializedForHallLabComboBox.getItems().addAll(
                "Software Project Management",
                "User Experience Engineering"
        );
        //specializedForHallLabComboBox.setPromptText("Select Module");

        // tag
        tagHallLabComboBox.getItems().addAll(
                "Lecture Hall",
                "Tutorial Hall",
                "Lecture/Tutorial Hall (Not-Consecutive)",
                "Lecture/Tutorial Hall (Consecutive)",
                "PC - Lab"
        );
        // specializedForHallLabComboBox.setPromptText("Select Tag");
        // tagHallLabComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown




        conditionHallLabComboBox.getItems().addAll(
                "OK",
                "Cancel"
        );
        conditionHallLabComboBox.setPromptText("Select Condition");

        //etExistingComboboxValues();

    }


    /**
     * this will get the buildingID from the
     * LocationsHallsInsideBuildingsItem.fxml (LocationsHallsInsideBuildingsItemController)
     * @param
     */
    public void getNecessaryID(LocationHallLab locationHallLab, String buildingName) {
        this.buildingID = locationHallLab.getBuildingID();
        this.moduleID = locationHallLab.getSubjectId();
        this.tagID = locationHallLab.getTagID();

        /*
        this.buildingID = buildingID;
        this.moduleID = moduleID;
        this.tagID = tagID;
        this.condition = condition;
         */

        setExistingComboboxValues();
        textTheBuilding.setText(buildingName);
        txtHallLabName.setText(locationHallLab.getLocationName());
        txtHallLabCapacity.setText(Integer.toString(locationHallLab.getLocationCapacity()));
        txtHallLabFloor.setText(Integer.toString(locationHallLab.getLocationFloor()));
    }

    public void setExistingComboboxValues() {

        if (this.moduleID == 1){
            specializedForHallLabComboBox.getSelectionModel().select("Software Project Management");
        } else if (this.moduleID ==2){
            specializedForHallLabComboBox.getSelectionModel().select("User Experience Engineering");
        } else {
            specializedForHallLabComboBox.getSelectionModel().select("Unknown");
        }

        if (this.tagID ==1){
            tagHallLabComboBox.getSelectionModel().select("Lecture Hall");
        } else if (this.tagID ==2){
            tagHallLabComboBox.getSelectionModel().select("Tutorial Hall");
        }
        else if (this.tagID ==3){
            tagHallLabComboBox.getSelectionModel().select("Lecture/Tutorial Hall (Not-Consecutive)");
        }
        else if (this.tagID ==4){
            tagHallLabComboBox.getSelectionModel().select("Lecture/Tutorial Hall (Consecutive)");
        }
        else if (this.tagID ==5){
            tagHallLabComboBox.getSelectionModel().select("PC - Lab");
        } else {
            tagHallLabComboBox.getSelectionModel().select("Unknown");
        }

        conditionHallLabComboBox.getSelectionModel().select(this.condition);
    }

    public void ActionEventEditHallLabPopup(ActionEvent actionEvent) {
        getPermissionToEditTheRecordFromConfirmBox(1);
    }



    /**
     * Get the permission from the user to EDIT OR UPDATE the record.
     * confirmation dialog box is used here.
     * if confirmation dialog: UPDATE button clicked by the user then, record should be updated,
     * otherwise it should not be
     */

    private void getPermissionToEditTheRecordFromConfirmBox(int locationID) {

        System.out.println("Clicked - Open Confirmation AlertBOX before UPDATING a hall/lab Record");

        Alert editHallLabAlert = new Alert(Alert.AlertType.CONFIRMATION);
        editHallLabAlert.setTitle("Confirmation");
        editHallLabAlert.setHeaderText("Edit / Update a new Hall/Lab");
        editHallLabAlert.setContentText("Do you want to Edit/Update the hall/lab?\nClick Update to Edit/Update the hall/lab,\nOtherwise click Cancel");

        ButtonType EditBtn = new ButtonType("CONFIRM UPDATE");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        editHallLabAlert.getButtonTypes().setAll(EditBtn,CancelBtn);

        Optional<ButtonType> result = editHallLabAlert.showAndWait();
        if (result.get() == EditBtn){
            editUpdateRecord(locationID); // parameter: facultyID is passed here, it is used to update the record.
            System.out.println("Hall/lab is edited/updated successfully");
        } else {
            System.out.println("Clicked Cancel Button - (edit/update a hall/lab)");
        }
    }



    /**
     * This method is used to insert a new faculty
     */
    public void editUpdateRecord(int locationID){

        // local variables created to get the user input from the pop up form

        // get user input
        //int lecturer_emp_id = 1;

        int location_id = locationID; // this is the id of the record which is to be updated
        String location_name = txtHallLabName.getText();
        String location_capacity = txtHallLabCapacity.getText();
        String location_floor = txtHallLabFloor.getText();
        String location_condition = conditionHallLabComboBox.getValue();
        int building_building_id = this.buildingID;   // facultyHeadComboBox.getValue();
        int tag_tag_id = this.tagID; // facultyHeadComboBox.getValue();
        int subject_subject_id = this.tagID;     //facultyHeadComboBox.getValue();
        // String faculty_delete_status = "N"; // this is not used in here


        // update query
        String query = "UPDATE `location` SET location_name = '" +location_name+ "', location_capacity = " +location_capacity+
                ", location_floor = " +location_floor+ ", location_condition = '" +location_condition+ "', building_building_id = "
                +building_building_id+
                ",tag_tag_id = "+tag_tag_id+", subject_subject_id = "+subject_subject_id+" WHERE location_id = " +location_id+ "";


        // execute the insert query
        executeQuery(query);
        closeEditFacultyPopUpForm();

    }

    private void closeEditFacultyPopUpForm() {
        // just used the txtHallLabName here to close the pop up when the record editing/update is successfully done.

        Stage stage = (Stage) txtHallLabName.getScene().getWindow();
        System.out.println("Succeed edit/update of the Hall/Lab - closing pop up form");
        stage.close();
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



}
