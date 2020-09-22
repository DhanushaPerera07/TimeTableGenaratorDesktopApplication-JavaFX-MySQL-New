package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.PreferredLocation;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.SuitableLocationForLecturer;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.SuitableLocationForStudentBatch;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed.SuitableLocationForTag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HallsLabsDatabaseHelper extends DatabaseHelper {

    // variable used to hold the checkbox true/false value
    Boolean statusTrue;

    // variables used for the subject part
    int subjectID;
    int locationID;
    int tagID;


    // variables used for the lecturer part
    int lecturerID;

    // variables used for the student batch part
    int studentBatchID;

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

        System.out.println("testing preferred_room_for_subject table: " + "SubjectID:" + subjectID + " ,LocationID: "+ locationID + ", Tag ID : " + tagID);
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



    // -------------------------- Lecturer part ---------------------------------------------------------------------------------------

    /**
     * Check whether there are locations set as preferred rooms for that particular subject
     *
     * @param lecturerID
     * @param locationID
     * @return
     */

    public ObservableList<SuitableLocationForLecturer> checkPreferredRoomsForLecturer(int lecturerID, int locationID, Boolean statusTrue) {

        this.lecturerID = lecturerID;
        this.locationID = locationID;

        this.statusTrue = statusTrue;

        // create ObservableList object
        ObservableList<SuitableLocationForLecturer> suitableLocationForLecturersList = FXCollections.observableArrayList();

        // get database connection
        Connection conn = getConnection();

        System.out.println("testing preferred_room_for_subject table: " + "lecturerID:" + lecturerID + " ,LocationID: "+ locationID + "");
        String query = "SELECT * FROM suitable_room_for_lecturer WHERE lecturer_lid = " + this.lecturerID + " AND location_location_id = " + this.locationID + " ORDER BY suitable_room_for_lecturer_id";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                SuitableLocationForLecturer suitableLocationForLecturer = new SuitableLocationForLecturer();
                suitableLocationForLecturer.setSuitable_room_for_lecturer_id(rs.getInt("suitable_room_for_lecturer_id"));
                suitableLocationForLecturer.setLocation_building_building_id(rs.getInt("location_location_id"));
                suitableLocationForLecturer.setLecturer_lid(rs.getInt("lecturer_lid"));
                suitableLocationForLecturer.setStatus_true(rs.getString("status_true"));

                // add the preferredLocation object to the observableList
                suitableLocationForLecturersList.add(suitableLocationForLecturer);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When suitable_room_for_lecturer table data retrieving ");
            ex.printStackTrace();
        }

        return suitableLocationForLecturersList;

    }


    public void setPreferredRoomsForLecturer(ObservableList<SuitableLocationForLecturer> suitableLocationForLecturerList) {

        String query;

        if (suitableLocationForLecturerList.isEmpty() != true) {
            // already there is/are record(s) in the database

            if (this.statusTrue) {
                // checkbox is marked by the user, that is why this.statusTrue == true
                for (SuitableLocationForLecturer suitableLocationForLecturer : suitableLocationForLecturerList) {
                    // update query
                    try {
                        query = "UPDATE `suitable_room_for_lecturer` SET status_true = 'Y' WHERE suitable_room_for_lecturer_id = " + suitableLocationForLecturer.getSuitable_room_for_lecturer_id() + "";

                        // execute the update query
                        executeQuery(query);
                    } catch (Exception ex) {
                        System.out.println("Error updating preferred location for lecturer: " + suitableLocationForLecturer.toString());
                        ex.printStackTrace();
                    }
                }
            } else {
                // checkbox is not marked by the user, that is why this.statusTrue == false
                for (SuitableLocationForLecturer suitableLocationForLecturer : suitableLocationForLecturerList) {
                    // update query
                    try {
                        query = "UPDATE `suitable_room_for_lecturer` SET status_true = 'N' WHERE suitable_room_for_lecturer_id = " + suitableLocationForLecturer.getSuitable_room_for_lecturer_id() + "";

                        // execute the update query
                        executeQuery(query);
                    } catch (Exception ex) {
                        System.out.println("Error updating preferred location for lecturer: " + suitableLocationForLecturer.toString());
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
                    query = "INSERT INTO `suitable_room_for_lecturer` (`location_location_id`,`lecturer_lid`) VALUES (" + this.locationID + "," + this.lecturerID + ")";

                    // execute the insert query
                    executeQuery(query);
                } catch (Exception ex) {
                    System.out.println("Error inserting preferred location for lecturer");
                    ex.printStackTrace();
                }
            } else {
                // checkbox is not marked by the user, that is why this.statusTrue == false
                System.out.println("checkbox is not selected, location is not a preferred location");

            }
        }

    }








    // -------------------------- Tag part ---------------------------------------------------------------------------------------

    /**
     * Check whether there are locations set as preferred rooms for that particular tag
     *
     * @param tagID
     * @param locationID
     * @return
     */

    public ObservableList<SuitableLocationForTag> checkPreferredRoomsForTag(int tagID, int locationID, Boolean statusTrue) {

        this.tagID = tagID;
        this.locationID = locationID;

        this.statusTrue = statusTrue;

        // create ObservableList object
        ObservableList<SuitableLocationForTag> suitableLocationForTagList = FXCollections.observableArrayList();

        // get database connection
        Connection conn = getConnection();

        System.out.println("testing preferred_room_for_tags table: " + "tagID:" + tagID + " ,LocationID: "+ locationID + "");
        String query = "SELECT * FROM suitable_room_for_tags WHERE tags_idtags = " + this.tagID + " AND location_location_id = " + this.locationID + " ORDER BY suitable_room_for_tags_id";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                SuitableLocationForTag suitableLocationForTag = new SuitableLocationForTag();
                suitableLocationForTag.setSuitable_room_for_tags_id(rs.getInt("suitable_room_for_tags_id"));
                suitableLocationForTag.setLocation_location_id(rs.getInt("location_location_id"));
                suitableLocationForTag.setTags_idtags(rs.getInt("tags_idtags"));
                suitableLocationForTag.setStatus_true(rs.getString("status_true"));

                // add the preferredLocation object to the observableList
                suitableLocationForTagList.add(suitableLocationForTag);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When suitable_room_for_tags table data retrieving ");
            ex.printStackTrace();
        }

        return suitableLocationForTagList;

    }


    public void setPreferredRoomsForTag(ObservableList<SuitableLocationForTag> suitableLocationForTagList) {

        String query;

        if (suitableLocationForTagList.isEmpty() != true) {
            // already there is/are record(s) in the database

            if (this.statusTrue) {
                // checkbox is marked by the user, that is why this.statusTrue == true
                for (SuitableLocationForTag suitableLocationForTag : suitableLocationForTagList) {
                    // update query
                    try {
                        query = "UPDATE `suitable_room_for_tags` SET status_true = 'Y' WHERE suitable_room_for_tags_id = " + suitableLocationForTag.getSuitable_room_for_tags_id() + "";

                        // execute the update query
                        executeQuery(query);
                    } catch (Exception ex) {
                        System.out.println("Error updating preferred location for tag: " + suitableLocationForTag.toString());
                        ex.printStackTrace();
                    }
                }
            } else {
                // checkbox is not marked by the user, that is why this.statusTrue == false
                for (SuitableLocationForTag suitableLocationForTag : suitableLocationForTagList) {
                    // update query
                    try {
                        query = "UPDATE `suitable_room_for_tags` SET status_true = 'N' WHERE suitable_room_for_tags_id = " + suitableLocationForTag.getSuitable_room_for_tags_id() + "";

                        // execute the update query
                        executeQuery(query);
                    } catch (Exception ex) {
                        System.out.println("Error updating preferred location for tag: " + suitableLocationForTag.toString());
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
                    query = "INSERT INTO `suitable_room_for_tags` (`location_location_id`,`tags_idtags`) VALUES (" + this.locationID + "," + this.tagID + ")";

                    // execute the insert query
                    executeQuery(query);
                } catch (Exception ex) {
                    System.out.println("Error inserting preferred location for tag");
                    ex.printStackTrace();
                }
            } else {
                // checkbox is not marked by the user, that is why this.statusTrue == false
                System.out.println("checkbox is not selected, location is not a preferred location");

            }
        }
    }









    // -------------------------- Student Batches part ---------------------------------------------------------------------------------------

    /**
     * Check whether there are locations set as preferred rooms for that particular student batch
     *
     * @param studentBatchID
     * @param locationID
     * @return
     */

    public ObservableList<SuitableLocationForStudentBatch> checkPreferredRoomsForStudentBatch(int studentBatchID, int locationID, Boolean statusTrue) {

        this.studentBatchID = studentBatchID;
        this.locationID = locationID;

        this.statusTrue = statusTrue;

        // create ObservableList object
        ObservableList<SuitableLocationForStudentBatch> suitableLocationForStudentBatchList = FXCollections.observableArrayList();

        // get database connection
        Connection conn = getConnection();

        System.out.println("testing preferred_room_for_tags table: " + "tagID:" + tagID + " ,LocationID: "+ locationID + "");
        String query = "SELECT * FROM suitable_room_for_student_batch WHERE studentbatches_id = " + this.studentBatchID + " AND location_location_id = " + this.locationID + " ORDER BY suitable_room_for_student_batch_id";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                SuitableLocationForStudentBatch suitableLocationForStudentBatch = new SuitableLocationForStudentBatch();
                suitableLocationForStudentBatch.setSuitable_room_for_student_batch_id(rs.getInt("suitable_room_for_student_batch_id"));
                suitableLocationForStudentBatch.setLocation_location_id(rs.getInt("location_location_id"));
                suitableLocationForStudentBatch.setStudentbatches_id(rs.getInt("studentbatches_id"));
                suitableLocationForStudentBatch.setStatus_true(rs.getString("status_true"));

                // add the preferredLocation object to the observableList
                suitableLocationForStudentBatchList.add(suitableLocationForStudentBatch);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When suitable_room_for_student_batch table data retrieving ");
            ex.printStackTrace();
        }

        return suitableLocationForStudentBatchList;

    }


    public void setPreferredRoomsForStudentBatch(ObservableList<SuitableLocationForStudentBatch> suitableLocationForStudentBatchList) {

        String query;

        if (suitableLocationForStudentBatchList.isEmpty() != true) {
            // already there is/are record(s) in the database

            if (this.statusTrue) {
                // checkbox is marked by the user, that is why this.statusTrue == true
                for (SuitableLocationForStudentBatch suitableLocationForStudentBatch : suitableLocationForStudentBatchList) {
                    // update query
                    try {
                        query = "UPDATE `suitable_room_for_student_batch` SET status_true = 'Y' WHERE suitable_room_for_student_batch_id = " + suitableLocationForStudentBatch.getSuitable_room_for_student_batch_id() + "";

                        // execute the update query
                        executeQuery(query);
                    } catch (Exception ex) {
                        System.out.println("Error updating preferred location for student batch: " + suitableLocationForStudentBatch.toString());
                        ex.printStackTrace();
                    }
                }
            } else {
                // checkbox is not marked by the user, that is why this.statusTrue == false
                for (SuitableLocationForStudentBatch suitableLocationForStudentBatch : suitableLocationForStudentBatchList) {
                    // update query
                    try {
                        query = "UPDATE `suitable_room_for_student_batch` SET status_true = 'N' WHERE suitable_room_for_student_batch_id = " + suitableLocationForStudentBatch.getSuitable_room_for_student_batch_id() + "";

                        // execute the update query
                        executeQuery(query);
                    } catch (Exception ex) {
                        System.out.println("Error updating preferred location for student batch: " + suitableLocationForStudentBatch.toString());
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
                    query = "INSERT INTO `suitable_room_for_student_batch` (`location_location_id`,`studentbatches_id`) VALUES (" + this.locationID + "," + this.studentBatchID + ")";

                    // execute the insert query
                    executeQuery(query);
                } catch (Exception ex) {
                    System.out.println("Error inserting preferred location for student batch");
                    ex.printStackTrace();
                }
            } else {
                // checkbox is not marked by the user, that is why this.statusTrue == false
                System.out.println("checkbox is not selected, location is not a preferred location");

            }
        }

    }



}
