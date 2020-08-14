package TimeTableGeneratorDesktopApp.StudentBatches.BatchForm;

import TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController;
import com.sun.javafx.scene.control.InputField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;



public class BatchFormController implements Initializable {
    public static int rowID = 0;
    public static String year = "";
    public static String semester = "";
    public static String intake = "";
    public static String faculty = "";
    public static String programme = "";
    public static String center = "";
    public static String batchID = "";
    public static Integer noOfStudents = 0;

    @FXML
    private AnchorPane batchFormPane;

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
    private TextField tfBatchID;

    @FXML
    public Button submitAddBatch;


    @FXML
    private Button batchFormDelBtn;

    @FXML
    private Button batchFormUpdtBtn;

    @FXML
    private Button batchFormGrpBtn;



    TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController studentBatchesController = new studentBatchesController();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rowID = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.rowID;
        year = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.year;
        semester = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.semester;
        intake = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.intake;
        faculty = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.faculty;
        programme = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.programme;
        center = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.center;
        noOfStudents = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.noofstd;
        batchID = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.batchID;

        //If a new batch is going to be added.
        if(TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.rowID == 0){

            comboBoxYear.getItems().removeAll(comboBoxYear.getItems());
            comboBoxYear.setPromptText("Select");
            comboBoxYear.getItems().addAll(
                    "Year 1", "Year 2", "Year 3", "Year 4"
            );

            comBoxSem.getItems().removeAll(comBoxSem.getItems());
            comBoxSem.setPromptText("Select");
            comBoxSem.getItems().addAll(
                    "Semester 1", "Semester 2"
            );

            comBoxIntake.getItems().removeAll(comBoxIntake.getItems());
            comBoxIntake.setPromptText("Select");
            comBoxIntake.getItems().addAll(
                    "Regular Intake", "June Intake"
            );

            comBoxFac.getItems().removeAll(comBoxFac.getItems());
            comBoxFac.setPromptText("Select");
            comBoxFac.getItems().addAll(
                    "Faculty of Computing",
                    "Faculty of Business Management",
                    "Faculty of Engineering",
                    "Faculty of Humanities and sciences",
                    "Faculty of Graduate Studies and sciences",
                    "School of Architecture",
                    "Faculty of Hospitatlity and Culnary",
                    "School of Law"
            );

            comBoxCen.getItems().removeAll(comBoxCen.getItems());
            comBoxCen.setPromptText("Select");
            comBoxCen.getItems().addAll(
                    "Malabe", "Kandy" , "Matara", "Jaffna" , "Metro"
            );

            batchFormDelBtn.setVisible(false);
            batchFormUpdtBtn.setVisible(false);
            batchFormGrpBtn.setVisible(false);

    }else {
            submitAddBatch.setVisible(false);

            comboBoxYear.getItems().removeAll(comboBoxYear.getItems());
            comboBoxYear.setPromptText(year);
            comboBoxYear.getItems().addAll(
                    "Year 1", "Year 2", "Year 3", "Year 4"
            );

            comBoxSem.getItems().removeAll(comBoxSem.getItems());
            comBoxSem.setPromptText(semester);
            comBoxSem.getItems().addAll(
                    "Semester 1", "Semester 2"
            );

            comBoxIntake.getItems().removeAll(comBoxIntake.getItems());
            comBoxIntake.setPromptText(intake);
            comBoxIntake.getItems().addAll(
                    "Regular Intake", "June Intake"
            );

            comBoxFac.getItems().removeAll(comBoxFac.getItems());
            comBoxFac.setPromptText(faculty);
            comBoxFac.getItems().addAll(
                    "Faculty of Computing",
                    "Faculty of Business Management",
                    "Faculty of Engineering",
                    "Faculty of Humanities and sciences",
                    "Faculty of Graduate Studies and sciences",
                    "School of Architecture",
                    "Faculty of Hospitatlity and Culnary",
                    "School of Law"
            );


            comBoxCen.getItems().removeAll(comBoxCen.getItems());
            comBoxCen.setPromptText(center);
            comBoxCen.getItems().addAll(
                    "Malabe", "Kandy" , "Matara", "Jaffna" , "Metro"
            );

            comBoxPro.setPromptText(programme);

            tfNoOfStd.setText(noOfStudents.toString());
            tfBatchID.setText(batchID.toString());
        }

    }


    public void selectBatchFac(ActionEvent actionEvent){
        faculty = comBoxFac.getSelectionModel().getSelectedItem().toString();
        System.out.println(faculty);

        if(faculty == "Faculty of Computing"){
            comBoxPro.getItems().removeAll(comBoxPro.getItems());
            comBoxPro.getItems().addAll(
                    "Software Engineering",
                    "Computer Systems and Networking" ,
                    "Cyber Security",
                    "Interactive Media" ,
                    "Information Technology",
                    "Information Management System",
                    "Data Science"
            );

        }else if(faculty == "Faculty of Business Management"){
            comBoxPro.getItems().removeAll(comBoxPro.getItems());
            comBoxPro.getItems().addAll(
                    "Accounting and Finance",
                    "Human Capital Management",
                    "Business Analytics",
                    "Logistics and Supply Management",
                    "Marketing Management",
                    "Business Management",
                    "Management Information Systems"
            );
        }else if(faculty == "Faculty of Engineering"){
            comBoxPro.getItems().removeAll(comBoxPro.getItems());
            comBoxPro.getItems().addAll(
                    "Civil Engineering",
                    "Electrical and Electronic Engineering",
                    "Mechanical Engineering",
                    "Material Engineering"
            );
        }else if(faculty == "Faculty of Humanities and sciences") {
            comBoxPro.getItems().removeAll(comBoxPro.getItems());
            comBoxPro.getItems().addAll(
                    "BED(Hons) in biological Sciences",
                    "BED(Hons) in English",
                    "BED(Hons) in physical sciences",
                    "BSC(Hons) in Nursing"
            );
        } else {
            comBoxPro.getItems().removeAll(comBoxPro.getItems());
            comBoxPro.getItems().addAll(
                    "Program 1",
                    "Program 2",
                    "Program 3",
                    "Program 4"
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



    public void selectBatchPro(ActionEvent actionEvent){
        programme = comBoxPro.getSelectionModel().getSelectedItem().toString();
        comBoxPro.setPromptText("Select");
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
        noOfStudents = 0;
        batchID = "";

        System.out.println(tfNoOfStd.getText());
        Stage stage = (Stage) submitAddBatch.getScene().getWindow();
        stage.close();


    }

    public void insertRecord(){

        String query = "INSERT INTO studentbatches (year,semester,intake,faculty,programme,center,noofstd,batchID) " +
                "VALUES ('" +year+ "','" +semester+ "','" +intake+ "','" +faculty+
                "','" +programme+ "','" +center+ "'," +tfNoOfStd.getText()+
                ",'" +tfBatchID.getText()+ "') ";

        executeQuery(query);

//        String queryBatchStats = "INSERT INTO batchStats (batch,nofStudents) VALUES (" +rowID+ "," +tfNoOfStd.getText()+ ")";
//        executeQuery(queryBatchStats);

    }

    @FXML
    private void deleteRecord(ActionEvent actionEvent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete?");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            String query = "DELETE from studentbatches WHERE id =" +rowID+ "";
            executeQuery(query);
            String query2 = "DELETE from batchstats WHERE batch =" +rowID+ "";
            executeQuery(query2);
            Stage stage = (Stage) batchFormDelBtn.getScene().getWindow();
            stage.close();
        }

    }


    @FXML
    public void generateBatchID(ActionEvent actionEvent){

        String generatedYear;
        String generatedSem;
        String generatedFac;
        String generatedIntake;
        String generatePro;

        if(year.equals("Year 1")){
            generatedYear = "Y1";
        }else if(year.equals("Year 2")){
            generatedYear = "Y2";
        }else if(year.equals("Year 3")){
             generatedYear = "Y3";
        }else if(year.equals("Year 4")){
            generatedYear="Y4";
        }else{
            generatedYear="-";
        }

        if(semester.equals("Semester 1")){
            generatedSem="S1";
        }else if(semester.equals("Semester 2")){
            generatedSem="S2";
        }else{
            generatedSem="-";
        }



        if(programme.equals("Software Engineering")){
            generatePro="SE";
        }else if(programme.equals("Computer Systems and Networking")){
            generatePro="CSN";
        }else if(programme.equals("Cyber Security")){
            generatePro="CS";
        }else if(programme.equals("Interactive Media")){
            generatePro="IM";
        }else if(programme.equals("Information Technology")){
            generatePro="IT";
        }else if(programme.equals("Information Management System")){
            generatePro="IMS";
        }else if(programme.equals("Data Science")){
            generatePro="DS";
        }else if(programme.equals("Accounting and Finance")){
            generatePro="AF";
        }else if(programme.equals("Human Capital Management")){
            generatePro="HCM";
        }else if(programme.equals("Business Analytics")){
            generatePro="BA";
        }else if(programme.equals("Logistics and Supply Management")){
            generatePro="LSM";
        }else if(programme.equals("Marketing Management")){
            generatePro="MM";
        }else if(programme.equals("Business Management")){
            generatePro="BM";
        }else if(programme.equals("Management Information Systems")){
            generatePro="MIS";
        }else if(programme.equals("Civil Engineering")){
            generatePro="CE";
        }else if(programme.equals("Electrical and Electronic Engineering")){
            generatePro="EEE";
        }else if(programme.equals("Mechanical Engineering")){
            generatePro="MaE";
        }else if(programme.equals("Material Engineering")){
            generatePro="MeE";
        }else if(programme.equals("BED(Hons) in biological Sciences")){
            generatePro="BioS";
        }else if(programme.equals("BED(Hons) in English")){
            generatePro="Eng";
        }else if(programme.equals("BED(Hons) in physical sciences")){
            generatePro="PS";
        }else if(programme.equals("BSC(Hons) in Nursing")){
            generatePro="NUR";
        }else if(programme.equals("Program 1")){
            generatePro="PR1";
        }else if(programme.equals("Program 2")){
            generatePro="PR2";
        }else if(programme.equals("Program 3")){
            generatePro="PR3";
        }else if(programme.equals("Program 4")){
            generatePro="PR4";
        }else{
            generatePro="-";
        }



        if (faculty.equals("Faculty of Computing")){
            generatedFac = "IT";
        }else if(faculty.equals("Faculty of Business Management")){
            generatedFac= "BM";
        }else if(faculty.equals("Faculty of Engineering")){
            generatedFac="EN";
        }else if(faculty.equals("Faculty of Humanities and sciences")){
            generatedFac= "HS";
        }else if(faculty.equals("Faculty of Graduate Studies and sciences")){
             generatedFac="GS";
        }else if(faculty.equals("School of Architecture")){
            generatedFac="AR";
        }else if(faculty.equals("Faculty of Hospitatlity and Culnary")){
            generatedFac="HC";
        }else if(faculty.equals("School of Law")){
            generatedFac="SL";
        }else{
            generatedFac="-";
        }

        if(intake.equals("Regular Intake")){
            generatedIntake = "01";
        }else if(intake.equals("June Intake")){
            generatedIntake = "02";
        }else {
            generatedIntake="-";
        }

        String generatedBatchID = generatedYear+"." +generatedSem+ "." +generatePro+ "." +generatedIntake;

        tfBatchID.setText(generatedBatchID);
    }


    @FXML
    public void updateRecord(ActionEvent actionEvent){


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Edit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            String query = "UPDATE studentbatches SET year = '" +year+ "', semester = '" +semester+
                    "', intake = '" +intake+ "', faculty = '" +faculty+ "', programme = '"
                    +programme+ "', center = '" +center+ "', noofstd = " +tfNoOfStd.getText()+ ", batchID = '" +tfBatchID.getText()+
                    "' WHERE id = " +rowID+ "";
            executeQuery(query);


//            String queryBatchStats = "INSERT INTO batchStats (batch,nofStudents) VALUES (" +rowID+ "," +noOfStudents+ ")";
//            executeQuery(queryBatchStats);
//

            Stage stage = (Stage) batchFormUpdtBtn.getScene().getWindow();
            stage.close();
        }

    }






    @FXML
    void ActionEventGroupBatchBtn(ActionEvent event) {

        String queryBatchStats = "INSERT INTO batchStats (batch,nofStudents) VALUES (" +rowID+ "," +noOfStudents+ ")";
        executeQuery(queryBatchStats);


        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/StudentBatches/subGroupForm/subGroupForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Manage sub groups of the batch");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(batchFormPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();


        }catch (Exception e){
            System.out.println("can't load new window");
            e.printStackTrace();
        }
    }



}

