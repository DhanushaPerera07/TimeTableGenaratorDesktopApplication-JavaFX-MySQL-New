package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.FacultyDepartments.Faculty;
import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import TimeTableGeneratorDesktopApp.LocationsLabsHalls.Building;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class BuildingDatabaseHelper extends DatabaseHelper {

    // when we pass building id, it should be returned the building instance
    /** Method when faculty name is given as parameter , returns that faculty instance
     */
    public Building getBuildingInstance(int buildingID){

        // create a faculty object
        Building building = new Building();

        // get database connection
        Connection conn = getConnection();


        // SELECT RECORD
        String query = "SELECT * FROM building WHERE building_delete_status = 'N' AND building_id = "+buildingID+"";

        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                // set building values to the created building object by fetching from the database
                building.setBuildingID(rs.getInt("building_id"));
                building.setBuildingName(rs.getString("building_name"));
                building.setBuildingNoOfFloors(rs.getInt("building_no_of_floors"));
                building.setBuildingCapacity(rs.getInt("building_capacity"));
                building.setBuildingCenter(rs.getString("building_center"));
                building.setBuildingCondition(rs.getString("building_condition"));
                building.setBuildingSpecializedFor(rs.getString("building_specialized_for"));
                building.setBuildingNoOfLectureHalls(rs.getInt("building_no_of_lecture_halls"));
                building.setBuildingNoOfTutorialHalls(rs.getInt("building_no_of_tutorial_halls"));
                building.setBuildingNoOfLabs(rs.getInt("building_no_of_labs"));
                building.setFacultyFacultyId(rs.getInt("faculty_faculty_id"));
            }


        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }

        return building;

    }

    // when we pass building name, it should be returned the building instance
    /** Method when faculty name is given as parameter , returns that faculty instance
     */
    public Building getBuildingInstance(String buildingName){

        // create a faculty object
        Building building = new Building();

        // get database connection
        Connection conn = getConnection();


        // SELECT RECORD
        String query = "SELECT * FROM building WHERE building_delete_status = 'N' AND building_name = '"+buildingName+"'";

        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);

            // set building values to the created building object by fetching from the database
            building.setBuildingID(rs.getInt("building_id"));
            building.setBuildingName(rs.getString("building_name"));
            building.setBuildingNoOfFloors(rs.getInt("building_no_of_floors"));
            building.setBuildingCapacity(rs.getInt("building_capacity"));
            building.setBuildingCenter(rs.getString("building_center"));
            building.setBuildingCondition(rs.getString("building_condition"));
            building.setBuildingSpecializedFor(rs.getString("building_specialized_for"));
            building.setBuildingNoOfLectureHalls(rs.getInt("building_no_of_lecture_halls"));
            building.setBuildingNoOfTutorialHalls(rs.getInt("building_no_of_tutorial_halls"));
            building.setBuildingNoOfLabs(rs.getInt("building_no_of_labs"));
            building.setFacultyFacultyId(rs.getInt("faculty_faculty_id"));


        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }

        return building;

    }


    // ======================================================================================

    /**
     * this method is to get all the faculties in the faculty table...
     * returns departmentList;
     * */
    public ObservableList<Building> getBuildingList() {

        ObservableList<Building> buildingList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String query = "SELECT * FROM building WHERE building_delete_status = 'N' ORDER BY building_name";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Building building;
            while (rs.next()) {
                building = new Building(
                        rs.getInt("building_id"),
                        rs.getString("building_name"),
                        rs.getInt("building_no_of_floors"),
                        rs.getInt("building_capacity"),
                        rs.getString("building_center"),
                        rs.getString("building_condition"),
                        rs.getString("building_specialized_for"),
                        rs.getInt("building_no_of_lecture_halls"),
                        rs.getInt("building_no_of_tutorial_halls"),
                        rs.getInt("building_no_of_labs"),
                        rs.getInt("faculty_faculty_id")
                );
                buildingList.add(building);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return buildingList;
    }
}
