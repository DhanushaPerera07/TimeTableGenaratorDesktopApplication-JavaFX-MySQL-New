package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.Sessions.Sessions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SessionDatabaseHelper extends DatabaseHelper {


    public ObservableList<Sessions> getSessionsList() {
        ObservableList<Sessions> sessionsList = FXCollections.observableArrayList();
        //Connection conn = getConnection();
        Connection conn = getConnection();

        String query = "SELECT * FROM session ";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Sessions sessions;
            while (rs.next()) {
                sessions = new Sessions(
                        rs.getInt("idsession"),
                        rs.getString("sessionSubject"),
                        rs.getString("sessionModuleCode"),
                        rs.getString("sessionTag"),
                        rs.getString("sessionStudentGroup"),
                        rs.getInt("sessionNoOfStudents"),
                        rs.getInt("sessionDuration"),
                        rs.getString("sessionID")
                );
                sessionsList.add(sessions);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return sessionsList;
    }
}
