package TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsPopUps;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.TagsDatabaseHelper;
import TimeTableGeneratorDesktopApp.Tags.Tags;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddLocationsHallsPopUpController implements Initializable {

    // variables hold values
    public int buildingID, moduleID;
    public String buildingName, moduleName;

    @FXML // button
    private Button btnAddHallLabPopUp;

    @FXML
    private TextField txtHallLabName;

    @FXML
    private TextField txtHallLabCapacity;

    @FXML
    private TextField txtHallLabFloor;

    @FXML
    private ComboBox<String> conditionHallLabComboBox;

    @FXML
    private ComboBox<String> tagHallLabComboBox;

    @FXML
    private Text textTheBuilding;

    Map<Integer, String> moduleHashMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeCombobox();

        // tags data should be retrieved from the database and show it in the dropdown
        // modules/ subjects data should be retrieved from the database and show it in the dropdown


    }

    private void initializeCombobox() {

        // get tags details from the database and make a list,
        TagsDatabaseHelper tagsDatabaseHelper = new TagsDatabaseHelper();
        ObservableList<Tags> tagList = tagsDatabaseHelper.getTagList();

        for (Tags tag : tagList) {
            // sysout check
            tagHallLabComboBox.getItems().add(tag.getTag()); // tag name is displayed in the combo box

        }
        //tagHallLabComboBox.getSelectionModel().select("Lecture + Tutorial");
        tagHallLabComboBox.setPromptText("Select Tag");


        conditionHallLabComboBox.getItems().addAll(
                "OK",
                "Cancel"
        );
        conditionHallLabComboBox.setPromptText("Select Condition");

    }

    public void ActionEventAddHallLabPopup(ActionEvent actionEvent) {

        // pop up - add a Hall /s Lab action event on ADD BUTTON
        System.out.println("clicked pop up - add a Hall / Lab action event on ADD BUTTON");
        insertRecord();
    }

    public void getBuildingDetails(int buildingID, String buildingName) {

        textTheBuilding.setText(buildingName);
        this.buildingID = buildingID;
        this.buildingName = buildingName;

    }


    // ===================== DATABASE PART - STARTS HERE =============================================================================

    /**
     * get the database connection here
     */
    DatabaseHelper databaseHelper = new DatabaseHelper();

    /**
     * This method is used to insert a new location/room ( hall or lab )
     */
    TagsDatabaseHelper tagsDatabaseHelper = new TagsDatabaseHelper();

    public void insertRecord() {

        try {
            // get user input
            String location_name = txtHallLabName.getText();

            int location_capacity = Integer.parseInt(txtHallLabCapacity.getText());
            int location_floor = Integer.parseInt(txtHallLabFloor.getText());

            String location_condition = conditionHallLabComboBox.getValue();
            String faculty_delete_status = "N";
            int building_building_id = this.buildingID;
            int tag_tag_id = 0; //tagHallLabComboBox.getValue();
            int subject_subject_id; //specializedForHallLabComboBox.getValue();

            tag_tag_id = tagsDatabaseHelper.getTagInstanceByTagName(tagHallLabComboBox.getValue()).getTagID();
            // get tags details from the database and make a list.

            if (location_name.equals("") || location_condition.equals("") || faculty_delete_status.equals("") || tag_tag_id == 0 || building_building_id == 0) {
                new Alert(Alert.AlertType.ERROR, "Error: Invalid Fields found.\nEmpty / Not selected field found.\nAll fields are required!").show();
            } else {
                try {
                    // insert query
                    String query = "INSERT INTO `location` (`location_name`,`location_capacity`,`location_floor`,`location_condition`,`location_delete_status`,`building_building_id`,`tag_tag_id`) VALUES ('" + location_name + "', " + location_capacity + ", " + location_floor + ", '" + location_condition + "','" + faculty_delete_status + "'," + building_building_id + "," + tag_tag_id + ")";

                    // execute the insert query
                    databaseHelper.executeQuery(query);
                    /*new Alert(Alert.AlertType.INFORMATION, "Insert successful !").show();*/
                    closeAddHallLabPopUpForm();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Error: Something went wrong when inserting data").show();
                    e.printStackTrace();
                }
            }//else
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Error: Invalid inputs and\nNumberFormatException,\nlocation capacity,location floor no\nshould be only numbers.\nAll fields are required!").show();
            e.printStackTrace();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: Something went wrong,\nEnter valid inputs,\ncheck inputs again.\nAll fields are required!").show();
            e.printStackTrace();
        }

    }

    private void closeAddHallLabPopUpForm() {
        // just used the txtFacultyName here to close the pop up when the record insertion is successfully done.

        Stage stage = (Stage) txtHallLabName.getScene().getWindow();
        System.out.println("Succeed insertion of a new hall or lab - closing pop up form");
        stage.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Insertion successfully!");
        alert.setContentText("Record Insertion successfully!\nPlease refresh the screen to view changes");
        alert.show();
    }
}
