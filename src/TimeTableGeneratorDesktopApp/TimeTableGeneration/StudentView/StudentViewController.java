package TimeTableGeneratorDesktopApp.TimeTableGeneration.StudentView;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
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

public class StudentViewController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        populateSubGroupRows();

    }

    @FXML
    private BorderPane borderPaneForTimeTables;

    @FXML
    private VBox timeTableVBox;


    public void populateSubGroupRows(){

        timeTableVBox.getChildren().clear();

        ObservableList<subGroups> subGroupList = getSubGroupList();

        // Populate the rows like a table
        Node[] nodes = new Node[subGroupList.size()];

        if (subGroupList.size() > 0) {
            for (int i = 0; i < subGroupList.size(); i++) {
                try {

                    System.out.println(subGroupList.size() + "wishslai");

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/TimeTableGeneration/SingleTImeTableStructure/TimeTableStructure.fxml"));

                    nodes[i] = (Node) loader.load();
                    TimeTableStructureController timeTableStructureController = loader.getController();

                    timeTableStructureController.showSubGroups(subGroupList.get(i)); // subject id should be got from Menura's part

                    timeTableVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - preferred room for subject Loading ======================================");
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println(subGroupList.size() + "kudai");
        }
    }

    public ObservableList<subGroups> getSubGroupList() {

        // ============================================ DATABASE PART ===================================================================================

        // database connection setup
        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<subGroups> subGroupList = FXCollections.observableArrayList();
        Connection conn =  databaseHelper.getConnection();
        String query;
        query = "SELECT * FROM subgroups";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            subGroups subGroup;
            while (rs.next()) {
                subGroup = new subGroups(
                        rs.getInt("id"),
                        rs.getString("subGroupId"),
                        rs.getInt("NofStudents"),
                        rs.getInt("batchID")

                );
                subGroupList.add(subGroup);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return subGroupList;
    }
}
