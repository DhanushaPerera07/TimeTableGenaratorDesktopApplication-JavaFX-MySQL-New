package TimeTableGeneratorDesktopApp.LocationsLabsHalls;

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

public class LocationsLabsHallsController implements Initializable {

    @FXML
    private BorderPane borderPaneLocationsMain;

    @FXML
    private TextField locationsSearchTxtBox;

    @FXML
    private Button btnSearchLocations;

    @FXML
    private ComboBox<String> locationsFilterByComboBox;

    @FXML
    private Button btnAddLocation;

    @FXML
    private ComboBox<String> locationsMoreComboBox;

    @FXML
    private VBox locationsVBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // filter by combobox
        locationsFilterByComboBox.getItems().addAll(
                "Select ALL",
                "FOC",
                "Blah Blah"
        );
        locationsFilterByComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown

        // More combobox
        locationsMoreComboBox.getItems().addAll(
                "Print",
                "Do something new",
                "Blah Blah"
        );

        // prompt text
        locationsMoreComboBox.setPromptText("More"); // I use this drop down, if I have to deal with a new function

    }

    public void setOnActionBtnSearch(MouseEvent mouseEvent) {
        System.out.println("Clicked - Search Button on Locations - Building Screen");
    }

    public void openAddLocationsPopUp(ActionEvent actionEvent) {
        System.out.println("Clicked - Open Add Locations - Buildings Pop Up");
    }
}
