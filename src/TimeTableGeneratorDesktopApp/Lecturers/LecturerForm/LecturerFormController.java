package TimeTableGeneratorDesktopApp.Lecturers.LecturerForm;

import TimeTableGeneratorDesktopApp.Lecturers.lecturersController;
//import TimeTableGeneratorDesktopApp.TimeTableGeneration.TimeTableGeneration;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.PreferredRoomForSubjectController;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SuitableRoomForLecturerController;
import TimeTableGeneratorDesktopApp.Subjects.Subjects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LecturerFormController implements Initializable {

    public int lid;
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
    private ComboBox<String> comboDepartmentBox;

    @FXML
    private TextField tfLecturerName;

    @FXML
    private TextField tfDepartment;

    @FXML
    private TextField tfEmployeeID;

    @FXML //added by Dhanusha
    private AnchorPane anchorPaneOfLecturerForm;

    TimeTableGeneratorDesktopApp.Lecturers.lecturersController lecturersController = new lecturersController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lid = lecturersController.lid;
        lecturerName = lecturersController.lecturerName;
        lecturerID = lecturersController.lecturerID;
        lecturerFaculty = lecturersController.lecturerFaculty;
        lecturerDepartment = lecturersController.lecturerDepartment;
        lecturerCenter = lecturersController.lecturerCenter;
        lecturerBuilding = lecturersController.lecturerBuilding;
        lecturerLevel = lecturersController.lecturerLevel;
        rank = lecturersController.rank;

        if (lecturersController.lecturerBuilding.equals("")){
            updateLecturerBtn.setVisible(false);
            addLecturerBtn.setVisible(true);
            deleteLecturerBtn.setVisible(false);


            comboBuildingBox.getItems().removeAll(comboBuildingBox.getItems());
            comboBuildingBox.getItems().addAll(
                    "New Building", "D-Block"
            );

            comboFacultyBox.getItems().removeAll(comboFacultyBox.getItems());
            comboFacultyBox.getItems().addAll(
                    "Faculty of Computing", "Faculty of Engineering", "Faculty of Business", "Faculty of Humanities & Science"
            );
        }
        else {

            addLecturerBtn.setVisible(false);
            updateLecturerBtn.setVisible(true);
            deleteLecturerBtn.setVisible(true);

            lecturerLabel.setText("Lecturer - update a lecturer");
            lid = lecturersController.lid;
            System.out.println("here lid : " +lid);
            lecturerName = lecturersController.lecturerName;
            tfLecturerName.setText(lecturerName);
            lecturerID = lecturersController.lecturerID;
            tfEmployeeID.setText(String.valueOf(lecturerID));
            lecturerFaculty = lecturersController.lecturerFaculty;
            lecturerDepartment = lecturersController.lecturerDepartment;
            //tfDepartment.setText(lecturerDepartment);
            comboDepartmentBox.setPromptText(lecturerDepartment);
            lecturerCenter = lecturersController.lecturerCenter;
            lecturerBuilding = lecturersController.lecturerBuilding;
            lecturerLevel = lecturersController.lecturerLevel;
            rank = lecturersController.rank;

            comboBuildingBox.getItems().removeAll(comboBuildingBox.getItems());
            comboBuildingBox.setPromptText(lecturerBuilding);
            comboBuildingBox.getItems().addAll(
                    "New Building", "D-Block"
            );

            comboFacultyBox.getItems().removeAll(comboFacultyBox.getItems());
            comboFacultyBox.setPromptText(lecturerFaculty);
            comboFacultyBox.getItems().addAll(
                    "Faculty of Computing", "Faculty of Engineering", "Faculty of Business", "Faculty of Humanities & Science"
            );

            if (lecturerFaculty.equals("Faculty of Computing")){
                comboDepartmentBox.getItems().removeAll(comboDepartmentBox.getItems());
                comboDepartmentBox.getItems().addAll(
                        "Computer Science and Software Engineering", "Information Technology", "Computer Science and Network Engineering",
                        "Cyber Security", "Information System Engineering"
                );
            }
            else if (lecturerFaculty.equals("Faculty of Engineering")){
                comboDepartmentBox.getItems().removeAll(comboDepartmentBox.getItems());
                comboDepartmentBox.getItems().addAll(
                        "Civil Engineering","Electrical Engineering"
                );
            }
            else if (lecturerFaculty.equals("Faculty of Business")){
                comboDepartmentBox.getItems().removeAll(comboDepartmentBox.getItems());
                comboDepartmentBox.getItems().addAll(
                        "Logistic","Human Resources"
                );
            }
            else if (lecturerFaculty.equals("Faculty of Humanities Studies")){
                comboDepartmentBox.getItems().removeAll(comboDepartmentBox.getItems());
                comboDepartmentBox.getItems().addAll(
                        "Mathematics","English","Law"
                );
            }




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



            if (lecturerFaculty=="Faculty of Computing"){
                comboDepartmentBox.getItems().removeAll(comboDepartmentBox.getItems());
                comboDepartmentBox.getItems().addAll(
                        "Computer Science and Software Engineering", "Information Technology", "Computer Science and Network Engineering",
                        "Cyber Security", "Information System Engineering"
                );
            }
            else if (lecturerFaculty=="Faculty of Engineering"){
                comboDepartmentBox.getItems().removeAll(comboDepartmentBox.getItems());
                comboDepartmentBox.getItems().addAll(
                        "Civil Engineering","Electrical Engineering"
                );
            }
            else if (lecturerFaculty=="Faculty of Business"){
                comboDepartmentBox.getItems().removeAll(comboDepartmentBox.getItems());
                comboDepartmentBox.getItems().addAll(
                        "Logistic","Human Resources"
                );
            }
            else if (lecturerFaculty=="Faculty of Humanities & Science"){
                comboDepartmentBox.getItems().removeAll(comboDepartmentBox.getItems());
                comboDepartmentBox.getItems().addAll(
                        "Mathematics","English","Law"
                );
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

        if (lecturerFaculty=="Faculty of Computing"){
            comboDepartmentBox.getItems().removeAll(comboDepartmentBox.getItems());
            comboDepartmentBox.getItems().addAll(
                    "Computer Science and Software Engineering", "Information Technology", "Computer Science and Network Engineering",
                    "Cyber Security", "Information System Engineering"
            );
        }
        else if (lecturerFaculty=="Faculty of Engineering"){
            comboDepartmentBox.getItems().removeAll(comboDepartmentBox.getItems());
            comboDepartmentBox.getItems().addAll(
                    "Civil Engineering","Electrical Engineering"
            );
        }
        else if (lecturerFaculty=="Faculty of Business"){
            comboDepartmentBox.getItems().removeAll(comboDepartmentBox.getItems());
            comboDepartmentBox.getItems().addAll(
                    "Logistic","Human Resources"
            );
        }
        else if (lecturerFaculty=="Faculty of Humanities & Science"){
            comboDepartmentBox.getItems().removeAll(comboDepartmentBox.getItems());
            comboDepartmentBox.getItems().addAll(
                    "Mathematics","English","Law"
            );
        }

    }

    public void selectLecturerDepartment(ActionEvent actionEvent) {
        System.out.println("Selected the department");
        lecturerDepartment = comboDepartmentBox.getSelectionModel().getSelectedItem().toString();
        System.out.println(lecturerDepartment);
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

//        String query = "INSERT INTO lecturer VALUES (" +tfEmployeeID.getText() +",'" +tfLecturerName.getText() +"','" +lecturerFaculty
//                +"','" +tfDepartment.getText() +"','" +lecturerCenter +"','" +lecturerBuilding +"','" +lecturerLevel  +"','" +rank +"')";

        String query = "INSERT INTO lecturer (lecturerID,lecturerName,lecturerFaculty,lecturerDepartment,lecturerCenter,lecturerBuilding,lecturerLevel,lecturerRank) " +
                "VALUES (" +tfEmployeeID.getText()+ ",'" +tfLecturerName.getText()+ "','" +lecturerFaculty+ "','" +lecturerDepartment+
                "','" +lecturerCenter+ "','" +lecturerBuilding+ "'," +lecturerLevel+
                "," +rank+ ") ";

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
        lecturerDepartment = "";
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
        lecturerDepartment = "";
        tfLecturerName.setText("");
        tfEmployeeID.setText("");

        Stage stage = (Stage) addLecturerBtn.getScene().getWindow();
        stage.close();
    }

    private void updateRecord(){
        rank =  lecturerLevel +"." +tfEmployeeID.getText().toString();
        System.out.println(rank);
        String query = "UPDATE lecturer SET lecturerID = '" + tfEmployeeID.getText() + "', lecturerName = '" + tfLecturerName.getText()
                + "', lecturerFaculty = '" + lecturerFaculty + "', lecturerDepartment = '" + lecturerDepartment +"', lecturerCenter = '" + lecturerCenter
                + "', lecturerBuilding = '" + lecturerBuilding +"', lecturerLevel = '" + lecturerLevel + "', lecturerRank = '" + rank
                + "' WHERE lid = " + lid + "";
        executeQuery(query);
    }

    public void deleteLecturerForm(ActionEvent actionEvent) {
        deleteRecord();
        Stage stage = (Stage) addLecturerBtn.getScene().getWindow();
        stage.close();
    }

    private void deleteRecord(){
        String query = "DELETE FROM lecturer WHERE lid =" + lid + "";
        executeQuery(query);
    }




    // ---------- Added by Dhanusha - Manage suitable room for Lecturer --------------------------------------
    @FXML
    void setOnActionSuitableRoom(ActionEvent event) {


        // pass values to PreferredRoomForSubjectController
        SuitableRoomForLecturerController suitableRoomForLecturerController = new SuitableRoomForLecturerController();
        suitableRoomForLecturerController.getNecessaryInformation(lecturerID,lecturerName);
        System.out.println("Menura's part: lecture ID = " + lecturerID + "\nLecturer name = " + lecturerName);


        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/suitableRoomForLecturer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();


            Stage stage = new Stage();
            stage.setTitle("Add suitable locations for a lecturer");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(anchorPaneOfLecturerForm.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();

        }catch (Exception e){
            System.out.println("can't load new (Add suitable locations for a lecturer) window");
            e.printStackTrace();
        }
    }

}
