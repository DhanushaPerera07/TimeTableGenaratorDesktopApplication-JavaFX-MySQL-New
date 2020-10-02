package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.ManageCanNotBeReservedTimeForRoom.ClassedUsed.DayTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DaysDatabaseHelper extends DatabaseHelper {

    public DayTM getDayTM() {
        DayTM dayTMClassObject = new DayTM();

        //Connection conn = getConnection();
        Connection conn = getConnection();

        String query = "SELECT * FROM daysname;";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            if (rs.next()) {
                dayTMClassObject = new DayTM(
                        rs.getInt("id"),
                        rs.getString("day1name"),
                        rs.getString("day2name"),
                        rs.getString("day3name"),
                        rs.getString("day4name"),
                        rs.getString("day5name"),
                        rs.getString("day6name"),
                        rs.getString("day7name")
                );
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }

        return dayTMClassObject;

    }

    // --------------------------------------------------------------------


}
