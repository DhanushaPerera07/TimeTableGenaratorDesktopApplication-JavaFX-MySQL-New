package TimeTableGeneratorDesktopApp.Extra.AddSuitableLocations;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.PreferredRoomForSubjectController;
import TimeTableGeneratorDesktopApp.Subjects.Subjects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ShowAllSubjectsController implements Initializable {

    public static int idmodule = 0;
    public static String moduleName = "";

    public static String query = "";

    @FXML
    private Pane subjectsPane;

    @FXML
    private TableColumn<Subjects, Integer> colTuteHour;

    @FXML
    private TableColumn<Subjects, String> colModuleCode;

    @FXML
    private TableColumn<Subjects, String> colYear;

    @FXML
    private TableColumn<Subjects, Integer> colLecHour;

    @FXML
    private TableColumn<Subjects, Integer> colEvaluatonHour;

    @FXML
    private TableColumn<Subjects, String> colModuleName;

    @FXML
    private TableColumn<Subjects, Integer> colLabHour;

    @FXML
    private TableView<Subjects> tvModules;

    @FXML
    private TableColumn<Subjects, String> colSemester;

    @FXML
    private ComboBox<String> filter1;

    @FXML
    private ComboBox<String> filter2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showModules();
    }

    public void showModules() {
        ObservableList<Subjects> list = getModuleList();

        colModuleName.setCellValueFactory(new PropertyValueFactory<Subjects, String>("moduleName"));
        colModuleCode.setCellValueFactory(new PropertyValueFactory<Subjects, String>("moduleCode"));
        colYear.setCellValueFactory(new PropertyValueFactory<Subjects, String>("offeredYear"));
        colSemester.setCellValueFactory(new PropertyValueFactory<Subjects, String>("offeredSemester"));
        colLecHour.setCellValueFactory(new PropertyValueFactory<Subjects, Integer>("lecHour"));
        colTuteHour.setCellValueFactory(new PropertyValueFactory<Subjects, Integer>("tuteHour"));
        colLabHour.setCellValueFactory(new PropertyValueFactory<Subjects, Integer>("labHour"));
        colEvaluatonHour.setCellValueFactory(new PropertyValueFactory<Subjects, Integer>("evaluationHour"));

        tvModules.setItems(list);
    }

    public ObservableList<Subjects> getModuleList() {
        ObservableList<Subjects> moduleList = FXCollections.observableArrayList();
        DatabaseHelper databaseHelper = new DatabaseHelper();
        Connection conn = databaseHelper.getConnection();

        query = "SELECT * FROM module";

        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Subjects subjects;
            while (rs.next()) {
                subjects = new Subjects(rs.getInt("idmodule"),
                        rs.getString("moduleName"),
                        rs.getString("moduleCode"),
                        rs.getString("offeredYear"),
                        rs.getString("offeredSemester"),
                        rs.getInt("lecHour"),
                        rs.getInt("tuteHour"),
                        rs.getInt("labHour"),
                        rs.getInt("evaluationHour"));
                moduleList.add(subjects);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moduleList;
    }


    public void handleMouseClicked(MouseEvent mouseEvent) {

        try {
            Subjects subjects = tvModules.getSelectionModel().getSelectedItem();

            idmodule = subjects.getIdmodule();
            moduleName = subjects.getModuleName();
            System.out.println("Subject id (Menura's part): " + idmodule);

            // pass values to PreferredRoomForSubjectController
            PreferredRoomForSubjectController preferredRoomForSubjectController = new PreferredRoomForSubjectController();
            preferredRoomForSubjectController.getInformationFromSubjectUI(idmodule, moduleName);


            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/preferredRoomForSubject.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();


                Stage stage = new Stage();
                stage.setTitle("Add preferred locations for a subject");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(subjectsPane.getScene().getWindow());
                stage.setResizable(false);
                stage.setScene(new Scene(root1));
                stage.show();

            } catch (Exception e) {
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
