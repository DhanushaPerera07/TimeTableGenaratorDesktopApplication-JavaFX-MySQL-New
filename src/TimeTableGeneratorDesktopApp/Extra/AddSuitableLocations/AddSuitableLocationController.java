package TimeTableGeneratorDesktopApp.Extra.AddSuitableLocations;

import TimeTableGeneratorDesktopApp.FxmlLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSuitableLocationController implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    void ActionEventSession(ActionEvent event) {

    }

    @FXML
    void ActionEventGroups(ActionEvent event) {

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Extra/AddSuitableLocations/showAllStudentGroups.fxml"));
            Parent root2 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add suitable location for student group");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root2));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening showAllStudentGroups.fxml as a pop up ==========================");
            e.printStackTrace();
        }


    }

    @FXML
    void ActionEventLecturer(ActionEvent event) {

    }

    @FXML
    void ActionEventSubject(ActionEvent event) {
        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Extra/AddSuitableLocations/showAllSubjects.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add suitable location for subject and relevant tag");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening showAllSubjects.fxml as a pop up ==========================");
            e.printStackTrace();
        }
    }

    @FXML
    void ActionEventTag(ActionEvent event) {

    }

    @FXML
    void handleBackButton(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
