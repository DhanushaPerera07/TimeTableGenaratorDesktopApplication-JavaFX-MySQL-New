package TimeTableGeneratorDesktopApp.TimePeriods;

import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import TimeTableGeneratorDesktopApp.TimePeriods.SetWorkingDays.WorkingDays;
import TimeTableGeneratorDesktopApp.TimePeriods.TimeSlots.TimeSlot;
import TimeTableGeneratorDesktopApp.TimePeriods.TimeSlots.TimeSlotsController;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class WorkingDaysAndHoursController implements Initializable {

    public String one;
    public String Two;
    public String Three;
    public String Four;
    public String Five;
    public String Six;
    public String Seven;


    public static int hourid;
    public static double range;
    public static String hour;


    @FXML
    private Pane workingPane;

    @FXML
    private Button addWorkingDaysBtn;

    @FXML
    private TableView<TimeSlot> timeTableTV;

    @FXML
    private TableColumn<TimeSlot, String> timeSlotCol;

    @FXML
    private TableColumn<WorkingDays, String> cl1;

    @FXML
    private TableColumn<WorkingDays, String> cl2;

    @FXML
    private TableColumn<WorkingDays, String> cl3;

    @FXML
    private TableColumn<WorkingDays, String> cl4;

    @FXML
    private TableColumn<WorkingDays, String> cl5;

    @FXML
    private TableColumn<WorkingDays, String> cl6;

    @FXML
    private TableColumn<WorkingDays, String> cl7;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getDayNames();
        getHours();
        displayTimeSlots();
    }


    @FXML
    void edit1(MouseEvent event) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimeSlots/TimeSlots.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Set Time Slots");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(workingPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void handleMouseAction(MouseEvent event) {
        TimeSlot timeSlot=timeTableTV.getSelectionModel().getSelectedItem();

        hourid = timeSlot.getSlotsID();
        hour = timeSlot.getValue_t();
        range=timeSlot.getRange_t();

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimeSlots/TimeSlots.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Edit Time Slots");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(workingPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));

            stage.show();


            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            hourid = -1;
                            hour = "0";
                            range = 0;
                            displayTimeSlots();
                        }

                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ObservableList<TimeSlot> getTimeSlotsList() {
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

            }
        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }
        return timeSlotList;
    }

    public void displayTimeSlots(){

        ObservableList<TimeSlot> TimeSlotsList = getTimeSlotsList();

        timeSlotCol.setCellValueFactory(new PropertyValueFactory<TimeSlot, String>("value_t"));

        timeTableTV.setItems(TimeSlotsList);

    }

    public void addWorkingDaysAction(ActionEvent actionEvent) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetWorkingDays/SetWorkingDaysForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Set Working Days");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(workingPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));

            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            getDayNames();
                            displayTimeSlots();
                        }

                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void addTimeSlots1(MouseEvent actionEvent) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimeSlots/TimeSlots.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Set Time Slots");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(workingPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));

            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {

                            displayTimeSlots();

                        }

                    });
                }
            });
        } catch (Exception e) {
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
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
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
                one = rs.getString("day1name");
                Two = rs.getString("day2name");
                Three = rs.getString("day3name");
                Four = rs.getString("day4name");
                Five = rs.getString("day5name");
                Six = rs.getString("day6name");
                Seven = rs.getString("day7name");

                cl1.setText(one);
                cl2.setText(Two);
                cl3.setText(Three);
                cl4.setText(Four);
                cl5.setText(Five);
                cl6.setText(Six);
                cl7.setText(Seven);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void getHours() {

    }
}
