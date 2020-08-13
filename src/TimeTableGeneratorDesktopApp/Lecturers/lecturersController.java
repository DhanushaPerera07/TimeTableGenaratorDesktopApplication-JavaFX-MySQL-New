package TimeTableGeneratorDesktopApp.Lecturers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class lecturersController implements Initializable {

    @FXML
    private Button addLecturerBtn;

    @FXML
    private Pane lecturersPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    void ActionEventAddLecturer(ActionEvent event) {
        System.out.println("Add Lecturer Button Clicked");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LecturerForm/lecturerForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add Lecturer");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(lecturersPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
