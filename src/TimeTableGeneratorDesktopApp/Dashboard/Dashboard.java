package TimeTableGeneratorDesktopApp.Dashboard;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DashboardDatabaseHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    @FXML
    private Text txtLecturerCount;

    @FXML
    private Text txtSubjectCount;

    @FXML
    private Text txtLectureHallCount;

    @FXML
    private Text txtStudentBatchedCount;

    @FXML
    private Text txtDepartmentCount;

    @FXML
    private Text txtTutorialHallCount;

    @FXML
    private Text txtLabCount;

    @FXML
    private Text txtStudentSubGroupCount;

    @FXML
    private Text txtFacultiesCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateStatistics();
    }

    private void updateStatistics() {
        System.out.println("Updating statistics !");
        DashboardDatabaseHelper dashboardDatabaseHelper = new DashboardDatabaseHelper();
        txtStudentBatchedCount.setText(Integer.toString(dashboardDatabaseHelper.countStudentBatches()));
        txtStudentSubGroupCount.setText(Integer.toString(dashboardDatabaseHelper.countStudentSubgroups()));

        txtFacultiesCount.setText(Integer.toString(dashboardDatabaseHelper.countFaculties()));
        txtDepartmentCount.setText(Integer.toString(dashboardDatabaseHelper.countDepartments()));
        txtLecturerCount.setText(Integer.toString(dashboardDatabaseHelper.countLecturers()));
        txtLectureHallCount.setText(Integer.toString(dashboardDatabaseHelper.countLectureHalls()));
        txtTutorialHallCount.setText(Integer.toString(dashboardDatabaseHelper.countTutorialHalls()));
        txtLabCount.setText(Integer.toString(dashboardDatabaseHelper.countLabs()));
        txtSubjectCount.setText(Integer.toString(dashboardDatabaseHelper.countSubjects()));
    }


}
