package TimeTableGeneratorDesktopApp.StudentBatches.BatchForm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class BatchFormController implements Initializable {

    @FXML
    private ComboBox<String> comboBoxYear;

    @FXML
    private ComboBox<String> comBoxSem;

    @FXML
    private ComboBox<String> comBoxIntake;

    @FXML
    private ComboBox<String> comBoxFac;

    @FXML
    private ComboBox<String> comBoxPro;

    @FXML
    private ComboBox<String> comBoxCen;



    @FXML
    public Button submitAddBatch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboBoxYear.getItems().removeAll(comboBoxYear.getItems());
        comboBoxYear.getItems().addAll(
                "Year 1 ", "Year 2", "Year 3", "Year 4"
        );

        comBoxSem.getItems().removeAll(comBoxSem.getItems());
        comBoxSem.getItems().addAll(
                "Semester 1", "Semester 2"
        );

        comBoxIntake.getItems().removeAll(comBoxIntake.getItems());
        comBoxIntake.getItems().addAll(
                "Regular Intake", "June Intake"
        );

        comBoxFac.getItems().removeAll(comBoxFac.getItems());
        comBoxFac.getItems().addAll(
                "IT","BM","ENG","H&S","ARCH","HOS","NUR"
        );


        comBoxCen.getItems().removeAll(comBoxCen.getItems());
        comBoxCen.getItems().addAll(
                "Malabe", "Kandy" , "Matara", "Jaffna" , "Metro"
        );
    }



    @FXML
    public void selectBatchYear(ActionEvent actionEvent){
        String year = comboBoxYear.getSelectionModel().getSelectedItem().toString();
        System.out.println(year);
    }
      public void selectBatchSem(ActionEvent actionEvent){
        String sem = comBoxSem.getSelectionModel().getSelectedItem().toString();
        System.out.println(sem);
    }
      public void selectBatchIntake(ActionEvent actionEvent){
        String intake = comBoxIntake.getSelectionModel().getSelectedItem().toString();
        System.out.println(intake);
    }
      public void selectBatchFac(ActionEvent actionEvent){
        String faculty = comBoxFac.getSelectionModel().getSelectedItem().toString();
        System.out.println(faculty);

        if(faculty == "IT"){
            comBoxPro.getItems().removeAll(comBoxPro.getItems());
            comBoxPro.getItems().addAll(
                    "SE", "CSN" , "CS", "IM" , "IT"
            );
        }else if(faculty == "BM"){
            comBoxPro.getItems().removeAll(comBoxPro.getItems());
            comBoxPro.getItems().addAll(
                    "BA", "BS"
            );
          }else {
            comBoxPro.getItems().removeAll(comBoxPro.getItems());
            comBoxPro.getItems().addAll(
                    "Elec", "Drawing"
            );
        }



    }
      public void selectBatchCen(ActionEvent actionEvent){
        String center = comBoxCen.getSelectionModel().getSelectedItem().toString();
        System.out.println(center);
    }



    public void submitForm(ActionEvent actionEvent){
        Stage stage = (Stage) submitAddBatch.getScene().getWindow();
        stage.close();
    }



}

