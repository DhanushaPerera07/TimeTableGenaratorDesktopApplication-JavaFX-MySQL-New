package TimeTableGeneratorDesktopApp.Extra.NotAvailableTime.SessionNATime;

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
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SessionNATimeController implements Initializable {
    public static int sessionRawID ;
    public static String sessionGenID ;

    @FXML
    private Pane setNATimePane;

    @FXML
    private TableView<NATSessions> NATimeTV;

    @FXML
    private ComboBox<String> dayCB;

    @FXML
    private TableColumn<NATSessions, String> dayCol;



    @FXML
    private ComboBox<String> hourCB;

    @FXML
    private Label sessionID;

    @FXML
    private TableColumn<NATSessions, String> hourCol;

    @FXML
    private TableView<Sessions> sessionListTV;

    @FXML
    private TableColumn<Sessions, String> tagCol;

    @FXML
    private TableColumn<Sessions, String> groupCol;

    @FXML
    private TableColumn<Sessions, String> moduleCol;

    @FXML
    private Button addBtn;

    @FXML
    private TextField searchBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNATimePane.setVisible(false);
        setValuesCombo();
        createTable();
        showSessions();
        

    }


    private void setValuesCombo(){
        dayCB.getItems().removeAll(dayCB.getItems());
        dayCB.setPromptText("Select");
        dayCB.getItems().addAll(
                "Monday", "Tuesday" , "Wednesday", "Friday" , "Saturday", "Sunday"
        );


        hourCB.getItems().removeAll(hourCB.getItems());
        hourCB.setPromptText("Select");
        hourCB.getItems().addAll(
                "8.00", "9.00" , "10.00", "11.00" , "12.00", "13.00","14.00","15.00","16.00"
        );
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
            sortedList.comparatorProperty().bind(sessionListTV.comparatorProperty());
            sessionListTV.setItems(sortedList);

        });
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

                System.out.println(sessions.getSessionModule());
                sessionsList.add(sessions);
            }



        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return sessionsList;
    }

    public void showSessions() {

        ObservableList<Sessions> list = getSessionsList();
        moduleCol.setCellValueFactory(new PropertyValueFactory<Sessions, String>("sessionModule"));
        tagCol.setCellValueFactory(new PropertyValueFactory<Sessions, String>("sessionTag"));
        groupCol.setCellValueFactory(new PropertyValueFactory<Sessions, String>("sessionGroupID"));

        sessionListTV.setItems(list);
        System.out.println(moduleCol);
        System.out.println(groupCol);
    }



    @FXML
    void handleMouseAction(MouseEvent event) {
        setNATimePane.setVisible(true);
        Sessions sessions = sessionListTV.getSelectionModel().getSelectedItem();
        sessionRawID = sessions.getSessionID();
        sessionGenID = sessions.getSessionGenID();

        try{

            showSessionNATimes();


        }catch (Exception e){
            System.out.println("can't load new window");
        }
    }


    @FXML
    public void insertRecord(){
        String day =  dayCB.getSelectionModel().getSelectedItem().toString();
        String hour = hourCB.getSelectionModel().getSelectedItem().toString();

        String query = "INSERT INTO sessionsnatime (sessionID,DayTM,Hour)" +
                "VALUES ('"+sessionGenID+"','" +day+ "','" +hour+ "') ";
        executeQuery(query);

        showSessionNATimes();
        setValuesCombo();

    }


    private void showSessionNATimes() {
            ObservableList<NATSessions> list = getSessionsNATImeList();
            dayCol.setCellValueFactory(new PropertyValueFactory<NATSessions,String>("DayTM"));
            hourCol.setCellValueFactory(new PropertyValueFactory<NATSessions,String>("Hour"));
            NATimeTV.setItems(list);


    }


    public ObservableList<NATSessions> getSessionsNATImeList(){
        ObservableList<NATSessions> sessionsNATimeList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM sessionsnatime WHERE sessionID = '" +sessionGenID+"'";
        System.out.println(sessionGenID);
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            NATSessions naTimeSessions;
            while (rs.next()) {
                naTimeSessions = new NATSessions(rs.getInt("id"),rs.getString("sessionID"),rs.getString("DayTM"),rs.getString("Hour"));
                sessionsNATimeList.add(naTimeSessions);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sessionsNATimeList;
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

    public void createTable(){
        String createTableQuery = "CREATE  TABLE IF NOT EXISTS `timetabledb`.`sessionsnatime` (" +
                "  `id` INT NOT NULL AUTO_INCREMENT," +
                "  `sessionID` VARCHAR(100) NULL ," +
                "  `DayTM` VARCHAR(45) NULL ," +
                "  `Hour` VARCHAR(45) NULL ," +
                "  PRIMARY KEY (`id`) );";

        executeQuery(createTableQuery);
    }

}
