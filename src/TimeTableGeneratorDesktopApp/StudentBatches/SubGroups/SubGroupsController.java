package TimeTableGeneratorDesktopApp.StudentBatches.SubGroups;

import TimeTableGeneratorDesktopApp.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SubGroupsController implements Initializable {

    @FXML
    private Button subGrpBackBtn;

    @FXML
    private AnchorPane subGrpPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void goBacktoStudentsBatches(javafx.event.ActionEvent actionEvent) throws IOException {
        URL fileURL = Main.class.getResource("/TimeTableGeneratorDesktopApp/StudentBatches/StudentBatches.fxml");
        if (fileURL == null) {
            throw new java.io.FileNotFoundException("FXML File can not be found");
        }else{
            Pane newPane = new FXMLLoader().load(fileURL);
            subGrpPane.getChildren().addAll(newPane);
        }
    }
}
