package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.ManageCanNotBeReservedTimeForRoom.ClassedUsed.CannotBeReservedTimeForLocation;
import TimeTableGeneratorDesktopApp.ManageCanNotBeReservedTimeForRoom.ClassedUsed.CannotBeReservedTimeForLocationTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CannotBeReservedTimeForLocationDatabaseHelper extends DatabaseHelper {


    // -------------------------- Cannot Be Reserved part ---------------------------------------------------------------------------------------

    public CannotBeReservedTimeForLocation cannotBeReservedTimeForLocation;
    public Boolean statusTrue;

    public ObservableList<CannotBeReservedTimeForLocationTM> checkCannotBeReservedTimeForLocationTable(CannotBeReservedTimeForLocation cannotBeReservedTimeForLocation, Boolean statusTrue) {

        this.cannotBeReservedTimeForLocation = cannotBeReservedTimeForLocation;

        this.statusTrue = statusTrue;

        // create ObservableList object
        ObservableList<CannotBeReservedTimeForLocationTM> cannotBeReservedTimeForLocationTMList = FXCollections.observableArrayList();

        // get database connection
        Connection conn = getConnection();

        System.out.println("testing preferred_room_for_tags table: " +
                "timeslotID= " + this.cannotBeReservedTimeForLocation.getTimeSlot().getSlotsID()
                + ", LocationID = " + this.cannotBeReservedTimeForLocation.getLocationHallLab().getLocationID() + ", " +
                "DayName = " + this.cannotBeReservedTimeForLocation.getDayName());

        String query = "SELECT * FROM cannot_be_reserved_time_for_location WHERE timeslot_id = " + this.cannotBeReservedTimeForLocation.getTimeSlot().getSlotsID() + " AND location_location_id = " + this.cannotBeReservedTimeForLocation.getLocationHallLab().getLocationID() + " AND day = '" + this.cannotBeReservedTimeForLocation.getDayName() + "'";

/*        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                CannotBeReservedTimeForLocationTM cannotBeReservedTimeForLocationTM = new CannotBeReservedTimeForLocationTM();
                cannotBeReservedTimeForLocationTM.setCannot_be_reserved_time_for_location_id(rs.getInt("cannot_be_reserved_time_for_location_id"));
                cannotBeReservedTimeForLocationTM.setDay(rs.getString("day"));

                cannotBeReservedTimeForLocationTM.setLocation_location_id(rs.getInt("location_location_id"));
                cannotBeReservedTimeForLocationTM.setTimeslot_id(rs.getInt("timeslot_id"));

                if (rs.getString("status_true").equals("Y")) {
                    cannotBeReservedTimeForLocationTM.setStatus_true(true);
                } else {
                    cannotBeReservedTimeForLocationTM.setStatus_true(false); // true was there, I corrected it false
                }

                // add the preferredLocation object to the observableList
                cannotBeReservedTimeForLocationTMList.add(cannotBeReservedTimeForLocationTM);
            }

        } catch (SQLException ex) {
            // if an error occurs print an error...
            System.out.println("Error SQLException - When suitable_room_for_tags table data retrieving ");
            ex.printStackTrace();
        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When suitable_room_for_tags table data retrieving ");
            ex.printStackTrace();
        }

        return cannotBeReservedTimeForLocationTMList;

    }


    public void setCannotBeReservedTimeForLocationTable(ObservableList<CannotBeReservedTimeForLocationTM> cannotBeReservedTimeForLocationTMList) {

        String query;

        if (cannotBeReservedTimeForLocationTMList.isEmpty() != true) {
            // already there is/are record(s) in the database

            if (this.statusTrue) {
                // checkbox is marked by the user, that is why this.statusTrue == true
                for (CannotBeReservedTimeForLocationTM cannotBeReservedTimeForLocationTM : cannotBeReservedTimeForLocationTMList) {
                    // update query
                    try {
                        query = "UPDATE `cannot_be_reserved_time_for_location` SET status_true = 'Y' WHERE cannot_be_reserved_time_for_location_id = " + cannotBeReservedTimeForLocationTM.getCannot_be_reserved_time_for_location_id() + "";

                        // execute the update query
                        executeQuery(query);
                    } catch (Exception ex) {
                        System.out.println("Error updating cannot_be_reserved_time_for_location and SET status_true = 'Y': " + cannotBeReservedTimeForLocationTM.toString());
                        ex.printStackTrace();
                    }
                }
            } else {
                // checkbox is not marked by the user, that is why this.statusTrue == false
                for (CannotBeReservedTimeForLocationTM cannotBeReservedTimeForLocationTM : cannotBeReservedTimeForLocationTMList) {
                    // update query
                    try {
                        query = "UPDATE `cannot_be_reserved_time_for_location` SET status_true = 'N' WHERE cannot_be_reserved_time_for_location_id = " + cannotBeReservedTimeForLocationTM.getCannot_be_reserved_time_for_location_id() + "";

                        // execute the update query
                        executeQuery(query);
                    } catch (Exception ex) {
                        System.out.println("Error updating cannot_be_reserved_time_for_location and SET status_true = 'N': " + cannotBeReservedTimeForLocationTM.toString());
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            // preferredLocationsList is empty,
            // then we have to insert a new preferred location record to preferred_room_for_subject table

            if (this.statusTrue) {
                // checkbox is marked by the user, that is why this.statusTrue == true
                try {
                    // insert query
                    //status_true default value = 'Y', did not include in the insert into query
                    query = "INSERT INTO `cannot_be_reserved_time_for_location` (`day`,`location_location_id`,`timeslot_id`) VALUES ('" + this.cannotBeReservedTimeForLocation.getDayName() + "'," + this.cannotBeReservedTimeForLocation.getLocationHallLab().getLocationID() + "," + this.cannotBeReservedTimeForLocation.getTimeSlot().getSlotsID() + ")";

                    // execute the insert query
                    executeQuery(query);
                } catch (Exception ex) {
                    System.out.println("Error inserting cannot_be_reserved_time_for_location");
                    ex.printStackTrace();
                }
            } else {
                // checkbox is not marked by the user, that is why this.statusTrue == false
                System.out.println("checkbox is not selected, timeslot is not a cannot_be_reserved_time_for_location");

            }
        }
    }

}
