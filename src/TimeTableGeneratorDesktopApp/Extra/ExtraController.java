package TimeTableGeneratorDesktopApp.Extra;

import TimeTableGeneratorDesktopApp.FxmlLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExtraController implements Initializable {


    @FXML
    private Button NASessionBtn;

    @FXML
    private Button backBtn;


    @FXML
    private Button ParallelSessionBtn;

    @FXML
    private Button ConsecetiveSessionBtn;

    @FXML
    private Pane pane;

    @FXML
    private Button OverlapSessionBtn;

    @FXML
    private Button btnAddSuitableLocation;


    @FXML
    private TilePane addSuitableRooms;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void handleActionNASessionButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("clicked NA time Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Extra/NotAvailableTime/NotAvailableTime");
        pane.getChildren().add(view);

    }

    @FXML
    public void handleActionParallelSessionsButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("clicked PS time Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Extra/ParallelSessions/ParallelSession");
        pane.getChildren().add(view);

    }

    @FXML
    public void handleActionOverlapSessionsButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("clicked PS time Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Extra/NotOverLapSessions/NotOverlapSessions");
        pane.getChildren().add(view);

    }


    @FXML
    public void handleActionConsecetiveSessionsButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("clicked CS time Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Extra/ConsecetiveSesssions/ConsecetiveSessions");
        pane.getChildren().add(view);

    }
    @FXML
    void openPopUpAddSuitableLocations(javafx.event.ActionEvent actionEvent) {

        System.out.println("clicked Add suitable button");
        FxmlLoader object = new FxmlLoader();
        //Pane view = object.getPane("Extra/NotAvailableTime/NotAvailableTime");
        Pane view = object.getPane("Extra/AddSuitableLocations/addSuitableLocation");
        //System.out.println(pane.getChildren());
        pane.getChildren().add(view);

    }




    @FXML
    void openOnClickTilePopUpAddSuitableLocations(MouseEvent mouseEvent) {

        System.out.println("clicked Add suitable button");
        FxmlLoader object = new FxmlLoader();
        //Pane view = object.getPane("Extra/NotAvailableTime/NotAvailableTime");
        Pane view = object.getPane("Extra/AddSuitableLocations/addSuitableLocation");
        //System.out.println(pane.getChildren());
        pane.getChildren().add(view);

    }


}
