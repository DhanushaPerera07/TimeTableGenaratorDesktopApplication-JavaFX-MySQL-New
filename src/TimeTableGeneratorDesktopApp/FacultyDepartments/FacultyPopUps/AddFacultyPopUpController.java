package TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyPopUps;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AddFacultyPopUpController implements Initializable {

    // components
    @FXML
    private TextField txtDepartmentName;

    @FXML
    private TextField txtDepartmentShortName;

    @FXML
    private ComboBox<String> departmentFacultyComboBox;

    @FXML
    private ComboBox<String> departmentBuildingComboBox;


    @FXML
    private ComboBox<?> departmentStatusComboBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /** ============== COMBO BOXES ====================================================================
         */
        // departmentBuildingComboBox combobox
        departmentFacultyComboBox.getItems().addAll(
                "Faculty of Computing",
                "Faculty of Business Management",
                "Faculty of Engineering"
        );
        // prompt text
        departmentFacultyComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown

        // departmentBuildingComboBox combobox
        departmentBuildingComboBox.getItems().addAll(
                "FOC - Main",
                "FOC - New Building",
                "FOE - Main",
                "FOBM - Main"
        );
        // prompt text
        departmentBuildingComboBox.setPromptText("FOC - Main"); // selects the first one in the dropdown

    }

    public void ActionEventAddFacultyPopUp(ActionEvent actionEvent) {
        // pop up - add a faculty action event on ADD BUTTON
        System.out.println("clicked pop up - add a faculty action event on ADD BUTTON");
    }
}
