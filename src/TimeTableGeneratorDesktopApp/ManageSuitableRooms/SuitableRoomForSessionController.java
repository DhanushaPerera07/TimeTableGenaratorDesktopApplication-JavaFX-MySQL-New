package TimeTableGeneratorDesktopApp.ManageSuitableRooms;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.Location;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.SuitableLocationForLecturer;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.SuitableLocationForSession;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SingleLocationForLecturer.LocationItemForLecturerController;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SingleLocationForSession.LocationItemForSessionController;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SingleLocationForStudentSubGroup.LocationItemForStudentSubGroupController;
import TimeTableGeneratorDesktopApp.Sessions.Sessions;
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

public class SuitableRoomForSessionController implements Initializable {

    //variables need to hold some values
    static Sessions session;
    static int sessionID;
    static String sessionName;

    @FXML
    private BorderPane borderPaneLocationsHallsInsideBuildingMain;

    @FXML
    private Label txtHeaderLabsHalls;

    @FXML
    private Label txtHeaderSessionSubject;

    @FXML
    private Label txtHeaderSessionSubjectName;

    @FXML
    private Label txtHeaderSessionTag;

    @FXML
    private Label txtHeaderSessionTagName;

    @FXML
    private Label txtHeaderSessionStudentGroup;

    @FXML
    private Label txtHeaderNoOfStudents;

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

    public void getNecessaryInformationFromShowAllSessions(Sessions session) {

        this.session = session;
       /* this.lecturerID = lecturerID;
        this.lecturerName = lecturerName;*/
    }

    // set session details in the header
    private void initializeHeaders() {
        txtHeaderSessionSubjectName.setText(this.session.getSessionModule());
        txtHeaderSessionTagName.setText(this.session.getSessionTag());
        txtHeaderSessionStudentGroup.setText(this.session.getSessionGroupID());
        txtHeaderNoOfStudents.setText(Integer.toString(this.session.getSessionStudentCount()));
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
        SuitableLocationForSession suitableLocationForSession;


        for (Location location : locationList){
            // sysout check
            System.out.println("Suitable Location for session rec: " + location.toString());

            suitableLocationForSession = checkSuitableRoomForSessionTableData(location.getLocationID(),this.session.getSessionID());

            if (suitableLocationForSession != null){
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
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/SingleLocationForSession/locationItemForSession.fxml"));

                    nodes[i] = (Node) loader.load();
                    LocationItemForSessionController locationItemForSessionController = loader.getController();
                    //System.out.println("Test: locationList.get(i),this.subject_id: " + locationList.get(i) + " and" +this.subject_id);
                    locationItemForSessionController.showPreferredLocationInformationForSession(locationList.get(i), this.session.getSessionID()); // studentSubGroup ID should be got from Soyza's part


                    locationsVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - preferred room for student batch Loading ======================================");
                    e.printStackTrace();
                }
            }
        }

    }


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

    /** Search records in suitable_room_for_lecturer table
     * returns locationList;
     */
    public SuitableLocationForSession checkSuitableRoomForSessionTableData(int locationID, int sessionID) {

        // database connection setup
        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<SuitableLocationForSession> suitableLocationForSessionList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();

        String query;

        query = "SELECT * \n" +
                "FROM timetabledb.suitable_room_for_session AS ss\n" +
                "WHERE ss.location_location_id = "+locationID+" AND ss.idsession = "+sessionID+"";


        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            SuitableLocationForSession suitableLocationForSession;
            while (rs.next()) {
                suitableLocationForSession = new SuitableLocationForSession(
                        rs.getInt("suitable_room_for_lecturer_id"),
                        rs.getInt("location_location_id"),
                        rs.getInt("lecturer_lid"),
                        rs.getString("status_true")

                );
                suitableLocationForSessionList.add(suitableLocationForSession);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error: when fetching data from suitable_room_for_session table");
            ex.printStackTrace();
        }

        if (suitableLocationForSessionList.isEmpty()){
            return null;
        } else {
            return suitableLocationForSessionList.get(0);
        }
    }
}
