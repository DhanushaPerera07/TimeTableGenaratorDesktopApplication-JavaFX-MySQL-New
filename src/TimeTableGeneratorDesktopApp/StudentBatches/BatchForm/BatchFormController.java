package TimeTableGeneratorDesktopApp.StudentBatches.BatchForm;

import TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController;
import com.sun.javafx.scene.control.InputField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;



public class BatchFormController implements Initializable {
    public static String year = "";
    public static String semester = "";
    public static String intake = "";
    public static String faculty = "";
    public static String programme = "";
    public static String center = "";
    public static Integer noOfStudents = 0;


    @FXML
    private ComboBox<String> comboBoxYear;

    @FXML
    private ComboBox<String> comBoxSem;

    @FXML
    private ComboBox<String> comBoxIntake;

    @FXML
    private ComboBox<String> comBoxFac;

    @FXML
    private ComboBox<String> comBoxPro;

    @FXML
    private ComboBox<String> comBoxCen;


    @FXML
    private TextField tfNoOfStd;

    @FXML
    public Button submitAddBatch;


    TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController studentBatchesController = new studentBatchesController();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.year.equals("")){
            comboBoxYear.getItems().removeAll(comboBoxYear.getItems());
            comboBoxYear.setPromptText("Select");
            comboBoxYear.getItems().addAll(
                    "Year 1 ", "Year 2", "Year 3", "Year 4"
            );

            comBoxSem.getItems().removeAll(comBoxSem.getItems());
            comBoxSem.setPromptText("Select");
            comBoxSem.getItems().addAll(
                    "Semester 1", "Semester 2"
            );

            comBoxIntake.getItems().removeAll(comBoxIntake.getItems());
            comBoxIntake.getItems().addAll(
                    "Regular Intake", "June Intake"
            );

            comBoxFac.getItems().removeAll(comBoxFac.getItems());
            comBoxFac.getItems().addAll(
                    "IT","BM","ENG","H&S","ARCH","HOS","NUR"
            );


            comBoxCen.getItems().removeAll(comBoxCen.getItems());
            comBoxCen.getItems().addAll(
                    "Malabe", "Kandy" , "Matara", "Jaffna" , "Metro"
            );


    }else {

            year = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.year;
            semester = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.semester;
            intake = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.intake;
            faculty = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.faculty;
            programme = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.programme;
//          programme = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.programme;
//          programme = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.programme;


            comboBoxYear.getItems().removeAll(comboBoxYear.getItems());
            comboBoxYear.setPromptText(TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.year);
            comboBoxYear.getItems().addAll(
                    "Year 1 ", "Year 2", "Year 3", "Year 4"
            );

            comBoxSem.getItems().removeAll(comBoxSem.getItems());
            comBoxSem.setPromptText(TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.semester);
            comBoxSem.getItems().addAll(
                    "Semester 1", "Semester 2"
            );

            comBoxIntake.getItems().removeAll(comBoxIntake.getItems());
            comBoxIntake.getItems().addAll(
                    "Regular Intake", "June Intake"
            );

            comBoxFac.getItems().removeAll(comBoxFac.getItems());
            comBoxFac.getItems().addAll(
                    "IT","BM","ENG","H&S","ARCH","HOS","NUR"
            );


            comBoxCen.getItems().removeAll(comBoxCen.getItems());
            comBoxCen.getItems().addAll(
                    "Malabe", "Kandy" , "Matara", "Jaffna" , "Metro"
            );
        }

    }




    @FXML
    public void selectBatchYear(ActionEvent actionEvent){
        year = comboBoxYear.getSelectionModel().getSelectedItem().toString();
        System.out.println(year);
    }
      public void selectBatchSem(ActionEvent actionEvent){
        semester = comBoxSem.getSelectionModel().getSelectedItem().toString();
        System.out.println(semester);
    }
      public void selectBatchIntake(ActionEvent actionEvent){
        intake = comBoxIntake.getSelectionModel().getSelectedItem().toString();
        System.out.println(intake);
    }

    public void selectBatchCen(ActionEvent actionEvent){
        center = comBoxCen.getSelectionModel().getSelectedItem().toString();
        System.out.println(center);
    }

    public void selectBatchFac(ActionEvent actionEvent){
         faculty = comBoxFac.getSelectionModel().getSelectedItem().toString();
        System.out.println(faculty);

        if(faculty == "IT"){
            comBoxPro.getItems().removeAll(comBoxPro.getItems());
            comBoxPro.getItems().addAll(
                    "SE", "CSN" , "CS", "IM" , "IT"
            );
        }else if(faculty == "BM"){
            comBoxPro.getItems().removeAll(comBoxPro.getItems());
            comBoxPro.getItems().addAll(
                    "BA", "BS"
            );
          }else {
            comBoxPro.getItems().removeAll(comBoxPro.getItems());
            comBoxPro.getItems().addAll(
                    "Elec", "Drawing"
            );
        }


    }

    public void selectBatchPro(ActionEvent actionEvent){
        programme = comBoxPro.getSelectionModel().getSelectedItem().toString();
        System.out.println(programme);
    }

    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetabledb", "root","root");
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


    public void BatchsubmitActionHandler(ActionEvent actionEvent){
        insertRecord();
        year = "";
        semester = "";
        intake = "";
        faculty = "";
        programme = "";
        center = "";

        System.out.println(tfNoOfStd.getText());
        Stage stage = (Stage) submitAddBatch.getScene().getWindow();
        stage.close();


    }

    public void insertRecord(){
//        int NoOfStudents = Integer.parseInt(tfNoOfStd.getText().toString());
        String query = "INSERT INTO studentbatches (year,semester,intake,faculty,programme,center,noofstd) " +
                "VALUES ('" +year+ "','" +semester+ "','" +intake+ "','" +faculty+ "','" +programme+ "','" +center+ "'," +tfNoOfStd.getText()+ ") ";
        executeQuery(query);

    }



}

