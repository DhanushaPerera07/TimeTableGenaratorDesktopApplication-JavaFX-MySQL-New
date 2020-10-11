package TimeTableGeneratorDesktopApp.TimeTableGeneration.HallView;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;

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
import java.util.ArrayList;
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

    ArrayList<String> a = new ArrayList<>();

    public void populateHalls(){

        timeTableVBox.getChildren().clear();

        ArrayList<String> locationList = getLocationListList();

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

    public ArrayList<String> getLocationListList() {

        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<Hall> locationList = FXCollections.observableArrayList();
        Connection conn =  databaseHelper.getConnection();
        String query;
        query = "SELECT DISTINCT `hall` FROM time_table";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            Hall hall;
            while (rs.next()) {
                String b = rs.getString("hall");
                a.add(b);
            }
        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return a;
    }
}
