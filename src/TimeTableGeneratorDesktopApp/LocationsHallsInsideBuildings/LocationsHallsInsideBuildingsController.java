package TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings;


import TimeTableGeneratorDesktopApp.DatabaseHelper.LocationHallLabDatabaseHelper;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsItem.LocationsHallsInsideBuildingsItemController;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsPopUps.AddLocationsHallsPopUpController;
import TimeTableGeneratorDesktopApp.LocationsLabsHalls.Building;
import javafx.collections.ObservableList;
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
    private Label lblFilterBy;

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
        populateRows();
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

        // set UI components are hidden
        btnSearchLocationsHallsInside.setVisible(false);
        lblFilterBy.setVisible(false);
        locationsHallsInsideFilterByComboBox.setVisible(false);
        //locationsHallsInsideMoreComboBox.setVisible(false);

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
                "View Consecutive Sessions in same location"
        );
        locationsHallsInsideMoreComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown

        locationsHallsInsideMoreComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            System.out.println("listener works !");
            System.out.println("oldValue : " + oldValue);
            System.out.println("newValue : " + newValue);

            if (newValue.equals("View Consecutive Sessions in same location")){
                System.out.println("Clicked - Open Pop Up to view consecutive sessions in same location");

                // open up the POP UP
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/ConsecutiveSessionsInSameLocation/ViewConsecutiveSessionsInSameLocation.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();

/*                    ConsecutiveSessionsInSameLocationController consecutiveSessionsInSameLocationController = fxmlLoader.getController();
                    consecutiveSessionsInSameLocationController.getInformationFromLocationsHallsLabsInsideBuildingsUI(this.locationHallLab);*/

                    Stage stage = new Stage();

                  /*  SingleConsecutiveSessionItemController singleConsecutiveSessionItemController = fxmlLoader.getController();
                    singleConsecutiveSessionItemController.showConsecutiveSessionInformation(this.locationHallLab);*/


                    stage.setTitle("View Consecutive Sessions in Same Location");
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(borderPaneLocationsHallsInsideBuildingMain.getScene().getWindow());
                    stage.setResizable(false);
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (Exception e) {
                    System.out.println("Exception - When Opening ConsecutiveSessionsInSameLocation.fxml as a pop up ");
                    e.printStackTrace();
                }
            }


            locationsHallsInsideMoreComboBox.getSelectionModel().select("More options");
        });

        locationsHallsInsideSearchTxtBox.textProperty().addListener((v,oldValue,newValue) -> {
            if(newValue.trim().equals("") || newValue == null){
                populateRows();
            } else {
                long t1  = System.currentTimeMillis();
                populateRowsAccordingToSearchBoxValue(newValue);
                long t2  = System.currentTimeMillis();
                System.out.println("Test sout: time calculation : " + (t2-t1));
            }
        });

    } // initComboBox


    private void populateRows() {
        populateHallLabRows();
    }//


    // ------------------------------------------------------------------------------------------------------------

    private void populateRowsAccordingToSearchBoxValue(String newValue) {

        locationsVBox.getChildren().clear();

        ObservableList<LocationHallLab> locationHallLabList = new LocationHallLabDatabaseHelper().getLocationHallLabListByLocationName(newValue,buildingID);

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
                } catch (Exception e) {
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

        locationsVBox.getChildren().clear();

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
                } catch (Exception e) {
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
