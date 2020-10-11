package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.TimePeriods.TimeSlots.TimeSlot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TimeSlotDatabaseHelper extends DatabaseHelper {

    public ObservableList<TimeSlot> getAllTimeSlotsFromTimeslotsTable() {

        ObservableList<TimeSlot> timeSlotList = FXCollections.observableArrayList();

        Connection conn = getConnection();

        String query = "SELECT * FROM timeslots";

/*        Statement st;
        ResultSet rs;

        try {

            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {


            TimeSlot timeSlot;
            while (rs.next()) {
                timeSlot = new TimeSlot(
                        rs.getInt("slotsID"),
                        rs.getDouble("range_t"),
                        rs.getString("value_t")
                );
                timeSlotList.add(timeSlot);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Error - When time slots data retrieving ");
            ex.printStackTrace();
        }

        return timeSlotList;
    }

}
