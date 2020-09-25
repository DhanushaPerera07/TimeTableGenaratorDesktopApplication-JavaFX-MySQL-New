package TimeTableGeneratorDesktopApp.DatabaseHelper;

import TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation.ConsecutiveSessionViewModel;
import TimeTableGeneratorDesktopApp.Extra.ConsecetiveSesssions.ConsecetiveSessions;
import TimeTableGeneratorDesktopApp.Sessions.Sessions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConsecutiveSessionsDatabaseHelper extends DatabaseHelper{


    /**
     *  This method will return all the consecutive sessions as a list
     * @return ObservableList<>
     */
    public ObservableList<ConsecutiveSessionViewModel> getAllConsecutiveSessions(){

        SessionDatabaseHelper sessionDatabaseHelper = new SessionDatabaseHelper();


        ObservableList<ConsecutiveSessionViewModel> consecutiveSessionViewModelList = FXCollections.observableArrayList();
        Connection conn = getConnection();


        String query = "SELECT cs.* \n" +
                "FROM consecetive_sessions AS cs, consecutive_session_in_same_room AS csir\n" +
                "WHERE csir.status_true = 'N' OR cs.id != csir.consecutive_session_id;";

/*        String query = "SELECT cs.* \n" +
                "FROM consecetive_sessions AS cs, consecutive_session_in_same_room AS csir\n" +
                "WHERE cs.id != csir.consecutive_session_id ;";*/
        //String query = "SELECT * FROM consecetive_sessions;";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            ConsecutiveSessionViewModel consecutiveSessionViewModel;
            while (rs.next()) {
                consecutiveSessionViewModel = new ConsecutiveSessionViewModel();
                consecutiveSessionViewModel.setId(rs.getInt("id"));

                Sessions session1 = sessionDatabaseHelper.getSessionBySessionID(rs.getString("session1ID"));
                consecutiveSessionViewModel.setSession1(session1);

                Sessions session2 = sessionDatabaseHelper.getSessionBySessionID(rs.getString("session2ID"));
                consecutiveSessionViewModel.setSession2(session2);

                //consecutiveSessionViewModel.setSuitableRoomTrue(rs.getString("status_true"));

                consecutiveSessionViewModelList.add(consecutiveSessionViewModel);

            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return consecutiveSessionViewModelList;

    }//


    // ----------------------------------------------------------------------------------



    /**
     * @return ObservableList<>
     */
    public ConsecutiveSessionViewModel getConsecutiveSessionsViewModelByID(int ID){

        SessionDatabaseHelper sessionDatabaseHelper = new SessionDatabaseHelper();

        ObservableList<ConsecutiveSessionViewModel> consecutiveSessionViewModelList = FXCollections.observableArrayList();
        ConsecutiveSessionViewModel consecutiveSessionViewModel = null;
        
        Connection conn = getConnection();

        String query = "SELECT cs.* \n" +
                "FROM consecetive_sessions AS cs" +
                " WHERE cs.id = " + ID + ";";
        //String query = "SELECT * FROM consecetive_sessions;";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);


            if (rs.next()) {
                consecutiveSessionViewModel = new ConsecutiveSessionViewModel();
                consecutiveSessionViewModel.setId(rs.getInt("id"));

                Sessions session1 = sessionDatabaseHelper.getSessionBySessionID(rs.getString("session1ID"));
                consecutiveSessionViewModel.setSession1(session1);

                Sessions session2 = sessionDatabaseHelper.getSessionBySessionID(rs.getString("session2ID"));
                consecutiveSessionViewModel.setSession2(session2);

                //consecutiveSessionViewModelList.add(consecutiveSessionViewModel);

            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return consecutiveSessionViewModel;

    }//

}
