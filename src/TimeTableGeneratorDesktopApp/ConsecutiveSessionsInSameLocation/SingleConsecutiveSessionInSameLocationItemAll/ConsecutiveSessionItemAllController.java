package TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.SingleConsecutiveSessionInSameLocationItemAll;

import TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.ConsecutiveSessionViewAll;
import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.TagsDatabaseHelper;
import TimeTableGeneratorDesktopApp.Sessions.Sessions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ConsecutiveSessionItemAllController implements Initializable {

    public ConsecutiveSessionViewAll consecutiveSessionViewAll;


    @FXML
    private VBox locationsHallsInsideItemVBOX;

    @FXML
    private Label txtLocationName;

    @FXML
    private Label txtLocationType;

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

    @FXML
    private Button btnUnsetConsecutiveSessionFromLocation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showConsecutiveSessionAllInformation(ConsecutiveSessionViewAll consecutiveSessionViewAll) {
        this.consecutiveSessionViewAll = consecutiveSessionViewAll;

        TagsDatabaseHelper tagsDatabaseHelper= new TagsDatabaseHelper();

        txtLocationName.setText(this.consecutiveSessionViewAll.getLocationHallLab().getLocationName());
        txtLocationType.setText(tagsDatabaseHelper.getTagInstanceByTagID(this.consecutiveSessionViewAll.getLocationHallLab().getTagID()).getTag());


        Sessions session1 = this.consecutiveSessionViewAll.getSessions1();
        Sessions session2 = this.consecutiveSessionViewAll.getSessions2();

        // session 1
        txtSubject.setText(session1.getSessionModule());
        txtTag.setText(session1.getSessionTag());
        txtStudentGroup.setText(session1.getSessionGroupID());
        txtNoOfStudents.setText(Integer.toString(session1.getSessionStudentCount()));

        // session 2
        txtSubject2.setText(session2.getSessionModule());
        txtTag2.setText(session2.getSessionTag());
        txtStudentGroup2.setText(session2.getSessionGroupID());
        txtNoOfStudents2.setText(Integer.toString(session2.getSessionStudentCount()));

    }


    // ----------------------------------------------------------------------------------------------------------------

    @FXML
    void onActionUnsetConsecutiveSessionFromLocation(ActionEvent event) {

        DatabaseHelper databaseHelper = new DatabaseHelper();

        //alertBox
        Alert editSuitableRoomTrueAlert = new Alert(Alert.AlertType.CONFIRMATION);
        editSuitableRoomTrueAlert.setTitle("Confirmation");
        editSuitableRoomTrueAlert.setHeaderText("Are you sure you want to \nUnset location: " + this.consecutiveSessionViewAll.getLocationHallLab().getLocationName() +"\nfrom this consecutive session.");
        editSuitableRoomTrueAlert.setContentText("Do you want to make changes?\nClick confirm button to make changes,\nOtherwise click Cancel");

        ButtonType EditBtn = new ButtonType("CONFIRM");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        editSuitableRoomTrueAlert.getButtonTypes().setAll(EditBtn,CancelBtn);

        Optional<ButtonType> result = editSuitableRoomTrueAlert.showAndWait();

        if (result.get() == EditBtn){
            String query;
            try {
                query = "UPDATE `consecutive_session_in_same_room` SET status_true = 'N' WHERE consecutive_session_in_same_room_id = " + this.consecutiveSessionViewAll.getConsecutive_session_in_same_room_id()+";";

                // execute the update query
                databaseHelper.executeQuery(query);

                // close popup
                Stage stage = (Stage) locationsHallsInsideItemVBOX.getScene().getWindow();
                System.out.println("Succeed update of the consecutive_session_in_same_room table - closing pop up");
                stage.close();

            } catch (Exception ex) {
                System.out.println("Error: updating consecutive_session_in_same_room table");
                ex.printStackTrace();
            }
        } else {
            System.out.println("Clicked - Cancel button of unset consecutive_session_in_same_room");
        }


    }



    // -----------------------------------------------------------------------------------------

}
