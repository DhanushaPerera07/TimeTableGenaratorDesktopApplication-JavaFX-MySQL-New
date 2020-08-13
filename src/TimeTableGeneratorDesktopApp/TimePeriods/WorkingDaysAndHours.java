package TimeTableGeneratorDesktopApp.TimePeriods;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WorkingDaysAndHours implements Initializable {


    @FXML
    private Button addWorkingDaysBtn;


    @FXML
    private Pane workingPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addWorkingDaysAction(ActionEvent actionEvent) {

        System.out.println("Add Lecturer Button Clicked");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetWorkingDays/SetWorkingDaysForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Set Working Days");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(workingPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
