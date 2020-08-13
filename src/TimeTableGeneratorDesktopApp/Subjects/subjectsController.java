package TimeTableGeneratorDesktopApp.Subjects;

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

public class subjectsController implements Initializable {

    @FXML
    private Button addModuleBtn;

    @FXML
    private Pane subjectsPane;

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
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(subjectsPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
