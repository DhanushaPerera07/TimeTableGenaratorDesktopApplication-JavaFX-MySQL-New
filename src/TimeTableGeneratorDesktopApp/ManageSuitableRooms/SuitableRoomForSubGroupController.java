package TimeTableGeneratorDesktopApp.ManageSuitableRooms;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseConnection;
import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.Location;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.SuitableLocationForStudentBatch;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.SuitableLocationForStudentSubGroup;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SingleLocationForStudentBatch.LocationItemForStudentBatchController;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SingleLocationForStudentSubGroup.LocationItemForStudentSubGroupController;
import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm.subGroups;
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

public class SuitableRoomForSubGroupController implements Initializable {

    static subGroups studentSubGroup;

    @FXML
    private BorderPane borderPaneLocationsHallsInsideBuildingMain;

    @FXML
    private Label txtHeaderLabsHalls;

    @FXML
    private Label txtHeaderStudentBatchName;

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
        initializeHeader();
        populateLocationRows();
    }

    public void getInformationFromShowAllStudentSubGroupUI(subGroups studentSubGroup) {
        this.studentSubGroup = studentSubGroup;
        System.out.println("test OUT: " + this.studentSubGroup.getId() + " and ID: " + this.studentSubGroup.getSubGroupId());
    }


    private void initializeHeader() {
        txtHeaderStudentBatchName.setText(this.studentSubGroup.getSubGroupId());
    }



    /**
     * populate halls and labs as rows,
     * then, user can select them or not
     * which means mark that room as a preferred room or not
     */
    public void populateLocationRows() {

        locationsVBox.getChildren().clear();

        ObservableList<Location> locationList = getLocationList();
        SuitableLocationForStudentSubGroup suitableLocationForStudentSubGroup;


        for (Location location : locationList){
            // sysout check
            System.out.println("Suitable Location for tag rec: " + location.toString());

            suitableLocationForStudentSubGroup = checkSuitableRoomForStudentSubGroupTableData(location.getLocationID(),this.studentSubGroup.getId());

            if (suitableLocationForStudentSubGroup != null){
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
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/SingleLocationForStudentSubGroup/locationItemForStudentSubGroup.fxml"));

                    nodes[i] = (Node) loader.load();
                    LocationItemForStudentSubGroupController locationItemForStudentSubGroupController = loader.getController();
                    //System.out.println("Test: locationList.get(i),this.subject_id: " + locationList.get(i) + " and" +this.subject_id);
                    locationItemForStudentSubGroupController.showPreferredLocationInformationForStudentSubGroup(locationList.get(i), this.studentSubGroup.getId()); // studentSubGroup ID should be got from Soyza's part


                    locationsVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - preferred room for student batch Loading ======================================");
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

        query = "SELECT * FROM `"+ DatabaseConnection.databaseName +"`.`location`";

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
    public SuitableLocationForStudentSubGroup checkSuitableRoomForStudentSubGroupTableData(int locationID,int studentSubGroupID) { // locationTagID = locationType

        // database connection setup
        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<SuitableLocationForStudentSubGroup> suitableLocationForStudentSubGroupList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();

        String query;

        query = "SELECT ssg.*\n" +
                "FROM `"+ DatabaseConnection.databaseName +"`.`suitable_room_for_student_subgroups` AS ssg\n" +
                "WHERE ssg.location_location_id = "+locationID+" AND ssg.subgroups_id = "+studentSubGroupID+"";


        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            SuitableLocationForStudentSubGroup suitableLocationForStudentSubGroup;
            while (rs.next()) {
                suitableLocationForStudentSubGroup = new SuitableLocationForStudentSubGroup(
                        rs.getInt("suitable_room_for_student_subgroups_id"),
                        rs.getInt("location_location_id"),
                        rs.getInt("subgroups_id"),
                        rs.getString("status_true")

                );
                suitableLocationForStudentSubGroupList.add(suitableLocationForStudentSubGroup);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }

        if (suitableLocationForStudentSubGroupList.isEmpty()){
            return null;
        } else {
            return suitableLocationForStudentSubGroupList.get(0);
        }
    }


    @FXML
    void setOnActionBtnSearch(MouseEvent event) {

    }


}
