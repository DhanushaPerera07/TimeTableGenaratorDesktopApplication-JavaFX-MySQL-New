package TimeTableGeneratorDesktopApp.Extra.AddSuitableLocations;

import TimeTableGeneratorDesktopApp.DatabaseHelper.StudentBatchesDatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SuitableRoomForGroupController;
import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
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
import java.util.ResourceBundle;

public class ShowAllStudentGroupsController implements Initializable {


    @FXML
    private Pane pane;

    @FXML
    private TableView<StudentBatches> tvBatches;

    @FXML
    private TableColumn<StudentBatches, String> batColYear;


    @FXML
    private TableColumn<StudentBatches, String> batColIntake;

    @FXML
    private TableColumn<StudentBatches, Integer> batColNoOfStd;

    @FXML
    private TableColumn<StudentBatches, String> batColFac;

    @FXML
    private TableColumn<StudentBatches, String> batColPro;


    @FXML
    private TableColumn<StudentBatches, String> batColSem;


    @FXML
    private TableColumn<StudentBatches, String> batColCen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBatches();
    }

    public void showBatches() {
        StudentBatchesDatabaseHelper studentBatchesDatabaseHelper = new StudentBatchesDatabaseHelper();

        ObservableList<StudentBatches> list = studentBatchesDatabaseHelper.getBatchesList();

        batColYear.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("year"));
        batColSem.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("semester"));
        batColIntake.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("batchID"));
        batColFac.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("faculty"));
        batColPro.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("programme"));
        batColCen.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("center"));
        batColNoOfStd.setCellValueFactory(new PropertyValueFactory<StudentBatches, Integer>("noofstd"));


        tvBatches.setItems(list);

    }

    public void handleMouseAction(MouseEvent mouseEvent) {
        System.out.println("Clicked a record in Tags table");

        try {
            StudentBatches studentBatches = tvBatches.getSelectionModel().getSelectedItem();

            System.out.println("Test sout of selected, BatchDbID: " + studentBatches.getId() + "BatchID: "+studentBatches.getBatchID());


            SuitableRoomForGroupController suitableRoomForGroupController = new SuitableRoomForGroupController();
            suitableRoomForGroupController.getInformationFromShowAllStudentBatchesUI(studentBatches);

            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/suitableRoomForGroup.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();


                Stage stage = new Stage();
                stage.setTitle("Add suitable locations for a student batch");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(pane.getScene().getWindow());
                stage.setResizable(false);
                stage.setScene(new Scene(root1));
                stage.show();

            }catch (Exception e){
                System.out.println("can't load new window");
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error: No records selected !");
            alert.setContentText("No records found or selected in the table to set suitable locations.");
            alert.show();
            e.printStackTrace();
        }

    }


}
