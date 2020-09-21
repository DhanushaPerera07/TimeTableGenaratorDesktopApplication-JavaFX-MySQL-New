package TimeTableGeneratorDesktopApp.ManageSuitableRooms.SingleLocationForTag;

import TimeTableGeneratorDesktopApp.DatabaseHelper.HallsLabsDatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.TagsDatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.Location;
import TimeTableGeneratorDesktopApp.Tags.Tags;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class LocationItemForTagController implements Initializable {


    // variable to hold location object
    Location location;
    int tagID;
    boolean checkBoxCheckedOrNotAccordingToDataBase;

    @FXML
    private VBox locationsHallsInsideItemVBOX;

    @FXML
    private Label txtLocationHallLabName;

    @FXML
    private Label txtLocationHallLabTag;

    @FXML
    private Label txtLocationHallLabCapacity;

    @FXML
    private Label txtLocationHallLabFloor;

    @FXML
    private Label txtLocationHallLabBuilding;

    @FXML
    private Label txtLocationHallLabSpecializedModule;

    @FXML
    private Label txtLocationHallLabCondition;

    @FXML
    private CheckBox checkBoxMarkAsSuitableRoom;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showPreferredLocationInformationForTag(Location location, int tagID) {

        this.location = location;
        this.tagID = tagID;
        boolean suitableRoomTrue;

        System.out.println("test location: " + this.location.toString());

        txtLocationHallLabName.setText(location.getLocationName());
        // txtLocationHallLabTag.setText(Integer.toString(location.getTagID()));

        TagsDatabaseHelper tagsDatabaseHelper = new TagsDatabaseHelper();
        Tags tag = tagsDatabaseHelper.getTagInstanceByTagID(location.getTagID());
        txtLocationHallLabTag.setText(tag.getTag());

        txtLocationHallLabCapacity.setText(Integer.toString(location.getLocationCapacity()));
        txtLocationHallLabFloor.setText(Integer.toString(location.getLocationFloor()));
        txtLocationHallLabBuilding.setText(Integer.toString(location.getBuildingID()));
        txtLocationHallLabSpecializedModule.setText("None");
        txtLocationHallLabCondition.setText(location.getLocationCondition());

        /** if location is already marked as a preferred room for the particular subject,
         * check box is checked otherwise it is not checked..
         */

        //System.out.println("location.getSuitableRoomTrue() = " + location.getSuitableRoomTrue());
        if (location.getSuitableRoomTrue() == 1) {
            suitableRoomTrue = true;
        } else {
            suitableRoomTrue = false;
        }

        checkBoxCheckedOrNotAccordingToDataBase = suitableRoomTrue; // remember checkBox is checked already
        checkBoxMarkAsSuitableRoom.setSelected(suitableRoomTrue);
    }


    HallsLabsDatabaseHelper hallsLabsDatabaseHelper = new HallsLabsDatabaseHelper();


    // have to deal with the changes of the checkbox
    @FXML
    void changeCheckBoxValueOfSuitableRoom(MouseEvent event) {

        String alertTitle = "Confirmation";
        String alertHeaderText;

        System.out.println("checkbox is clicked");

        if ((checkBoxCheckedOrNotAccordingToDataBase == true && checkBoxMarkAsSuitableRoom.isSelected()) && (checkBoxCheckedOrNotAccordingToDataBase == false && checkBoxMarkAsSuitableRoom.isSelected() == false)){
            System.out.println("nothing major happens");
        } else {
            // want to make a change
            // UPDATE preferred_room_for_subject SET status_true = 'Y' WHERE preferred_room_for_subject_id = **** >;

            if (checkBoxCheckedOrNotAccordingToDataBase == true &&  checkBoxMarkAsSuitableRoom.isSelected() == false){
                alertHeaderText = "Do you really want to make " + this.location.getLocationName() + " as a NOT preferable location";
            } else {
                alertHeaderText = "Do you really want to make " + this.location.getLocationName() + " as a preferable location";
            }


            //alertBox
            Alert editSuitableRoomTrueAlert = new Alert(Alert.AlertType.CONFIRMATION);
            editSuitableRoomTrueAlert.setTitle(alertTitle);
            editSuitableRoomTrueAlert.setHeaderText(alertHeaderText);
            editSuitableRoomTrueAlert.setContentText("Do you want to make changes?\nClick confirm button to make changes,\nOtherwise click Cancel");

            ButtonType EditBtn = new ButtonType("CONFIRM");
            ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            editSuitableRoomTrueAlert.getButtonTypes().setAll(EditBtn,CancelBtn);

            Optional<ButtonType> result = editSuitableRoomTrueAlert.showAndWait();


            boolean checkBoxSelectedOrNot;
            if (checkBoxMarkAsSuitableRoom.isSelected()) {
                checkBoxSelectedOrNot = true;
            } else {
                checkBoxSelectedOrNot = false;
            }

            if (result.get() == EditBtn){
                hallsLabsDatabaseHelper.setPreferredRoomsForTag(hallsLabsDatabaseHelper.checkPreferredRoomsForTag(this.tagID, this.location.getLocationID(),checkBoxSelectedOrNot));

            } else {
                checkBoxMarkAsSuitableRoom.setSelected(checkBoxCheckedOrNotAccordingToDataBase);
                System.out.println("Clicked Cancel Button - (edit/update a faculty)");
            }

        }

    }

}
