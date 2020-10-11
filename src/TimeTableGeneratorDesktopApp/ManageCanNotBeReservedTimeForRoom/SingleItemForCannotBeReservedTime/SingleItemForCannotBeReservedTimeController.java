package TimeTableGeneratorDesktopApp.ManageCanNotBeReservedTimeForRoom.SingleItemForCannotBeReservedTime;

import TimeTableGeneratorDesktopApp.DatabaseHelper.CannotBeReservedTimeForLocationDatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageCanNotBeReservedTimeForRoom.ClassedUsed.CannotBeReservedTimeForLocation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SingleItemForCannotBeReservedTimeController implements Initializable {


    public CannotBeReservedTimeForLocation cannotBeReservedTimeForLocation;
    public Boolean checkBoxSelectedTrueOrNotDbValue;

    @FXML
    private VBox locationsHallsInsideItemVBOX;

    @FXML
    private Label txtDay;

    @FXML
    private CheckBox checkBoxMarkAsCannotBeReserved;

    @FXML
    private Label txtTimePeriod;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    // ------------------------------------------------------------------------------------------


    public void showInformationOfTimeSlots(CannotBeReservedTimeForLocation cannotBeReservedTimeForLocation) {
        this.cannotBeReservedTimeForLocation = cannotBeReservedTimeForLocation;

        txtDay.setText(this.cannotBeReservedTimeForLocation.getDayName());
        txtTimePeriod.setText("(" + this.cannotBeReservedTimeForLocation.getTimeSlot().getValue_t() +")");

        checkBoxSelectedTrueOrNotDbValue = this.cannotBeReservedTimeForLocation.getStatus_true();
        checkBoxMarkAsCannotBeReserved.setSelected(this.cannotBeReservedTimeForLocation.getStatus_true());
    }


    // ------------------------------------------------------------------------------------------

    CannotBeReservedTimeForLocationDatabaseHelper cannotBeReservedTimeForLocationDatabaseHelper = new CannotBeReservedTimeForLocationDatabaseHelper();

    @FXML
    void changeCheckBoxValueOfCannotBeReserved(MouseEvent event) {

        String alertTitle = "Confirmation";
        String alertHeaderText;

        System.out.println("checkbox is clicked");

        if ((checkBoxSelectedTrueOrNotDbValue == true && checkBoxMarkAsCannotBeReserved.isSelected()) && (checkBoxSelectedTrueOrNotDbValue == false && checkBoxMarkAsCannotBeReserved.isSelected() == false)) {
            System.out.println("nothing major happens");
        } else {
            // want to make a change
            // UPDATE preferred_room_for_subject SET status_true = 'Y' WHERE preferred_room_for_subject_id = **** >;

            if (checkBoxSelectedTrueOrNotDbValue == true && checkBoxMarkAsCannotBeReserved.isSelected() == false) {
                alertHeaderText = "Do you really want to make " + this.cannotBeReservedTimeForLocation.getDayName() + "," + this.cannotBeReservedTimeForLocation.getTimeSlot().getValue_t() + " timeslot\na reservable timeslot for location (" +
                        "" + this.cannotBeReservedTimeForLocation.getLocationHallLab().getLocationName() + ")";
            } else {
                alertHeaderText = "Do you really want to make\nDay: " + this.cannotBeReservedTimeForLocation.getDayName() + ",\nTimeSlot: " + this.cannotBeReservedTimeForLocation.getTimeSlot().getValue_t() + "\na cannot be reservable timeslot for location (" +
                        "" + this.cannotBeReservedTimeForLocation.getLocationHallLab().getLocationName() + ")";
            }


            //alertBox
            Alert editSuitableRoomTrueAlert = new Alert(Alert.AlertType.CONFIRMATION);
            editSuitableRoomTrueAlert.setTitle(alertTitle);
            editSuitableRoomTrueAlert.setHeaderText(alertHeaderText);
            editSuitableRoomTrueAlert.setContentText("Do you want to make changes?\nClick confirm button to make changes,\nOtherwise click Cancel");

            ButtonType EditBtn = new ButtonType("CONFIRM");
            ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            editSuitableRoomTrueAlert.getButtonTypes().setAll(EditBtn, CancelBtn);

            Optional<ButtonType> result = editSuitableRoomTrueAlert.showAndWait();


            boolean checkBoxSelectedOrNot;
            if (checkBoxMarkAsCannotBeReserved.isSelected()) {
                checkBoxSelectedOrNot = true;
            } else {
                checkBoxSelectedOrNot = false;
            }

            if (result.get() == EditBtn) {
                cannotBeReservedTimeForLocationDatabaseHelper.setCannotBeReservedTimeForLocationTable(cannotBeReservedTimeForLocationDatabaseHelper.checkCannotBeReservedTimeForLocationTable(this.cannotBeReservedTimeForLocation, checkBoxSelectedOrNot));

            } else {
                //checkBoxMarkAsSuitableRoom.setSelected(cannotBeReservedTimeForLocationDatabaseHelper.checkCannotBeReservedTimeForLocationTable(this.cannotBeReservedTimeForLocation,));
                System.out.println("Clicked Cancel Button - (edit/update setCannotBeReservedTimeForLocationTable)");
                checkBoxMarkAsCannotBeReserved.setSelected(checkBoxSelectedTrueOrNotDbValue);
            }

        }

    }

    // ------------------------------------------------------------------------------------------
}
