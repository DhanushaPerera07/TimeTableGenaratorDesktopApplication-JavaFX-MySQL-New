package TimeTableGeneratorDesktopApp.Extra.ParallelSessions;

import TimeTableGeneratorDesktopApp.Extra.NotAvailableTime.LecturerNATime.NATLecturers;
import TimeTableGeneratorDesktopApp.Sessions.Sessions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class ParallelSessionController implements Initializable {
    public static String session2 = "";
    public static String session1 = "";

    @FXML
    private TextField searchBox;

    @FXML
    private TextField searchBox2;



    @FXML
    private TableView<ParallelSession> pSessionsTV;

    @FXML
    private Label ses2Label;

    @FXML
    private TableView<Sessions> sessionsTV1;

    @FXML
    private TableView<Sessions> sessionsTV2;

    @FXML
    private TableColumn<ParallelSession, String> ses1ID;

    @FXML
    private TableColumn<Sessions, String> sessionNameCol2;

    @FXML
    private TableColumn<Sessions, String> sessionNameCol1;

    @FXML
    private TableColumn<ParallelSession, String> ses2ID;

    @FXML
    private TableColumn<Sessions, Integer> sessionIDCol2;

    @FXML
    private TableColumn<Sessions, Integer> sessionIDCol1;

    @FXML
    private Label ses1Label;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showSessions();
        createTable();
        showParallelSessions();
    }





    public ObservableList<Sessions> getSessionsList() {
        ObservableList<Sessions> sessionsList = FXCollections.observableArrayList();
        //Connection conn = getConnection();
        Connection conn = getConnection();

        String query = "SELECT * FROM session ";

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

    public ObservableList<ParallelSession> getParallelSessionsList() {
        ObservableList<ParallelSession> parallelSessionsList = FXCollections.observableArrayList();
        //Connection conn = getConnection();
        Connection conn = getConnection();

        String query = "SELECT * FROM parallel_sessions ";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ParallelSession parallelSession;
            while (rs.next()) {
                parallelSession = new ParallelSession(
                        rs.getInt("id"),
                        rs.getString("session1ID"),
                        rs.getString("session2ID")
                );
                parallelSessionsList.add(parallelSession);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return parallelSessionsList;
    }



    public void showParallelSessions() {
        ObservableList<ParallelSession> list = getParallelSessionsList();
        ses1ID.setCellValueFactory(new PropertyValueFactory<ParallelSession, String>("session1ID"));
        ses2ID.setCellValueFactory(new PropertyValueFactory<ParallelSession, String>("session2ID"));
        pSessionsTV.setItems(list);

    }


    public void showSessions() {
        ObservableList<Sessions> list = getSessionsList();
        sessionIDCol1.setCellValueFactory(new PropertyValueFactory<Sessions, Integer>("sessionID"));
        sessionNameCol1.setCellValueFactory(new PropertyValueFactory<Sessions, String>("sessionGenID"));
        sessionsTV1.setItems(list);
        sessionIDCol2.setCellValueFactory(new PropertyValueFactory<Sessions, Integer>("sessionID"));
        sessionNameCol2.setCellValueFactory(new PropertyValueFactory<Sessions, String>("sessionGenID"));
        sessionsTV2.setItems(list);
    }

    @FXML
    void handleMouseAction(MouseEvent event) {

        Sessions sessions = sessionsTV1.getSelectionModel().getSelectedItem();
         session1 = sessions.getSessionGenID();
        System.out.println(session1);
        try{
                ses1Label.setText(session1);


        }catch (Exception e){
            System.out.println("can't load new window");
        }
    }

    @FXML
    void handleMouseAction2(MouseEvent event) {
        Sessions sessions2 = sessionsTV2.getSelectionModel().getSelectedItem();
         session2 = sessions2.getSessionGenID();

        try{
            ses2Label.setText(session2);

        }catch (Exception e){
            System.out.println("can't load new window");
        }
    }

    @FXML
    private void deleteRecord(MouseEvent mouseEvent){

        ParallelSession parallelSession = pSessionsTV.getSelectionModel().getSelectedItem();
        int rowID = parallelSession.getId();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete?");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            String query = "DELETE from parallel_sessions WHERE id ="+rowID+"";
            executeQuery(query);
            showParallelSessions();
        }

    }


    @FXML
    public void insertParSession(){

        if (session1.equals("") || session2.equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please make sure to select 2 sessions");
            alert.show();
        }

        else if(session1.equals(session2)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Both the selected sessions are a same session");
            alert.show();
        }

        else{
            String query = "insert into parallel_sessions (session1ID, session2ID) VALUES ('" +session1+ "','" +session2+ "')";
            executeQuery(query);
            ses1Label.setText("Session 1");
            ses2Label.setText("Session 2");
            session2 = "";
            session1="";

            showParallelSessions();
        }

    }




    @FXML
    public void searchRecord(KeyEvent ke) {
        FilteredList<Sessions> filterData = new FilteredList<>(getSessionsList(), p -> true);
        searchBox.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
            filterData.setPredicate(session -> {
                if (newvalue == null || newvalue.isEmpty()) {
                    return true;
                }
                String typedText = newvalue.toLowerCase();
                if (session.getSessionGenID().toLowerCase().indexOf(typedText) != -1) {
                    return true;
                }
                return false;
            });
            SortedList<Sessions> sortedList = new SortedList<>(filterData);
            sortedList.comparatorProperty().bind(sessionsTV1.comparatorProperty());
            sessionsTV1.setItems(sortedList);

        });
    }

    @FXML
    public void searchRecord2(KeyEvent ke) {
        FilteredList<Sessions> filterData = new FilteredList<>(getSessionsList(), p -> true);
        searchBox2.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
            filterData.setPredicate(session -> {
                if (newvalue == null || newvalue.isEmpty()) {
                    return true;
                }
                String typedText = newvalue.toLowerCase();
                if (session.getSessionGenID().toLowerCase().indexOf(typedText) != -1) {
                    return true;
                }
                return false;
            });
            SortedList<Sessions> sortedList = new SortedList<>(filterData);
            sortedList.comparatorProperty().bind(sessionsTV2.comparatorProperty());
            sessionsTV2.setItems(sortedList);

        });
    }



    public void createTable(){
        String query="CREATE  TABLE IF NOT EXISTS parallel_sessions (" +
                "  `id` INT NOT NULL AUTO_INCREMENT ," +
                "  `session1ID` VARCHAR(45) NULL ," +
                "  `session2ID` VARCHAR(45) NULL ," +
                "  PRIMARY KEY (`id`) );";
        executeQuery(query);
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
}
