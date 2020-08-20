package TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsItem;

import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsPopUps.EditLocationsHallsPopUpController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class LocationsHallsInsideBuildingsItemController implements Initializable {

    // holds the building value
    public LocationHallLab locationHallLab;
    public int moduleID,tagID;
    String buildingName;

    @FXML
    private VBox locationsHallsInsideItemVBOX;

    @FXML
    private ComboBox<String> onActionsHallsComboBox;

    @FXML
    private Button btnViewHallTimeTable;


    @FXML
    private FontAwesomeIcon btnEditIcon;

    @FXML
    private FontAwesomeIcon btnPrintIcon;

    @FXML
    private FontAwesomeIcon btnDeleteIcon;

    ////////

    @FXML
    private Label txtLocationHallLabName;

    @FXML
    private Label txtLocationHallLabTag;

    @FXML
    private Label txtLocationHallLabCapacity;

    @FXML
    private Label txtLocationHallLabFloor;

    @FXML
    private Label txtLocationHallLabBuilding;

    @FXML
    private Label txtLocationHallLabSpecializedModule;

    @FXML
    private Label txtLocationHallLabCondition;

    // ======================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // filter by combobox
        onActionsHallsComboBox.getItems().addAll(
                "More options",
                "Add suitable room(s) for Lecturer",
                "Add suitable room(s) for Group",
                "Add suitable room(s) for Session",
                "Add consecutive sessions in the same room",
                "Add time that room can not be reserved",
                "Blah Blah"
        );
        onActionsHallsComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown

    }

    // ======================================================================================

    public void openTimeTableOfTheHall(MouseEvent mouseEvent) {
        System.out.println("Clicked - Open Time Table of the Hall or Lab");
    }

    public void openEditHallPopUp(MouseEvent mouseEvent) {
        System.out.println("Clicked - Open pop up to edit Locations - Hall/ Labs Record");

        // should open the pop up to edit  the Hall / Lab

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsHallsInsideBuildings/LocationsHallsInsideBuildingsPopUps/editLocationsHallsPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();


            EditLocationsHallsPopUpController editLocationsHallsPopUpController = fxmlLoader.getController();
            editLocationsHallsPopUpController.getNecessaryID(this.locationHallLab,this.buildingName);

            Stage stage = new Stage();

            stage.setTitle("Edit Hall/Lab");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(locationsHallsInsideItemVBOX.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening editLocationsHallsPopUp.fxml as a pop up ==========================");
            e.printStackTrace();
        }

    }

    public void openDeleteHallConfirmationAlertBoxPopUp(MouseEvent mouseEvent) {
        System.out.println("Clicked - Open Confirmation AlertBOX before deleting a Hall/ Labs Record");

        Alert deleteFacultyAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteFacultyAlert.setTitle("Confirmation");
        deleteFacultyAlert.setHeaderText("Give Confirmation to delete this Hall / Lab");
        deleteFacultyAlert.setContentText("Do you want to delete the Hall / Lab? \nClick Delete to Delete the Hall / Lab, \nOtherwise click Cancel");

        ButtonType DeleteBtn = new ButtonType("Delete");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        deleteFacultyAlert.getButtonTypes().setAll(DeleteBtn,CancelBtn);

        Optional<ButtonType> result = deleteFacultyAlert.showAndWait();
        if (result.get() == DeleteBtn){
            try {
                deleteHallLabRecord(this.locationHallLab.getLocationID());
                System.out.println("Hall/ Lab is deleted successfully");
            } catch (IOException e) {
                System.out.println("Error - Hall/ Lab is not deleted successfully");
                e.printStackTrace();
            }
        } else {
            System.out.println("Clicked Cancel Button - (Deleting a Hall/ Labs)");
        }
    }




    public void printTimeTableOfTheHall(MouseEvent mouseEvent) {
        System.out.println("Clicked - print Locations - Hall/ Labs Record");
    }

    public void showInformation(LocationHallLab locationHallLab, String buildingName) {
        this.locationHallLab = locationHallLab;
        this.buildingName = buildingName;
        this.moduleID = locationHallLab.getSubjectId();
        this.tagID = locationHallLab.getTagID();

        // set labels
        txtLocationHallLabName.setText(locationHallLab.getLocationName());
        txtLocationHallLabCapacity.setText(Integer.toString(locationHallLab.getLocationCapacity()));
        txtLocationHallLabFloor.setText(Integer.toString(locationHallLab.getLocationFloor()));
        txtLocationHallLabCondition.setText(locationHallLab.getLocationCondition());
        //txtLocationHallLabSpecializedModule.setText(Integer.toString(locationHallLab.getSubjectId()));
        //txtLocationHallLabBuilding.setText(Integer.toString(locationHallLab.getBuildingID()));
        txtLocationHallLabTag.setText(Integer.toString(locationHallLab.getTagID()));

        // subject
        if(locationHallLab.getSubjectId() == 1){
            txtLocationHallLabSpecializedModule.setText("Software Project Management");
        } else if (locationHallLab.getSubjectId() == 2){
            txtLocationHallLabSpecializedModule.setText("User Experience Engineering");
        } else {
            txtLocationHallLabSpecializedModule.setText("Unknown");
        }

        // tag
        if (locationHallLab.getTagID() == 1){
            txtLocationHallLabTag.setText("Lecture Hall");
        }
        else if (locationHallLab.getTagID() == 2){
            txtLocationHallLabTag.setText("Tutorial Hall");
        }
        else if (locationHallLab.getTagID() == 3){
            txtLocationHallLabTag.setText("Lecture/Tutorial Hall (Not-Consecutive)");
        }
        else if (locationHallLab.getTagID() == 4){
            txtLocationHallLabTag.setText("Lecture/Tutorial Hall (Not Consecutive)");
        } else if(locationHallLab.getTagID() == 5){
            txtLocationHallLabTag.setText("PC - Lab");
        } else {
            txtLocationHallLabTag.setText("Unknown");
        }

        //building
        if (locationHallLab.getBuildingID() == 1){
            txtLocationHallLabBuilding.setText("FOC - Main");
        } else {
            txtLocationHallLabBuilding.setText("New - Building");
        }

    }





    // ===================== DATABASE PART - STARTS HERE =============================================================================

    /** get the database connection here
     */
    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetabledb", "root","root");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: getConnection() :::: " + ex.getMessage());
            return null;
        }
    }

    /** execute the query string
     * @param query string is passed here
     * this query will execute by this method
     */
    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }







    public void deleteHallLabRecord(int locationHallLabID) throws IOException {

        // delete query
        String query = "UPDATE `location` SET location_delete_status = 'Y' WHERE location_id = "+locationHallLabID+"";

        // execute the insert query
        executeQuery(query);

        /* // ERROR in this code segment
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyDepartments.fxml"));
        loader.load();
        FacultyDepartmentsController facultyDepartmentsController = loader.getController();
        facultyDepartmentsController.populateAndRefreshFacultyDataRow();
         */

        System.out.println("Faculty is deleted successfully");

    }
}
