package TimeTableGeneratorDesktopApp.StudentBatches;

import com.sun.glass.ui.EventLoop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class studentBatchesController implements Initializable {
    @FXML
    private Button addBathcBtn;

    @FXML
    void ActionEventAddBatchBtn(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BatchForm/batchForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add batch");
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("can't load new window");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
