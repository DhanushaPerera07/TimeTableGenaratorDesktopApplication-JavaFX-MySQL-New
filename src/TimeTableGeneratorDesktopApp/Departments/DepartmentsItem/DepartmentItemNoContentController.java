package TimeTableGeneratorDesktopApp.Departments.DepartmentsItem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentItemNoContentController implements Initializable {

    @FXML
    private VBox departmentItemNoContentVBOX;

    @FXML
    private Label txtNoContentFound;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Department Item No Content - Displayed successfully");

    }
}
