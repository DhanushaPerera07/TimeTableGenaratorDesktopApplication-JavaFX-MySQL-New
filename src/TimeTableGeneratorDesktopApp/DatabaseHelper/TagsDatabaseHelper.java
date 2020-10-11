package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.Tags.Tags;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TagsDatabaseHelper extends DatabaseHelper {


    /**
     * pass tag id as the parameter and get the tag instance
     *
     * @param tagID
     */
    public Tags getTagInstanceByTagID(int tagID) {
        // create a faculty object
        Tags tag = new Tags();

        // get database connection
        Connection conn = getConnection();

        String query = "SELECT * FROM tags WHERE idtags = " + tagID + " ORDER BY idtags";

        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                tag.setTagID(rs.getInt("idtags"));
                tag.setTag(rs.getString("Tag"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When TAGs data retrieving ");
            ex.printStackTrace();
        }

        return tag;
    }

    /**
     * pass tag name as the parameter and get the tag id
     *
     * @param tagName
     */
    public Tags getTagInstanceByTagName(String tagName) {
        // create a faculty object
        Tags tag = new Tags();

        // get database connection
        Connection conn = getConnection();

        String query = "SELECT * FROM tags WHERE Tag = '" + tagName + "' ORDER BY Tag";

        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                tag.setTagID(rs.getInt("idtags"));
                tag.setTag(rs.getString("Tag"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When TAGs data retrieving ");
            ex.printStackTrace();
        }

        return tag;
    }


    /**
     * this method is to get all the tags in the tags table...
     * returns departmentList;
     */
    public ObservableList<Tags> getTagList() {
        ObservableList<Tags> tagList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String query = "SELECT * FROM tags ORDER BY idtags ASC";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Tags tag;
            while (rs.next()) {
                tag = new Tags(
                        rs.getInt("idtags"),
                        rs.getString("Tag")
                );
                tagList.add(tag);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return tagList;
    }
}
