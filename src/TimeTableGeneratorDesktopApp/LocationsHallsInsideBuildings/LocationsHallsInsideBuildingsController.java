package TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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

        // filter by combobox
        locationsHallsInsideFilterByComboBox.getItems().addAll(
                "Select ALL",
                "Lecture Halls Only",
                "Tutorial Halls Only",
                "PC - Labs Only",
                "Blah Blah"
        );
        locationsHallsInsideFilterByComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown



        // filter by combobox
        locationsHallsInsideMoreComboBox.getItems().addAll(
                "More options",
                "More Options If Any",
                "More Options If Any",
                "Blah Blah"
        );
        locationsHallsInsideMoreComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown



        // Populate the rows like a table
        Node[] nodes = new Node[10];

        for (int i = 0;i< nodes.length;i++){
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsHallsInsideBuildings/LocationsHallsInsideBuildingsItem/LocationsHallsInsideBuildingsItem.fxml"));
                locationsVBox.getChildren().add(nodes[i]);
            } catch (IOException e) {
                System.out.println("Error - Hall/Labs inside a building Loading ======================================");
                e.printStackTrace();
            }
        }

    }




    // =============================================================================================

    public void setOnActionBtnSearch(MouseEvent mouseEvent) {
        System.out.println("Clicked - Search Button on Locations - Buildings inside Halls, Labs Screen");
    }


    public void openAddLocationsHallsInsidePopUp(ActionEvent actionEvent) {
        System.out.println("Clicked - Open Add Locations - Buildings inside Halls, Labs Pop Up");


        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsHallsInsideBuildings/LocationsHallsInsideBuildingsPopUps/addLocationsHallsPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add Hall/Lab");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(borderPaneLocationsHallsInsideBuildingMain.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening addLocationsHallsPopUp.fxml as a pop up ==========================");
            e.printStackTrace();
        }


    }



}
