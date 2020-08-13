package TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyPopUps;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditFacultyPopUpController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void ActionEventEditFacultyPopUp(ActionEvent actionEvent) {
        // pop up - edit a faculty action event on EDIT BUTTON
        System.out.println("clicked pop up - edit a faculty action event on EDIT BUTTON");
    }
}
