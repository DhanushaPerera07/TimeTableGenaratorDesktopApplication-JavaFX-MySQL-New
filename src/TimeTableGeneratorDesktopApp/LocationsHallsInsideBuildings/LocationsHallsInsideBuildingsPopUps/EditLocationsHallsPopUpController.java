package TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsPopUps;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.TagsDatabaseHelper;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import TimeTableGeneratorDesktopApp.Tags.Tags;
import javafx.collections.ObservableList;
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

    // holder building ID
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
    private ComboBox<String> conditionHallLabComboBox;

    @FXML
    private ComboBox<String> tagHallLabComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCombobox();
    }

    private void initializeCombobox() {

        // get tags details from the database and make a list,
        TagsDatabaseHelper tagsDatabaseHelper = new TagsDatabaseHelper();
        ObservableList<Tags> tagList = tagsDatabaseHelper.getTagList();

        for (Tags tag : tagList){
            // sysout check
            tagHallLabComboBox.getItems().add(tag.getTag()); // tag name is displayed in the combo box

        }

        conditionHallLabComboBox.getItems().addAll(
                "OK",
                "Cancel"
        );
        //conditionHallLabComboBox.setPromptText("Select Condition");

        //etExistingComboboxValues();

    }


    /**
     * this will get the buildingID from the
     * LocationsHallsInsideBuildingsItem.fxml (LocationsHallsInsideBuildingsItemController)
     * @param
     */
    public void getNecessaryID(LocationHallLab locationHallLab, String buildingName) {
        this.buildingID = locationHallLab.getBuildingID();
        this.tagID = locationHallLab.getTagID();
        this.condition = locationHallLab.getLocationCondition();

        // get tags details from the database and make a list,
        TagsDatabaseHelper tagsDatabaseHelper = new TagsDatabaseHelper();

        // get tag instance by passing tagID // tag name is used here
        tagHallLabComboBox.getSelectionModel().select(tagsDatabaseHelper.getTagInstanceByTagID(this.tagID).getTag());
        conditionHallLabComboBox.getSelectionModel().select(this.condition);
        textTheBuilding.setText(buildingName);
        txtHallLabName.setText(locationHallLab.getLocationName());
        txtHallLabCapacity.setText(Integer.toString(locationHallLab.getLocationCapacity()));
        txtHallLabFloor.setText(Integer.toString(locationHallLab.getLocationFloor()));
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
            //System.out.println("Hall/lab is edited/updated successfully");
        } else {
            System.out.println("Clicked Cancel Button - (edit/update a hall/lab)");
        }
    }



    /**
     * This method is used to insert a new faculty
     */
    TagsDatabaseHelper tagsDatabaseHelper = new TagsDatabaseHelper();
    public void editUpdateRecord(int locationID){

        // local variables created to get the user input from the pop up form

        // get user input

        try {
            int location_id = locationID; // this is the id of the record which is to be updated
            String location_name = txtHallLabName.getText();

            int location_capacity = Integer.parseInt(txtHallLabCapacity.getText());
            int location_floor = Integer.parseInt(txtHallLabFloor.getText());

            String location_condition = conditionHallLabComboBox.getValue();
            int building_building_id = this.buildingID;   // facultyHeadComboBox.getValue();
            //int tag_tag_id = this.tagID; // facultyHeadComboBox.getValue();
            int tag_tag_id = tagsDatabaseHelper.getTagInstanceByTagName(tagHallLabComboBox.getValue()).getTagID();

            // String faculty_delete_status = "N"; // this is not used in here

            if (location_name.equals("") || location_condition.equals("") || tag_tag_id == 0 || building_building_id == 0 || location_id == 0) {
                new Alert(Alert.AlertType.ERROR, "Error: Invalid Fields found.\nEmpty / Not selected field found.\nAll fields are required!").show();
            } else  {
                try {
                    // update query
                    String query = "UPDATE `location` SET location_name = '" +location_name+ "', location_capacity = " +location_capacity+
                            ", location_floor = " +location_floor+ ", location_condition = '" +location_condition+ "', building_building_id = "
                            +building_building_id+
                            ",tag_tag_id = "+tag_tag_id+" WHERE location_id = " +location_id+ "";


                    // execute the insert query
                    databaseHelper.executeQuery(query);
                    /*new Alert(Alert.AlertType.INFORMATION,"Update successful !").show();*/
                    closeEditFacultyPopUpForm();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR,"Error: Something went wrong when updating data").show();
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

    private void closeEditFacultyPopUpForm() {
        // just used the txtHallLabName here to close the pop up when the record editing/update is successfully done.

        Stage stage = (Stage) txtHallLabName.getScene().getWindow();
        System.out.println("Succeed edit/update of the Hall/Lab - closing pop up form");
        stage.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Update successful!");
        alert.setContentText("Record Update successful!\nPlease refresh the screen to view changes");
        alert.show();
    }




    // ===================== DATABASE PART - STARTS HERE =============================================================================

    DatabaseHelper databaseHelper = new DatabaseHelper();


}
