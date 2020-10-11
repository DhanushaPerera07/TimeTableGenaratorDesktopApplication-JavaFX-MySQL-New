package TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.SingleConsecutiveSessionItem;

import TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.ConsecutiveSessionViewModel;
import TimeTableGeneratorDesktopApp.DatabaseHelper.ConsecutiveSessionInSameLocationDatabaseHelper;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import TimeTableGeneratorDesktopApp.Sessions.Sessions;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Optional;

public class SingleConsecutiveSessionItemController {

    // variables need for hold some values
    public LocationHallLab locationHallLab;
    public ConsecutiveSessionViewModel consecutiveSessionViewModel;

    // variable to hold location object
    public int consecutiveSessionID;
    boolean checkBoxCheckedOrNotAccordingToDataBase;

    @FXML
    private VBox locationsHallsInsideItemVBOX;

    @FXML
    private CheckBox checkBox;

    @FXML
    private Text txtSubject;

    @FXML
    private Text txtTag;

    @FXML
    private Text txtStudentGroup;

    @FXML
    private Text txtNoOfStudents;

    @FXML
    private Text txtSubject2;

    @FXML
    private Text txtTag2;

    @FXML
    private Text txtStudentGroup2;

    @FXML
    private Text txtNoOfStudents2;

 /*   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }*/

    public void showConsecutiveSessionInformation(ConsecutiveSessionViewModel consecutiveSessionViewModel, LocationHallLab locationHallLab) {


        this.locationHallLab = locationHallLab;
        this.consecutiveSessionViewModel = consecutiveSessionViewModel;
        // this.consecutiveSessionID = ;

        Sessions sessions1 = this.consecutiveSessionViewModel.getSession1();
        Sessions sessions2 = this.consecutiveSessionViewModel.getSession2();

        // session 1
        txtSubject.setText(sessions1.getSessionModule());
        txtTag.setText(sessions1.getSessionTag());
        txtStudentGroup.setText(sessions1.getSessionGroupID());
        txtNoOfStudents.setText(Integer.toString(sessions1.getSessionStudentCount()));

        // session 2
        txtSubject2.setText(sessions2.getSessionModule());
        txtTag2.setText(sessions2.getSessionTag());
        txtStudentGroup2.setText(sessions2.getSessionGroupID());
        txtNoOfStudents2.setText(Integer.toString(sessions2.getSessionStudentCount()));

        if (consecutiveSessionViewModel.getSuitableRoomTrue() == 1) {
            checkBoxCheckedOrNotAccordingToDataBase = true; // remember checkBox is checked already
            checkBox.setSelected(true);
        } else {
            checkBoxCheckedOrNotAccordingToDataBase = false; // remember checkBox is checked already
            checkBox.setSelected(false);
        }



    } //


    ConsecutiveSessionInSameLocationDatabaseHelper consecutiveSessionInSameLocationDatabaseHelper = new ConsecutiveSessionInSameLocationDatabaseHelper();

    @FXML
    void checkBoxSetTheLocation(MouseEvent event) {

        String alertTitle = "Confirmation";
        String alertHeaderText;

        System.out.println("checkbox is clicked");

        if ((checkBoxCheckedOrNotAccordingToDataBase == true && checkBox.isSelected()) && (checkBoxCheckedOrNotAccordingToDataBase == false && checkBox.isSelected() == false)){
            System.out.println("nothing major happens");
        } else {
            // want to make a change
            // UPDATE preferred_room_for_subject SET status_true = 'Y' WHERE preferred_room_for_subject_id = **** >;

            if (checkBoxCheckedOrNotAccordingToDataBase == true &&  checkBox.isSelected() == false){
                alertHeaderText = "Do you really want to make " + this.locationHallLab.getLocationName() + " as a NOT preferable location";
            } else {
                alertHeaderText = "Do you really want to make " + this.locationHallLab.getLocationName() + " as a preferable location";
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
            if (checkBox.isSelected()) {
                checkBoxSelectedOrNot = true;
            } else {
                checkBoxSelectedOrNot = false;
            }

            if (result.get() == EditBtn){
                consecutiveSessionInSameLocationDatabaseHelper.setConsecutiveSessionsInTheSameLocation(consecutiveSessionInSameLocationDatabaseHelper.checkConsecutiveSessionsInTheSameLocation(this.consecutiveSessionViewModel.getId(), this.locationHallLab.getLocationID(),checkBoxSelectedOrNot));
                //hallsLabsDatabaseHelper.setPreferredRoomsForTag(hallsLabsDatabaseHelper.checkPreferredRoomsForTag(this.tagID, this.location.getLocationID(),checkBoxSelectedOrNot));

            } else {
                checkBox.setSelected(checkBoxCheckedOrNotAccordingToDataBase);
                System.out.println("Clicked Cancel Button - consecutive_session_in_same_room");
            }

        }
    }
}
