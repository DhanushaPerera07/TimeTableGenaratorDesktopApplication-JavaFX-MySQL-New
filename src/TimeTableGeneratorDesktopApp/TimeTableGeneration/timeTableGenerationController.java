package TimeTableGeneratorDesktopApp.TimeTableGeneration;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.Sessions.Sessions;
import TimeTableGeneratorDesktopApp.Sessions.sessionController;
import TimeTableGeneratorDesktopApp.Sessions.sessionLecturers;
import TimeTableGeneratorDesktopApp.TimePeriods.TimeSlots.TimeSlot;
import TimeTableGeneratorDesktopApp.TimeTableGeneration.SingleTImeTableStructure.TimeTableStructureController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class timeTableGenerationController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getDayNames();
        getHoursValues();
    }

    @FXML
    private Button hallViewBtn;

    @FXML
    private Button lecturerViewBtn;

    @FXML
    private Button studentViewBtn;
    @FXML
    private Pane timeTablePane;

    private String day1;
    private String day2;
    private String day3;
    private String day4;
    private String day5;
    private String day6;
    private String day7;
    private double hours;
    private int totalTime = 0;
    private int k=0;
    private int j =0;
    private boolean Checker = false;

    ArrayList<String> a = new ArrayList<>();

    @FXML
    void hallView(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HallView/HallView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Time Table for Hall View");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(timeTablePane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));

            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Enter here What you want to do in the window Closing...
                        }

                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void lecturerView(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LecturerView/LecturerView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Time Table for Lecturer View");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(timeTablePane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));

            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Enter here What you want to do in the window Closing...
                        }

                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void studentView(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentView/StudentView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Time Table for Student View");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(timeTablePane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));

            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Enter here What you want to do in the window Closing...
                        }

                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void timeTableGenerate(ActionEvent event) {
        try {
            ObservableList<Sessions> sessionsList1 = FXCollections.observableArrayList();
            sessionController sessionController = new sessionController();
            sessionsList1 = sessionController.getSessionsList();

            ArrayList<String> sessionsList =getSessionList();

                    String query1 = "DELETE FROM time_table";
                    executeQuery(query1);

                    for (int i = 0; i < sessionsList1.size(); i++) {
                        try {

                            Sessions session = sessionsList1.get(i);

                            for (int k = 0; k < session.getSessionDuration(); k++) {
                                String query = "INSERT INTO time_table (`timeSlot`,`Module`,`tag`,`Hall`,`group`,`lecturer`,`sessionId`,`duration`) VALUES ('time"+i+"','"+session.getSessionModule()+" ("+session.getSessionTag()+")"+"','"+session.getSessionTag()+"','B501','"+session.getSessionGroupID()+"','Lecturer"+i+"','"+session.getSessionGenID()+"',"+1+");";
                                executeQuery(query);
                            }

                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                        System.out.println();
                    }

        } catch (Exception e) {
            e.printStackTrace();
        }
        setTimeToSession();
        setLecturerToSession();
    }

    public void setLecturerToSession(){
//        ObservableList<Sessions> sessionsListValue = getAllSessionList();
        ObservableList<TimeTable> TimeTableListValue = getTimeTableValues();

        for (int i = 0; i < TimeTableListValue.size(); i++) {

            ObservableList<sessionLecturers> LecturersList = FXCollections.observableArrayList();

            Connection conn = getConnection();
            String  query = "SELECT * FROM session_lecturer WHERE sessionID = '"+TimeTableListValue.get(i).getSessionId()+"'";
            Statement st;
            ResultSet rs;
            try {
                st = conn.createStatement();
                rs = st.executeQuery(query);
                sessionLecturers sessionLecturer;
                while (rs.next()) {
                    sessionLecturer = new sessionLecturers(
                            rs.getString("sessionID"),
                            rs.getString("sessionLecturerName")
                    );
                    LecturersList.add(sessionLecturer);
                }
            } catch (Exception ex) {
                // if an error occurs print an error...
                System.out.println("Error - When department data retrieving ");
                ex.printStackTrace();
            }
            j=0;k=0;
            for ( j = 0; j < TimeTableListValue.size(); j++) {

                for ( k = 0; k < LecturersList.size(); k++) {

                    if (!TimeTableListValue.get(j).getLecturer().equals(LecturersList.get(k).getLecturerName()) ) {
//                        && TimeTableListValue.get(i).getTimeSlot().equals(TimeTableListValue.get(j).getTimeSlot())

                        String query1 = "UPDATE time_table SET `lecturer` ='" +LecturersList.get(k).getLecturerName()+"' WHERE Id =" +TimeTableListValue.get(i).getId();
                        executeQuery(query1);
                        k=99;
                        j=99;
                }
                }
            }
//            if (Checker == true) {
//
//            }


        }
    }

    public void setTimeToSession(){
        String query3 = "DELETE FROM time_table WHERE Module = 'INTERVAL'";
//        String query2 = "DELETE from daysname WHERE id =1 ";
        executeQuery(query3);

        String tempSessionId = "non";
        ObservableList<TimeSlot> timeSlotList = getTimeSlotsList1();

        ObservableList<TimeTable> timeTableList = getTimeTableValues();

        ArrayList<String> sessionsList2 =getSessionList();

        for (int j = 0; j < sessionsList2.size(); j++) {
            int x = 0;
             totalTime = 0;
            for (int i = 0; i < timeTableList.size(); i++) {


                if (sessionsList2.get(j).equals(timeTableList.get(i).getGroup()))
                {
                    if (totalTime == hours || totalTime ==hours*2 ||totalTime ==hours*3 || totalTime == hours*4 || totalTime==hours*5) {
                        x = 0;
                    }

                    if (totalTime < hours) {

                        if (totalTime == 4) {

                            String query1 = "INSERT INTO time_table (`timeSlot`,`Module`,`tag`,`Hall`,`group`,`lecturer`,`sessionId`,`duration`,`dayName`) VALUES ('" +timeSlotList.get(x).getValue_t()+"','INTERVAL','INTERVAL','INTERVAL','INTERVAL','INTERVAL','INTERVAL',"+1+",'"+day1+"');";
//                            executeQuery(query1);
                            x++;
                            totalTime = totalTime+1;
                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day1+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);


                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;

                        }else if (totalTime > 4) {
                            x++;
                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day1+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);


                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;
                        }else{

                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day1+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);
                            x++;

                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;
                        }


                    }else if (totalTime < hours*2) {

                        if (totalTime == 14) {

                            String query1 = "INSERT INTO time_table (`timeSlot`,`Module`,`tag`,`Hall`,`group`,`lecturer`,`sessionId`,`duration`,`dayName`) VALUES ('" +timeSlotList.get(x).getValue_t()+"','INTERVAL','INTERVAL','INTERVAL','INTERVAL','INTERVAL','INTERVAL',"+1+",'"+day2+"');";
//                            executeQuery(query1);
                            x++;
                            totalTime = totalTime+1;
                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day2+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);


                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;

                        }else if (totalTime > 14) {
                            x++;
                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day2+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);


                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;
                        }else{

                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day2+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);
                            x++;

                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;
                        }

                    }else if (totalTime < hours*3) {

                        if (totalTime == 23) {

                            String query1 = "INSERT INTO time_table (`timeSlot`,`Module`,`tag`,`Hall`,`group`,`lecturer`,`sessionId`,`duration`,`dayName`) VALUES ('" +timeSlotList.get(x).getValue_t()+"','INTERVAL','INTERVAL','INTERVAL','INTERVAL','INTERVAL','INTERVAL',"+1+",'"+day3+"');";
//                            executeQuery(query1);
                            x++;
                            totalTime = totalTime+1;
                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day3+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);


                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;

                        }else if (totalTime > 23) {
                            x++;
                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day3+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);


                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;
                        }else{

                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day3+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);
                            x++;

                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;
                        }

                    } else if (totalTime < hours*4) {

                        if (totalTime == 32) {

                            String query1 = "INSERT INTO time_table (`timeSlot`,`Module`,`tag`,`Hall`,`group`,`lecturer`,`sessionId`,`duration`,`dayName`) VALUES ('" +timeSlotList.get(x).getValue_t()+"','INTERVAL','INTERVAL','INTERVAL','INTERVAL','INTERVAL','INTERVAL',"+1+",'"+day4+"');";
//                            executeQuery(query1);
                            x++;
                            totalTime = totalTime+1;
                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day4+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);


                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;

                        }else if (totalTime > 32) {
                            x++;
                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day4+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);


                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;
                        }else{

                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day4+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);
                            x++;

                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;
                        }

                    } else if (totalTime < hours*5) {

                        if (totalTime == 41) {

                            String query1 = "INSERT INTO time_table (`timeSlot`,`Module`,`tag`,`Hall`,`group`,`lecturer`,`sessionId`,`duration`,`dayName`) VALUES ('" +timeSlotList.get(x).getValue_t()+"','INTERVAL','INTERVAL','INTERVAL','INTERVAL','INTERVAL','INTERVAL',"+1+",'"+day5+"');";
//                            executeQuery(query1);
                            x++;
                            totalTime = totalTime+1;
                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day5+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);


                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;

                        }else if (totalTime > 41) {
                            x++;
                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day5+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);


                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;
                        }else{

                            String query = "UPDATE time_table SET `timeSlot` ='" +timeSlotList.get(x).getValue_t()+"',`dayName` ='"+day5+"' WHERE Id =" +timeTableList.get(i).getId();
                            executeQuery(query);
                            x++;

                            tempSessionId = timeTableList.get(i).getSessionId();

                            totalTime = totalTime+1;
                        }

                    } else{
                        System.out.println("Working hours  not enough to add the total sessions");
                    }

                }

            }

        }
    }

    public void getHoursValues() {
        Connection conn = getConnection();
        String query = "SELECT * FROM hours where id = 1";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {

                hours = Double.parseDouble(rs.getString("hour1"));

            }



        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void getDayNames() {
        Connection conn = getConnection();
        String query = "SELECT * FROM daysname where id = 1";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                day1 = rs.getString("day1name");
                day2 = rs.getString("day2name");
                day3 = rs.getString("day3name");
                day4 = rs.getString("day4name");
                day5 = rs.getString("day5name");
                day6 = rs.getString("day6name");
                day7 = rs.getString("day7name");
            }



        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public ObservableList<TimeSlot> getTimeSlotsList1() {
        ObservableList<TimeSlot> timeSlotList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String  query = "SELECT * FROM timeslots";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            TimeSlot timeSlot;
            while (rs.next()) {
                timeSlot = new TimeSlot(
                        rs.getInt("slotsID"),
                        rs.getFloat("range_t"),
                        rs.getString("value_t")


                );
                timeSlotList.add(timeSlot);
                System.out.println(timeSlot.getValue_t());
            }
        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }
        return timeSlotList;
    }

    public ObservableList<TimeTable>  getTimeTableValues(){
        ObservableList<TimeTable> timeTableList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String  query = "SELECT * FROM time_table";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            TimeTable timeTable;
            while (rs.next()) {
                timeTable = new TimeTable(
                        rs.getInt("Id"),
                        rs.getString("timeSlot"),
                        rs.getString("Module"),
                        rs.getString("tag"),
                        rs.getString("Hall"),
                        rs.getString("group"),
                        rs.getString("lecturer"),
                        rs.getString("sessionId"),
                        rs.getString("dayName"),
                        rs.getInt("duration")

                );
                timeTableList.add(timeTable);


            }
        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }
        return timeTableList;
    }

    public ArrayList<String> getSessionList() {

        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<TimeTable> sessionsList = FXCollections.observableArrayList();
        Connection conn =  databaseHelper.getConnection();
        String query;

        query = "SELECT DISTINCT `sessionStudentGroup` FROM session";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            TimeTable timeTable;
            while (rs.next()) {
                String b = rs.getString("sessionStudentGroup");

                a.add(b);

            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return a;
    }

    public ObservableList<Sessions> getAllSessionList() {

        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<Sessions> sessionsList = FXCollections.observableArrayList();
        Connection conn =  databaseHelper.getConnection();
        String query;

        query = "SELECT * FROM session";

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

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
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
}
