package TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation;

import TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.SingleConsecutiveSessionInSameLocationItemAll.ConsecutiveSessionItemAllController;
import TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.SingleConsecutiveSessionItem.SingleConsecutiveSessionItemController;
import TimeTableGeneratorDesktopApp.DatabaseHelper.ConsecutiveSessionInSameLocationDatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.ConsecutiveSessionsDatabaseHelper;
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
import java.util.ResourceBundle;

public class ViewConsecutiveSessionsInSameLocationController implements Initializable {


    @FXML
    private BorderPane borderPaneLocationsHallsInsideBuildingMain;

    @FXML
    private Label txtHeaderLabsHalls;

    @FXML
    private TextField locationsHallsInsideSearchTxtBox;

    @FXML
    private Button btnSearchLocationsHallsInside;

    @FXML
    private ComboBox<String> FilterByComboBox;

    @FXML
    private VBox locationsVBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateRows();
    }


    public void populateRows(){


        locationsVBox.getChildren().clear();

        ConsecutiveSessionInSameLocationDatabaseHelper consecutiveSessionInSameLocationDatabaseHelper = new ConsecutiveSessionInSameLocationDatabaseHelper();
        ObservableList<ConsecutiveSessionViewAll> consecutiveSessionViewAllList = consecutiveSessionInSameLocationDatabaseHelper.getAllConsecutiveSessionInSameLocationTable();

        for (ConsecutiveSessionViewAll consecutiveSessionViewAll : consecutiveSessionViewAllList){
            // sysout check
            System.out.println("ConsecutiveSessionViewModel rec: " + consecutiveSessionViewAll.toString());

        }


        /**
         * Dynamically change the rows by getting data from the database
         * locationItemForLecturer.fxml is used as the UI, it acts as a customized data row
         * I pass the building object to the locationItemForLecturer.fxml and populate the view
         */
        // Populate the rows like a table
        Node[] nodes = new Node[consecutiveSessionViewAllList.size()];

        if (consecutiveSessionViewAllList.size() > 0) {  // location table is not empty, there are some locations (halls/ lab)
            for (int i = 0; i < consecutiveSessionViewAllList.size(); i++) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ConsecutiveSessionsInSameLocation/SingleConsecutiveSessionInSameLocationItemAll/ConsecutiveSessionItemAll.fxml"));

                    nodes[i] = (Node) loader.load();
                    ConsecutiveSessionItemAllController consecutiveSessionItemAllController = loader.getController();
                    //System.out.println("Test: locationList.get(i),this.subject_id: " + locationList.get(i) + " and" +this.subject_id);
                    consecutiveSessionItemAllController.showConsecutiveSessionAllInformation(consecutiveSessionViewAllList.get(i));

                    locationsVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - ConsecutiveSessionItemAll.fxml Loading ======================================");
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
                loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ConsecutiveSessionsInSameLocation/SingleConsecutiveSessionInSameLocationItemAll/ConsecutiveSessionItemAllNoContent.fxml"));
                nodeThatSaysNoFacultyFound = (Node) loader.load();
                locationsVBox.getChildren().addAll(nodeThatSaysNoFacultyFound);
            } catch (IOException e) {
                System.out.println("Error - ConsecutiveSessionItemAllNoContent.fxml Loading ======================================");
                e.printStackTrace();
            }
        }

    }


    @FXML
    void setOnActionBtnSearch(MouseEvent event) {

    }
}
