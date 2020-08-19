package TimeTableGeneratorDesktopApp.Departments.DepartmentsPopUps;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditDepartmentPopUpController implements Initializable {

    // component
    @FXML
    private TextField txtDepartmentName;

    @FXML
    private TextField txtDepartmentShortName;

    @FXML
    private ComboBox<String> departmentFacultyComboBox;

    @FXML
    private ComboBox<String> departmentBuildingComboBox;

    @FXML
    private TextField txtDepartmentFloorNo;

    @FXML
    private TextField txtDepartmentHead;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void ActionEventEditDepartmentPopUp(ActionEvent actionEvent) {
    }
}
