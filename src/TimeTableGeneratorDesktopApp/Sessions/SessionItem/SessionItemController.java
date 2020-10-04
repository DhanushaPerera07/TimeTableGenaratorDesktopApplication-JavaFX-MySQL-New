package TimeTableGeneratorDesktopApp.Sessions.SessionItem;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.FacultyDepartments.Faculty;
import TimeTableGeneratorDesktopApp.Sessions.Sessions;
import TimeTableGeneratorDesktopApp.Sessions.sessionLecturers;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SessionItemController implements Initializable
{

    DatabaseHelper databaseHelper = new DatabaseHelper();

    public Sessions sessionInstance;
    public String sessionGenID;
    public int sessionID;

    public static int sessionID2 = 0;
    public static String sessionModule = "";
    public static String sessionModuleCode = "";
    public static String sessionTag = "";
    public static String sessionGroupID = "";
    public static int sessionStudentCount = 0;
    public static int sessionDuration = 0;
    public static String sessionGenID2 = "";

    @FXML
    private Label groupL;

    @FXML
    private Label noofStdL;

    @FXML
    private Label tagL;

    @FXML
    private Label lecturerNameL;

    @FXML
    private Label moduleL;

    @FXML
    private Label durationL;

    @FXML
    private Button deleteSessionBtn;

    @FXML
    private Button updateSessionBtn;

    @FXML
    private VBox sessionItemVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void showInformation(Sessions sessions){
        this.sessionInstance = sessions; // facultyInstance holds the faculty obj here, then we use that when we edit or delete it
        groupL.setText(sessions.getSessionGroupID());
        moduleL.setText(sessions.getSessionModule());
        noofStdL.setText(String.valueOf(sessions.getSessionStudentCount()));
        durationL.setText(String.valueOf(sessions.getSessionDuration()));
        tagL.setText(sessions.getSessionTag());
        sessionID = sessions.getSessionID();
        sessionGenID= sessions.getSessionGenID();
        getLecturersList();

    }

    /*public Connection getConnection(){
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

    public void getLecturersList(){
        ObservableList<sessionLecturers> lecturersList = FXCollections.observableArrayList();
        //Connection conn = getConnection();
        Connection conn = databaseHelper.getConnection();

        String query = "SELECT * FROM session_lecturer where sessionID = '" +sessionGenID+ "'";

        Statement st;
        ResultSet rs;



        ArrayList<String>  lecturersArrayList = new ArrayList<>();

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            sessionLecturers sessionLecturers;
            while (rs.next()) {
                   String lecName =     rs.getString("sessionLecturerName");
                   lecturersArrayList.add(lecName);
            }
            lecturerNameL.setText(String.valueOf(lecturersArrayList));

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
    }


    public void deleteSessionRecord(int sessionID, String lecID){
//        String query = "UPDATE `session` SET location_delete_status = 'Y' WHERE idsession = " + sessionID + "";
        String query = "DELETE FROM session WHERE idsession =" + sessionID + "";
        String query1 = "DELETE FROM session_lecturer WHERE sessionID ='" + lecID + "'";

//        String query1 = "UPDATE `session_lecturer` SET location_delete_status = 'Y' WHERE sessionID = '" + lecID + "'";

        databaseHelper.executeQuery(query);
        databaseHelper.executeQuery(query1);



    }


    @FXML
    void deleteSessionAction(ActionEvent event) {

        Alert deleteFacultyAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteFacultyAlert.setTitle("Confirmation");
        deleteFacultyAlert.setHeaderText("Are you sure to delete this session?");

        ButtonType confirmBtn = new ButtonType("Delete");
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        deleteFacultyAlert.getButtonTypes().setAll(confirmBtn, cancelBtn);

        Optional<ButtonType> result = deleteFacultyAlert.showAndWait();
        if (result.get() == confirmBtn){
            deleteSessionRecord(this.sessionInstance.getSessionID(),this.sessionInstance.getSessionGenID());
        }
        else {

        }

//        deleteSessionRecord(this.sessionInstance.getSessionID(),this.sessionInstance.getSessionGenID());

    }


    @FXML
    void updateSessionAction(ActionEvent event) {

        sessionID2 = sessionInstance.getSessionID();
        sessionModule = sessionInstance.getSessionModule();
        sessionModuleCode = sessionInstance.getSessionModuleCode();
        sessionTag = sessionInstance.getSessionTag();
        sessionGroupID = sessionInstance.getSessionGroupID();
        sessionStudentCount = sessionInstance.getSessionStudentCount();
        sessionDuration = sessionInstance.getSessionDuration();
        sessionGenID2 = sessionInstance.getSessionGenID();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Sessions/SessionForm/sessionForm.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Edit Session");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(sessionItemVbox.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
//                    String dropTempSessions = "Drop TABLE temp_session_lecturer";
//                    executeQuery(dropTempSessions);
                    sessionID2 = 0;
                    sessionModule = "";
                    sessionModuleCode = "";
                    sessionTag = "";
                    sessionGroupID = "";
                    sessionStudentCount = 0;
                    sessionDuration = 0;
                    sessionGenID2 = "";
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
