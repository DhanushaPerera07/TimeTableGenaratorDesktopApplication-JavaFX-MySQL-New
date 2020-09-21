package TimeTableGeneratorDesktopApp.Sessions.SessionForm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SessionFormController implements Initializable {

    @FXML
    private Label sessionID;

    @FXML
    private ComboBox<String> comboSubjectBox;

    @FXML
    private TextField tfSessionNoStudents;

    @FXML
    private Button updateSessionBtn;

    @FXML
    private Button deleteSessionBtn;

    @FXML
    private Button addSessionBtn;

    @FXML
    private Label lecturerLabel;

    @FXML
    private ScrollPane lecturersPane;

    @FXML
    private ComboBox<Integer> comboDurationBox;

    @FXML
    private ComboBox<String> comboTagBox;

    @FXML
    private ComboBox<String> comboStudentGroupBox;

    @FXML
    private ListView<String> savedLecturerList;

    @FXML
    private ListView<String> selectedLecturerList;

    @FXML
    private ListView<String> lecturerList;

    private static String sessionTag ="";
    private static String sessionStudentGroup = "";
    private static String sessionSubject = "";
    private int sessionNoOfStudents = 0;
    private int sessionDuration = 0;
    public static String sessionGenID ="";

    ArrayList<String> subjects = new ArrayList<String>();
    ArrayList<String> tags = new ArrayList<String>();
    ArrayList<String> studentGroup = new ArrayList<String>();
    int count = 0;
    int sessionid = 0;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        print();

        tableCreateQuery();
        getSubjects();

        comboTagBox.getItems().removeAll(comboTagBox.getItems());
        comboTagBox.setItems(FXCollections.observableArrayList(getTags()));

        comboStudentGroupBox.getItems().removeAll(comboStudentGroupBox.getItems());
        comboStudentGroupBox.setItems(FXCollections.observableArrayList(getStudentGroups()));

        comboSubjectBox.getItems().removeAll(comboSubjectBox.getItems());
        comboSubjectBox.setItems(FXCollections.observableArrayList(getSubjects()));

        comboDurationBox.getItems().removeAll(comboDurationBox.getItems());
        comboDurationBox.getItems().addAll(
                1,2,3,4,5,6
        );


        getLecturerList();
//        checkSessions();
//
//        System.out.println(""+count);
//        if (count==0){
//            sessionID.setText("S" +(sessionid+1));
//        }

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

    public void print(){
        Connection connection = getConnection();
        System.out.println("working");
    }


    @FXML
    public void submitSessionForm(ActionEvent actionEvent) {


        generateSessionID();
        saveSessionLecturer();
        Stage stage = (Stage) addSessionBtn.getScene().getWindow();
        stage.close();

        sessionNoOfStudents = Integer.parseInt(tfSessionNoStudents.getText().toString());


        String query = "INSERT INTO session (sessionID , sessionTag,sessionStudentGroup,sessionSubject,sessionNoOfStudents,sessionDuration,sessionModuleCode) " +
                "VALUES ('" +sessionGenID+ "','" +sessionTag+ "','" +sessionStudentGroup+ "','" +sessionSubject+ "','" +sessionNoOfStudents+
                "'," +sessionDuration+ ",'OOPTest1') ";
        executeQuery(query);

//        String query = "INSERT INTO session_lecturer ()"
    }



    public void generateSessionID(){
        sessionGenID = sessionStudentGroup+sessionTag+sessionSubject;
        System.out.println(sessionGenID);
    }

    public void tableCreateQuery(){
        String queryTempSessions = "CREATE TABLE temp_session_lecturer (" +
                "  `idtemp_session_lecturer` int NOT NULL AUTO_INCREMENT," +
                "  `temp_lecturer` varchar(45) NOT NULL," +
                "  PRIMARY KEY (`idtemp_session_lecturer`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";

        executeQuery(queryTempSessions);

    }

    @FXML
    public void updateSessionForm(ActionEvent actionEvent) {
    }


    @FXML
    public void selectSessionTag(ActionEvent actionEvent) {
        sessionTag = comboTagBox.getSelectionModel().getSelectedItem().toString();

    }


    @FXML
    public void selectSessionStudentGroup(ActionEvent actionEvent) {
        sessionStudentGroup = comboStudentGroupBox.getSelectionModel().getSelectedItem().toString();
    }


    @FXML
    public void selectSessionSubject(ActionEvent actionEvent) {
        sessionSubject = comboSubjectBox.getSelectionModel().getSelectedItem().toString();
    }


    @FXML
    public void selectSessionDuration(ActionEvent actionEvent) {
        sessionDuration = comboDurationBox.getSelectionModel().getSelectedItem();
    }


    @FXML
    public void deleteSessionForm(ActionEvent actionEvent) {
    }


    public List<String> getSubjects(){
        Connection con = getConnection();
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


    public List<String> getTags(){
        Connection con = getConnection();
        try{
            Statement st;
            ResultSet rs;
            // assume that all objects were all properly defined
            st = con.createStatement();
            st.executeQuery("SELECT Tag FROM tags ORDER BY Tag");
            rs = st.getResultSet();
            while(rs.next()){
                String c = rs.getString("Tag");
                tags.add(c);
            }
            return tags;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<String> getStudentGroups(){
        Connection con = getConnection();
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

    public void getLecturerList(){
        ObservableList<String> list = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String  query2 = "SELECT * FROM lecturer order by lecturerName";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query2);
            String name;
            String id;
            while (rs.next()) {
                name =rs.getString("lecturerName");
                id = rs.getString("lecturerID");
                list.add(name);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        lecturerList.setItems(list);
    }


    @FXML
    public void setSelectedLecturers(MouseEvent mouseEvent) {
        String i = lecturerList.getSelectionModel().getSelectedItem();
//        String q = "SELECT * from lecturer where lecturerName = '" +i +"'";
//        System.out.println(""+q);
        System.out.println(""+i);
//        String query = "INSERT INTO selectedLecturer"

        String query = "INSERT INTO temp_session_lecturer (temp_lecturer)" +
                "VALUES ('" +i+ "') ";
        executeQuery(query);
        getTempValues();
    }

    @FXML
    public void deleteLecturer(MouseEvent mouseEvent) {

    }


    public void saveLecturers(){

    }

    public void checkSessions(){
        Connection conn = getConnection();
        String  query = "SELECT * FROM session";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                count = count + 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getTempValues(){
        ObservableList<String> tempLecturerList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String  query2 = "SELECT * FROM temp_session_lecturer";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query2);
            String tags;
            while (rs.next()) {
                tags = rs.getString("temp_lecturer");
                tempLecturerList.add(tags);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        selectedLecturerList.setItems(tempLecturerList);
    }

    public void saveSessionLecturer(){
        Connection conn = getConnection();
        String  query2 = "SELECT * FROM temp_session_lecturer";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query2);
            String name;
            while (rs.next()) {
                name = rs.getString("temp_lecturer");
                String query = "INSERT INTO session_lecturer (sessionID,sessionLecturerName) " +
                        "VALUES ('"+sessionGenID+"','" +name+ "') ";
                executeQuery(query);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String query = "Delete from tempTags";
        executeQuery(query);
    }
}
