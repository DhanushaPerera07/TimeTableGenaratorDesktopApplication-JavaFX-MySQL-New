package TimeTableGeneratorDesktopApp.Extra.AddSuitableLocations;

import TimeTableGeneratorDesktopApp.DatabaseHelper.StudentBatchesDatabaseHelper;
import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowAllStudentGroupsController implements Initializable {

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
    }
}
