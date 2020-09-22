package TimeTableGeneratorDesktopApp.TimeTableGeneration;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.Sessions.Sessions;
import TimeTableGeneratorDesktopApp.Sessions.sessionController;
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
    }

    @FXML
    private Button hallViewBtn;

    @FXML
    private Button lecturerViewBtn;

    @FXML
    private Button studentViewBtn;
    @FXML
    private Pane timeTablePane;

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

            for (int j = 0; j < sessionsList.size(); j++) {

                if (sessionsList1.size() > 0) {

                    String query1 = "DELETE FROM time_table";
                    executeQuery(query1);

                    for (int i = 0; i < sessionsList1.size(); i++) {
                        try {

                            Sessions session = sessionsList1.get(i);

                            String query = "INSERT INTO time_table (`timeSlot`,`Module`,`tag`,`Hall`,`group`,`lecturer`) VALUES ('time"+i+"','"+session.getSessionModule()+" ("+session.getSessionTag()+")"+"','"+session.getSessionTag()+"','time"+i+"','"+session.getSessionGroupID()+"','time"+i+"');";
                            executeQuery(query);

                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                        System.out.println();
                    }
                }else{
                    System.out.println("Database Problem...!");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
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
                String b = rs.getString("group");

                a.add(b);

            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return a;
    }

//    @FXML
//    void timeTableGenerate(ActionEvent event) {
//        try {
//            ObservableList<Sessions> sessionsList = FXCollections.observableArrayList();
//            sessionController sessionController = new sessionController();
//            sessionsList = sessionController.getSessionsList();
//
//            if (sessionsList.size() > 0) {
//
//                    String query1 = "DELETE FROM time_table";
//                            executeQuery(query1);
//
//                for (int i = 0; i < sessionsList.size(); i++) {
//                    try {
//
//                        Sessions session = sessionsList.get(i);
//
//                        String query = "INSERT INTO time_table (`timeSlot`,`Module`,`tag`,`Hall`,`group`,`lecturer`) VALUES ('time"+i+"','"+session.getSessionModule()+" ("+session.getSessionTag()+")"+"','"+session.getSessionTag()+"','time"+i+"','"+session.getSessionGroupID()+"','time"+i+"');";
//                        executeQuery(query);
//
//                    } catch (Exception e) {
//
//                        e.printStackTrace();
//                    }
//                    System.out.println();
//                }
//            }else{
//                System.out.println("Database Problem...!");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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
