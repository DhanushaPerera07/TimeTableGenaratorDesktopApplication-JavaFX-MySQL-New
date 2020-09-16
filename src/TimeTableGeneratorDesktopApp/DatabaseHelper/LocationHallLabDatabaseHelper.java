package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LocationHallLabDatabaseHelper extends DatabaseHelper{

    // methods are needed for the Hall/Lab operations



    /**
     * this method is to get all the faculties in the faculty table...
     * returns departmentList;
     * */
    public ObservableList<LocationHallLab> getLocationHallLabList(int buildingID) {

        ObservableList<LocationHallLab> locationHallLabList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String query = "SELECT * FROM location WHERE building_building_id = "+buildingID+" AND location_delete_status = 'N' ORDER BY location_name";

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

}
