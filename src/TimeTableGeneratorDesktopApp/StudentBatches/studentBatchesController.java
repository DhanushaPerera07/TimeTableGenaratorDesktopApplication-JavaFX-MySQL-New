package TimeTableGeneratorDesktopApp.StudentBatches;

import TimeTableGeneratorDesktopApp.Main;
import javafx.application.Platform;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class studentBatchesController implements Initializable {
    public static int rowID = 0;
    public static String year = "";
    public static String semester = "";
    public static String intake = "";
    public static String faculty = "";
    public static String programme = "";
    public static String center = "";
    public static int noofstd = 0;
    public static String batchID = "";
    public static String filterType = "All";
    public static String filterValue = "";
    public static String query="";


    @FXML
    private Button addBathcBtn;

    @FXML
    private Button refreshBatches;

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



    @FXML
    private Pane studentsPane;

    @FXML
    private Button statisticsBtn;



    @FXML
    private ComboBox<String> CBFilter;

    @FXML
    private ComboBox<String> CBFilter2;


    @FXML
    void actionRefreshButton(ActionEvent actionEvent){
        showBatches();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        refreshBatches.setVisible(false);
        CBFilter.getItems().removeAll(CBFilter.getItems());
        CBFilter.getItems().addAll(
                "All","Year", "Semester", "Intake", "Faculty", "Programme", "Center"
        );


        showBatches();
    }

    @FXML
    public void selectFilterType(ActionEvent actionEvent){

        filterType = CBFilter.getSelectionModel().getSelectedItem().toString();

        if(filterType.equals("All")){
            CBFilter2.getItems().removeAll(CBFilter2.getItems());
            CBFilter2.setPromptText("Select");
            CBFilter2.getItems().addAll(
                    "All"
            );
        }
        else if(filterType.equals("Year")){
            CBFilter2.getItems().removeAll(CBFilter2.getItems());
            CBFilter2.setPromptText("Select");
            CBFilter2.getItems().addAll(
                    "Year 1", "Year 2", "Year 3", "Year 4"
            );
        }else if(filterType.equals("Semester")){
            CBFilter2.getItems().removeAll(CBFilter2.getItems());
            CBFilter2.setPromptText("Select");
            CBFilter2.getItems().addAll(
                    "Semester 1", "Semester 2"
            );
        }else if(filterType.equals("Faculty")){
            CBFilter2.getItems().removeAll(CBFilter2.getItems());
            CBFilter2.setPromptText("Select");
            CBFilter2.getItems().addAll(
                    "Faculty of Computing",
                    "Faculty of Business Management",
                    "Faculty of Engineering",
                    "Faculty of Humanities and sciences",
                    "Faculty of Graduate Studies and sciences",
                    "School of Architecture",
                    "Faculty of Hospitatlity and Culnary",
                    "School of Law"
            );
        }else if(filterType.equals("Programme")){
            CBFilter2.getItems().removeAll(CBFilter2.getItems());
            CBFilter2.setPromptText("Select");
            CBFilter2.getItems().addAll(
                    "Software Engineering",
                    "Computer Systems and Networking" ,
                    "Cyber Security",
                    "Interactive Media" ,
                    "Information Technology",
                    "Information Management System",
                    "Data Science",
                    "Accounting and Finance",
                    "Human Capital Management",
                    "Business Analytics",
                    "Logistics and Supply Management",
                    "Marketing Management",
                    "Business Management",
                    "Management Information Systems",
                    "Accounting and Finance",
                    "Human Capital Management",
                    "Business Analytics",
                    "Logistics and Supply Management",
                    "Marketing Management",
                    "Business Management",
                    "Management Information Systems",
                    "BED(Hons) in biological Sciences",
                    "BED(Hons) in English",
                    "BED(Hons) in physical sciences",
                    "BSC(Hons) in Nursing",
                    "Program 1",
                    "Program 2",
                    "Program 3",
                    "Program 4"
            );
        }else if(filterType.equals("Center")){
            CBFilter2.getItems().removeAll(CBFilter2.getItems());
            CBFilter2.setPromptText("Select");
            CBFilter2.getItems().addAll(
                    "Malabe", "Kandy" , "Matara", "Jaffna" , "Metro"
            );
        }else if(filterType.equals("Intake")){
            CBFilter2.getItems().removeAll(CBFilter2.getItems());
            CBFilter2.setPromptText("Select");
            CBFilter2.getItems().addAll(
                    "Regular Intake", "June Intake"
            );
        }

    }


    @FXML
    void getFilterValue(ActionEvent actionEvent){
        filterValue = CBFilter2.getSelectionModel().getSelectedItem().toString();
        CBFilter2.setPromptText("Select");
        System.out.println(filterValue);
        showBatches();
    }

    @FXML
    void handleMouseAction(MouseEvent event) {
        StudentBatches batch = tvBatches.getSelectionModel().getSelectedItem();

        rowID = batch.getId();
        year = batch.getYear();
        semester = batch.getSemester();
        intake = batch.getIntake();
        faculty = batch.getFaculty();
        programme = batch.getProgramme();
        center = batch.getCenter();
        noofstd = batch.getNoofstd();
        batchID = batch.getBatchID();



        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BatchForm/batchForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Edit batch Details");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(studentsPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            year = "";
                            semester = "";
                            intake = "";
                            faculty = "";
                            programme = "";
                            center = "";
                            batchID = "";
                            noofstd = 0;
                            rowID=0;

                            showBatches();
                        }

                    });
                }
            });
        }catch (Exception e){
            System.out.println("can't load new window");
        }
    }


    @FXML
    void ActionEventAddBatchBtn(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BatchForm/batchForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Add a new batch");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(studentsPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            showBatches();
                        }

                    });
                }
            });

        }catch (Exception e){
            System.out.println("can't load new window");
            e.printStackTrace();
        }
    }


    public void setTips(){
        Tooltip tt = new Tooltip();
        tt.setText("Add a new Batch");
        tt.setStyle("-fx-font: normal bold 4 Langdon; "
                + "-fx-base: #AE3522; "
                + "-fx-text-fill: orange;");

        addBathcBtn.setTooltip(tt);
    }



    public void showBatches() {
        ObservableList<StudentBatches> list = getBatchesList();

        batColYear.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("year"));
        batColSem.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("semester"));
        batColIntake.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("batchID"));
        batColFac.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("faculty"));
        batColPro.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("programme"));
        batColCen.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("center"));
        batColNoOfStd.setCellValueFactory(new PropertyValueFactory<StudentBatches, Integer>("noofstd"));


        tvBatches.setItems(list);


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

    public ObservableList<StudentBatches> getBatchesList() {
        ObservableList<StudentBatches> studentBatchesList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        if(filterType.equals("All")){
            query = "SELECT * FROM studentBatches ORDER BY year";
        }else{
            query = "SELECT * from studentbatches WHERE " +filterType+ " = '" +filterValue+ "'";
        }

//        String query = "SELECT * FROM studentBatches";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            StudentBatches studentBatch;
            while (rs.next()) {
                studentBatch = new StudentBatches(rs.getInt("id"), rs.getString("year"), rs.getString("semester"),
                        rs.getString("intake"), rs.getString("faculty"),
                        rs.getString("programme"),
                        rs.getString("center"),
                        rs.getInt("noofstd"), rs.getString("batchID"));
                studentBatchesList.add(studentBatch);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentBatchesList;
    }




}
