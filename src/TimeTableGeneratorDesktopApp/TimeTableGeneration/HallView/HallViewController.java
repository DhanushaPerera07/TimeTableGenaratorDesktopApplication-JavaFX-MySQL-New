package TimeTableGeneratorDesktopApp.TimeTableGeneration.HallView;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.Location;
import TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm.subGroups;
import TimeTableGeneratorDesktopApp.TimeTableGeneration.SingleTImeTableStructure.TimeTableStructureController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HallViewController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        populateHalls();

    }

    @FXML
    private BorderPane borderPaneForTimeTables;

    @FXML
    private VBox timeTableVBox;


    public void populateHalls(){

        timeTableVBox.getChildren().clear();

        ObservableList<Location> locationList = getlocationListList();

        // Populate the rows like a table
        Node[] nodes = new Node[locationList.size()];

        if (locationList.size() > 0) {
            for (int i = 0; i < locationList.size(); i++) {
                try {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/TimeTableGeneration/SingleTImeTableStructure/TimeTableStructure.fxml"));

                    nodes[i] = (Node) loader.load();
                    TimeTableStructureController timeTableStructureController = loader.getController();

                    timeTableStructureController.showLocations(locationList.get(i)); // subject id should be got from Menura's part

                    timeTableVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - preferred room for subject Loading ======================================");
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("Database Problem...!");
        }
    }

    public ObservableList<Location> getlocationListList() {

        // ============================================ DATABASE PART ===================================================================================

        // database connection setup
        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<Location> locationList = FXCollections.observableArrayList();
        Connection conn =  databaseHelper.getConnection();
        String query;
        query = "SELECT * FROM subgroups";

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
