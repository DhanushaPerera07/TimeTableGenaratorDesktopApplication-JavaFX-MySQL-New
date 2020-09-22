package TimeTableGeneratorDesktopApp.ManageSuitableRooms;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.Location;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SingleLocationForLecturer.LocationItemForLecturerController;
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

public class SuitableRoomForLecturerController implements Initializable {

    //variables need to hold some values
    static int lecturerID;
    static String lecturerName;

    @FXML
    private BorderPane borderPaneLocationsHallsInsideBuildingMain;

    @FXML
    private Label txtHeaderLabsHalls;

    @FXML
    private Label txtHeaderLecturerName;

    @FXML
    private TextField locationsHallsInsideSearchTxtBox;

    @FXML
    private Button btnSearchLocationsHallsInside;

    @FXML
    private ComboBox<String> preferredSubjectFilterByComboBox;

    @FXML
    private ComboBox<String> selectTagComboBox;

    @FXML
    private VBox locationsVBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeHeaders();
        populateLocationRows();
    }

    public void getNecessaryInformation(int lecturerID, String lecturerName) {
        this.lecturerID = lecturerID;
        this.lecturerName = lecturerName;

   /*     System.out.println("lecturerID: " + this.lecturerID);
        System.out.println("lecturerName: " + this.lecturerName);*/
    }

    // set lecturer name in the header
    private void initializeHeaders() {
        txtHeaderLecturerName.setText(lecturerName);
    }

    @FXML
    void setOnActionBtnSearch(MouseEvent event) {

    }



    /**
     * populate halls and labs as rows,
     * then, user can select them or not
     * which means mark that room as a preferred room or not
     */
    private void populateLocationRows() {

        locationsVBox.getChildren().clear();

        ObservableList<Location> locationList = getLocationList();

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
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/SingleLocationForLecturer/locationItemForLecturer.fxml"));
                    nodes[i] = (Node) loader.load();
                    LocationItemForLecturerController locationItemForLecturerController = loader.getController();
                    //System.out.println("Test: locationList.get(i),this.subject_id: " + locationList.get(i) + " and" +this.subject_id);
                    locationItemForLecturerController.showPreferredLocationInformationForLecturer(locationList.get(i), this.lecturerID); // lecturer id should be got from Menura's part

                    locationsVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - preferred room for lecturer Loading ======================================");
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * this method is to get all the Locations in the location table but...
     * here, location table and preferred_room_for_subject table are being checked
     * returns locationList;
     */
    private ObservableList<Location> getLocationList() {

        // ============================================ DATABASE PART ===================================================================================

        // database connection setup
        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<Location> locationList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();

        // if the filter by combo box value is set as ALL, get all the departments
        String query;

        //query = "SELECT l.*, IF(l.location_id = ps.location_location_id, TRUE, FALSE) AS suitableRoomTrue FROM location AS l, preferred_room_for_subject AS ps WHERE ps.subject_subject_id = "+this.subject_id+"";

        query = "SELECT l.*, IF(l.location_id = pl.location_location_id, TRUE, FALSE) AS suitableRoomTrue \n" +
                "FROM location AS l, suitable_room_for_lecturer AS pl\n" +
                "WHERE pl.lecturer_lid = " + this.lecturerID + "";


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
                        //rs.getInt("subject_subject_id"),
                        rs.getInt("suitableRoomTrue")
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
