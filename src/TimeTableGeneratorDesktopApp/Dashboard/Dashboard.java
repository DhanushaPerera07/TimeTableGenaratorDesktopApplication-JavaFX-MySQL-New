package TimeTableGeneratorDesktopApp.Dashboard;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Statistics should be updated using this method according to the database data.
     */
    public void updateStatistics(){
        System.out.println("Dashboard: Statics are being updating");
    }
}
