package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LocationHallLabDatabaseHelper extends DatabaseHelper{

    // methods are needed for the Hall/Lab operations

    //-------------------------------------------------------------

    public LocationHallLab getLocationHallLabInstanceByID(int locationHallLabID){

        // create a LocationHallLab object
        LocationHallLab locationHallLab = null;

        // get database connection
        Connection conn = getConnection();

        String query = "SELECT * FROM location WHERE location_delete_status = 'N' AND location_id = '" + locationHallLabID + "' ORDER BY location_name";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                locationHallLab = new LocationHallLab(
                        rs.getInt("location_id"),
                        rs.getString("location_name"),
                        rs.getInt("location_capacity"),
                        rs.getInt("location_floor"),
                        rs.getString("location_condition"),
                        rs.getInt("building_building_id"),
                        rs.getInt("tag_tag_id")
                );
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }

        return locationHallLab;

    }


    // ------------------------------------------------------------


    /**
     * this method is to get all the faculties in the faculty table...
     * returns departmentList;
     * */
    public ObservableList<LocationHallLab> getLocationHallLabList(int buildingID) {

        ObservableList<LocationHallLab> locationHallLabList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String query = "SELECT * FROM location WHERE building_building_id = "+ buildingID +
                " AND location_delete_status = 'N' ORDER BY location_name";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            LocationHallLab locationHallLab;
            while (rs.next()) {
                locationHallLab = new LocationHallLab(
                        rs.getInt("location_id"),
                        rs.getString("location_name"),
                        rs.getInt("location_capacity"),
                        rs.getInt("location_floor"),
                        rs.getString("location_condition"),
                        rs.getInt("building_building_id"),
                        rs.getInt("tag_tag_id")
                );
                locationHallLabList.add(locationHallLab);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return locationHallLabList;
    }



    // --------------------------------------------------------------------------------


    /**
     * this method is to get all the locations in the location table...
     * returns locationList;
     * */
    public ObservableList<LocationHallLab> getAllLocationHallLabList() {

        ObservableList<LocationHallLab> locationHallLabList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String query = "SELECT * FROM location ORDER BY location_name";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            LocationHallLab locationHallLab;
            while (rs.next()) {
                locationHallLab = new LocationHallLab(
                        rs.getInt("location_id"),
                        rs.getString("location_name"),
                        rs.getInt("location_capacity"),
                        rs.getInt("location_floor"),
                        rs.getString("location_condition"),
                        rs.getInt("building_building_id"),
                        rs.getInt("tag_tag_id")
                );
                locationHallLabList.add(locationHallLab);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return locationHallLabList;
    }






    // ------------------------------------------------------------------------------

    public int getLectureHallCount() {

        Connection conn = getConnection();

        String count = "";
        String query = "SELECT COUNT(location_id) AS NumberOfLectureHall " +
                "FROM location AS l " +
                "WHERE l.tag_tag_id = (SELECT tags.idtags " +
                "FROM tags WHERE tags.Tag = 'Lecture');";

        /*String query = "SELECT COUNT(location_id) AS NumberOfLectureHall " +
                "FROM location AS l " +
                "WHERE l.tag_tag_id = (SELECT tags.idtags " +
                "FROM tags WHERE tags.Tag = 'Lecture Hall' OR tags.Tag LIKE 'Lecture%');";*/


        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);


            if (rs.next()) {
                count = rs.getString("NumberOfLectureHall");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }



        return Integer.parseInt(count);
    }



    // ------------------------------------------------------------------------------

    public int getTutorialHallCount() {

        Connection conn = getConnection();

        String count = "";
        String query = "SELECT COUNT(location_id) AS NumberOfTutorialHall " +
                "FROM location AS l " +
                "WHERE l.tag_tag_id = (SELECT tags.idtags " +
                "FROM tags WHERE tags.Tag = 'Tutorial');";


        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);


            if (rs.next()) {
                count = rs.getString("NumberOfTutorialHall");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Integer.parseInt(count);
    }


    // ------------------------------------------------------------------------------

    public int getLabCount() {

        Connection conn = getConnection();

        String count = "";
        int intCount = 0;
        String query = "SELECT COUNT(l.location_id) AS NumberOfLab " +
                "FROM location AS l " +
                "WHERE l.tag_tag_id = (SELECT tags.idtags " +
                "FROM tags WHERE tags.Tag = 'PC-Lab');";


        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            if (rs.next()) {
                count = rs.getString("NumberOfLab");
            }

            intCount += Integer.parseInt(count);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        String query2 = "SELECT COUNT(l.location_id) AS NumberOfLab " +
                "FROM location AS l " +
                "WHERE l.tag_tag_id = (SELECT tags.idtags " +
                "FROM tags WHERE tags.Tag = 'Lab');";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query2);

            StudentBatches studentBatch;

            if (rs.next()) {
                count += rs.getString("NumberOfLab");
            }

            intCount += Integer.parseInt(count);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        String query3 = "SELECT COUNT(l.location_id) AS NumberOfLab " +
                "FROM location AS l " +
                "WHERE l.tag_tag_id = (SELECT tags.idtags " +
                "FROM tags WHERE tags.Tag = 'Practical');";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query3);

            if (rs.next()) {
                count += rs.getString("NumberOfLab");
            }

            intCount += Integer.parseInt(count);

        } catch (Exception ex) {
            ex.printStackTrace();
        }



        return intCount;
    }

}
