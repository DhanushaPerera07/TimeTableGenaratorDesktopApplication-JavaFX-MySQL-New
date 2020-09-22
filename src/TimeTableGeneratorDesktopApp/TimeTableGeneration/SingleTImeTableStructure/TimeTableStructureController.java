package TimeTableGeneratorDesktopApp.TimeTableGeneration.SingleTImeTableStructure;

import TimeTableGeneratorDesktopApp.Lecturers.Lecturers;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.Location;
import TimeTableGeneratorDesktopApp.Sessions.Sessions;
import TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm.subGroups;
import TimeTableGeneratorDesktopApp.TimePeriods.SetWorkingDays.WorkingDays;
import TimeTableGeneratorDesktopApp.TimePeriods.TimeSlots.TimeSlot;
import TimeTableGeneratorDesktopApp.TimeTableGeneration.HallView.Hall;
import TimeTableGeneratorDesktopApp.TimeTableGeneration.TimeTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TimeTableStructureController implements Initializable {

    TimeTable TimetableValue;
//    TimeTable timeTable;
    Hall hall;
    Lecturers lecturers;
    int subGroupID;

    @FXML
    private VBox TimeTableVBox;

    @FXML
    private Label structureTblHeader;

    @FXML
    private TableView<TimeTable> TimeTableStructureTbl;

    @FXML
    private TableColumn<TimeSlot, TimeTable> StructureTimeSlots;

    @FXML
    private TableColumn<TimeTable, String> StrructureC1;

    @FXML
    private TableColumn<WorkingDays, String> StrructureC2;

    @FXML
    private TableColumn<WorkingDays, String> StrructureC3;

    @FXML
    private TableColumn<WorkingDays, String> StrructureC4;

    @FXML
    private TableColumn<WorkingDays, String> StrructureC5;

    @FXML
    private TableColumn<WorkingDays, String> StrructureC6;

    @FXML
    private TableColumn<WorkingDays, String> StrructureC7;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void showSessions( String Group) {

//        this.timeTable = timeTable;
        structureTblHeader.setText(Group);
        getDayNames();
        displayTimeSlots();
        displaySessions(Group);
    }

    public void showLocation(Hall hall) {

        this.hall = hall;
        structureTblHeader.setText(hall.getLocation_name());
        getDayNames();
        displayTimeSlots();
    }

    public void showlecturers(Lecturers lecturers) {

        this.lecturers = lecturers;
        structureTblHeader.setText(lecturers.getLecturerName());
        getDayNames();
        displayTimeSlots();

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
    public void getDayNames() {
        Connection conn = getConnection();
        String query = "SELECT * FROM daysname where id = 1";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {

                StrructureC1.setText(rs.getString("day1name"));
                StrructureC2.setText(rs.getString("day2name"));
                StrructureC3.setText(rs.getString("day3name"));
                StrructureC4.setText(rs.getString("day4name"));
                StrructureC5.setText(rs.getString("day5name"));
                StrructureC6.setText(rs.getString("day6name"));
                StrructureC7.setText(rs.getString("day7name"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
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

    public ObservableList<TimeTable> getTimetableSessions() {
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
                        rs.getString("sessionId")
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

    public void displayTimeSlots(){

        ObservableList<TimeSlot> TimeSlotsList = getTimeSlotsList();

        StructureTimeSlots.setCellValueFactory(new PropertyValueFactory<TimeSlot, TimeTable>("value_t"));

//        TimeTableStructureTbl.setItems(TimeSlotsList);

    }

    public void displaySessions(String group){

        ObservableList<TimeTable> timeTableList = getTimetableSessions();
        ObservableList<TimeTable> timeTableViewList = FXCollections.observableArrayList();



        for (int i = 0; i < timeTableList.size(); i++) {
            TimetableValue = timeTableList.get(i);
            if (TimetableValue.getGroup().equals(group)){

                timeTableViewList.add(TimetableValue);

            }
        }



        StrructureC1.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("Module"));
        TimeTableStructureTbl.setItems(timeTableViewList);

    }
}
