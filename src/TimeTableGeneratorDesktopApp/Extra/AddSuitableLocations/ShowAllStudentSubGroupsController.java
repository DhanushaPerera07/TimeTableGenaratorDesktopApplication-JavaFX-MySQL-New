package TimeTableGeneratorDesktopApp.Extra.AddSuitableLocations;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SuitableRoomForGroupController;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SuitableRoomForSubGroupController;
import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm.subGroups;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ShowAllStudentSubGroupsController implements Initializable {


    @FXML
    private Pane pane;

    @FXML
    private TableView<subGroups> tvSubGroups;
    @FXML
    private TableColumn<subGroups, String> colSubId;
    @FXML
    private TableColumn<subGroups, Integer> colNoS;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showSubGroups();
    }


    public void showSubGroups() {
        ObservableList<subGroups> list = getSubGroupList();
        colSubId.setCellValueFactory(new PropertyValueFactory<subGroups, String>("subGroupId"));
        colNoS.setCellValueFactory(new PropertyValueFactory<subGroups, Integer>("NofStudents"));
        tvSubGroups.setItems(list);
    }

    public ObservableList<subGroups> getSubGroupList() {

        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<subGroups> subGroupList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();

        String  query = "SELECT * FROM subgroups ORDER BY subGroupId ASC;";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            subGroups subGroups;
            while (rs.next()) {
                subGroups = new subGroups(
                        rs.getInt("id"),
                        rs.getString("subGroupId"),
                        rs.getInt("NofStudents"),
                        rs.getInt("batchID")
                );
                subGroupList.add(subGroups);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return subGroupList;
    }


    @FXML
    void handleMouseAction(MouseEvent event) {
        System.out.println("Clicked a record in Tags table");

        try {
            subGroups studentSubGroup = tvSubGroups.getSelectionModel().getSelectedItem();

            System.out.println("Test sout of selected, studentSubGroupDbID: " + studentSubGroup.getId() + "studentSubGroupBatchID: "+ studentSubGroup.getBatchID());


            SuitableRoomForSubGroupController suitableRoomForSubGroupController = new SuitableRoomForSubGroupController();
            suitableRoomForSubGroupController.getInformationFromShowAllStudentSubGroupUI(studentSubGroup);

            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/suitableRoomForSubGroup.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();


                Stage stage = new Stage();
                stage.setTitle("Add suitable locations for a student sub group");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(pane.getScene().getWindow());
                stage.setResizable(false);
                stage.setScene(new Scene(root1));
                stage.show();

            }catch (Exception e){
                System.out.println("can't load - suitable locations for a student sub group window");
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error: No records found or selected !");
            alert.setContentText("No records found or selected in the table to set suitable locations.");
            alert.show();
            e.printStackTrace();
        }

    }

}
