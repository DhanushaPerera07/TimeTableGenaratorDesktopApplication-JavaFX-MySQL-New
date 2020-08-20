package TimeTableGeneratorDesktopApp.Subjects.SubjectForm;

import TimeTableGeneratorDesktopApp.Lecturers.lecturersController;
import TimeTableGeneratorDesktopApp.Subjects.subjectsController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SubjectFormController implements Initializable {

    public static int idmodule;
    public static String moduleName;
    public static String moduleCode;
    public static String offeredYear;
    public static String offeredSemester;
    public static int lecHour;
    public static int tuteHour;
    public static int labHour;
    public static int evaluationHour;

    @FXML
    private TextField tfModuleCode;

    @FXML
    private TextField tfModuleName;

    @FXML
    private Button addModuleBtn;

    @FXML
    private Button updateModuleBtn;

    @FXML
    private Button deleteModuleBtn;

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

    @FXML
    private Label moduleLabel;

    TimeTableGeneratorDesktopApp.Subjects.subjectsController subjectsController = new subjectsController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (subjectsController.offeredYear.equals("")){
            updateModuleBtn.setVisible(false);
            deleteModuleBtn.setVisible(false);
            addModuleBtn.setVisible(true);

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
                    0,1,2,3,4
            );

            comboSelectTuteHourBox.getItems().removeAll(comboSelectTuteHourBox.getItems());
            comboSelectTuteHourBox.getItems().addAll(
                    0,1,2,3,4
            );

            comboSelectLabHourBox.getItems().removeAll(comboSelectLabHourBox.getItems());
            comboSelectLabHourBox.getItems().addAll(
                    0,1,2,3,4
            );

            comboSelectEvaluationHourBox.getItems().removeAll(comboSelectEvaluationHourBox.getItems());
            comboSelectEvaluationHourBox.getItems().addAll(
                    0,1,2,3,4
            );
        }
        else {

            updateModuleBtn.setVisible(true);
            deleteModuleBtn.setVisible(true);
            addModuleBtn.setVisible(false);

            moduleLabel.setText("Module - update a module");
            idmodule = subjectsController.idmodule;
            moduleName = subjectsController.moduleName;
            tfModuleName.setText(moduleName);
            moduleCode = subjectsController.moduleCode;
            tfModuleCode.setText(moduleCode);
            offeredYear = subjectsController.offeredYear;
            offeredSemester = subjectsController.offeredSemester;
            lecHour = subjectsController.lecHour;
            tuteHour = subjectsController.tuteHour;
            labHour = subjectsController.labHour;
            evaluationHour = subjectsController.evaluationHour;

            comboSelectYearBox.getItems().removeAll(comboSelectYearBox.getItems());
            comboSelectYearBox.setPromptText(offeredYear);
            comboSelectYearBox.getItems().addAll(
                    "Year 1 ", "Year 2", "Year 3", "Year 4"
            );

            comboSelectSemesterBox.getItems().removeAll(comboSelectSemesterBox.getItems());
            comboSelectSemesterBox.setPromptText(offeredSemester);
            comboSelectSemesterBox.getItems().addAll(
                    "Semester 1", "Semester 2"
            );

            comboSelectLecHourBox.getItems().removeAll(comboSelectLecHourBox.getItems());
            comboSelectLecHourBox.setPromptText(String.valueOf(lecHour));
            comboSelectLecHourBox.getItems().addAll(
                    0,1,2,3,4
            );

            comboSelectTuteHourBox.getItems().removeAll(comboSelectTuteHourBox.getItems());
            comboSelectTuteHourBox.setPromptText(String.valueOf(tuteHour));
            comboSelectTuteHourBox.getItems().addAll(
                    0,1,2,3,4
            );

            comboSelectLabHourBox.getItems().removeAll(comboSelectLabHourBox.getItems());
            comboSelectLabHourBox.setPromptText(String.valueOf(labHour));
            comboSelectLabHourBox.getItems().addAll(
                    0,1,2,3,4
            );

            comboSelectEvaluationHourBox.getItems().removeAll(comboSelectEvaluationHourBox.getItems());
            comboSelectEvaluationHourBox.setPromptText(String.valueOf(evaluationHour));
            comboSelectEvaluationHourBox.getItems().addAll(
                    0,1,2,3,4
            );
        }


    }

    public void addModuleAction(ActionEvent actionEvent) {
        insertRecord();


        System.out.println("Add button clicked");
        Stage stage = (Stage) addModuleBtn.getScene().getWindow();
        stage.close();
    }

//    private void insertRecord(){
//        //String query = "INSERT INTO books VALUES ('" +tfModuleName.getText() +"','" +tfModuleCode.getText() +"'," + +"," +tfYear.getText() +"," +tfPages.getText() +")";
//
//        //executeQuery(query);
//    }

    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetabledb", "root","root");
            System.out.println("Database connected");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void selectOfferedYear(ActionEvent actionEvent) {
        System.out.println("Selected the year");
        offeredYear = comboSelectYearBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(offeredYear);
    }

    public void selectOfferedSemester(ActionEvent actionEvent) {
        System.out.println("Selected the semester");
        offeredSemester = comboSelectSemesterBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(offeredSemester);
    }

    public void selectLecHour(ActionEvent actionEvent) {
        System.out.println("Selected the lecture hour");
        lecHour = comboSelectLecHourBox.getSelectionModel().getSelectedIndex();
        System.out.println(lecHour);
    }

    public void selectTuteHour(ActionEvent actionEvent) {
        System.out.println("Selected the tutorial hour");
        tuteHour = comboSelectTuteHourBox.getSelectionModel().getSelectedIndex();
        System.out.println(tuteHour);
    }

    public void selectLabHour(ActionEvent actionEvent) {
        System.out.println("Selected the lab hour");
        labHour = comboSelectLabHourBox.getSelectionModel().getSelectedIndex();
        System.out.println(labHour);
    }

    public void selectEvaluationHour(ActionEvent actionEvent) {
        System.out.println("Selected the evaluation hour");
        evaluationHour = comboSelectEvaluationHourBox.getSelectionModel().getSelectedIndex();
        System.out.println(evaluationHour);
    }

    private void insertRecord(){
        String query = "INSERT INTO module (moduleName,moduleCode,offeredYear,offeredSemester,lecHour,tuteHour,labHour,evaluationHour) " +
                "VALUES ('" +tfModuleName.getText()+ "','" +tfModuleCode.getText()+ "','" +offeredYear+ "','" +offeredSemester+
                "'," +lecHour+ "," +tuteHour+ "," +labHour+
                "," +evaluationHour+ ") ";
        executeQuery(query);
        System.out.println("Data Inserted");
    }

    public void updateModuleAction(ActionEvent actionEvent) {
        System.out.println("Update clicked");
        updateRecord();

        Stage stage = (Stage) updateModuleBtn.getScene().getWindow();
        stage.close();
    }

    private void updateRecord(){
        String query = "UPDATE module SET moduleName = '" + tfModuleName.getText() + "', moduleCode = '" + tfModuleCode.getText()
                + "', offeredYear = '" + offeredYear + "', offeredSemester = '" + offeredSemester +"', lecHour = " + lecHour
                + ", tuteHour = " + tuteHour +", labHour = " + labHour + ", evaluationHour = " + evaluationHour
                + " WHERE idmodule = " + idmodule + "";
        executeQuery(query);
    }

    public void deleteModuleAction(ActionEvent actionEvent) {
        System.out.println("Delete clicked");
        deleteRecord();

        Stage stage = (Stage) deleteModuleBtn.getScene().getWindow();
        stage.close();

    }

    private void deleteRecord(){
        String query = "DELETE FROM module WHERE idmodule =" + idmodule + "";
        executeQuery(query);
    }
}
