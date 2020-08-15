package TimeTableGeneratorDesktopApp.Lecturers.LecturerForm;

import TimeTableGeneratorDesktopApp.Lecturers.lecturersController;
import TimeTableGeneratorDesktopApp.TimeTableGeneration.TimeTableGeneration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LecturerFormController implements Initializable {

    public static String lecturerName;
    public static int lecturerID;
    public static String lecturerFaculty;
    public static String lecturerDepartment;
    public static String lecturerCenter;
    public static String lecturerBuilding;
    public static int lecturerLevel;
    public static String rank;

    @FXML
    private Label lecturerLabel;

    @FXML
    private Button addLecturerBtn;

    @FXML
    private Button updateLecturerBtn;

    @FXML
    private Button deleteLecturerBtn;

    @FXML
    private ComboBox<String> comboLevelBox;

    @FXML
    private ComboBox<String> comboCenterBox;

    @FXML
    private ComboBox<String> comboBuildingBox;

    @FXML
    private ComboBox<String> comboFacultyBox;

    @FXML
    private TextField tfLecturerName;

    @FXML
    private TextField tfDepartment;

    @FXML
    private TextField tfEmployeeID;

    TimeTableGeneratorDesktopApp.Lecturers.lecturersController lecturersController = new lecturersController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (lecturersController.lecturerBuilding.equals("")){
            updateLecturerBtn.setVisible(false);
            addLecturerBtn.setVisible(true);
            deleteLecturerBtn.setVisible(false);


            comboBuildingBox.getItems().removeAll(comboBuildingBox.getItems());
            comboBuildingBox.getItems().addAll(
                    "New Building", "D-Block", "etc"
            );

            comboFacultyBox.getItems().removeAll(comboFacultyBox.getItems());
            comboFacultyBox.getItems().addAll(
                    "Computing", "Engineering", "Business", "Humanities", "Science", "etc"
            );
        }
        else {

            addLecturerBtn.setVisible(false);
            updateLecturerBtn.setVisible(true);
            deleteLecturerBtn.setVisible(true);

            lecturerLabel.setText("Lecturer - update a lecturer");
            lecturerName = lecturersController.lecturerName;
            tfLecturerName.setText(lecturerName);
            lecturerID = lecturersController.lecturerID;
            tfEmployeeID.setText(String.valueOf(lecturerID));
            lecturerFaculty = lecturersController.lecturerFaculty;
            lecturerDepartment = lecturersController.lecturerDepartment;
            tfDepartment.setText(lecturerDepartment);
            lecturerCenter = lecturersController.lecturerCenter;
            lecturerBuilding = lecturersController.lecturerBuilding;
            lecturerLevel = lecturersController.lecturerLevel;
            rank = lecturersController.rank;

            comboBuildingBox.getItems().removeAll(comboBuildingBox.getItems());
            comboBuildingBox.setPromptText(lecturerBuilding);
            comboBuildingBox.getItems().addAll(
                    "New Building", "D-Block", "etc"
            );

            comboFacultyBox.getItems().removeAll(comboFacultyBox.getItems());
            comboFacultyBox.setPromptText(lecturerFaculty);
            comboFacultyBox.getItems().addAll(
                    "Computing", "Engineering", "Business", "Humanities", "Science", "etc"
            );

            comboCenterBox.setPromptText(lecturerCenter);
//            comboLevelBox.setPromptText(String.valueOf(lecturerLevel));

            System.out.println(lecturerLevel);
            if (lecturerLevel==1){
                comboLevelBox.setPromptText("1 - Professor");
            }
            else if (lecturerLevel==2){
                comboLevelBox.setPromptText("2 - Assistant Professor");
            }
            else if (lecturerLevel==3){
                comboLevelBox.setPromptText("3 - Senior Lecturer(HG)");
            }
            else if (lecturerLevel==4){
                comboLevelBox.setPromptText("4 - Senior Lecturer");
            }
            else if (lecturerLevel==5){
                comboLevelBox.setPromptText("5 - Lecturer");
            }
            else if (lecturerLevel==6){
                comboLevelBox.setPromptText("6 - Assistant Lecturer");
            }
            else if (lecturerLevel==7){
                comboLevelBox.setPromptText("7 - Instructors");
            }
            else {
                comboLevelBox.setPromptText("Something wrong");
            }

        }

    }

    public void selectLecturerLevel(ActionEvent actionEvent) {
        System.out.println("Selected the level");
        String l = comboLevelBox.getSelectionModel().getSelectedItem().toString();
        lecturerLevel = comboLevelBox.getSelectionModel().getSelectedIndex() + 1;
        System.out.println(l);
        System.out.println(lecturerLevel);
        //rank = lecturerLevel +"." +tfEmployeeID;
    }

    public void selectLecturerCenter(ActionEvent actionEvent) {
        System.out.println("Selected the center");
        lecturerCenter = comboCenterBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(lecturerCenter);
    }

    public void selectLecturerBuilding(ActionEvent actionEvent) {
        System.out.println("Selected the building");
        lecturerBuilding = comboBuildingBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(lecturerBuilding);
    }

    public void selectLecturerFaculty(ActionEvent actionEvent) {
        System.out.println("Selected the faculty");
        lecturerFaculty = comboFacultyBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(lecturerFaculty);
    }

    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetabledb", "root","root");
            System.out.println("Database connected");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void insertRecord(){
        rank =  lecturerLevel +"." +tfEmployeeID.getText().toString();
        System.out.println(rank);
        String query = "INSERT INTO lecturer VALUES (" +tfEmployeeID.getText() +",'" +tfLecturerName.getText() +"','" +lecturerFaculty
                +"','" +tfDepartment.getText() +"','" +lecturerCenter +"','" +lecturerBuilding +"','" +lecturerLevel  +"','" +rank +"')";
        executeQuery(query);
        System.out.println("Data Inserted");
    }

    public void submitLecturerForm(ActionEvent actionEvent) {
        insertRecord();

        lecturerLevel = 0;
        lecturerFaculty = "";
        lecturerBuilding = "";
        lecturerCenter = "";
        rank = "";
        tfDepartment.setText("");
        tfLecturerName.setText("");
        tfEmployeeID.setText("");

        Stage stage = (Stage) addLecturerBtn.getScene().getWindow();
        stage.close();
    }

    public void updateLecturerForm(ActionEvent actionEvent) {
        updateRecord();

        lecturerLevel = 0;
        lecturerFaculty = "";
        lecturerBuilding = "";
        lecturerCenter = "";
        rank = "";
        tfDepartment.setText("");
        tfLecturerName.setText("");
        tfEmployeeID.setText("");

        Stage stage = (Stage) addLecturerBtn.getScene().getWindow();
        stage.close();
    }

    private void updateRecord(){
        rank =  lecturerLevel +"." +tfEmployeeID.getText().toString();
        System.out.println(rank);
        String query = "UPDATE lecturer SET lecturerID = '" + lecturerID + "', lecturerName = '" + tfLecturerName.getText()
                + "', lecturerFaculty = '" + lecturerFaculty + "', lecturerDepartment = '" + tfDepartment.getText() +"', lecturerCenter = '" + lecturerCenter
                + "', lecturerBuilding = '" + lecturerBuilding +"', lecturerLevel = '" + lecturerLevel + "', lecturerRank = '" + rank
                + "' WHERE lecturerID = " + lecturerID + "";
        executeQuery(query);
    }

    public void deleteLecturerForm(ActionEvent actionEvent) {
        deleteRecord();
        Stage stage = (Stage) addLecturerBtn.getScene().getWindow();
        stage.close();
    }

    private void deleteRecord(){
        String query = "DELETE FROM lecturer WHERE lecturerID =" + lecturerID + "";
        executeQuery(query);
    }
}
