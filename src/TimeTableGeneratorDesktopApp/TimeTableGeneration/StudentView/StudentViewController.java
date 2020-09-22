package TimeTableGeneratorDesktopApp.TimeTableGeneration.StudentView;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.Sessions.Sessions;
import TimeTableGeneratorDesktopApp.Sessions.sessionController;
import TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm.subGroups;
import TimeTableGeneratorDesktopApp.TimeTableGeneration.SingleTImeTableStructure.TimeTableStructureController;
import TimeTableGeneratorDesktopApp.TimeTableGeneration.TimeTable;
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

    private String GroupId = "non";

    public void populateSubGroupRows(){

        timeTableVBox.getChildren().clear();


        ObservableList<TimeTable> sessionsList = getSessionList();

        // Populate the rows like a table
        Node[] nodes = new Node[sessionsList.size()];

        if (sessionsList.size() > 0) {
            for (int i = 0; i < sessionsList.size(); i++) {
                try {

                    TimeTable timeTable = sessionsList.get(i);

                    if (!GroupId.equals(timeTable.getGroup())){

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/TimeTableGeneration/SingleTImeTableStructure/TimeTableStructure.fxml"));

                        nodes[i] = (Node) loader.load();
                        TimeTableStructureController timeTableStructureController = loader.getController();

                        timeTableStructureController.showSessions(sessionsList.get(i), timeTable.getGroup());

                        timeTableVBox.getChildren().addAll(nodes[i]);
                        GroupId = timeTable.getGroup();
                        System.out.println(GroupId);

                    }

                } catch (IOException e) {
                    System.out.println("Error - preferred room for subject Loading ======================================");
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println(sessionsList.size() + "kudai");
        }
    }

    public ObservableList<TimeTable> getSessionList() {

        // ============================================ DATABASE PART ===================================================================================

        // database connection setup
        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<TimeTable> sessionsList = FXCollections.observableArrayList();
        Connection conn =  databaseHelper.getConnection();
        String query;
        query = "SELECT * FROM time_table";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            TimeTable timeTable;
            while (rs.next()) {
                timeTable = new TimeTable(
                        rs.getInt("Id"),
                        rs.getString("timeSlot"),
                        rs.getString("Module"),
                        rs.getString("tag"),
                        rs.getString("Hall"),
                        rs.getString("group"),
                        rs.getString("lecturer"),
                        rs.getString("sessionId")
                );
                sessionsList.add(timeTable);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return sessionsList;
    }
}
