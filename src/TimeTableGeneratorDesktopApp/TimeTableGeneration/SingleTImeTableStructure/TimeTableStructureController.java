package TimeTableGeneratorDesktopApp.TimeTableGeneration.SingleTImeTableStructure;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TimeTableStructureController implements Initializable {

    DatabaseHelper databaseHelper = new DatabaseHelper();

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
    int tempT1 = 0;
    int tempT2 = 0;
    int tempT3 = 0;
    int tempT4 = 0;
    int tempT5 = 0;
    int tempT6 = 0;
    int tempT7 = 0;
    ArrayList<String> Complicts = new ArrayList();

    ObservableList<TimeTable> timeTableList = getTimetableSessions();

    int Id, duration;
    String timeSlot,Module,tag,Hall,group1,lecturer,sessionId,dayName;

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

/*    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetabledb", "root","root");
            System.out.println("Database connected");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }*/

    public void getDayNames() {
        Connection conn = databaseHelper.getConnection();
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

        Connection conn = databaseHelper.getConnection();
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

        Connection conn = databaseHelper.getConnection();
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

        Connection conn = databaseHelper.getConnection();

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
        Connection conn = databaseHelper.getConnection();

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
            }else if (TimetableValue.getGroup().equals(group) && TimetableValue.getDayName().equals("Wednsday")){
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
                dayName = timeTableViewListSunday.get(i).getModule();
            }else{
                dayName = null;
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
                    dayName,
                    duration

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

    public void displaySessionstoLecturer(String lec){

        TimeTable yy = new TimeTable(
                0,
                "",
                "---",
                "---",
                "---",
                "---",
                "---",
                "",
                "",
                0
        );


        ObservableList<TimeSlot> timeSlots = getTimeSlotsList();

        ObservableList<TimeTable> timeTableViewListMonday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListTuesday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListWednesday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListThursday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListFriday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListSaturday = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListSunday = FXCollections.observableArrayList();

        ObservableList<TimeTable> timeTableViewListMondayX = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListTuesdayX = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListWednesdayX = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListThursdayX = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListFridayX = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListSaturdayX = FXCollections.observableArrayList();
        ObservableList<TimeTable> timeTableViewListSundayX = FXCollections.observableArrayList();


        for (int i = 0; i < timeTableList.size(); i++) {

            TimetableValue = timeTableList.get(i);

            if (TimetableValue.getLecturer().equals(lec) && TimetableValue.getDayName().equals("Monday")){
                timeTableViewListMonday.add(TimetableValue);

//                System.out.println("timeTableViewListMonday value Added");
//                System.out.println(timeTableViewListMonday.size());

            }else if (TimetableValue.getLecturer().equals(lec) && TimetableValue.getDayName().equals("Tuesday")){
                timeTableViewListTuesday.add(TimetableValue);

//                System.out.println("timeTableViewListTuesday value Added");

            }else if (TimetableValue.getLecturer().equals(lec) && TimetableValue.getDayName().equals("Wednsday")){
                timeTableViewListWednesday.add(TimetableValue);

//                System.out.println("timeTableViewListWednesday value Added");

            }else if (TimetableValue.getLecturer().equals(lec) && TimetableValue.getDayName().equals("Thursday")){
                timeTableViewListThursday.add(TimetableValue);

//                System.out.println("timeTableViewListThursday Size = " + timeTableViewListThursday.size());

            }else if (TimetableValue.getLecturer().equals(lec) && TimetableValue.getDayName().equals("Friday")){
                timeTableViewListFriday.add(TimetableValue);

//                System.out.println("timeTableViewListFriday value Added");

            }else if (TimetableValue.getLecturer().equals(lec) && TimetableValue.getDayName().equals("Saturday")){
                timeTableViewListSaturday.add(TimetableValue);

            }else if (TimetableValue.getLecturer().equals(lec) && TimetableValue.getDayName().equals("Sunday")){
                timeTableViewListSunday.add(TimetableValue);
            }


        }

        for (int i = 0; i < timeTableViewListMonday.size(); i++) {
            for (int j = 0; j < timeTableViewListMonday.size(); j++) {

                if (!timeTableViewListMonday.get(i).getSessionId().equals(timeTableViewListMonday.get(j).getSessionId())
                        &&
                        timeTableViewListMonday.get(i).getTimeSlot().equals(timeTableViewListMonday.get(j).getTimeSlot())
                ) {
                  String message =  "The "+ timeTableViewListMonday.get(i).getLecturer() + "has more than 1 session at " + timeTableViewListMonday.get(i).getTimeSlot()+"  " +timeTableViewListMonday.get(i).getId()+"  " +timeTableViewListMonday.get(i).getSessionId() + " " + i + " " + j ;
                }
            }
        }
        for (int i = 0; i < timeTableViewListTuesday.size(); i++) {
            for (int j = 0; j < timeTableViewListTuesday.size(); j++) {

                if (!timeTableViewListTuesday.get(i).getSessionId().equals(timeTableViewListTuesday.get(j).getSessionId())
                        &&
                        timeTableViewListTuesday.get(i).getTimeSlot().equals(timeTableViewListTuesday.get(j).getTimeSlot())
                ) {
                    System.out.println("The "+ timeTableViewListTuesday.get(i).getLecturer() + "has more than 1 session at " + timeTableViewListTuesday.get(i).getTimeSlot()+"  " +timeTableViewListTuesday.get(i).getId()+"  " +timeTableViewListTuesday.get(i).getSessionId() + " " + i + " " + j );
                }
            }
        }
        for (int i = 0; i < timeTableViewListWednesday.size(); i++) {
            for (int j = 0; j < timeTableViewListWednesday.size(); j++) {

                if (!timeTableViewListWednesday.get(i).getSessionId().equals(timeTableViewListWednesday.get(j).getSessionId())
                        &&
                        timeTableViewListWednesday.get(i).getTimeSlot().equals(timeTableViewListWednesday.get(j).getTimeSlot())
                ) {
                    System.out.println("The "+ timeTableViewListWednesday.get(i).getLecturer() + "has more than 1 session at " + timeTableViewListWednesday.get(i).getTimeSlot()+"  " +timeTableViewListWednesday.get(i).getId()+"  " +timeTableViewListWednesday.get(i).getSessionId() + " " + i + " " + j );
                }
            }
        }
        for (int i = 0; i < timeTableViewListThursday.size(); i++) {
            for (int j = 0; j < timeTableViewListThursday.size(); j++) {

                if (!timeTableViewListThursday.get(i).getSessionId().equals(timeTableViewListThursday.get(j).getSessionId())
                        &&
                        timeTableViewListThursday.get(i).getTimeSlot().equals(timeTableViewListThursday.get(j).getTimeSlot())
                ) {
                    System.out.println("The "+ timeTableViewListThursday.get(i).getLecturer() + "has more than 1 session at " + timeTableViewListThursday.get(i).getTimeSlot()+"  " +timeTableViewListThursday.get(i).getId()+"  " +timeTableViewListThursday.get(i).getSessionId() + " " + i + " " + j );
                }
            }
        }
        for (int i = 0; i < timeTableViewListFriday.size(); i++) {
            for (int j = 0; j < timeTableViewListFriday.size(); j++) {

                if (!timeTableViewListFriday.get(i).getSessionId().equals(timeTableViewListFriday.get(j).getSessionId())
                        &&
                        timeTableViewListFriday.get(i).getTimeSlot().equals(timeTableViewListFriday.get(j).getTimeSlot())
                ) {
                    System.out.println("The "+ timeTableViewListFriday.get(i).getLecturer() + "has more than 1 session at " + timeTableViewListFriday.get(i).getTimeSlot()+"  " +timeTableViewListFriday.get(i).getId()+"  " +timeTableViewListFriday.get(i).getSessionId() + " " + i + " " + j );
                }
            }
        }
        for (int i = 0; i < timeTableViewListSaturday.size(); i++) {
            for (int j = 0; j < timeTableViewListSaturday.size(); j++) {

                if (!timeTableViewListSaturday.get(i).getSessionId().equals(timeTableViewListSaturday.get(j).getSessionId())
                        &&
                        timeTableViewListSaturday.get(i).getTimeSlot().equals(timeTableViewListSaturday.get(j).getTimeSlot())
                ) {
                    System.out.println("The "+ timeTableViewListSaturday.get(i).getLecturer() + "has more than 1 session at " + timeTableViewListSaturday.get(i).getTimeSlot()+"  " +timeTableViewListSaturday.get(i).getId()+"  " +timeTableViewListSaturday.get(i).getSessionId() + " " + i + " " + j );
                }
            }
        }
        for (int i = 0; i < timeTableViewListSunday.size(); i++) {
            for (int j = 0; j < timeTableViewListSunday.size(); j++) {

                if (!timeTableViewListSunday.get(i).getSessionId().equals(timeTableViewListSunday.get(j).getSessionId())
                        &&
                        timeTableViewListSunday.get(i).getTimeSlot().equals(timeTableViewListSunday.get(j).getTimeSlot())
                ) {
                    System.out.println("The "+ timeTableViewListSunday.get(i).getLecturer() + "has more than 1 session at " + timeTableViewListSunday.get(i).getTimeSlot()+"  " +timeTableViewListSunday.get(i).getId()+"  " +timeTableViewListSunday.get(i).getSessionId() + " " + i + " " + j );
                }
            }
        }

        for (int i = 0; i < timeSlots.size(); i++) {

            if (timeTableViewListMonday.size()>tempT1) {
                if (!timeSlots.get(i).getValue_t().equals(timeTableViewListMonday.get(tempT1).getTimeSlot())) {
                    timeTableViewListMondayX.add(yy);
                }else{
                    timeTableViewListMondayX.add(timeTableViewListMonday.get(tempT1));
                    tempT1++;
                }
            }else{
                timeTableViewListMondayX.add(yy);
            }

            if (timeTableViewListTuesday.size()>tempT2){
                if (!timeSlots.get(i).getValue_t().equals(timeTableViewListTuesday.get(tempT2).getTimeSlot())) {
                    timeTableViewListTuesdayX.add(yy);
                }else{
                    timeTableViewListTuesdayX.add(timeTableViewListTuesday.get(tempT2));
                    tempT2++;
                }
            }else{
                timeTableViewListTuesdayX.add(yy);
            }

            if (timeTableViewListWednesday.size()>tempT3){

                if (!timeSlots.get(i).getValue_t().equals(timeTableViewListWednesday.get(tempT3).getTimeSlot())) {
                    timeTableViewListWednesdayX.add(yy);
                }else{
                    timeTableViewListWednesdayX.add(timeTableViewListWednesday.get(tempT3));
                    tempT3++;
                }
            }else{
                timeTableViewListWednesdayX.add(yy);
            }

            if (timeTableViewListThursday.size()>tempT4){
                if (!timeSlots.get(i).getValue_t().equals(timeTableViewListThursday.get(tempT4).getTimeSlot())) {
                    timeTableViewListThursdayX.add(yy);
                }else if(timeSlots.get(i).getValue_t().equals(timeTableViewListThursday.get(tempT4).getTimeSlot())){
                    timeTableViewListThursdayX.add(timeTableViewListThursday.get(tempT4));
                    tempT4++;
                }
            }else{
                timeTableViewListThursdayX.add(yy);
            }

            if (timeTableViewListFriday.size()>tempT5){
                if (!timeSlots.get(i).getValue_t().equals(timeTableViewListFriday.get(tempT5).getTimeSlot())) {
                    timeTableViewListFridayX.add(yy);
                }else{
                    timeTableViewListFridayX.add(timeTableViewListFriday.get(tempT5));
                    tempT5++;
                }
            }else{
                timeTableViewListFridayX.add(yy);
            }

            if (timeTableViewListSaturday.size()>tempT6){
                if (!timeSlots.get(i).getValue_t().equals(timeTableViewListSaturday.get(tempT6).getTimeSlot())) {
                    timeTableViewListSaturdayX.add(yy);
                }else{
                    timeTableViewListSaturdayX.add(timeTableViewListSaturday.get(tempT6));
                    tempT6++;
                }
            }else{
                timeTableViewListSaturdayX.add(yy);
            }

            if (timeTableViewListSunday.size()>tempT7){

                if (!timeSlots.get(i).getValue_t().equals(timeTableViewListSunday.get(tempT7).getTimeSlot())) {
                    timeTableViewListSundayX.add(yy);
                }else{
                    timeTableViewListSundayX.add(timeTableViewListSunday.get(tempT7));
                    tempT7++;
                }
            }else{
                timeTableViewListSundayX.add(yy);
            }


        }




        ObservableList<TimeTable> temp = FXCollections.observableArrayList();
        for (int i = 0; i < timeSlots.size(); i++) {

            if (timeTableViewListMondayX.size() > i) {
                if (timeTableViewListMondayX.get(i) != null) {
                    Id = timeTableViewListMondayX.get(i).getId();
                    timeSlot = timeSlots.get(i).getValue_t();
                    Module = timeTableViewListMondayX.get(i).getModule();

//                    System.out.println("Monday has values " + timeTableViewListMondayX.size());
                }
                else{
                    Id = 0;
                    timeSlot = timeSlots.get(i).getValue_t();
                    Module = null;
                }

            } else{
                Id = 0;
                timeSlot = null;
                Module = null;

            }


            if (timeTableViewListTuesdayX.size() > i) {

                if (timeTableViewListTuesdayX.get(i) != null) {
                    tag = timeTableViewListTuesdayX.get(i).getModule();
                }
                else{
                    tag = null;
                }
            }else{
                tag = null;
            }

            if (timeTableViewListWednesdayX.size() > i) {

                if (timeTableViewListWednesdayX.get(i) != null) {
                    Hall = timeTableViewListWednesdayX.get(i).getModule();
                }
                else{
                    Hall = null;
                }

            }else{
                Hall = null;
            }

            if (timeTableViewListThursdayX.size() > i) {

                if (timeTableViewListThursdayX.get(i) != null) {
                    group1 = timeTableViewListThursdayX.get(i).getModule();
                }
                else{
                    group1 = null;
                }

            }else{
                group1 = null;
            }

            if (timeTableViewListFridayX.size() > i) {

                if (timeTableViewListFridayX.get(i) != null) {
                    lecturer = timeTableViewListFridayX.get(i).getModule();
                }
                else{
                    lecturer = null;
                }

            }else{
                lecturer = null;
            }

            if (timeTableViewListSaturdayX.size() > i) {

                if (timeTableViewListSaturdayX.get(i) != null) {
                    sessionId = timeTableViewListSaturdayX.get(i).getModule();
                }
                else{
                    sessionId = null;
                }
            }else{
                sessionId = null;
            }

            if (timeTableViewListSundayX.size() > i) {

                if (timeTableViewListSundayX.get(i) != null) {
                    dayName = timeTableViewListSundayX.get(i).getModule();
                }
                else{
                    dayName = null;
                }

            }else{
                dayName = null;
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
                    dayName,
                    duration


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
            }else if (TimetableValue.getHall().equals(group) && TimetableValue.getDayName().equals("Wednsday")){
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
            }else{
                sessionId = null;
            }

            if (timeTableViewListSunday.size() > i) {
                dayName = timeTableViewListSunday.get(i).getModule();
            }else{
                dayName = null;
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
                    dayName,
                    duration
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
                databaseHelper.executeQuery(query);
                System.out.println(ss);
                System.out.println();

            }
        }



    }

/*    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/

}
