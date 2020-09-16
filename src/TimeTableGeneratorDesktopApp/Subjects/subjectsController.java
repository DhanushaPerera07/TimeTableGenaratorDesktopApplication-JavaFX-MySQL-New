package TimeTableGeneratorDesktopApp.Subjects;

import TimeTableGeneratorDesktopApp.Lecturers.Lecturers;
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

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class subjectsController implements Initializable {

    public static int idmodule = 0;
    public static String moduleName = "";
    public static String moduleCode = "";
    public static String offeredYear = "";
    public static String offeredSemester = "";
    public static int lecHour = 0;
    public static int tuteHour = 0;
    public static int labHour = 0;
    public static int evaluationHour = 0;
    public static String filterType = "All";
    public static String filterValue = "";
    public static String query="";

    @FXML
    private Button addModuleBtn;

    @FXML
    private Button refreshModuleListBtn;

    @FXML
    private Pane subjectsPane;

    @FXML
    private TableColumn<Subjects, Integer> colTuteHour;

    @FXML
    private TableColumn<Subjects, String> colModuleCode;

    @FXML
    private TableColumn<Subjects, String> colYear;

    @FXML
    private TableColumn<Subjects, Integer> colLecHour;

    @FXML
    private TableColumn<Subjects, Integer> colEvaluatonHour;

    @FXML
    private TableColumn<Subjects, String> colModuleName;

    @FXML
    private TableColumn<Subjects, Integer> colLabHour;

    @FXML
    private TableView<Subjects> tvModules;

    @FXML
    private TableColumn<Subjects, String> colSemester;

    @FXML
    private ComboBox<String> filter1;

    @FXML
    private ComboBox<String> filter2;

    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setVisible(false);
        filter1.getItems().removeAll(filter1.getItems());
        filter1.getItems().addAll(
                "All", "Year"
        );

        showModules();
    }

    @FXML
    void ActionEventAddModule(ActionEvent event) {
        System.out.println("Add Module Button Clicked");
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SubjectForm/subjectForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add Module");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(subjectsPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showModules();
                        }
                    });
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

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

    public ObservableList<Subjects> getModuleList(){
        ObservableList<Subjects> moduleList = FXCollections.observableArrayList();
        Connection conn = getConnection();

//        String query = "SELECT * FROM module";
        if(filterType.equals("All")){
            query = "SELECT * FROM module";
        }
        else if (filterType.equals("Year")){
            query = "Select * from module WHERE offeredYear = '" + filterValue + "'";
        }

        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Subjects subjects;
            while (rs.next()){
                subjects = new Subjects(rs.getInt("idmodule"),rs.getString("moduleName"), rs.getString("moduleCode"),
                        rs.getString("offeredYear"), rs.getString("offeredSemester"),
                        rs.getInt("lecHour"), rs.getInt("tuteHour"),
                        rs.getInt("labHour"), rs.getInt("evaluationHour"));
                moduleList.add(subjects);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return moduleList;
    }

    public void showModules(){
        ObservableList<Subjects> list = getModuleList();

        colModuleName.setCellValueFactory(new PropertyValueFactory<Subjects, String>("moduleName"));
        colModuleCode.setCellValueFactory(new PropertyValueFactory<Subjects, String>("moduleCode"));
        colYear.setCellValueFactory(new PropertyValueFactory<Subjects, String>("offeredYear"));
        colSemester.setCellValueFactory(new PropertyValueFactory<Subjects, String>("offeredSemester"));
        colLecHour.setCellValueFactory(new PropertyValueFactory<Subjects, Integer>("lecHour"));
        colTuteHour.setCellValueFactory(new PropertyValueFactory<Subjects, Integer>("tuteHour"));
        colLabHour.setCellValueFactory(new PropertyValueFactory<Subjects, Integer>("labHour"));
        colEvaluatonHour.setCellValueFactory(new PropertyValueFactory<Subjects, Integer>("evaluationHour"));

        tvModules.setItems(list);
    }

    public void handleMouseClicked(MouseEvent mouseEvent) {
        Subjects subjects = tvModules.getSelectionModel().getSelectedItem();

        idmodule = subjects.getIdmodule();
        System.out.println(idmodule);
        moduleName = subjects.getModuleName();
        moduleCode = subjects.getModuleCode();
        offeredYear = subjects.getOfferedYear();
        offeredSemester = subjects.getOfferedSemester();
        lecHour = subjects.getLecHour();
        tuteHour = subjects.getTuteHour();
        labHour = subjects.getLabHour();
        evaluationHour = subjects.getEvaluationHour();


        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SubjectForm/subjectForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Edit Module Details");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(subjectsPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();


            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {

                            idmodule = 0;
                            moduleName = "";
                            moduleCode = "";
                            offeredYear = "";
                            offeredSemester = "";
                            lecHour = 0;
                            tuteHour = 0;
                            labHour = 0;
                            evaluationHour = 0;

                            showModules();
                        }
                    });
                }
            });

        }catch (Exception e){
            System.out.println("can't load new window");
            e.printStackTrace();
        }
    }

    public void getFilterValues(ActionEvent actionEvent) {
        filterValue = filter2.getSelectionModel().getSelectedItem().toString();
        filter2.setPromptText("Select");
        System.out.println(filterValue);
        showModules();
    }

    public void selectFilterType(ActionEvent actionEvent) {

        filterType = filter1.getSelectionModel().getSelectedItem().toString();

        if(filterType.equals("All")){
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.getItems().addAll(
                    "All"
            );
        }
        else if(filterType.equals("Year")) {
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.getItems().addAll(
                    "Year 1", "Year 2", "Year 3", "Year 4"
            );
        }
    }

//    public void refreshModuleListAction(ActionEvent actionEvent) {
//        showModules();
//    }
}
