package TimeTableGeneratorDesktopApp.Lecturers.LecturerForm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LecturerFormController implements Initializable {

    @FXML
    private ComboBox<?> comboLevelBox;

    @FXML
    private ComboBox<?> comboCenterBox;

    @FXML
    private ComboBox<?> comboBuildingBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void selectLecturerLevel(ActionEvent actionEvent) {
        System.out.println("Selected the level");
        String level = comboLevelBox.getSelectionModel().getSelectedItem().toString();
        int i = comboLevelBox.getSelectionModel().getSelectedIndex() + 1;
        System.out.println(level);
        System.out.println(i);

    }

    public void selectLecturerCenter(ActionEvent actionEvent) {
        System.out.println("Selected the center");
        String center = comboCenterBox.getSelectionModel().getSelectedItem().toString();
        int i = comboCenterBox.getSelectionModel().getSelectedIndex() + 1;
        System.out.println(center);
        System.out.println(i);
    }

    public void selectLecturerBuilding(ActionEvent actionEvent) {
        System.out.println("Selected the building");
    }

    public void selectLecturerFaculty(ActionEvent actionEvent) {
        System.out.println("Selected the faculty");
    }
}
