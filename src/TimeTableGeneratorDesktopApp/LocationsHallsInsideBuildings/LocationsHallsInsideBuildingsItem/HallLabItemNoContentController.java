package TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsItem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HallLabItemNoContentController implements Initializable {
    @FXML
    private Label txtLocationHallLabNameNoContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Location, Hall or Lab Item No Content - Displayed successfully");
    }
}
