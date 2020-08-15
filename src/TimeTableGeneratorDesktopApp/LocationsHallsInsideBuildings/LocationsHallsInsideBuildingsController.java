package TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LocationsHallsInsideBuildingsController implements Initializable {


    @FXML
    private BorderPane borderPaneLocationsHallsInsideBuildingMain;

    @FXML
    private TextField locationsHallsInsideSearchTxtBox;

    @FXML
    private Button btnSearchLocationsHallsInside;

    @FXML
    private ComboBox<String> locationsHallsInsideFilterByComboBox;

    @FXML
    private Button btnAddLocationsHallsInside;

    @FXML
    private ComboBox<String> locationsHallsInsideMoreComboBox;

    @FXML
    private VBox locationsVBox;


    // ============================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }




    // =============================================================================================

    public void setOnActionBtnSearch(MouseEvent mouseEvent) {
        System.out.println("Clicked - Search Button on Locations - Buildings inside Halls, Labs Screen");
    }


    public void openAddLocationsHallsInsidePopUp(ActionEvent actionEvent) {
        System.out.println("Clicked - Open Add Locations - Buildings inside Halls, Labs Pop Up");


    }



}
