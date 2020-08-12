package TimeTableGeneratorDesktopApp.StudentBatches.BatchForm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BatchFormController implements Initializable {

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    public void getBatchType(ActionEvent actionEvent){
        String batchType = comboBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(batchType);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboBox.getItems().removeAll(comboBox.getItems());

        comboBox.getItems().addAll(
                "Year 1 Semester 1",
                "Year 1 semester 2",
                "Year 2 semester 1"
        );


    }
}

