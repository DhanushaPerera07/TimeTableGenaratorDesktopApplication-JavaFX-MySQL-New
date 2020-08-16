package TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyPopUps;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditFacultyPopUpController implements Initializable {

    // components
    @FXML
    private TextField txtDepartmentName;

    @FXML
    private TextField txtDepartmentShortName;

    @FXML
    private ComboBox<?> departmentFacultyComboBox;

    @FXML
    private ComboBox<?> departmentBuildingComboBox;

    @FXML
    private Button btnEditFacultyPopUp;

    @FXML
    private ComboBox<?> departmentStatusComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void ActionEventEditFacultyPopUp(ActionEvent actionEvent) {
        // pop up - edit a faculty action event on EDIT BUTTON
        System.out.println("clicked pop up - edit a faculty action event on EDIT BUTTON");
    }
}
