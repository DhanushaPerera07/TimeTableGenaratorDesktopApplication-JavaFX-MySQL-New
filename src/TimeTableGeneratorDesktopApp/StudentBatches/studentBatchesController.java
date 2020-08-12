package TimeTableGeneratorDesktopApp.StudentBatches;

import TimeTableGeneratorDesktopApp.FxmlLoader;
import TimeTableGeneratorDesktopApp.Main;
import com.sun.glass.ui.EventLoop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class studentBatchesController implements Initializable {
    @FXML
    private Button addBathcBtn;

    @FXML
    private Pane studentsPane;

    @FXML
    void ActionEventAddBatchBtn(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BatchForm/batchForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add batch");
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(new Scene(root1));
            stage.show();

        }catch (Exception e){
            System.out.println("can't load new window");
        }
    }

    public void subGroupButtonAction(javafx.event.ActionEvent actionEvent) throws IOException {

        URL fileURL = Main.class.getResource("/TimeTableGeneratorDesktopApp/StudentBatches/SubGroups/subGroups.fxml");
        if (fileURL == null) {
            throw new java.io.FileNotFoundException("FXML File can not be found");
        }else{
            Pane newPane = new FXMLLoader().load(fileURL);
            studentsPane.getChildren().addAll(newPane);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
