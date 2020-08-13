package TimeTableGeneratorDesktopApp.Subjects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class subjectsController implements Initializable {

    @FXML
    private Button addModuleBtn;

    @FXML
    private Pane subjectsPane;

    @FXML
    private TableColumn<?, ?> colTuteHour;

    @FXML
    private TableColumn<?, ?> colModuleCode;

    @FXML
    private TableColumn<?, ?> colYear;

    @FXML
    private TableColumn<?, ?> colLecHour;

    @FXML
    private TableColumn<?, ?> colEvaluatonHour;

    @FXML
    private TableColumn<?, ?> colModuleName;

    @FXML
    private TableColumn<?, ?> colLabHour;

    @FXML
    private TableView<?> tvModules;

    @FXML
    private TableColumn<?, ?> colSemester;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void ActionEventAddModule(ActionEvent event) {
        System.out.println("Add Module Button Clicked");
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SubjectForm/subjectForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add Module");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(subjectsPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
