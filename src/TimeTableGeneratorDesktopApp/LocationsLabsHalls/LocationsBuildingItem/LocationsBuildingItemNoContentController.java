package TimeTableGeneratorDesktopApp.LocationsLabsHalls.LocationsBuildingItem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LocationsBuildingItemNoContentController implements Initializable {



    @FXML
    private Label txtBuildingNameNoContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Building Item No Content - Displayed successfully");
    }
}
