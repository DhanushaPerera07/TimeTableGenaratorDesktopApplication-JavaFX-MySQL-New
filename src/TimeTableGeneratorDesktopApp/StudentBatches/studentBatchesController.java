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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    public static String year = "";
    public static String semester = "";
    public static String intake = "";
    public static String faculty = "";
    public static String programme = "";
    public static String center = "";
    public static int noofstd = 0;


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
    void actionRefreshButton(ActionEvent actionEvent){
        showBatches();
    }


    @FXML
    void handleMouseAction(MouseEvent event) {
        StudentBatches batch = tvBatches.getSelectionModel().getSelectedItem();

        year = batch.getYear();
        semester = batch.getSemester();
        intake = batch.getIntake();
        faculty = batch.getFaculty();
        programme = batch.getProgramme();
        center = batch.getCenter();
        noofstd = batch.getNoofstd();



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


        }catch (Exception e){
            System.out.println("can't load new window");
            e.printStackTrace();
        }
    }

    public void subGroupButtonAction(javafx.event.ActionEvent actionEvent) throws IOException {

        URL fileURL = Main.class.getResource("/TimeTableGeneratorDesktopApp/StudentBatches/SubGroups/subGroups.fxml");
        if (fileURL == null) {
            throw new java.io.FileNotFoundException("FXML File can not be found");
        }else{
            Pane newPane = new FXMLLoader().load(fileURL);
            studentsPane.getChildren().addAll(newPane);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBatches();
    }

    public void showBatches() {
        ObservableList<StudentBatches> list = getBatchesList();

        batColYear.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("year"));
        batColSem.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("semester"));
        batColIntake.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("intake"));
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
        String query = "SELECT * FROM studentBatches";
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
