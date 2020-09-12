package TimeTableGeneratorDesktopApp.ManageSuitableRooms.SingleLocationItem;

import TimeTableGeneratorDesktopApp.ManageSuitableRooms.Location;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LocationItemController implements Initializable {


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

    public void showPreferredLocationInformationForSubject(Location location, int subjectID){
        txtLocationHallLabName.setText(location.getLocationName());
        txtLocationHallLabTag.setText(Integer.toString(location.getTagID()));
        txtLocationHallLabCapacity.setText(Integer.toString(location.getLocationCapacity()));
        txtLocationHallLabFloor.setText(Integer.toString(location.getLocationFloor()));
        txtLocationHallLabBuilding.setText(Integer.toString(location.getBuildingID()));
        txtLocationHallLabSpecializedModule.setText("None");
        txtLocationHallLabCondition.setText(location.getLocationCondition());

        /**if location is already marked as a preferred room for the particular subject,
         * check box is checked otherwise it is not checked..
         */
        checkBoxMarkAsSuitableRoom.setSelected(location.isSuitableRoomTrue());
    }


    // have to deal with the changes of the checkbox



}
