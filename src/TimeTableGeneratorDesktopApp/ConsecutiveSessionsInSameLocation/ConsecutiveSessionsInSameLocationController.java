package TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation;

import TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.SingleConsecutiveSessionItem.SingleConsecutiveSessionItemController;
import TimeTableGeneratorDesktopApp.DatabaseHelper.*;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class ConsecutiveSessionsInSameLocationController{


    // variables which used to keep necessary data
    LocationHallLab locationHallLab;
    Boolean checkBoxStatusDb;

    @FXML
    private BorderPane borderPaneLocationsHallsInsideBuildingMain;

    @FXML
    private Label txtHeaderLabsHalls;

    @FXML
    private Label txtLocationName;

    @FXML
    private Label txtTagName;

    @FXML
    private TextField locationsHallsInsideSearchTxtBox;

    @FXML
    private Button btnSearchLocationsHallsInside;

    @FXML
    private ComboBox<String> FilterByComboBox;

    @FXML
    private VBox locationsVBox;


    public void getInformationFromLocationsHallsLabsInsideBuildingsUI(LocationHallLab locationHallLab) {
        this.locationHallLab = locationHallLab;

        System.out.println("test this.locationHallLab rec : " + this.locationHallLab.toString());

        TagsDatabaseHelper tagsDatabaseHelper = new TagsDatabaseHelper();
        String tagName = tagsDatabaseHelper.getTagInstanceByTagID(this.locationHallLab.getTagID()).getTag();
        String locationName = this.locationHallLab.getLocationName();

        txtLocationName.setText(locationName);
        txtTagName.setText(tagName);

        populateLocationRows();

    }


    // -------------------------------------------------------------------


    /**
     * populate halls and labs as rows,
     * then, user can select them or not
     * which means mark that room as a preferred room or not
     */
    public void populateLocationRows() {

        locationsVBox.getChildren().clear();

        ConsecutiveSessionsDatabaseHelper consecutiveSessionsDatabaseHelper = new ConsecutiveSessionsDatabaseHelper();

        // observable list
        ObservableList<ConsecutiveSessionViewModel> consecutiveSessionViewModelList = consecutiveSessionsDatabaseHelper.getAllConsecutiveSessions();

        ConsecutiveSessionInSameLocationDatabaseHelper consecutiveSessionInSameLocationDatabaseHelper = new ConsecutiveSessionInSameLocationDatabaseHelper();
        ConsecutiveSessionInSameLocation consecutiveSessionInSameLocation;


        for (ConsecutiveSessionViewModel consecutiveSessionViewModel : consecutiveSessionViewModelList){
            // sysout check
            System.out.println("ConsecutiveSessionViewModel rec: " + consecutiveSessionViewModel.toString());

            consecutiveSessionInSameLocation = consecutiveSessionInSameLocationDatabaseHelper.checkForConsecutiveSessionInSameLocationTableData(this.locationHallLab.getLocationID(),consecutiveSessionViewModel.getId());

            if (consecutiveSessionInSameLocation != null){
                if (consecutiveSessionInSameLocation.getStatus_true().equals("Y")){
                    consecutiveSessionViewModel.setSuitableRoomTrue(1);
                } else {
                    consecutiveSessionViewModel.setSuitableRoomTrue(0);
                }
                //consecutiveSessionViewModel.setSuitableRoomTrue(1);
            } else {
                consecutiveSessionViewModel.setSuitableRoomTrue(0);
            }

        }


        /**
         * Dynamically change the rows by getting data from the database
         * locationItemForLecturer.fxml is used as the UI, it acts as a customized data row
         * I pass the building object to the locationItemForLecturer.fxml and populate the view
         */
        // Populate the rows like a table
        Node[] nodes = new Node[consecutiveSessionViewModelList.size()];

        if (consecutiveSessionViewModelList.size() > 0) {  // location table is not empty, there are some locations (halls/ lab)
            for (int i = 0; i < consecutiveSessionViewModelList.size(); i++) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ConsecutiveSessionsInSameLocation/SingleConsecutiveSessionItem/SingleConsecutiveSessionItem.fxml"));

                    nodes[i] = (Node) loader.load();
                    SingleConsecutiveSessionItemController singleConsecutiveSessionItemController = loader.getController();
                    //System.out.println("Test: locationList.get(i),this.subject_id: " + locationList.get(i) + " and" +this.subject_id);
                    singleConsecutiveSessionItemController.showConsecutiveSessionInformation(consecutiveSessionViewModelList.get(i),this.locationHallLab); // location Data should be got from Soyza's part


                    locationsVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - preferred room for subject Loading ======================================");
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No consecutive sessions are found in the database");
            locationsVBox.getChildren().clear();

            // this means that no consecutive session is found
            // so we gonna display that no consecutive sessions are found
            Node nodeThatSaysNoFacultyFound;
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ConsecutiveSessionsInSameLocation/SingleConsecutiveSessionItem/SingleConsecutiveSessionItemNoContent.fxml"));
                nodeThatSaysNoFacultyFound = (Node) loader.load();
                locationsVBox.getChildren().addAll(nodeThatSaysNoFacultyFound);
            } catch (IOException e) {
                System.out.println("Error - SingleConsecutiveSessionItemNoContent.fxml Loading ======================================");
                e.printStackTrace();
            }
        }


    } //


    // -------------------------------------------------------------------


    public void setOnActionBtnSearch(MouseEvent mouseEvent) {
        System.out.println("search clicked");
    }
}
