package TimeTableGeneratorDesktopApp;

import TimeTableGeneratorDesktopApp.DatabaseQueries.DatabaseCreation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private BorderPane mainPane;


    /** This static block will run only once when the application is started
     */
    static {
        System.out.println("Testing sout: This is where the creating database process happens\n(database is not creating right now this is a test sout)");
        createDatabase();

    }

    static void createDatabase(){
        DatabaseCreation databaseCreation = new DatabaseCreation();
        databaseCreation.createDatabase();
        System.out.println("Testing sout: Creating database id successfully completed");
    }

    /*
    @FXML
    public void handleActionOnDashboardButton(ActionEvent event) {
        System.out.println("clicked Dashboard Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("/Dashboard/Dashboard.fxml");
        mainPane.setCenter(view);

    }

    @FXML
    public void handleActionOnFacultyDepartmentsButton(ActionEvent event) {
        System.out.println("clicked FacultyDepartments Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("/FacultyDepartments/FacultyDepartments.fxml");
        mainPane.setCenter(view);

    }
    */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // show Dashboard page when the screen is loaded first time
        System.out.println("Initializing - show Dashboard Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Dashboard/Dashboard");
        mainPane.setCenter(view);
    }


    public void handleActionOnDashboardButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("clicked Dashboard Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Dashboard/Dashboard");
        mainPane.setCenter(view);
    }

    public void handleActionOnFacultyDepartmentsButton(ActionEvent actionEvent) {
        System.out.println("clicked FacultyDepartments Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("FacultyDepartments/FacultyDepartments");
        mainPane.setCenter(view);
    }

    public void handleActionOnLocationsLabsHallsButton(ActionEvent actionEvent) {
        System.out.println("clicked LocationsLabsHalls Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("LocationsLabsHalls/LocationsLabsHalls");
        mainPane.setCenter(view);
    }

    public void handleActionOnTagsButton(ActionEvent actionEvent) {
        System.out.println("clicked Tags Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Tags/Tags");
        mainPane.setCenter(view);
    }

    public void handleActionOnSubjectsButton(ActionEvent actionEvent) {
        System.out.println("clicked Subjects Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Subjects/Subjects");
        mainPane.setCenter(view);
    }

    public void handleActionOnLecturersButton(ActionEvent actionEvent) {
        System.out.println("clicked Lecturers Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Lecturers/Lecturers");
        mainPane.setCenter(view);
    }

    public void handleActionOnStudentBatchesButton(ActionEvent actionEvent) {
        System.out.println("clicked StudentBatches Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("StudentBatches/StudentBatches");
        mainPane.setCenter(view);
    }

    public void handleActionOnSessionsButton(ActionEvent actionEvent) {
        System.out.println("clicked Sessions Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Sessions/Sessions");
        mainPane.setCenter(view);
    }

    public void handleActionOnTimePeriodsButton(ActionEvent actionEvent) {
        System.out.println("clicked TimePeriods Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("TimePeriods/WorkingDaysAndHours");
        mainPane.setCenter(view);
    }

    public void handleActionOnTimeTimeTableGenerationButton(ActionEvent actionEvent) {
        System.out.println("clicked TimeTimeTableGeneration Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("TimeTableGeneration/HallView/HallView");
        mainPane.setCenter(view);
    }

    public void handleActionOnExtraButton(ActionEvent actionEvent) {
        System.out.println("clicked Extra Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("Extra/Extra");
        mainPane.setCenter(view);
    }


    /*
    public void goToHallsAndLabsUI(int building_id) {
        System.out.println("clicked View - Halls/Labs Button. Building ID: " + building_id);
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("LocationsHallsInsideBuildings/LocationsHallsInsideBuildings");
        try {
            mainPane.setCenter(view);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }
     */
}
