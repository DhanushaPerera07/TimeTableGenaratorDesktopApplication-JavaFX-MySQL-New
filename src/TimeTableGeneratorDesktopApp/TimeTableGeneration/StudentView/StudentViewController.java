package TimeTableGeneratorDesktopApp.TimeTableGeneration.StudentView;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentViewController implements Initializable {

    @FXML
    private BorderPane borderPaneForTimeTables;

    @FXML
    private VBox timeTableVBox;

    private String GroupId = "non";

    private int xx = 1;

    ObservableList<String> GroupIdList = FXCollections.observableArrayList();

    ArrayList<String> a = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        populateSubGroupRows();

    }

    public void populateSubGroupRows(){

        timeTableVBox.getChildren().clear();

        ArrayList<String> sessionsList =getSessionList();

        Node[] nodes = new Node[sessionsList.size()];

        if (sessionsList.size() > 0) {
            for (int i = 0; i < sessionsList.size(); i++) {

                try {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/TimeTableGeneration/SingleTImeTableStructure/TimeTableStructure.fxml"));

                    nodes[i] = (Node) loader.load();
                    TimeTableStructureController timeTableStructureController = loader.getController();

                    timeTableStructureController.showSessions(sessionsList.get(i));

                    timeTableVBox.getChildren().addAll(nodes[i]);


                } catch (IOException e) {
                    System.out.println("Error - preferred room for subject Loading ======================================");
                    e.printStackTrace();
                }
                System.out.println(sessionsList.get(i));

            }
        }else{
            System.out.println(sessionsList.size() + "Database Error...!");
        }

    }

    public ArrayList<String> getSessionList() {

        DatabaseHelper databaseHelper = new DatabaseHelper();


        Connection conn =  databaseHelper.getConnection();
        String query;

        query = "SELECT DISTINCT `group` FROM time_table";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            TimeTable timeTable;
            while (rs.next()) {
                String b = rs.getString("group");
                a.add(b);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return a;
    }
}
