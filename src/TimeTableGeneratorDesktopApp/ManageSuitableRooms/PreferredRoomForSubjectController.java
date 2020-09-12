package TimeTableGeneratorDesktopApp.ManageSuitableRooms;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsItem.LocationsHallsInsideBuildingsItemController;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SingleLocationItem.LocationItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PreferredRoomForSubjectController implements Initializable {

    // variable declaration to keep some useful data
    int subject_id = 1;
    int tag_id = 1;

    @FXML
    private BorderPane borderPaneLocationsHallsInsideBuildingMain;

    @FXML
    private Label txtHeaderLabsHalls;

    @FXML
    private Label txtHeaderBuildingName;

    @FXML
    private TextField locationsHallsInsideSearchTxtBox;

    @FXML
    private Button btnSearchLocationsHallsInside;

    @FXML
    private ComboBox<String> locationsHallsInsideFilterByComboBox;

    @FXML
    private ComboBox<String> locationsHallsInsideMoreComboBox;

    @FXML
    private VBox locationsVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    void setOnActionBtnSearch(MouseEvent event) {

    }


    /** This method is used to get subject ID from menura's part
     */
    public void getInformationFromSubjectUI(){
        System.out.println("Student id + " + this.subject_id);

        populateLocationRows();
    }


    // ============================================ DATABASE PART ===================================================================================

    // database connection setup
    DatabaseHelper databaseHelper = new DatabaseHelper();


    /**
     * populate halls and labs as rows,
     * then, user can select them or not
     * which means mark that room as a preferred room or not
     */
    public void populateLocationRows(){
        ObservableList<Location> locationList = getLocationList();

        for (Location location : locationList){
            // sysout check
            System.out.println("Location preferred for subject rec: " + location.toString());
        }

        /**
         * Dynamically change the rows by getting data from the database
         * locationItem.fxml is used as the UI, it acts as a customized data row
         * I pass the building object to the locationItem.fxml and populate the view
         */
        // Populate the rows like a table
        Node[] nodes = new Node[locationList.size()];

        if (locationList.size() != 0) {
            for (int i = 0; i < locationList.size(); i++) {
                try {
                    //nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItem.fxml"));
                    //facultyVBox.getChildren().addAll(nodes[i]);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/SingleLocationItem/locationItem.fxml"));
                    //Parent newRoot = loader.load();
                    //Scene scene = new Scene(newRoot);
                    nodes[i] = (Node) loader.load();
                    LocationItemController locationItemController = loader.getController();
                    locationItemController.showPreferredLocationInformationForSubject(locationList.get(i),this.subject_id); // subject id should be got from Menura's part
                    //facultyItemController = nodes[i].getController;
                    //nodes[i] = (Node) loader.load();

                    locationsVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - preferred room for subject Loading ======================================");
                    e.printStackTrace();
                }
            }
        } else {
            // this means that no halls or labs are found
            // so we gonna display that no halls or labs are found
            Node nodeThatSaysNoFacultyFound;
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/SingleLocationItem/locationItemNoContent.fxml"));
                nodeThatSaysNoFacultyFound = (Node) loader.load();
                locationsVBox.getChildren().addAll(nodeThatSaysNoFacultyFound);
            } catch (IOException e) {
                System.out.println("Error - preferred room for subject Loading ======================================");
                e.printStackTrace();
            }
        }
    } //

    /**
     * this method is to get all the Locations in the location table but...
     * here, location table and preferred_room_for_subject table are being checked
     * returns locationList;
     * */
    public ObservableList<Location> getLocationList() {

        ObservableList<Location> locationList = FXCollections.observableArrayList();
        Connection conn =  databaseHelper.getConnection();

        // if the filter by combo box value is set as ALL, get all the departments
        String query;
        //query = "SELECT * FROM location WHERE building_building_id = "+this.buildingID+" AND location_delete_status = 'N' ORDER BY location_name";
        //query = "SELECT * FROM location WHERE location_delete_status = 'N' ORDER BY location_name";

        //query = "SELECT l.*, IF(l.location_id = ps.location_location_id, TRUE, FALSE) AS suitableRoomTrue FROM location AS l, preferred_room_for_subject AS ps WHERE ps.subject_subject_id = "+this.subject_id+"";
        query = "SELECT l.*, IF(l.location_id = ps.location_location_id, TRUE, FALSE) AS suitableRoomTrue FROM location AS l, preferred_room_for_subject AS ps WHERE ps.subject_subject_id = "+this.subject_id+" AND ps.tag_tag_id = "+this.tag_id+"";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Location location;
            while (rs.next()) {
                location = new Location(
                        rs.getInt("location_id"),
                        rs.getString("location_name"),
                        rs.getInt("location_capacity"),
                        rs.getInt("location_floor"),
                        rs.getString("location_condition"),
                        rs.getInt("building_building_id"),
                        rs.getInt("tag_tag_id"),
                        rs.getInt("subject_subject_id"),
                        rs.getBoolean("suitableRoomTrue")
                );
                locationList.add(location);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return locationList;
    }


}
