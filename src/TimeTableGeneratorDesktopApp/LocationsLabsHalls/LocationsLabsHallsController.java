package TimeTableGeneratorDesktopApp.LocationsLabsHalls;

import TimeTableGeneratorDesktopApp.DatabaseHelper.BuildingDatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.FacultyDepartments.Faculty;
import TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyItem.FacultyItemController;
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

public class LocationsLabsHallsController implements Initializable {

    @FXML
    private BorderPane borderPaneLocationsMain;

    @FXML
    private TextField locationsSearchTxtBox;

    @FXML
    private Label lblFilterBy;

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

        initializationOfCombobox();
        populateRows();

    }


    // =======================================================================================================


    private void initializationOfCombobox() {

        // some UI components are hidden
        lblFilterBy.setVisible(false);
        btnSearchLocations.setVisible(false);
        locationsFilterByComboBox.setVisible(false);
        //locationsMoreComboBox.setVisible(false);

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

        locationsSearchTxtBox.textProperty().addListener((v,oldValue,newValue) -> {
            if(newValue.trim().equals("") || newValue == null){
                populateRows();
            } else {
                populateRowsAccordingToSearchBoxValue(newValue);
            }
        });

    }

    private void populateRows() {

        locationsVBox.getChildren().clear();

        ObservableList<Building> buildingList = getBuildingList();

        for (Building building : buildingList){
            // sysout check
            System.out.println("building table rec: " + building.toString());
        }

        /**
         * Dynamically change the rows by getting data from the database
         * LocationsBuildingItem.fxml is used as the UI, it acts as a customized data row
         * I pass the building object to the LocationsBuildingItem.fxml and populate the view
         */
        // Populate the rows like a table
        Node [] nodes = new Node[buildingList.size()];

        if (buildingList.size() != 0) {
            for (int i = 0; i < buildingList.size(); i++) {
                try {
                    //nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItem.fxml"));
                    //facultyVBox.getChildren().addAll(nodes[i]);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsLabsHalls/LocationsBuildingItem/LocationsBuildingItem.fxml"));
                    //Parent newRoot = loader.load();
                    //Scene scene = new Scene(newRoot);
                    nodes[i] = (Node) loader.load();
                    LocationsBuildingItemController locationsBuildingItemController = loader.getController();
                    locationsBuildingItemController.showInformation(buildingList.get(i));
                    //facultyItemController = nodes[i].getController;
                    //nodes[i] = (Node) loader.load();

                    locationsVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - Locations: Buildings Loading ======================================");
                    e.printStackTrace();
                }
            }
        } else {
            // this means that no building is found
            // so we gonna display that no buildings are found
            Node nodeThatSaysNoFacultyFound;
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsLabsHalls/LocationsBuildingItem/LocationsBuildingItemNoContent.fxml"));
                nodeThatSaysNoFacultyFound = (Node) loader.load();
                locationsVBox.getChildren().addAll(nodeThatSaysNoFacultyFound);
            } catch (IOException e) {
                System.out.println("Error - Locations: Buildings Loading ======================================");
                e.printStackTrace();
            }
        }
    }

    BuildingDatabaseHelper buildingDatabaseHelper = new BuildingDatabaseHelper();
    private void populateRowsAccordingToSearchBoxValue(String newValue) {

        locationsVBox.getChildren().clear();

        ObservableList<Building> buildingList = buildingDatabaseHelper.getBuildingListByBuildingName(newValue);

        for (Building building : buildingList){
            // sysout check
            System.out.println("building table rec: " + building.toString());
        }

        /**
         * Dynamically change the rows by getting data from the database
         * LocationsBuildingItem.fxml is used as the UI, it acts as a customized data row
         * I pass the building object to the LocationsBuildingItem.fxml and populate the view
         */
        // Populate the rows like a table
        Node [] nodes = new Node[buildingList.size()];

        if (buildingList.size() != 0) {
            for (int i = 0; i < buildingList.size(); i++) {
                try {
                    //nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItem.fxml"));
                    //facultyVBox.getChildren().addAll(nodes[i]);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsLabsHalls/LocationsBuildingItem/LocationsBuildingItem.fxml"));
                    //Parent newRoot = loader.load();
                    //Scene scene = new Scene(newRoot);
                    nodes[i] = (Node) loader.load();
                    LocationsBuildingItemController locationsBuildingItemController = loader.getController();
                    locationsBuildingItemController.showInformation(buildingList.get(i));
                    //facultyItemController = nodes[i].getController;
                    //nodes[i] = (Node) loader.load();

                    locationsVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - Locations: Buildings Loading ======================================");
                    e.printStackTrace();
                }
            }
        } else {
            // this means that no building is found
            // so we gonna display that no buildings are found
            Node nodeThatSaysNoFacultyFound;
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsLabsHalls/LocationsBuildingItem/LocationsBuildingItemNoContent.fxml"));
                nodeThatSaysNoFacultyFound = (Node) loader.load();
                locationsVBox.getChildren().addAll(nodeThatSaysNoFacultyFound);
            } catch (IOException e) {
                System.out.println("Error - Locations: Buildings Loading ======================================");
                e.printStackTrace();
            }
        }
    }



    public void setOnActionBtnSearch(MouseEvent mouseEvent) {
        System.out.println("Clicked - Search Button on Locations - Building Screen");
    }

    public void openAddLocationsPopUp(ActionEvent actionEvent) {
        System.out.println("Clicked - Open Add Locations - Buildings Pop Up");

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsLabsHalls/LocationsBuildingPopUps/addLocationsBuildingPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add a building");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(borderPaneLocationsMain.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening addLocationsBuildingPopUp.fxml as a pop up ==========================");
            e.printStackTrace();
        }
    }



    // ===================== DATABASE PART - STARTS HERE =============================================================================

    /** get the database connection here
     */
    DatabaseHelper databaseHelper = new DatabaseHelper();



    /**
     * this method is to get all the faculties in the faculty table...
     * returns departmentList;
     * */
    public ObservableList<Building> getBuildingList() {

        locationsVBox.getChildren().clear();

        ObservableList<Building> buildingList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();

        // if the filter by combo box value is set as ALL, get all the departments
        String query;
        if (locationsFilterByComboBox.equals("Select ALL")) {
            query = "SELECT * FROM building WHERE building_delete_status = 'N' ORDER BY building_name";
        } else {
            query = "SELECT * FROM building WHERE building_delete_status = 'N' ORDER BY building_name";
            // query = "SELECT * from faculty WHERE " +filterType+ " = '" +filterValue+ "'";
        }

//        String query = "SELECT * FROM department";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Building building;
            while (rs.next()) {
                building = new Building(
                        rs.getInt("building_id"),
                        rs.getString("building_name"),
                        rs.getInt("building_no_of_floors"),
                        rs.getInt("building_capacity"),
                        rs.getString("building_center"),
                        rs.getString("building_condition"),
                        rs.getString("building_specialized_for"),
                        rs.getInt("building_no_of_lecture_halls"),
                        rs.getInt("building_no_of_tutorial_halls"),
                        rs.getInt("building_no_of_labs"),
                        rs.getInt("faculty_faculty_id")
                );
                buildingList.add(building);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return buildingList;
    }



}
