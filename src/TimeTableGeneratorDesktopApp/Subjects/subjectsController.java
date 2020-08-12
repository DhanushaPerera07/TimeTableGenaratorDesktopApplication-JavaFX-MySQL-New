package TimeTableGeneratorDesktopApp.Subjects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class subjectsController implements Initializable {

    @FXML
    private Button addModuleBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void ActionEventAddModule(ActionEvent event) {
        System.out.println("Add Module Button Clicked");
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SubjectForm/subjectForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add Module");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("can't load new window");
        }
    }
}
