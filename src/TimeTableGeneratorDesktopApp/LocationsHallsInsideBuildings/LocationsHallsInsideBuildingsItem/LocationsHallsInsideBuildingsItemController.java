package TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsItem;

import TimeTableGeneratorDesktopApp.DatabaseHelper.BuildingDatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.TagsDatabaseHelper;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsPopUps.EditLocationsHallsPopUpController;
import TimeTableGeneratorDesktopApp.Tags.Tags;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.ObservableList;
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
    public int moduleID, tagID;
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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsHallsInsideBuildings/LocationsHallsInsideBuildingsPopUps/editLocationsHallsPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();


            EditLocationsHallsPopUpController editLocationsHallsPopUpController = fxmlLoader.getController();
            editLocationsHallsPopUpController.getNecessaryID(this.locationHallLab, this.buildingName);

            Stage stage = new Stage();

            stage.setTitle("Edit Hall/Lab");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(locationsHallsInsideItemVBOX.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
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

        deleteFacultyAlert.getButtonTypes().setAll(DeleteBtn, CancelBtn);

        Optional<ButtonType> result = deleteFacultyAlert.showAndWait();
        if (result.get() == DeleteBtn) {
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
        this.tagID = locationHallLab.getTagID();

        // set labels
        txtLocationHallLabName.setText(locationHallLab.getLocationName());
        txtLocationHallLabCapacity.setText(Integer.toString(locationHallLab.getLocationCapacity()));
        txtLocationHallLabFloor.setText(Integer.toString(locationHallLab.getLocationFloor()));
        txtLocationHallLabCondition.setText(locationHallLab.getLocationCondition());
        //txtLocationHallLabSpecializedModule.setText(Integer.toString(locationHallLab.getSubjectId()));
        //txtLocationHallLabBuilding.setText(Integer.toString(locationHallLab.getBuildingID()));
        txtLocationHallLabTag.setText(Integer.toString(locationHallLab.getTagID()));

        // tag
        // get tags details from the database and make a list then, using that list combo box values are displayed
        TagsDatabaseHelper tagsDatabaseHelper = new TagsDatabaseHelper();

        Tags tag =  tagsDatabaseHelper.getTagInstanceByTagID(locationHallLab.getTagID());
        txtLocationHallLabTag.setText(tag.getTag());

        //building
        BuildingDatabaseHelper buildingDatabaseHelper = new BuildingDatabaseHelper();
        String building_Name =  buildingDatabaseHelper.getBuildingInstance(locationHallLab.getBuildingID()).getBuildingName();

        txtLocationHallLabBuilding.setText(building_Name);


    }


    // ===================== DATABASE PART - STARTS HERE =============================================================================

    /**
     * get the database connection here
     */
    DatabaseHelper databaseHelper = new DatabaseHelper();


    public void deleteHallLabRecord(int locationHallLabID) throws IOException {

        // delete query
        String query = "UPDATE `location` SET location_delete_status = 'Y' WHERE location_id = " + locationHallLabID + "";

        // execute the insert query
        databaseHelper.executeQuery(query);

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
