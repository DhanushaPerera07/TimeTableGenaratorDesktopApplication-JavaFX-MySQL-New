package TimeTableGeneratorDesktopApp.ManageSuitableRooms;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.TagsDatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.Location;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.SuitableLocationForTag;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SingleLocationForTag.LocationItemForTagController;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SingleLocationItem.LocationItemController;
import TimeTableGeneratorDesktopApp.Tags.Tags;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SuitableRoomForTagController implements Initializable {

    // variables used to hold necessary values
    static Tags tag;


    @FXML
    private BorderPane borderPaneLocationsHallsInsideBuildingMain;

    @FXML
    private Label txtHeaderLabsHalls;

    @FXML
    private Label txtTagName;

    @FXML
    private TextField locationsHallsInsideSearchTxtBox;

    @FXML
    private Button btnSearchLocationsHallsInside;

    @FXML
    private ComboBox<String> preferredSubjectFilterByComboBox;

    @FXML
    private VBox locationsVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeHeader(); // set tag name in the UI
        populateLocationRows();
    }

    private void initializeHeader() {
        txtTagName.setText(this.tag.getTag());
    }


    public void getInformationFromShowAllTagUI(Tags tag) {
        this.tag = tag;
    }


    /**
     * populate halls and labs as rows,
     * then, user can select them or not
     * which means mark that room as a preferred room or not
     */
    public void populateLocationRows() {

        locationsVBox.getChildren().clear();

        ObservableList<Location> locationList = getLocationList();
        SuitableLocationForTag suitableLocationForTag = new SuitableLocationForTag();


        for (Location location : locationList){
            // sysout check
            System.out.println("Suitable Location for tag rec: " + location.toString());

            suitableLocationForTag = checkSuitableRoomForTagTableData(location.getLocationID(),this.tag.getTagID());

            if (suitableLocationForTag != null){
                location.setSuitableRoomTrue(1);
            } else {
                location.setSuitableRoomTrue(0);
            }

        }


        /**
         * Dynamically change the rows by getting data from the database
         * locationItemForLecturer.fxml is used as the UI, it acts as a customized data row
         * I pass the building object to the locationItemForLecturer.fxml and populate the view
         */
        // Populate the rows like a table
        Node[] nodes = new Node[locationList.size()];

        if (locationList.size() >= 0) {  // location table is not empty, there are some locations (halls/ lab)
            for (int i = 0; i < locationList.size(); i++) {
                try {
                    //nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItem.fxml"));
                    //facultyVBox.getChildren().addAll(nodes[i]);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/SingleLocationForTag/locationItemForTag.fxml"));
                    //Parent newRoot = loader.load();
                    //Scene scene = new Scene(newRoot);
                    nodes[i] = (Node) loader.load();
                    LocationItemForTagController locationItemController = loader.getController();
                    //System.out.println("Test: locationList.get(i),this.subject_id: " + locationList.get(i) + " and" +this.subject_id);
                    locationItemController.showPreferredLocationInformationForTag(locationList.get(i), this.tag.getTagID()); // tag id should be got from Soyza's part
                    //facultyItemController = nodes[i].getController;
                    //nodes[i] = (Node) loader.load();

                    locationsVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - preferred room for subject Loading ======================================");
                    e.printStackTrace();
                }
            }
        }


    } //

    /**
     * this method is to get all the Locations in the location table but...
     * here, location table and preferred_room_for_tag table are being checked
     * returns locationList;
     */
    public ObservableList<Location> getLocationList() { // locationTagID = locationType

        // ============================================ DATABASE PART ===================================================================================

        // database connection setup
        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<Location> locationList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();

        // if the filter by combo box value is set as ALL, get all the departments
        String query;

        query = "SELECT * FROM timetabledb.location";

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
                        rs.getInt("tag_tag_id")
                        //rs.getInt("suitableRoomTrue")
                );
                locationList.add(location);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return locationList;
    }






    // ---------------------------------------------------------------------

    /**
     * returns locationList;
     */
    public SuitableLocationForTag checkSuitableRoomForTagTableData(int locationID,int tagID) { // locationTagID = locationType

        // database connection setup
        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<SuitableLocationForTag> suitableLocationForTagList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();

        String query;

        query = "SELECT pt.*\n" +
                "FROM suitable_room_for_tags AS pt\n" +
                "WHERE pt.location_location_id = "+locationID+" AND pt.tags_idtags = "+tagID+"";


        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            SuitableLocationForTag suitableLocationForTag;
            while (rs.next()) {
                suitableLocationForTag = new SuitableLocationForTag(
                        rs.getInt("suitable_room_for_tags_id"),
                        rs.getInt("location_location_id"),
                        rs.getInt("tags_idtags"),
                        rs.getString("status_true")

                );
                suitableLocationForTagList.add(suitableLocationForTag);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }

        if (suitableLocationForTagList.isEmpty()){
            return null;
        } else {
            return suitableLocationForTagList.get(0);
        }
    }


    public void setOnActionBtnSearch(MouseEvent mouseEvent) {
    }
}
