package TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.LocationHallLabDatabaseHelper;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsItem.LocationsHallsInsideBuildingsItemController;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsPopUps.AddLocationsHallsPopUpController;
import TimeTableGeneratorDesktopApp.LocationsLabsHalls.Building;
import TimeTableGeneratorDesktopApp.LocationsLabsHalls.LocationsBuildingItem.LocationsBuildingItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LocationsHallsInsideBuildingsController implements Initializable {


    // holds the building obj;
    public Building buildingInstance;
    public int buildingID,BuildingIdDB;
    public String buildingName;

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

    @FXML
    private Label txtHeaderLabsHalls;

    @FXML
    private Label txtHeaderBuildingName;




    // ============================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // combobox(s) are being initialized
        initializationCombobox();

        /*
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

         */

    }
    // =============================================================================================


    private void initializationCombobox() {
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

    }


    public void setOnActionBtnSearch(MouseEvent mouseEvent) {
        System.out.println("Clicked - Search Button on Locations - Buildings inside Halls, Labs Screen");
    }


    public void openAddLocationsHallsInsidePopUp(MouseEvent mouseEvent) {
        System.out.println("Clicked - Open Add Locations - Buildings inside Halls, Labs Pop Up");


        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsHallsInsideBuildings/LocationsHallsInsideBuildingsPopUps/addLocationsHallsPopUp.fxml"));

            Parent root2 = (Parent) fxmlLoader.load();

            AddLocationsHallsPopUpController addLocationsHallsPopUpController = fxmlLoader.getController();
            addLocationsHallsPopUpController.getBuildingDetails(this.buildingID,this.buildingName);

            Stage stage = new Stage();

            stage.setTitle("Add Hall/Lab");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(borderPaneLocationsHallsInsideBuildingMain.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root2));
            stage.show();



        }catch (Exception e){
            System.out.println("Exception / Error - When Opening addLocationsHallsPopUp.fxml as a pop up ==========================");
            e.printStackTrace();
        }


    }


    /**
     * Get building instance from the building UI,
     * then we can populate the relevant locationHallLabs as rows
     */
    public void showInformation(int buildingID,String buildingName) {
        //this.buildingInstance = building;
        //txtHeaderBuildingName.setText(building.getBuildingName());
        this.buildingID = buildingID;
        this.buildingName = buildingName;
        txtHeaderBuildingName.setText(buildingName);

        populateHallLabRows();

    }


    public void populateHallLabRows(){
        ObservableList<LocationHallLab> locationHallLabList = new LocationHallLabDatabaseHelper().getLocationHallLabList(buildingID);

        for (LocationHallLab locationHallLab : locationHallLabList){
            // sysout check
            System.out.println("Hall or Lab rec: " + locationHallLab.toString());
        }

        /**
         * Dynamically change the rows by getting data from the database
         * LocationsBuildingItem.fxml is used as the UI, it acts as a customized data row
         * I pass the building object to the LocationsBuildingItem.fxml and populate the view
         */
        // Populate the rows like a table
        Node [] nodes = new Node[locationHallLabList.size()];

        if (locationHallLabList.size() != 0) {
            for (int i = 0; i < locationHallLabList.size(); i++) {
                try {
                    //nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItem.fxml"));
                    //facultyVBox.getChildren().addAll(nodes[i]);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsHallsInsideBuildings/LocationsHallsInsideBuildingsItem/LocationsHallsInsideBuildingsItem.fxml"));
                    //Parent newRoot = loader.load();
                    //Scene scene = new Scene(newRoot);
                    nodes[i] = (Node) loader.load();
                    LocationsHallsInsideBuildingsItemController locationsHallsInsideBuildingsItemController = loader.getController();
                    locationsHallsInsideBuildingsItemController.showInformation(locationHallLabList.get(i),this.buildingName);
                    //facultyItemController = nodes[i].getController;
                    //nodes[i] = (Node) loader.load();

                    locationsVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - Hall/Labs inside a building Loading ======================================");
                    e.printStackTrace();
                }
            }
        } else {
            // this means that no halls or labs are found
            // so we gonna display that no halls or labs are found
            Node nodeThatSaysNoFacultyFound;
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsHallsInsideBuildings/LocationsHallsInsideBuildingsItem/LocationsHallsInsideBuildingsItemNoContent.fxml"));
                nodeThatSaysNoFacultyFound = (Node) loader.load();
                locationsVBox.getChildren().addAll(nodeThatSaysNoFacultyFound);
            } catch (IOException e) {
                System.out.println("Error - Hall/Labs inside a building Loading ======================================");
                e.printStackTrace();
            }
        }
    }






    // ===================== DATABASE PART - STARTS HERE =============================================================================





}
