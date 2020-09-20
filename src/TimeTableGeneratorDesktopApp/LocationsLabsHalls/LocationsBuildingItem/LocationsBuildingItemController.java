package TimeTableGeneratorDesktopApp.LocationsLabsHalls.LocationsBuildingItem;

import TimeTableGeneratorDesktopApp.Controller;
import TimeTableGeneratorDesktopApp.DatabaseHelper.FacultyDatabaseHelper;
import TimeTableGeneratorDesktopApp.Departments.DepartmentsController;
import TimeTableGeneratorDesktopApp.FxmlLoader;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsController;
import TimeTableGeneratorDesktopApp.LocationsLabsHalls.Building;
import TimeTableGeneratorDesktopApp.LocationsLabsHalls.LocationsBuildingPopUps.EditLocationsBuildingPopUpController;
import com.sun.media.jfxmedia.events.BufferListener;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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


public class LocationsBuildingItemController implements Initializable {

    // holds the building obj
    public Building buildingInstance;
    public int buildingID;
    public String buildingName;


    @FXML
    private VBox locationsBuildingItemVBOX;

    @FXML
    private ComboBox<String> onActionsBuildingComboBox;

    @FXML   // button
    private Button btnViewHallTimeTable;

    @FXML   // button
    private Button btnViewHallsAndLabs;

    @FXML
    private FontAwesomeIcon btnEditIcon;

    @FXML
    private FontAwesomeIcon btnPrintIcon;

    @FXML
    private FontAwesomeIcon btnDeleteIcon;

    @FXML
    private Label txtBuildingName;

    @FXML
    private Label txtNoOfFloors;

    @FXML
    private Label txtCapacity;

    @FXML
    private Label txtReservedFaculty;

    @FXML
    private Label txtNoOfLectureHalls;

    @FXML
    private Label txtSpecializedFor;

    @FXML
    private Label txtCondition;

    @FXML
    private Label txtCenter;

    @FXML
    private Label txtNoOfTutorialHalls;

    @FXML
    private Label txtNoOfLabs;

    // ======================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // filter by combobox
        onActionsBuildingComboBox.getItems().addAll(
                "More options",
                "Add time that room can not be reserved",
                "Blah Blah"
        );
        onActionsBuildingComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown

    }

    // ======================================================================================




    public void openEditBuildingPopUp(MouseEvent mouseEvent) {
        System.out.println("Clicked - Open pop up to edit Locations - Building Record");

        // should open the pop up to edit Building

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsLabsHalls/LocationsBuildingPopUps/editLocationsBuildingPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            // here we pass the selected building instance to the editLocationsBuildingPopUp.fxml (to its controller)
            EditLocationsBuildingPopUpController editLocationsBuildingPopUpController = fxmlLoader.getController();
            editLocationsBuildingPopUpController.getBuildingInstance(this.buildingInstance);

            stage.setTitle("Edit Building");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(locationsBuildingItemVBOX.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening editLocationsBuildingPopUp.fxml as a pop up ==========================");
            e.printStackTrace();
        }

    }

    public void openDeleteBuildingConfirmationAlertBoxPopUp(MouseEvent mouseEvent) {
        System.out.println("Clicked - Open Confirmation AlertBOX before deleting a Building Record");

        Alert deleteBuildingAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteBuildingAlert.setTitle("Confirmation");
        deleteBuildingAlert.setHeaderText("Give Confirmation to delete this Building");
        deleteBuildingAlert.setContentText("Do you want to delete the Building? \nClick Delete to Delete the Building, \nOtherwise click Cancel");

        ButtonType DeleteBtn = new ButtonType("Delete");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        deleteBuildingAlert.getButtonTypes().setAll(DeleteBtn,CancelBtn);

        Optional<ButtonType> result = deleteBuildingAlert.showAndWait();
        if (result.get() == DeleteBtn){
            try {
                deleteBuildingRecord(this.buildingInstance.getBuildingID());
            } catch (IOException e) {
                System.out.println("Error - delete building is not successful");
                e.printStackTrace();
            }
            System.out.println("Building is deleted successfully");
        } else {
            System.out.println("Clicked Cancel Button - (Deleting a Building)");
        }
    }


    public void openHallsLabsUI(MouseEvent mouseEvent) {


        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsHallsInsideBuildings/LocationsHallsInsideBuildings.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            LocationsHallsInsideBuildingsController locationsHallsInsideBuildingsController = fxmlLoader.getController();
            locationsHallsInsideBuildingsController.showInformation(this.buildingID,this.buildingName);

            Stage stage = new Stage();

            stage.setTitle("Departments");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(locationsBuildingItemVBOX.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening LocationsHallsInsideBuildings.fxml as a pop up ==========================");
            e.printStackTrace();
        }




        ///////////////////////////////////

        /*
        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsHallsInsideBuildings/LocationsHallsInsideBuildings.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            //LocationsHallsInsideBuildingsController locationsHallsInsideBuildingsController = fxmlLoader.load();

            // pass the  building ID and the building name to the department screen
            //locationsHallsInsideBuildingsController.showInformation(this.building.getBuildingID(),this.building.getBuildingName());

            Stage stage = new Stage();


            stage.setTitle("Departments");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(locationsBuildingItemVBOX.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening LocationsHallsInsideBuildings.fxml as a pop up ==========================");
            e.printStackTrace();
        }
        */




        // right hand side of the screen should be updated with the view of LocationsHallsInsideBuildings.fxml
        // then, LocationsHallsInsideBuildings.fxml lists out the corresponding halls and labs

        /*
        Controller controller = new Controller();
        controller.goToHallsAndLabsUI(10);

         */

        /*
        System.out.println("clicked View - Halls/Labs Button. Building ID: ");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("LocationsHallsInsideBuildings/LocationsHallsInsideBuildings");
        mainPane.setCenter(view);
        */

    }

    /**
     * Show relevant details of the building obj in the  LocationsBuildingItem.fxml UI
     * Get the building obj as the parameter
     * @param building
     */
    public void showInformation(Building building) {

        // variables are updated with the corresponding building details
        this.buildingInstance = building;
        this.buildingID = building.getBuildingID();
        this.buildingName = building.getBuildingName();

        txtBuildingName.setText(building.getBuildingName());
        txtCapacity.setText(Integer.toString(building.getBuildingCapacity()));
        txtNoOfFloors.setText(Integer.toString(building.getBuildingNoOfFloors()));
        txtCenter.setText(building.getBuildingCenter());
        txtSpecializedFor.setText(building.getBuildingSpecializedFor());
        txtCondition.setText(building.getBuildingCondition());
        txtNoOfLectureHalls.setText(Integer.toString(building.getBuildingNoOfLectureHalls()));
        txtNoOfTutorialHalls.setText(Integer.toString(building.getBuildingNoOfTutorialHalls()));
        txtNoOfLabs.setText(Integer.toString(building.getBuildingNoOfLabs()));
        //txtReservedFaculty.setText(Integer.toString(building.getFacultyFacultyId()));

        FacultyDatabaseHelper facultyDatabaseHelper = new FacultyDatabaseHelper();
        txtReservedFaculty.setText(facultyDatabaseHelper.getFacultyInstance(building.getFacultyFacultyId()).getName());

        //System.out.println("Hey this this building: " + this.buildingInstance.toString());

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



    /**
     * This method is used to delete a faculty.
     * faculty_delete_status is set to Y in order to mark that record is deleted.
     * It is done as a database good practise.
     * Any record is not deleted from the database.
     */
    public void deleteBuildingRecord(int buildingID) throws IOException {

        // delete query
        String query = "UPDATE `building` SET building_delete_status = 'Y' WHERE building_id = "+buildingID+"";

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
