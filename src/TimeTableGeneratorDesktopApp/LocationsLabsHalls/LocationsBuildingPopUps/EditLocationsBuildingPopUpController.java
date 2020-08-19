package TimeTableGeneratorDesktopApp.LocationsLabsHalls.LocationsBuildingPopUps;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditLocationsBuildingPopUpController implements Initializable {

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

    }


    // ======================================================================


    public void ActionEventEditBuildingPopUp(ActionEvent actionEvent) {

        // pop up - edit a building action event on ADD BUTTON
        System.out.println("clicked pop up - edit a building action event on ADD BUTTON");
    }
}
