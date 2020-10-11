package TimeTableGeneratorDesktopApp.ManageSuitableRooms;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseConnection;
import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.Location;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.SuitableLocationForStudentBatch;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.SuitableLocationForTag;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SingleLocationForStudentBatch.LocationItemForStudentBatchController;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SingleLocationForTag.LocationItemForTagController;
import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
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

public class SuitableRoomForGroupController implements Initializable {

    static StudentBatches studentBatches;
    
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

    public void getInformationFromShowAllStudentBatchesUI(StudentBatches studentBatches) {
        this.studentBatches = studentBatches;
        System.out.println("test OUT: " + this.studentBatches.getId() + " and ID: " + this.studentBatches.getBatchID());
    }


    private void initializeHeader() {
        txtHeaderStudentBatchName.setText(this.studentBatches.getBatchID());
    }



    /**
     * populate halls and labs as rows,
     * then, user can select them or not
     * which means mark that room as a preferred room or not
     */
    public void populateLocationRows() {

        locationsVBox.getChildren().clear();

        ObservableList<Location> locationList = getLocationList();
        SuitableLocationForStudentBatch suitableLocationForStudentBatch;


        for (Location location : locationList){
            // sysout check
            System.out.println("Suitable Location for tag rec: " + location.toString());

            suitableLocationForStudentBatch = checkSuitableRoomForStudentBatchTableData(location.getLocationID(),this.studentBatches.getId());

            if (suitableLocationForStudentBatch != null){
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
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/SingleLocationForStudentBatch/locationItemForStudentBatch.fxml"));
                    //Parent newRoot = loader.load();
                    //Scene scene = new Scene(newRoot);
                    nodes[i] = (Node) loader.load();
                    LocationItemForStudentBatchController locationItemForStudentBatchController = loader.getController();
                    //System.out.println("Test: locationList.get(i),this.subject_id: " + locationList.get(i) + " and" +this.subject_id);
                    locationItemForStudentBatchController.showPreferredLocationInformationForStudentBatches(locationList.get(i), this.studentBatches.getId()); // tag id should be got from Soyza's part
                    //facultyItemController = nodes[i].getController;
                    //nodes[i] = (Node) loader.load();

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
    public SuitableLocationForStudentBatch checkSuitableRoomForStudentBatchTableData(int locationID,int studentBatchID) { // locationTagID = locationType

        // database connection setup
        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<SuitableLocationForStudentBatch> suitableLocationForStudentBatchList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();

        String query;

        query = "SELECT ssb.*\n" +
                "FROM `"+ DatabaseConnection.databaseName +"`.`suitable_room_for_student_batch` AS ssb\n" +
                "WHERE ssb.location_location_id = "+locationID+" AND ssb.studentbatches_id = "+studentBatchID+"";


        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            SuitableLocationForStudentBatch suitableLocationForStudentBatch;
            while (rs.next()) {
                suitableLocationForStudentBatch = new SuitableLocationForStudentBatch(
                        rs.getInt("suitable_room_for_student_batch_id"),
                        rs.getInt("location_location_id"),
                        rs.getInt("studentbatches_id"),
                        rs.getString("status_true")

                );
                suitableLocationForStudentBatchList.add(suitableLocationForStudentBatch);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }

        if (suitableLocationForStudentBatchList.isEmpty()){
            return null;
        } else {
            return suitableLocationForStudentBatchList.get(0);
        }
    }


    @FXML
    void setOnActionBtnSearch(MouseEvent event) {

    }


}
