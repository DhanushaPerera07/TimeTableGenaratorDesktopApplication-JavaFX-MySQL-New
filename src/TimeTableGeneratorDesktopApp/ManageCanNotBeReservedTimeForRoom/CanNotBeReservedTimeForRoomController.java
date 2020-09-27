package TimeTableGeneratorDesktopApp.ManageCanNotBeReservedTimeForRoom;

import TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.ConsecutiveSessionInSameLocation;
import TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.ConsecutiveSessionViewModel;
import TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.SingleConsecutiveSessionItem.SingleConsecutiveSessionItemController;
import TimeTableGeneratorDesktopApp.DatabaseHelper.*;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import TimeTableGeneratorDesktopApp.ManageCanNotBeReservedTimeForRoom.ClassedUsed.CannotBeReservedTimeForLocation;
import TimeTableGeneratorDesktopApp.ManageCanNotBeReservedTimeForRoom.ClassedUsed.CannotBeReservedTimeForLocationTM;
import TimeTableGeneratorDesktopApp.ManageCanNotBeReservedTimeForRoom.ClassedUsed.DayTM;
import TimeTableGeneratorDesktopApp.ManageCanNotBeReservedTimeForRoom.SingleItemForCannotBeReservedTime.SingleItemForCannotBeReservedTimeController;
import TimeTableGeneratorDesktopApp.TimePeriods.TimeSlots.TimeSlot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CanNotBeReservedTimeForRoomController implements Initializable {


    //variable to keep some values needed
    public LocationHallLab locationHallLab;

    static String dayNameToDisplay;

    @FXML
    private BorderPane borderPaneLocationsHallsInsideBuildingMain;

    @FXML
    private Label txtHeaderLabsHalls;

    @FXML
    private Label txtLocationName;

    @FXML
    private Label txtTagName;

    @FXML
    private TextField locationsHallsInsideSearchTxtBox;

    @FXML
    private Button btnSearchLocationsHallsInside;

/*    @FXML
    private ComboBox<String> comboBoxLocation;*/

    @FXML
    private ComboBox<String> comboBoxDay;

    @FXML
    private VBox locationsVBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeComboBoxes();

    }


    // ----------------------------------------------------------------------------------------

    public void getNecessaryInformationFromLocationHallsLabsUI(LocationHallLab locationHallLab) {
        this.locationHallLab = locationHallLab;
        System.out.println("Test this.locationHallLab rec: " + this.locationHallLab.toString());

        TagsDatabaseHelper tagsDatabaseHelper = new TagsDatabaseHelper();
        String tagName = tagsDatabaseHelper.getTagInstanceByTagID(this.locationHallLab.getTagID()).getTag();
        String locationName = this.locationHallLab.getLocationName();

        txtLocationName.setText(locationName);
        txtTagName.setText(tagName);

        populateRows();

    }

    // ----------------------------------------------------------------------------------------

    private void initializeComboBoxes() {

        DaysDatabaseHelper daysDatabaseHelper = new DaysDatabaseHelper();

        DayTM dayTM = daysDatabaseHelper.getDayTM();

        System.out.println("Test sout dayTM :" + dayTM.toString());

        ObservableList<String> dayList = FXCollections.observableArrayList();

        dayList.add(dayTM.getDay1());
        dayList.add(dayTM.getDay2());
        dayList.add(dayTM.getDay3());
        dayList.add(dayTM.getDay4());
        dayList.add(dayTM.getDay5());
        dayList.add(dayTM.getDay6());
        dayList.add(dayTM.getDay7());


        for(String dayName: dayList) {
            if (dayName == null){
                System.out.println("Test sout: dayName is not valid");
            } else {
                comboBoxDay.getItems().add(dayName);
                System.out.println("Test sout DayName: " + dayName);
            }
        }

        comboBoxDay.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            System.out.println("listener works !");
            System.out.println("oldValue : " + oldValue);
            System.out.println("newValue : " + newValue);

            //comboBoxDay.getSelectionModel().selectFirst();

            this.dayNameToDisplay = newValue;

            populateRows();

        });


        //populateRows();
    }

    // ----------------------------------------------------------------------------------------

    private void populateRows() {

        locationsVBox.getChildren().clear();


        //Days
        DaysDatabaseHelper daysDatabaseHelper = new DaysDatabaseHelper();

        DayTM dayTM = daysDatabaseHelper.getDayTM();

        System.out.println("Test sout dayTM :" + dayTM.toString());

        ObservableList<String> dayList = FXCollections.observableArrayList();

        if (dayTM.getDay1() != null){
            dayList.add(dayTM.getDay1());
        }

        if (dayTM.getDay2() != null){
            dayList.add(dayTM.getDay2());
        }

        if (dayTM.getDay3() != null){
            dayList.add(dayTM.getDay3());
        }

        if (dayTM.getDay4() != null){
            dayList.add(dayTM.getDay4());
        }

        if (dayTM.getDay5() != null){
            dayList.add(dayTM.getDay5());
        }

        if (dayTM.getDay6() != null){
            dayList.add(dayTM.getDay6());
        }

        if (dayTM.getDay7() != null){
            dayList.add(dayTM.getDay7());
        }


        TimeSlotDatabaseHelper timeSlotDatabaseHelper = new TimeSlotDatabaseHelper();

        // observable list
        ObservableList<TimeSlot> timeSlotList = timeSlotDatabaseHelper.getAllTimeSlotsFromTimeslotsTable();

        ObservableList<CannotBeReservedTimeForLocation> cannotBeReservedTimeForLocationList = FXCollections.observableArrayList();


        for (String dayName:dayList) {
            System.out.println("DayName : " + dayName);
            for (int i = 0; i < timeSlotList.size(); i++) {
                CannotBeReservedTimeForLocation cannotBeReservedTimeForLocation = new CannotBeReservedTimeForLocation();
                cannotBeReservedTimeForLocation.setDayName(dayName);
                cannotBeReservedTimeForLocation.setLocationHallLab(this.locationHallLab);
                cannotBeReservedTimeForLocation.setTimeSlot(timeSlotList.get(i));


                cannotBeReservedTimeForLocation.setStatus_true(checkCannotBeReservedTimeOrNot(dayName,this.locationHallLab,timeSlotList.get(i).getSlotsID()));

                cannotBeReservedTimeForLocationList.add(cannotBeReservedTimeForLocation);
            }
        }


        populateNodes(cannotBeReservedTimeForLocationList);



    }//


    // ---------------------------------------------------------------------------------------

    private void populateNodes(ObservableList<CannotBeReservedTimeForLocation> cannotBeReservedTimeForLocationList){
        // get timeslots and other details according to the day
        ObservableList<CannotBeReservedTimeForLocation> timeSlotsNodesToDisplayForSelectedDay = FXCollections.observableArrayList();

        for(CannotBeReservedTimeForLocation cannotBeReservedTimeForLocation: cannotBeReservedTimeForLocationList){
            if(cannotBeReservedTimeForLocation.getDayName().equals(this.dayNameToDisplay)){
                timeSlotsNodesToDisplayForSelectedDay.add(cannotBeReservedTimeForLocation);
            }
        }


        /**
         * Dynamically change the rows by getting data from the database
         * locationItemForLecturer.fxml is used as the UI, it acts as a customized data row
         * I pass the building object to the locationItemForLecturer.fxml and populate the view
         */
        // Populate the rows like a table
        Node[] nodes = new Node[timeSlotsNodesToDisplayForSelectedDay.size()];


        if (timeSlotsNodesToDisplayForSelectedDay.size() > 0) {  // location table is not empty, there are some locations (halls/ lab)
            for (int i = 0; i < timeSlotsNodesToDisplayForSelectedDay.size(); i++) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageCanNotBeReservedTimeForRoom/SingleItemForCannotBeReservedTime/SingleItemForCannotBeReservedTime.fxml"));

                    nodes[i] = (Node) loader.load();
                    SingleItemForCannotBeReservedTimeController singleItemForCannotBeReservedTimeController = loader.getController();
                    //System.out.println("Test: locationList.get(i),this.subject_id: " + locationList.get(i) + " and" +this.subject_id);
                    singleItemForCannotBeReservedTimeController.showInformationOfTimeSlots(timeSlotsNodesToDisplayForSelectedDay.get(i));


                    locationsVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - SingleItemForCannotBeReservedTime.fxml Loading ======================================");
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No timeslots are found in the database");
            locationsVBox.getChildren().clear();

            // this means that no consecutive session is found
            // so we gonna display that no consecutive sessions are found
            Node nodeThatSaysNoFacultyFound;
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageCanNotBeReservedTimeForRoom/SingleItemForCannotBeReservedTime/SingleItemForCannotBeReservedTimeNoContent.fxml"));
                nodeThatSaysNoFacultyFound = (Node) loader.load();
                locationsVBox.getChildren().addAll(nodeThatSaysNoFacultyFound);
            } catch (IOException e) {
                System.out.println("Error - SingleItemForCannotBeReservedTimeNoContent.fxml Loading ======================================");
                e.printStackTrace();
            }
        }

    } // populateNodes


    // ----------------------------------------------------------------------------------------

    @FXML
    void setOnActionBtnSearch(MouseEvent event) {

    }

    // ----------------------------------------------------------------------------------------



    public Boolean checkCannotBeReservedTimeOrNot(String dayName,LocationHallLab locationHallLab,int timeSlotID){

        Boolean status_true;
        DatabaseHelper databaseHelper = new DatabaseHelper();

        ObservableList<CannotBeReservedTimeForLocationTM> cannotBeReservedTimeForLocationTMList = FXCollections.observableArrayList();

        //Connection conn = getConnection();
        Connection conn = databaseHelper.getConnection();

        String query = "SELECT crt.* " +
                "FROM cannot_be_reserved_time_for_location AS crt " +
                "WHERE crt.location_location_id = " + locationHallLab.getLocationID() + " AND crt.timeslot_id = "+ timeSlotID + " AND crt.day = '"+ dayName +"' ;";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            CannotBeReservedTimeForLocationTM cannotBeReservedTimeForLocationTM;
            if (rs.next()) {
                cannotBeReservedTimeForLocationTM = new CannotBeReservedTimeForLocationTM();
                cannotBeReservedTimeForLocationTM.setCannot_be_reserved_time_for_location_id(rs.getInt("cannot_be_reserved_time_for_location_id"));
                cannotBeReservedTimeForLocationTM.setDay(rs.getString("day"));
                cannotBeReservedTimeForLocationTM.setLocation_location_id(rs.getInt("location_location_id"));
                cannotBeReservedTimeForLocationTM.setTimeslot_id(rs.getInt("timeslot_id"));

                if (rs.getString("status_true").equals("Y")){
                    cannotBeReservedTimeForLocationTM.setStatus_true(true);
                } else {
                    cannotBeReservedTimeForLocationTM.setStatus_true(false);
                }

                cannotBeReservedTimeForLocationTMList.add(cannotBeReservedTimeForLocationTM);

            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }


        if (cannotBeReservedTimeForLocationTMList.size() != 0) {
            if (cannotBeReservedTimeForLocationTMList.get(0).getStatus_true() == true){
                status_true = true;
            } else {
                status_true = false;
            }
        } else {
            status_true = false;
        }

        return status_true; // return Boolean value
    }//


}
