package TimeTableGeneratorDesktopApp.LocationsLabsHalls;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
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
    private VBox locationsVBox = null;


    // =======================================================================================================

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
                "Add suitable room(s) for Lecturer",
                "Add suitable room(s) for Group",
                "Add suitable room(s) for Session",
                "Add consecutive sessions in the same room",
                "Do something new",
                "Blah Blah"
        );

        // prompt text
        locationsMoreComboBox.setPromptText("More"); // I use this drop down, if I have to deal with a new function


        // Populate the rows like a table
        Node[] nodes = new Node[10];

        for (int i = 0;i< nodes.length;i++){
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsLabsHalls/LocationsBuildingItem/LocationsBuildingItem.fxml"));
                locationsVBox.getChildren().add(nodes[i]);
            } catch (IOException e) {
                System.out.println("Error - FacultyItem Loading ======================================");
                e.printStackTrace();
            }
        }

    }

    // =======================================================================================================




    public void setOnActionBtnSearch(MouseEvent mouseEvent) {
        System.out.println("Clicked - Search Button on Locations - Building Screen");
    }

    public void openAddLocationsPopUp(ActionEvent actionEvent) {
        System.out.println("Clicked - Open Add Locations - Buildings Pop Up");
    }
}
