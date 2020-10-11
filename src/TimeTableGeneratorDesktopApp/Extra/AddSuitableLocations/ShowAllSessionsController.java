package TimeTableGeneratorDesktopApp.Extra.AddSuitableLocations;

import TimeTableGeneratorDesktopApp.DatabaseHelper.SessionDatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SuitableRoomForSessionController;
import TimeTableGeneratorDesktopApp.Sessions.Sessions;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowAllSessionsController implements Initializable {

    @FXML
    private Pane pane;

    @FXML
    private TableView<Sessions> tblSession;

    @FXML
    private TableColumn<Sessions, String> colSubjectCode;

    @FXML
    private TableColumn<Sessions, String> colSubject;

    @FXML
    private TableColumn<Sessions, String> colTag;

    @FXML
    private TableColumn<Sessions, String> colStudentGroup;

    @FXML
    private TableColumn<Sessions, String> colNoOfStudents;

    @FXML
    private TableColumn<Sessions, String> colDuration;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showSessions();
    }

    public void showSessions() {
        // get database data
        SessionDatabaseHelper sessionDatabaseHelper = new SessionDatabaseHelper();

        ObservableList<Sessions> list = sessionDatabaseHelper.getSessionsList();

        for(Sessions session:list){
            System.out.println("session rec: "+ session.toString());
        }

        colSubjectCode.setCellValueFactory(new PropertyValueFactory<Sessions, String>("sessionModuleCode"));
        colSubject.setCellValueFactory(new PropertyValueFactory<Sessions, String>("sessionModule"));
        colTag.setCellValueFactory(new PropertyValueFactory<Sessions, String>("sessionTag"));
        colStudentGroup.setCellValueFactory(new PropertyValueFactory<Sessions, String>("sessionGroupID"));
        colNoOfStudents.setCellValueFactory(new PropertyValueFactory<Sessions, String>("sessionStudentCount"));
        colDuration.setCellValueFactory(new PropertyValueFactory<Sessions, String>("sessionDuration"));


        tblSession.setItems(list);

    }

    public void handleMouseAction(MouseEvent mouseEvent) {
        System.out.println("Clicked a record in Tags table");

        try {
            Sessions sessions = tblSession.getSelectionModel().getSelectedItem();

            System.out.println("Test sout of selected, sessionDbID: " + sessions.getSessionID());


            SuitableRoomForSessionController suitableRoomForSessionController = new SuitableRoomForSessionController();
            suitableRoomForSessionController.getNecessaryInformationFromShowAllSessions(sessions);

            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/suitableRoomForSession.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();


                Stage stage = new Stage();
                stage.setTitle("Add suitable locations for a session");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(pane.getScene().getWindow());
                stage.setResizable(false);
                stage.setScene(new Scene(root1));
                stage.show();

            }catch (Exception e){
                System.out.println("can't load new window");
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error: No records selected !");
            alert.setContentText("No records found or selected in the table to set suitable locations.");
            alert.show();
            e.printStackTrace();
        }

    }


}
