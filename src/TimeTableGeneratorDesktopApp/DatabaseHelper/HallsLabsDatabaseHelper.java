package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.FacultyDepartments.Faculty;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.Location;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.PreferredLocation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HallsLabsDatabaseHelper extends DatabaseHelper {

    int subjectID;
    int locationID;
    int tagID;
    Boolean statusTrue;


    /**
     * Check whether there are locations set as preferred rooms for that particular subject
     *
     * @param subjectID
     * @param locationID
     * @param tagID
     * @return
     */

    public ObservableList<PreferredLocation> checkPreferredRoomsForSubject(int subjectID, int locationID, int tagID, Boolean statusTrue) {

        this.subjectID = subjectID;
        this.locationID = locationID;
        this.tagID = tagID;
        this.statusTrue = statusTrue;

        // create ObservableList object
        ObservableList<PreferredLocation> preferredLocationsList = FXCollections.observableArrayList();

        // get database connection
        Connection conn = getConnection();

        String query = "SELECT * FROM preferred_room_for_subject WHERE subject_subject_id = " + this.subjectID + " AND location_location_id = " + this.locationID + " AND tag_tag_id = " + this.tagID + " ORDER BY preferred_room_for_subject_id";
        //String query = "SELECT * FROM preferred_room_for_subject WHERE status_true = 'Y' AND subject_subject_id = " + this.subjectID + " AND location_location_id = " + this.locationID + " AND tag_tag_id = " + this.tagID + " ORDER BY preferred_room_for_subject_id";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                PreferredLocation preferredLocation = new PreferredLocation();
                preferredLocation.setPreferredRoomForSubjectID(rs.getInt("preferred_room_for_subject_id"));
                preferredLocation.setSubjectSubjectID(rs.getInt("subject_subject_id"));
                preferredLocation.setLocationLocationID(rs.getInt("location_location_id"));
                preferredLocation.setTagTagID(rs.getInt("tag_tag_id"));
                preferredLocation.setStatusTrue(rs.getString("status_true"));

                // add the preferredLocation object to the observableList
                preferredLocationsList.add(preferredLocation);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }

        return preferredLocationsList;

    }


    public void setPreferredRoomsForSubject(ObservableList<PreferredLocation> preferredLocationsList) {

        String query;

        if (preferredLocationsList.isEmpty() != true) {
            // already there is/are record(s) in the database

            if (this.statusTrue) {
                // checkbox is marked by the user, that is why this.statusTrue == true
                for (PreferredLocation preferredLocation : preferredLocationsList) {
                    // update query
                    try {
                        query = "UPDATE `preferred_room_for_subject` SET status_true = 'Y' WHERE preferred_room_for_subject_id = " + preferredLocation.getPreferredRoomForSubjectID() + "";

                        // execute the update query
                        executeQuery(query);
                    } catch (Exception ex) {
                        System.out.println("Error updating preferred location for subject: " + preferredLocation.toString());
                        ex.printStackTrace();
                    }
                }
            } else {
                // checkbox is not marked by the user, that is why this.statusTrue == false
                for (PreferredLocation preferredLocation : preferredLocationsList) {
                    // update query
                    try {
                        query = "UPDATE `preferred_room_for_subject` SET status_true = 'N' WHERE preferred_room_for_subject_id = " + preferredLocation.getPreferredRoomForSubjectID() + "";

                        // execute the update query
                        executeQuery(query);
                    } catch (Exception ex) {
                        System.out.println("Error updating preferred location for subject: " + preferredLocation.toString());
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
                    query = "INSERT INTO `preferred_room_for_subject` (`subject_subject_id`,`location_location_id`,`tag_tag_id`,`status_true`) VALUES (" + this.subjectID + ", " + this.locationID + ", " + this.tagID + ")";

                    // execute the insert query
                    executeQuery(query);
                } catch (Exception ex) {
                    System.out.println("Error inserting preferred location for subject");
                    ex.printStackTrace();
                }
            } else {
                // checkbox is not marked by the user, that is why this.statusTrue == false
                System.out.println("checkbox is not selected, location is not a preferred location");

            }
        }

    }

}
