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
    private TableColumn<TimeTable, String> StructureTimeSlots;

    @FXML
    private TableColumn<TimeTable, String> StrructureC1;

    @FXML
    private TableColumn<TimeTable, String> StrructureC2;

    @FXML
    private TableColumn<TimeTable, String> StrructureC3;

    @FXML
    private TableColumn<TimeTable, String> StrructureC4;

    @FXML
    private TableColumn<TimeTable, String> StrructureC5;

    @FXML
    private TableColumn<TimeTable, String> StrructureC6;

    @FXML
    private TableColumn<TimeTable, String> StrructureC7;

    public String query;

    ObservableList<TimeTable> timeTableList = getTimetableSessions();

    int Id, dayName;
    String timeSlot,Module,tag,Hall,group1,lecturer,sessionId,duration;

    ObservableList<TimeSlot> timeSlotList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showSessions( String Group) {
        structureTblHeader.setText(Group);
        getDayNames();
        displaySessions(Group);
    }

    public void showLocation(String hall) {

//        this.hall = hall;
        structureTblHeader.setText(hall);
        getDayNames();
        displaySessionstoHalls(hall);
    }

    public void showlecturers(String lecturer) {

        structureTblHeader.setText(lecturer);
        getDayNames();
        displaySessionstoLecturer(lecturer);

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

    public ObservableList<TimeTable> getTimeTablesByLecName(Lecturers lecturers) {

        ObservableList<TimeTable> timeTableList1 = FXCollections.observableArrayList();

        Connection conn = getConnection();
        String query = "SELECT * FROM time_table where lecturer = '"+lecturers.getLecturerName()+"'";

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
                timeTableList1.add(timeTable);


            }
        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }
    return timeTableList1;
    }

    public ObservableList<TimeTable> getTimeTablesByLocation(Hall hall) {

        ObservableList<TimeTable> timeTableList1 = FXCollections.observableArrayList();

        Connection conn = getConnection();
        String query = "SELECT * FROM time_table where Hall = '"+hall.getLocation_name()+"'";

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
                timeTableList1.add(timeTable);


            }
        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }
        return timeTableList1;
    }

    public ObservableList<TimeSlot> getTimeSlotsList() {

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

//    public void displaySessions(String group){
//        ObservableList<TimeTable> timeTableViewList = FXCollections.observableArrayList();
////        displayTimeSlots(group);
//        for (int i = 0; i < timeTableList.size(); i++) {
//            TimetableValue = timeTableList.get(i);
//            if (TimetableValue.getGroup().equals(group)){
//                timeTableViewList.add(TimetableValue);
//            }
//        }
//        StrructureC1.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("Module"));
//        StructureTimeSlots.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("timeSlot"));
//        TimeTableStructureTbl.setItems(timeTableViewList);
//    }

    public void displaySessions(String group){


        ObservableList<TimeTable> timeTableViewListMonday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListTuesday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListWednesday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListThursday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListFriday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListSaturday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListSunday = FXCollections.observableArrayList();


        for (int i = 0; i < timeTableList.size(); i++) {

            TimetableValue = timeTableList.get(i);

            if (TimetableValue.getGroup().equals(group) && TimetableValue.getDayName().equals("Monday")){
                timeTableViewListMonday.add(TimetableValue);
            }else if (TimetableValue.getGroup().equals(group) && TimetableValue.getDayName().equals("Tuesday")){
                timeTableViewListTuesday.add(TimetableValue);
            }else if (TimetableValue.getGroup().equals(group) && TimetableValue.getDayName().equals("Wednesday")){
                timeTableViewListWednesday.add(TimetableValue);
            }else if (TimetableValue.getGroup().equals(group) && TimetableValue.getDayName().equals("Thursday")){
                timeTableViewListThursday.add(TimetableValue);
            }else if (TimetableValue.getGroup().equals(group) && TimetableValue.getDayName().equals("Friday")){
                timeTableViewListFriday.add(TimetableValue);
            }else if (TimetableValue.getGroup().equals(group) && TimetableValue.getDayName().equals("Saturday")){
                timeTableViewListSaturday.add(TimetableValue);
            }else if (TimetableValue.getGroup().equals(group) && TimetableValue.getDayName().equals("Sunday")){
                timeTableViewListSunday.add(TimetableValue);
            }
        }
        ObservableList<TimeTable> temp = FXCollections.observableArrayList();
        for (int i = 0; i < 9; i++) {

            if (timeTableViewListMonday.size() > i) {
                Id = timeTableViewListMonday.get(i).getId();
                timeSlot = timeTableViewListMonday.get(i).getTimeSlot();
                Module = timeTableViewListMonday.get(i).getModule();
            } else{
                Id = 0;
                timeSlot = null;
                Module = null;
            }


            if (timeTableViewListTuesday.size() > i) {
                tag = timeTableViewListTuesday.get(i).getModule();
            }else{
                tag = null;
            }

            if (timeTableViewListWednesday.size() > i) {
                Hall = timeTableViewListWednesday.get(i).getModule();
            }else{
                Hall = null;
            }

            if (timeTableViewListThursday.size() > i) {
                group1 = timeTableViewListThursday.get(i).getModule();
            }else{
                group1 = null;
            }

            if (timeTableViewListFriday.size() > i) {
                lecturer = timeTableViewListFriday.get(i).getModule();
            }else{
                lecturer = null;
            }

            if (timeTableViewListSaturday.size() > i) {
                sessionId = timeTableViewListSaturday.get(i).getModule();
            }else

            if (timeTableViewListSunday.size() > i) {
                duration = timeTableViewListSunday.get(i).getModule();
            }else{
                duration = null;
            }

            TimeTable xx = new TimeTable(
                    Id,
                    timeSlot,
                    Module,
                    tag,
                    Hall,
                    group1,
                    lecturer,
                    sessionId,
                    duration,
                    dayName
            );

            temp.add(xx);
        }


        StructureTimeSlots.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("timeSlot"));
        StrructureC1.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("Module"));
        StrructureC2.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("tag"));
        StrructureC3.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("Hall"));
        StrructureC4.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("group"));
        StrructureC5.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("lecturer"));
        StrructureC6.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("sessionId"));
        StrructureC7.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("dayName"));

        TimeTableStructureTbl.setItems(temp);



    }

    public void displaySessionstoLecturer(String group){

        ObservableList<TimeTable> timeTableViewListMonday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListTuesday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListWednesday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListThursday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListFriday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListSaturday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListSunday = FXCollections.observableArrayList();


        for (int i = 0; i < timeTableList.size(); i++) {

            TimetableValue = timeTableList.get(i);

            if (TimetableValue.getLecturer().equals(group) && TimetableValue.getDayName().equals("Monday")){
                timeTableViewListMonday.add(TimetableValue);
            }else if (TimetableValue.getLecturer().equals(group) && TimetableValue.getDayName().equals("Tuesday")){
                timeTableViewListTuesday.add(TimetableValue);
            }else if (TimetableValue.getLecturer().equals(group) && TimetableValue.getDayName().equals("Wednesday")){
                timeTableViewListWednesday.add(TimetableValue);
            }else if (TimetableValue.getLecturer().equals(group) && TimetableValue.getDayName().equals("Thursday")){
                timeTableViewListThursday.add(TimetableValue);
            }else if (TimetableValue.getLecturer().equals(group) && TimetableValue.getDayName().equals("Friday")){
                timeTableViewListFriday.add(TimetableValue);
            }else if (TimetableValue.getLecturer().equals(group) && TimetableValue.getDayName().equals("Saturday")){
                timeTableViewListSaturday.add(TimetableValue);
            }else if (TimetableValue.getLecturer().equals(group) && TimetableValue.getDayName().equals("Sunday")){
                timeTableViewListSunday.add(TimetableValue);
            }
        }
        ObservableList<TimeTable> temp = FXCollections.observableArrayList();
        for (int i = 0; i < 9; i++) {

            if (timeTableViewListMonday.size() > i) {
                Id = timeTableViewListMonday.get(i).getId();
                timeSlot = timeTableViewListMonday.get(i).getTimeSlot();
                Module = timeTableViewListMonday.get(i).getModule();
            } else{
                Id = 0;
                timeSlot = null;
                Module = null;
            }


            if (timeTableViewListTuesday.size() > i) {
                tag = timeTableViewListTuesday.get(i).getModule();
            }else{
                tag = null;
            }

            if (timeTableViewListWednesday.size() > i) {
                Hall = timeTableViewListWednesday.get(i).getModule();
            }else{
                Hall = null;
            }

            if (timeTableViewListThursday.size() > i) {
                group1 = timeTableViewListThursday.get(i).getModule();
            }else{
                group1 = null;
            }

            if (timeTableViewListFriday.size() > i) {
                lecturer = timeTableViewListFriday.get(i).getModule();
            }else{
                lecturer = null;
            }

            if (timeTableViewListSaturday.size() > i) {
                sessionId = timeTableViewListSaturday.get(i).getModule();
            }else

            if (timeTableViewListSunday.size() > i) {
                duration = timeTableViewListSunday.get(i).getModule();
            }else{
                duration = null;
            }

            TimeTable xx = new TimeTable(
                    Id,
                    timeSlot,
                    Module,
                    tag,
                    Hall,
                    group1,
                    lecturer,
                    sessionId,
                    duration,
                    dayName
            );

            temp.add(xx);
        }


        StructureTimeSlots.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("timeSlot"));
        StrructureC1.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("Module"));
        StrructureC2.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("tag"));
        StrructureC3.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("Hall"));
        StrructureC4.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("group"));
        StrructureC5.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("lecturer"));
        StrructureC6.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("sessionId"));
        StrructureC7.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("dayName"));

        TimeTableStructureTbl.setItems(temp);



//
//
////        ObservableList<TimeTable> timeTableViewList = getTimeTablesByLecName(lecturers);
//
//
//        ObservableList<TimeTable> timeTableViewListMonday = FXCollections.observableArrayList();
//        ObservableList<TimeTable> timeTableViewListTuesday = FXCollections.observableArrayList();
//        ObservableList<TimeTable> timeTableViewListWednesday = FXCollections.observableArrayList();
//        ObservableList<TimeTable> timeTableViewListThursday = FXCollections.observableArrayList();
//        ObservableList<TimeTable> timeTableViewListFriday = FXCollections.observableArrayList();
//        ObservableList<TimeTable> timeTableViewListSaturday = FXCollections.observableArrayList();
//        ObservableList<TimeTable> timeTableViewListSunday = FXCollections.observableArrayList();
//
//
//        for (int i = 0; i < timeTableList.size(); i++) {
//
//            TimetableValue = timeTableList.get(i);
//
//            if (TimetableValue.getLecturer().equals(lecturers1) && TimetableValue.getDayName().equals("Monday")){
//                timeTableViewListMonday.add(TimetableValue);
//            }else if (TimetableValue.getLecturer().equals(lecturers1) && TimetableValue.getDayName().equals("Tuesday")){
//                timeTableViewListTuesday.add(TimetableValue);
//            }else if (TimetableValue.getLecturer().equals(lecturers1) && TimetableValue.getDayName().equals("Wednesday")){
//                timeTableViewListWednesday.add(TimetableValue);
//            }else if (TimetableValue.getLecturer().equals(lecturers1) && TimetableValue.getDayName().equals("Thursday")){
//                timeTableViewListThursday.add(TimetableValue);
//            }else if (TimetableValue.getLecturer().equals(lecturers1) && TimetableValue.getDayName().equals("Friday")){
//                timeTableViewListFriday.add(TimetableValue);
//            }else if (TimetableValue.getLecturer().equals(lecturers1) && TimetableValue.getDayName().equals("Saturday")){
//                timeTableViewListSaturday.add(TimetableValue);
//            }else if (TimetableValue.getLecturer().equals(lecturers1) && TimetableValue.getDayName().equals("Sunday")){
//                timeTableViewListSunday.add(TimetableValue);
//            }
//        }
//        ObservableList<TimeTable> temp = FXCollections.observableArrayList();
//        for (int i = 0; i < 9; i++) {
//
//            if (timeTableViewListMonday.size() > i) {
//                Id = timeTableViewListMonday.get(i).getId();
//                timeSlot = timeTableViewListMonday.get(i).getTimeSlot();
//                Module = timeTableViewListMonday.get(i).getModule();
//            } else{
//                Id = 0;
//                timeSlot = null;
//                Module = null;
//            }
//
//
//            if (timeTableViewListTuesday.size() > i) {
//                tag = timeTableViewListTuesday.get(i).getModule();
//            }else{
//                tag = null;
//            }
//
//            if (timeTableViewListWednesday.size() > i) {
//                Hall = timeTableViewListWednesday.get(i).getModule();
//            }else{
//                Hall = null;
//            }
//
//            if (timeTableViewListThursday.size() > i) {
//                group1 = timeTableViewListThursday.get(i).getModule();
//            }else{
//                group1 = null;
//            }
//
//            if (timeTableViewListFriday.size() > i) {
//                lecturer = timeTableViewListFriday.get(i).getModule();
//            }else{
//                lecturer = null;
//            }
//
//            if (timeTableViewListSaturday.size() > i) {
//                sessionId = timeTableViewListSaturday.get(i).getModule();
//            }else
//
//            if (timeTableViewListSunday.size() > i) {
//                duration = timeTableViewListSunday.get(i).getModule();
//            }else{
//                duration = null;
//            }
//
//            TimeTable xx = new TimeTable(
//                    Id,
//                    timeSlot,
//                    Module,
//                    tag,
//                    Hall,
//                    group1,
//                    lecturer,
//                    sessionId,
//                    duration,
//                    dayName
//            );
//
//            temp.add(xx);
//        }
//
//        StrructureC1.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("Module"));
//        StructureTimeSlots.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("timeSlot"));
//        StrructureC2.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("tag"));
//        TimeTableStructureTbl.setItems(temp);
//        StructureTimeSlots.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("timeSlot"));
//        StrructureC1.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("Module"));
//        StrructureC2.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("tag"));
//        StrructureC3.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("Hall"));
//        StrructureC4.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("group"));
//        StrructureC5.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("lecturer"));
//        StrructureC6.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("sessionId"));
//        StrructureC7.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("dayName"));
//        TimeTableStructureTbl.setItems(temp);
//
//

    }

    public void displaySessionstoHalls(String group){


        ObservableList<TimeTable> timeTableViewListMonday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListTuesday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListWednesday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListThursday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListFriday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListSaturday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListSunday = FXCollections.observableArrayList();


        for (int i = 0; i < timeTableList.size(); i++) {

            TimetableValue = timeTableList.get(i);

            if (TimetableValue.getHall().equals(group) && TimetableValue.getDayName().equals("Monday")){
                timeTableViewListMonday.add(TimetableValue);
            }else if (TimetableValue.getHall().equals(group) && TimetableValue.getDayName().equals("Tuesday")){
                timeTableViewListTuesday.add(TimetableValue);
            }else if (TimetableValue.getHall().equals(group) && TimetableValue.getDayName().equals("Wednesday")){
                timeTableViewListWednesday.add(TimetableValue);
            }else if (TimetableValue.getHall().equals(group) && TimetableValue.getDayName().equals("Thursday")){
                timeTableViewListThursday.add(TimetableValue);
            }else if (TimetableValue.getHall().equals(group) && TimetableValue.getDayName().equals("Friday")){
                timeTableViewListFriday.add(TimetableValue);
            }else if (TimetableValue.getHall().equals(group) && TimetableValue.getDayName().equals("Saturday")){
                timeTableViewListSaturday.add(TimetableValue);
            }else if (TimetableValue.getHall().equals(group) && TimetableValue.getDayName().equals("Sunday")){
                timeTableViewListSunday.add(TimetableValue);
            }
        }
        ObservableList<TimeTable> temp = FXCollections.observableArrayList();
        for (int i = 0; i < 9; i++) {

            if (timeTableViewListMonday.size() > i) {
                Id = timeTableViewListMonday.get(i).getId();
                timeSlot = timeTableViewListMonday.get(i).getTimeSlot();
                Module = timeTableViewListMonday.get(i).getModule();
            } else{
                Id = 0;
                timeSlot = null;
                Module = null;
            }


            if (timeTableViewListTuesday.size() > i) {
                tag = timeTableViewListTuesday.get(i).getModule();
            }else{
                tag = null;
            }

            if (timeTableViewListWednesday.size() > i) {
                Hall = timeTableViewListWednesday.get(i).getModule();
            }else{
                Hall = null;
            }

            if (timeTableViewListThursday.size() > i) {
                group1 = timeTableViewListThursday.get(i).getModule();
            }else{
                group1 = null;
            }

            if (timeTableViewListFriday.size() > i) {
                lecturer = timeTableViewListFriday.get(i).getModule();
            }else{
                lecturer = null;
            }

            if (timeTableViewListSaturday.size() > i) {
                sessionId = timeTableViewListSaturday.get(i).getModule();
            }else

            if (timeTableViewListSunday.size() > i) {
                duration = timeTableViewListSunday.get(i).getModule();
            }else{
                duration = null;
            }

            TimeTable xx = new TimeTable(
                    Id,
                    timeSlot,
                    Module,
                    tag,
                    Hall,
                    group1,
                    lecturer,
                    sessionId,
                    duration,
                    dayName
            );

            temp.add(xx);
        }


        StructureTimeSlots.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("timeSlot"));
        StrructureC1.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("Module"));
        StrructureC2.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("tag"));
        StrructureC3.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("Hall"));
        StrructureC4.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("group"));
        StrructureC5.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("lecturer"));
        StrructureC6.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("sessionId"));
        StrructureC7.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("dayName"));

        TimeTableStructureTbl.setItems(temp);



    }

//    public void displaySessionstoHalls(Hall hall){
//
//
//        ObservableList<TimeTable> timeTableViewList = getTimeTablesByLocation(hall);
//
////        ObservableList<TimeTable> temp = FXCollections.observableArrayList();
//
//        StructureTimeSlots.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("timeSlot"));
//        StrructureC1.setCellValueFactory(new PropertyValueFactory<TimeTable, String>("Module"));
//        TimeTableStructureTbl.setItems(timeTableViewList);
//
//    }


    public void displayTimeSlots(String group){

        for (int i = 0; i < timeTableList.size(); i++) {

            TimetableValue = timeTableList.get(i);

            if (TimetableValue.getGroup().equals(group)){


                String ss = timeSlotList.get(i).getValue_t();
                query = "UPDATE time_table SET `timeSlot` = '"+ss+"', `Module` = '"+TimetableValue.getModule()+" "+ss+"' WHERE  Id =" +TimetableValue.getId()+ "";
                executeQuery(query);
                System.out.println(ss);
                System.out.println();

            }
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

}
