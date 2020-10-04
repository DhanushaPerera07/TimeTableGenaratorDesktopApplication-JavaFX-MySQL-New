package TimeTableGeneratorDesktopApp.Sessions;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.Sessions.SessionItem.SessionItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class sessionController implements Initializable {

    DatabaseHelper databaseHelper = new DatabaseHelper();

    @FXML
    private Button addLecturerBtn;


    @FXML
    private Pane sessionsPane;


    @FXML
    private VBox sessionsVBox;

    @FXML
    private ComboBox<String> filter1;

    @FXML
    private ComboBox<String> filter2;

    Sessions s;
    public static String filterType = "All";
    public static String filterValue = "";

    ArrayList<String> subjects = new ArrayList<String>();
    ArrayList<String> studentGroup = new ArrayList<String>();
    ArrayList<String> lecturers = new ArrayList<String>();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        filter1.getItems().removeAll(filter1.getItems());
        filter1.getItems().addAll(
                "All", "Lecturer", "Subject", "Group ID", "Duration"
        );

        print();

        createTablesSessions();

        showSessions();

    }

    public void showSessions(){
        ObservableList<Sessions> sessionsList = getSessionsList();

        Node[] nodes = new Node[sessionsList.size()];

        if (sessionsList.size() != 0) {
            for (int i = 0; i < sessionsList.size(); i++) {
                try {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/Sessions/SessionItem/SessionItem.fxml"));

                    nodes[i] = (Node) loader.load();
                    SessionItemController sessionItemController = loader.getController();
                    sessionItemController.showInformation(sessionsList.get(i));


                    sessionsVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - session Loading ======================================");
                    e.printStackTrace();
                }
            }
        } else {

            System.out.println("no sessions found");
        }
    }

    private void createTablesSessions() {
        String sessionsTable ="CREATE TABLE IF NOT EXISTS  session (" +
                "  `idsession` int NOT NULL AUTO_INCREMENT," +
                "  `sessionID` varchar(100) NOT NULL," +
                "  `sessionTag` varchar(45) NOT NULL," +
                "  `sessionStudentGroup` varchar(45) NOT NULL," +
                "  `sessionSubject` varchar(45) NOT NULL," +
                "  `sessionNoOfStudents` varchar(45) NOT NULL," +
                "  `sessionDuration` varchar(45) NOT NULL," +
                "  `sessionModuleCode` varchar(45) NOT NULL," +
                "        PRIMARY KEY (`idsession`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8";

        databaseHelper.executeQuery(sessionsTable);


        String session_lecturerTable = "CREATE TABLE IF NOT EXISTS session_lecturer (" +
                "  `idsession_lecturer` int NOT NULL AUTO_INCREMENT," +
                "  `sessionID` varchar(100) NOT NULL," +
                "  `sessionLecturerName` varchar(45) NOT NULL," +
                "  PRIMARY KEY (`idsession_lecturer`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8";
        databaseHelper.executeQuery(session_lecturerTable);

    }

    @FXML
    public void ActionEventAddSession(ActionEvent actionEvent) {
        System.out.println("Session button clicked");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SessionForm/sessionForm.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add Session");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(sessionsPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    String dropTempSessions = "Drop TABLE temp_session_lecturer";
                    databaseHelper.executeQuery(dropTempSessions);
                    showSessions();

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

   /* public Connection getConnection(){
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
    }*/

    public void print(){
        Connection connection = databaseHelper.getConnection();
        System.out.println("working");
    }

    @FXML
    public void getFilterValues(ActionEvent actionEvent) {
    }

    @FXML
    public void selectFilterType(ActionEvent actionEvent) {

        filterType = filter1.getSelectionModel().getSelectedItem().toString();

        if(filterType.equals("All")){
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.getItems().addAll(
                    "All"
            );
        }
        else if (filterType.equals("Lecturer")){
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.setItems(FXCollections.observableArrayList(getLecturer()));
        }

        else if (filterType.equals("Subject")){
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.setItems(FXCollections.observableArrayList(getSubjects()));
        }
        else if (filterType.equals("Group ID")){
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.setItems(FXCollections.observableArrayList(getStudentGroups()));
        }
        else if (filterType.equals("Duration")){
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.getItems().addAll(
                    "1","2","3","4","5","6"
            );
        }
    }

    public List<String> getLecturer(){
        Connection con = databaseHelper.getConnection();
        try {
            Statement st;
            ResultSet rs;

            st = con.createStatement();
            st.executeQuery("SELECT lecturerName FROM lecturer ORDER BY lecturerName");
            rs = st.getResultSet();

            while (rs.next()){
                String a = rs.getString("lecturerName");
                lecturers.add(a);
            }
            return lecturers;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getStudentGroups(){
        Connection con = databaseHelper.getConnection();
        try{
            Statement st;
            ResultSet rs;
            // assume that all objects were all properly defined
            st = con.createStatement();
            st.executeQuery("SELECT batchID FROM studentbatches ORDER BY batchID");
            rs = st.getResultSet();
            while(rs.next()){
                String c = rs.getString("batchID");
                studentGroup.add(c);
            }
            st.executeQuery("SELECT subGroupId FROM subgroups ORDER BY subGroupId");
            rs = st.getResultSet();
            while (rs.next()){
                String c = rs.getString("subGroupId");
                studentGroup.add(c);
            }
            return studentGroup;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getSubjects(){
        Connection con = databaseHelper.getConnection();
        try{
            Statement st;
            ResultSet rs;
            // assume that all objects were all properly defined
            st = con.createStatement();
            st.executeQuery("SELECT moduleName FROM module ORDER BY moduleName");
            rs = st.getResultSet();
            while(rs.next()){
                String c = rs.getString("moduleName");
                subjects.add(c);
            }
            return subjects;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }






    public ObservableList<Sessions> getSessionsList() {
        ObservableList<Sessions> sessionsList = FXCollections.observableArrayList();
        //Connection conn = getConnection();
        Connection conn = databaseHelper.getConnection();

        String query = "SELECT * FROM session ";
//        String query = "";


//        if(filterType.equals("All")){
//            query = "SELECT * FROM session ";
//        }
//        else if (filterType.equals("Lecturer")){
//            query = "Select * from session_lecturer WHERE sessionLecturerName = '" + filterValue + "'";
//        }
//        else if (filterType.equals("Subject")){
//            query = "Select * from session WHERE sessionSubject = '" + filterValue + "'";
//        }
//        else if (filterType.equals("Group ID")){
//            query = "Select * from session WHERE sessionStudentGroup = '" + filterValue + "'";
//        }
//        else if (filterType.equals("Duration")){
//            query = "Select * from session WHERE sessionDuration = '" + filterValue + "'";
//        }

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Sessions sessions;
            while (rs.next()) {
                sessions = new Sessions(
                        rs.getInt("idsession"),
                        rs.getString("sessionSubject"),
                        rs.getString("sessionModuleCode"),
                        rs.getString("sessionTag"),
                        rs.getString("sessionStudentGroup"),
                        rs.getInt("sessionNoOfStudents"),
                        rs.getInt("sessionDuration"),
                        rs.getString("sessionID")
                );
                sessionsList.add(sessions);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return sessionsList;
    }

    //    public void refreshLecturerListAction(ActionEvent actionEvent) {
//        showLecturers();
//    }
}
