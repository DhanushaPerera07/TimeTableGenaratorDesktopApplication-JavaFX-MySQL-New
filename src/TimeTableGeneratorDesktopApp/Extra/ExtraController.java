package TimeTableGeneratorDesktopApp.Extra;

import TimeTableGeneratorDesktopApp.FxmlLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ExtraController implements Initializable {

    @FXML
    private Button NASessionBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void handleActionNASessionButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("clicked NA sessions Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Extra/NotAvailableTime/NotAvailableTime");
        pane.getChildren().add(view);
    }




}
