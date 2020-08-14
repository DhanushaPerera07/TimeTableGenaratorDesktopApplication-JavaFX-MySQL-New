package TimeTableGeneratorDesktopApp.Subjects.SubjectForm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class SubjectFormController implements Initializable {

    @FXML
    private TextField tfModuleCode;

    @FXML
    private TextField tfModuleName;

    @FXML
    private Button addModuleBtn;

    @FXML
    private ComboBox<Integer> comboSelectLecHourBox;

    @FXML
    private ComboBox<Integer> comboSelectTuteHourBox;

    @FXML
    private ComboBox<Integer> comboSelectLabHourBox;

    @FXML
    private ComboBox<String> comboSelectSemesterBox;

    @FXML
    private ComboBox<String> comboSelectYearBox;

    @FXML
    private ComboBox<Integer> comboSelectEvaluationHourBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboSelectYearBox.getItems().removeAll(comboSelectYearBox.getItems());
        comboSelectYearBox.getItems().addAll(
                "Year 1 ", "Year 2", "Year 3", "Year 4"
        );

        comboSelectSemesterBox.getItems().removeAll(comboSelectSemesterBox.getItems());
        comboSelectSemesterBox.getItems().addAll(
                "Semester 1", "Semester 2"
        );

        comboSelectLecHourBox.getItems().removeAll(comboSelectLecHourBox.getItems());
        comboSelectLecHourBox.getItems().addAll(
                1,2,3,4
        );

        comboSelectTuteHourBox.getItems().removeAll(comboSelectTuteHourBox.getItems());
        comboSelectTuteHourBox.getItems().addAll(
                1,2,3,4
        );

        comboSelectLabHourBox.getItems().removeAll(comboSelectLabHourBox.getItems());
        comboSelectLabHourBox.getItems().addAll(
                1,2,3,4
        );

        comboSelectEvaluationHourBox.getItems().removeAll(comboSelectEvaluationHourBox.getItems());
        comboSelectEvaluationHourBox.getItems().addAll(
                1,2,3,4
        );

    }

    public void addModuleAction(ActionEvent actionEvent) {
        System.out.println("Add button clicked");
        Stage stage = (Stage) addModuleBtn.getScene().getWindow();
        stage.close();
    }

    private void insertRecord(){
        //String query = "INSERT INTO books VALUES ('" +tfModuleName.getText() +"','" +tfModuleCode.getText() +"'," + +"," +tfYear.getText() +"," +tfPages.getText() +")";

        //executeQuery(query);
    }
}
