package TimeTableGeneratorDesktopApp.TimeTableGeneration.HallView;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.Location;

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

        ObservableList<Hall> locationList = getLocationListList();

        // Populate the rows like a table
        Node[] nodes = new Node[locationList.size()];

        if (locationList.size() > 0) {
            for (int i = 0; i < locationList.size(); i++) {
                try {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/TimeTableGeneration/SingleTImeTableStructure/TimeTableStructure.fxml"));

                    nodes[i] = (Node) loader.load();
                    TimeTableStructureController timeTableStructureController = loader.getController();

                    timeTableStructureController.showLocation(locationList.get(i)); // subject id should be got from Menura's part

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

    public ObservableList<Hall> getLocationListList() {

        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<Hall> locationList = FXCollections.observableArrayList();
        Connection conn =  databaseHelper.getConnection();
        String query;
        query = "SELECT * FROM location";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            Hall hall;
            while (rs.next()) {
                hall = new Hall(
                        rs.getInt("location_id"),
                        rs.getString("location_name"),
                        rs.getInt("location_capacity"),
                        rs.getInt("location_floor"),
                        rs.getString("location_condition"),
                        rs.getString("location_delete_status"),
                        rs.getString("location_timestamp"),
                        rs.getString("location_created"),
                        rs.getInt("building_building_id"),
                        rs.getInt("tag_tag_id")

                );
                locationList.add(hall);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return locationList;
    }
}
