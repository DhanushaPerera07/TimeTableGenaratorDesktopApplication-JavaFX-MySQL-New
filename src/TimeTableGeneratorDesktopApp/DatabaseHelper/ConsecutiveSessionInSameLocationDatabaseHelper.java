package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.ConsecutiveSessionInSameLocation;
import TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.ConsecutiveSessionViewAll;
import TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.ConsecutiveSessionViewModel;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsecutiveSessionInSameLocationDatabaseHelper extends DatabaseHelper {

    /**
     * This method check whether that consecutive session is already set in a location
     * returns locationList;
     */
    public ConsecutiveSessionInSameLocation checkForConsecutiveSessionInSameLocationTableData(int locationID, int consecutiveSessionID) {


        ObservableList<ConsecutiveSessionInSameLocation> consecutiveSessionInSameLocationList
                = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String query;

        query = "SELECT c.*\n" +
                "FROM consecutive_session_in_same_room AS c\n" +
                "WHERE c.location_location_id = " + locationID +
                " AND c.consecutive_session_id = " + consecutiveSessionID + "";


/*        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {


            ConsecutiveSessionInSameLocation consecutiveSessionInSameLocation;
            while (rs.next()) {

                consecutiveSessionInSameLocation = new ConsecutiveSessionInSameLocation();
                consecutiveSessionInSameLocation.setConsecutive_session_in_same_room_id(rs.getInt("consecutive_session_in_same_room_id"));
                consecutiveSessionInSameLocation.setLocation_location_id(rs.getInt("location_location_id"));
                consecutiveSessionInSameLocation.setConsecutive_session_int_id(rs.getInt("consecutive_session_id"));
                consecutiveSessionInSameLocation.setStatus_true(rs.getString("status_true"));


/*                consecutiveSessionInSameLocation = new ConsecutiveSessionInSameLocation(
                        rs.getInt("consecutive_session_in_same_room_id"),
                        rs.getInt("location_location_id"),
                        rs.getInt("consecutive_session_id"),
                        rs.getString("status_true")

                );*/
                consecutiveSessionInSameLocationList.add(consecutiveSessionInSameLocation);
            }

        }catch (SQLException ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }

        if (consecutiveSessionInSameLocationList.isEmpty()) {
            return null;
        } else {
            return consecutiveSessionInSameLocationList.get(0);
        }
    }


    // --------------------------------------------------------------------------------


    /**
     * This method check whether that consecutive session is already set in a location
     * and status_true = "Y" or "N"
     * returns locationList;
     */
    public ConsecutiveSessionInSameLocation checkWhetherConsecutiveSessionAlreadySetInSameLocationTableData(int locationID, int consecutiveSessionInSameLocationID) {


        ObservableList<ConsecutiveSessionInSameLocation> consecutiveSessionInSameLocationList
                = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String query;

        query = "SELECT c.*\n" +
                "FROM consecutive_session_in_same_room AS c\n" +
                "WHERE c.location_location_id = " + locationID +
                " AND c.consecutive_session_id = " + consecutiveSessionInSameLocationID + "";


/*        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/


        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {


            ConsecutiveSessionInSameLocation consecutiveSessionInSameLocation;
            while (rs.next()) {
                consecutiveSessionInSameLocation = new ConsecutiveSessionInSameLocation(
                        rs.getInt("consecutive_session_in_same_room_id"),
                        rs.getInt("location_location_id"),
                        rs.getString("consecutive_session_id"),
                        rs.getString("status_true")

                );
                consecutiveSessionInSameLocationList.add(consecutiveSessionInSameLocation);
            }

        } catch (SQLException ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }

        if (consecutiveSessionInSameLocationList.isEmpty()) {
            return null;
        } else {
            return consecutiveSessionInSameLocationList.get(0);
        }
    }


    // ----------------------------------------------------------------------------------------


    // variable need to hold values
    int consecutiveSessionID;
    int locationID;
    Boolean statusTrue;

    /**
     * Check whether there are consecutiveSessions set in a same location for that particular student batch
     *
     * @param consecutiveSessionID
     * @param locationID
     * @return
     */

    public ObservableList<ConsecutiveSessionInSameLocation> checkConsecutiveSessionsInTheSameLocation(int consecutiveSessionID, int locationID, Boolean statusTrue) {

        this.consecutiveSessionID = consecutiveSessionID;
        this.locationID = locationID;

        this.statusTrue = statusTrue;

        // create ObservableList object
        ObservableList<ConsecutiveSessionInSameLocation> consecutiveSessionInSameLocationList = FXCollections.observableArrayList();

        // get database connection
        Connection conn = getConnection();

        System.out.println("testing consecutive_session_in_same_room table: " + "consecutive_session_id: " + consecutiveSessionID + " ,LocationID: " + locationID + "");
        String query = "SELECT * FROM consecutive_session_in_same_room WHERE consecutive_session_id = " + this.consecutiveSessionID + " AND location_location_id = " + this.locationID + " ORDER BY consecutive_session_in_same_room_id";

/*        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                ConsecutiveSessionInSameLocation consecutiveSessionInSameLocation = new ConsecutiveSessionInSameLocation();
                consecutiveSessionInSameLocation.setConsecutive_session_in_same_room_id(rs.getInt("consecutive_session_in_same_room_id"));
                consecutiveSessionInSameLocation.setLocation_location_id(rs.getInt("location_location_id"));
                consecutiveSessionInSameLocation.setConsecutive_session_int_id(rs.getInt("consecutive_session_id"));
                consecutiveSessionInSameLocation.setStatus_true(rs.getString("status_true"));

                // add the preferredLocation object to the observableList
                consecutiveSessionInSameLocationList.add(consecutiveSessionInSameLocation);
            }

        } catch (SQLException ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When consecutive_session_in_same_room table data retrieving ");
            ex.printStackTrace();
        }

        return consecutiveSessionInSameLocationList;

    }


    public void setConsecutiveSessionsInTheSameLocation(ObservableList<ConsecutiveSessionInSameLocation> consecutiveSessionInSameLocationList) {

        String query;

        if (consecutiveSessionInSameLocationList.isEmpty() != true) {
            // already there is/are record(s) in the database

            if (this.statusTrue) {
                // checkbox is marked by the user, that is why this.statusTrue == true
                for (ConsecutiveSessionInSameLocation consecutiveSessionInSameLocation : consecutiveSessionInSameLocationList) {
                    // update query
                    try {
                        query = "UPDATE `consecutive_session_in_same_room` SET status_true = 'Y' WHERE consecutive_session_in_same_room_id = " + consecutiveSessionInSameLocation.getConsecutive_session_in_same_room_id() + "";

                        // execute the update query
                        executeQuery(query);
                    } catch (Exception ex) {
                        System.out.println("Error updating consecutive_session_in_same_room: " + consecutiveSessionInSameLocation.toString());
                        ex.printStackTrace();
                    }
                }
            } else {
                // checkbox is not marked by the user, that is why this.statusTrue == false
                for (ConsecutiveSessionInSameLocation consecutiveSessionInSameLocation : consecutiveSessionInSameLocationList) {
                    // update query
                    try {
                        query = "UPDATE `consecutive_session_in_same_room` SET status_true = 'N' WHERE consecutive_session_in_same_room_id = " + consecutiveSessionInSameLocation.getConsecutive_session_in_same_room_id() + "";

                        // execute the update query
                        executeQuery(query);
                    } catch (Exception ex) {
                        System.out.println("Error updating consecutive_session_in_same_room: " + consecutiveSessionInSameLocation.toString());
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
                    query = "INSERT INTO `consecutive_session_in_same_room` (`location_location_id`,`consecutive_session_id`) VALUES (" + this.locationID + "," + this.consecutiveSessionID + ")";

                    // execute the insert query
                    executeQuery(query);
                } catch (Exception ex) {
                    System.out.println("Error inserting consecutive_session_in_same_room");
                    ex.printStackTrace();
                }
            } else {
                // checkbox is not marked by the user, that is why this.statusTrue == false
                System.out.println("checkbox is not selected, consecutive session is not in same room");

            }
        }

    }

    // ----------------------------------------------------------------------------------------


    public ObservableList<ConsecutiveSessionViewAll> getAllConsecutiveSessionInSameLocationTable() {

        //observable lists
        //ObservableList<ConsecutiveSessionInSameLocation> consecutiveSessionInSameLocationList = FXCollections.observableArrayList();
        //ObservableList<ConsecutiveSessionViewModel> consecutiveSessionViewModelList = FXCollections.observableArrayList();
        ObservableList<ConsecutiveSessionViewAll> consecutiveSessionViewAllList = FXCollections.observableArrayList();

        ConsecutiveSessionsDatabaseHelper consecutiveSessionsDatabaseHelper = new ConsecutiveSessionsDatabaseHelper();
        LocationHallLabDatabaseHelper locationHallLabDatabaseHelper = new LocationHallLabDatabaseHelper();

        Connection conn = getConnection();

        String query = "SELECT * FROM `consecutive_session_in_same_room` WHERE status_true = 'Y' ORDER BY consecutive_session_in_same_room_id";

/*        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            ConsecutiveSessionInSameLocation consecutiveSessionInSameLocation;
            ConsecutiveSessionViewModel consecutiveSessionViewModel;
            ConsecutiveSessionViewAll consecutiveSessionViewAll = new ConsecutiveSessionViewAll();

            while (rs.next()) {
                consecutiveSessionInSameLocation = new ConsecutiveSessionInSameLocation();
                //LocationHallLab locationHallLab = new LocationHallLab();

                consecutiveSessionInSameLocation.setConsecutive_session_in_same_room_id(rs.getInt("consecutive_session_in_same_room_id"));
                consecutiveSessionInSameLocation.setConsecutive_session_int_id(rs.getInt("consecutive_session_id"));
                consecutiveSessionInSameLocation.setLocation_location_id(rs.getInt("location_location_id"));
                consecutiveSessionInSameLocation.setStatus_true(rs.getString("status_true"));

   /*                 Sessions session1 = consecutiveSessionsDatabaseHelper.getConsecutiveSessionsByID()
                    Sessions session2 = */

                if (consecutiveSessionsDatabaseHelper.getConsecutiveSessionsViewModelByID(consecutiveSessionInSameLocation.getConsecutive_session_int_id()) != null){
                    consecutiveSessionViewModel = consecutiveSessionsDatabaseHelper.getConsecutiveSessionsViewModelByID(consecutiveSessionInSameLocation.getConsecutive_session_int_id());
                } else {
                    consecutiveSessionViewModel = new ConsecutiveSessionViewModel();
                }


                consecutiveSessionViewAll.setConsecutive_session_in_same_room_id(consecutiveSessionInSameLocation.getConsecutive_session_in_same_room_id());
                LocationHallLab locationHallLabTemp = locationHallLabDatabaseHelper.getLocationHallLabInstanceByID(consecutiveSessionInSameLocation.getLocation_location_id());
                consecutiveSessionViewAll.setLocationHallLab(locationHallLabTemp); // location (Actually, I want the location id and location name)
                consecutiveSessionViewAll.setSessions1(consecutiveSessionViewModel.getSession1()); // session 1
                consecutiveSessionViewAll.setSessions2(consecutiveSessionViewModel.getSession2()); // session 2

                consecutiveSessionViewAllList.add(consecutiveSessionViewAll);

                //consecutiveSessionInSameLocationList.add(consecutiveSessionInSameLocation);
            }

        } catch (SQLException ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return consecutiveSessionViewAllList;
    }


    // ----------------------------------------------------------------------------------------


}
