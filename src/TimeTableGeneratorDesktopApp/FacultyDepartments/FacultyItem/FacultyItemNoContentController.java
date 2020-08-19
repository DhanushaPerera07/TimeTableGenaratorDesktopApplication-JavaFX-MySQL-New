package TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyItem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class FacultyItemNoContentController implements Initializable {

    @FXML
    private VBox facultyItemVBOX;

    @FXML
    private Label txtFacultyNoContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Faculty Item No Content - Displayed successfully");

    }
}
