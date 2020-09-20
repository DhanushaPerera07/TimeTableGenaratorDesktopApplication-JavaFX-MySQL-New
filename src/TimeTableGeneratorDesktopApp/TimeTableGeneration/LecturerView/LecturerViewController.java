package TimeTableGeneratorDesktopApp.TimeTableGeneration.LecturerView;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.Lecturers.Lecturers;
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

public class LecturerViewController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        populateLecturersRows();

    }

    @FXML
    private BorderPane borderPaneForTimeTables;

    @FXML
    private VBox timeTableVBox;


    public void populateLecturersRows(){

        timeTableVBox.getChildren().clear();

        ObservableList<Lecturers> lecturersList = getlecturersList();

        // Populate the rows like a table
        Node[] nodes = new Node[lecturersList.size()];

        if (lecturersList.size() > 0) {
            for (int i = 0; i < lecturersList.size(); i++) {
                try {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/TimeTableGeneration/SingleTImeTableStructure/TimeTableStructure.fxml"));

                    nodes[i] = (Node) loader.load();
                    TimeTableStructureController timeTableStructureController = loader.getController();

                    timeTableStructureController.showlecturers(lecturersList.get(i));

                    timeTableVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - preferred room for subject Loading ======================================");
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("Database Error...!");
        }
    }

    public ObservableList<Lecturers> getlecturersList() {

        // ============================================ DATABASE PART ===================================================================================

        // database connection setup
        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<Lecturers> lecturersList = FXCollections.observableArrayList();
        Connection conn =  databaseHelper.getConnection();
        String query;
        query = "SELECT * FROM Lecturer";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            Lecturers lecturers;
            while (rs.next()) {
                lecturers = new Lecturers(
                        rs.getInt("lid"),
                        rs.getInt("lecturerID"),
                        rs.getString("lecturerName"),
                        rs.getString("lecturerFaculty"),
                        rs.getString("lecturerDepartment"),
                        rs.getString("lecturerCenter"),
                        rs.getString("lecturerBuilding"),
                        rs.getInt("lecturerLevel"),
                        rs.getString("lecturerRank")

                );
                lecturersList.add(lecturers);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return lecturersList;
    }
}
