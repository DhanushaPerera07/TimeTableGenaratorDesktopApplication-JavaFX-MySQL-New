package TimeTableGeneratorDesktopApp.ManageSuitableRooms;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseConnection;
import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.LocationHallLabDatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.TagsDatabaseHelper;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.Location;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.PreferredLocation;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.PreferredLocationForSubject;
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


// imported


public class PreferredRoomForSubjectController implements Initializable {

    // variable declaration to keep some useful data
    public static int subject_id;
    public static String subject_name;
    //int tag_id = 1;

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
    private ComboBox<String> preferredSubjectFilterByComboBox;

    @FXML
    private ComboBox<String> selectTagComboBox;

    @FXML
    private VBox locationsVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboBoxes();
        populateLocationRows();
    }


    void initializeComboBoxes() {

        txtHeaderBuildingName.setText(subject_name);

        // get tags details from the database and make a list then, using that list combo box values are displayed
        TagsDatabaseHelper tagsDatabaseHelper = new TagsDatabaseHelper();
        ObservableList<Tags> tagList = tagsDatabaseHelper.getTagList();

        for (Tags tag : tagList) {
            // sysout check
            selectTagComboBox.getItems().add(tag.getTag()); // tag name is displayed in the combo box

        }
        selectTagComboBox.getSelectionModel().select("Lec");

        selectTagComboBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            System.out.println("listener works !");
            System.out.println("oldValue : " + oldValue);
            System.out.println("newValue : " + newValue);
            populateLocationRows();
        });

    }


    @FXML
    void setOnActionBtnSearch(MouseEvent event) {

    }


    /**
     * This method is used to get subject ID from menura's part
     */
    public void getInformationFromSubjectUI(int subjectID, String subjectName) {
        subject_id = subjectID;
        subject_name = subjectName;
        //tag_id = tagID;
        System.out.println("Student id + " + this.subject_id);

    }


    /**
     * populate halls and labs as rows,
     * then, user can select them or not
     * which means mark that room as a preferred room or not
     */
    public void populateLocationRows() {

        locationsVBox.getChildren().clear();

        TagsDatabaseHelper tagsDatabaseHelper = new TagsDatabaseHelper();
        Tags tag = new Tags();
        tag = tagsDatabaseHelper.getTagInstanceByTagName(selectTagComboBox.getValue());

        ObservableList<PreferredLocationForSubject> preferredLocationForSubjectList;

        String tagName = tag.getTag();

        if (tagName.equals("Lecture") || tagName.equals("Lec") ) {
            preferredLocationForSubjectList = getPreferredLocationForSubjectList(tag.getTagID());
        } else if (tagName.equals("Tute") || tagName.equals("Tutorial")) {
            preferredLocationForSubjectList = getPreferredLocationForSubjectList(tag.getTagID());
        } else if (tagName.equals("Lec+ Tute") || tagName.equals("Lecture + Tutorial")) {
            preferredLocationForSubjectList = getPreferredLocationForSubjectList(tag.getTagID());
        } else if (tagName.equals("Lab")) {
            preferredLocationForSubjectList = getPreferredLocationForSubjectList(tag.getTagID());
        }
        else if (tagName.equals("Evaluation")) {
            preferredLocationForSubjectList = getPreferredLocationForSubjectList(tag.getTagID());
        } else {
            preferredLocationForSubjectList = getPreferredLocationForSubjectList(0);
        }

        System.out.println("Test sout: preferredLocationForSubjectList = " + preferredLocationForSubjectList.size());

        /**
         * Dynamically change the rows by getting data from the database
         * locationItemForLecturer.fxml is used as the UI, it acts as a customized data row
         * I pass the building object to the locationItemForLecturer.fxml and populate the view
         */
        // Populate the rows like a table
        Node[] nodes = new Node[preferredLocationForSubjectList.size()];

        if (preferredLocationForSubjectList.size() >= 0) {  // location table is not empty, there are some locations (halls/ lab)
            for (int i = 0; i < preferredLocationForSubjectList.size(); i++) {
                try {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/SingleLocationItem/locationItem.fxml"));

                    nodes[i] = (Node) loader.load();
                    LocationItemController locationItemController = loader.getController();

                    System.out.println("test preferredLocationForSubjectList.get(i): " + preferredLocationForSubjectList.get(i));

                    locationItemController.showPreferredLocationForSubjectInformationForSubject(preferredLocationForSubjectList.get(i), this.subject_id); // subject id should be got from Menura's part

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
     * here, location table and preferred_room_for_subject table are being checked
     * returns locationList;
     */
  /*  public ObservableList<Location> getLocationList(int tagID) {

        // ============================================ DATABASE PART ===================================================================================

        // database connection setup
        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<Location> locationList = FXCollections.observableArrayList();
        Connection conn =  databaseHelper.getConnection();

        // if the filter by combo box value is set as ALL, get all the departments
        String query;
        //query = "SELECT * FROM location WHERE building_building_id = "+this.buildingID+" AND location_delete_status = 'N' ORDER BY location_name";
        //query = "SELECT * FROM location WHERE location_delete_status = 'N' ORDER BY location_name";


        if (tagID >= 0){
            //query = "SELECT l.*, IF(l.location_id = ps.location_location_id, TRUE, FALSE) AS suitableRoomTrue FROM location AS l, preferred_room_for_subject AS ps WHERE ps.subject_subject_id = "+this.subject_id+"";
            query = "SELECT l.*, IF(l.location_id = ps.location_location_id, TRUE, FALSE) AS suitableRoomTrue FROM location AS l, preferred_room_for_subject AS ps WHERE ps.subject_subject_id = "+this.subject_id + " AND l.tag_tag_id =" + tagID + "";

        } else {
            query = "SELECT l.*, IF(l.location_id = ps.location_location_id, TRUE, FALSE) AS suitableRoomTrue FROM location AS l, preferred_room_for_subject AS ps WHERE ps.subject_subject_id = "+this.subject_id+" AND ps.tag_tag_id = "+tagID+"";

        }

        *//*
        //query = "SELECT l.*, IF(l.location_id = ps.location_location_id, TRUE, FALSE) AS suitableRoomTrue FROM location AS l, preferred_room_for_subject AS ps WHERE ps.subject_subject_id = "+this.subject_id+"";
        query = "SELECT l.*, IF(l.location_id = ps.location_location_id, TRUE, FALSE) AS suitableRoomTrue FROM location AS l, preferred_room_for_subject AS ps WHERE ps.subject_subject_id = "+this.subject_id+" AND ps.tag_tag_id = "+this.tag_id+"";

         *//*

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
    }*/


    // ---------------------------------------------------------------------------------------------------------------


    // database connection setup
    DatabaseHelper databaseHelper = new DatabaseHelper();
    LocationHallLabDatabaseHelper locationHallLabDatabaseHelper = new LocationHallLabDatabaseHelper();

    public ObservableList<PreferredLocationForSubject> getPreferredLocationForSubjectList(int tagID) {

        // ============================================ DATABASE PART ===================================================================================

        ObservableList<LocationHallLab> locationList = locationHallLabDatabaseHelper.getAllLocationHallLabList();
        ObservableList<PreferredLocation> preferredLocationList = FXCollections.observableArrayList();
        ObservableList<PreferredLocationForSubject> preferredLocationForSubjectsList = FXCollections.observableArrayList();

        Connection conn = databaseHelper.getConnection();

        // if the filter by combo box value is set as ALL, get all the departments
        String query;

        query = "SELECT ps.* " +
                "FROM `"+ DatabaseConnection.databaseName +"`.`preferred_room_for_subject` AS ps";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            PreferredLocation preferredLocation;
            while (rs.next()) {

                preferredLocation = new PreferredLocation();
                preferredLocation.setPreferredRoomForSubjectID(rs.getInt("preferred_room_for_subject_id"));
                preferredLocation.setSubjectSubjectID(rs.getInt("subject_subject_id"));
                preferredLocation.setLocationLocationID(rs.getInt("location_location_id"));
                preferredLocation.setTagTagID(rs.getInt("tag_tag_id"));

                if (rs.getString("status_true").equals("Y")) {
                    preferredLocation.setStatusTrue("Y");
                } else {
                    preferredLocation.setStatusTrue("N");
                }

                preferredLocationList.add(preferredLocation);
            }


            for (int i = 0; i < locationList.size(); i++) {

                PreferredLocationForSubject preferredLocationForSubject = new PreferredLocationForSubject();
                //preferredLocationForSubject.setLocationHallLab(locationList.get(i));

                for (int j = 0; j < preferredLocationList.size(); j++) {
                    int subjectID = preferredLocationList.get(j).getSubjectSubjectID();
                    int preferredLocationID = preferredLocationList.get(j).getLocationLocationID();
                    int preferredLocationTagID = preferredLocationList.get(j).getTagTagID();
                    String preferredLocationStatusTrue = preferredLocationList.get(j).getStatusTrue();

                    int locationID = locationList.get(i).getLocationID();
                    // tagID

                    if (subjectID == this.subject_id && preferredLocationID == locationID && preferredLocationTagID == tagID){
                        preferredLocationForSubject.setLocationHallLab(locationList.get(i));
                        preferredLocationForSubject.setPreferredLocation(preferredLocationList.get(j));
                        break;

                    } else {

                        preferredLocationForSubject.setLocationHallLab(locationList.get(i));
                        preferredLocationForSubject.setPreferredLocation(new PreferredLocation());

                    }

                }

                if (locationList.get(i).getTagID() == tagID) {
                    preferredLocationForSubjectsList.add(preferredLocationForSubject);
                }

            }


        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return preferredLocationForSubjectsList;
    }


    // ---------------------------------------------------------------------------------------------------------------


    /**
     * Get selected rooms and save
     *
     * @param event
     */
    @FXML
    void SavePreferredRoomForSubject(MouseEvent event) {

    }


}
