package TimeTableGeneratorDesktopApp.Extra.AddSuitableLocations;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.ModulesDatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.PreferredRoomForSubjectController;
import TimeTableGeneratorDesktopApp.Subjects.Subjects;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ShowAllSubjectsController implements Initializable {

    public static int idmodule = 0;
    public static String moduleName = "";

    public static String filterType = "All";
    public static String filterValue = "";
    public static String query = "";

    @FXML
    private Button addModuleBtn;

    @FXML
    private Button refreshModuleListBtn;

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

        filter1.getItems().removeAll(filter1.getItems());
        filter1.getItems().addAll(
                "All", "Year"
        );

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

//        String query = "SELECT * FROM module";
        if (filterType.equals("All")) {
            query = "SELECT * FROM module";
        } else if (filterType.equals("Year")) {
            query = "Select * from module WHERE offeredYear = '" + filterValue + "'";
        }

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

    public void getFilterValues(ActionEvent actionEvent) {
        filterValue = filter2.getSelectionModel().getSelectedItem().toString();
        filter2.setPromptText("Select");
        System.out.println(filterValue);
        showModules();
    }

    public void selectFilterType(ActionEvent actionEvent) {

        filterType = filter1.getSelectionModel().getSelectedItem().toString();

        if (filterType.equals("All")) {
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.getItems().addAll(
                    "All"
            );
        } else if (filterType.equals("Year")) {
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.getItems().addAll(
                    "Year 1", "Year 2", "Year 3", "Year 4"
            );
        }
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
