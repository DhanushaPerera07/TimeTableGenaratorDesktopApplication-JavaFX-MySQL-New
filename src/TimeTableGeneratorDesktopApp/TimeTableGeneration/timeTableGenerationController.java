package TimeTableGeneratorDesktopApp.TimeTableGeneration;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class timeTableGenerationController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private BorderPane borderPaneForTimeTables;

    @FXML
    private VBox timeTableVBox;
}
