package TimeTableGeneratorDesktopApp.Extra.AddSuitableLocations;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.Lecturers.Lecturers;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SuitableRoomForLecturerController;
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

public class ShowAllLecturersController implements Initializable {

    public static int lid = 0;
    public static String lecturerName = "";
    public static String lecturerID = "";
    public static String lecturerFaculty = "";
    public static String lecturerDepartment = "";
    public static String lecturerCenter = "";
    public static String lecturerBuilding = "";
    public static int lecturerLevel = 0;
    public static String rank = "";
    public static String filterType = "All";
    public static String filterValue = "";
    public static String query = "";


    @FXML
    private Pane lecturersPane;


    @FXML
    private TableColumn<Lecturers, String> colFaculty;

    @FXML
    private TableColumn<Lecturers, Integer> colLevel;

    @FXML
    private TableColumn<Lecturers, String> colCenter;

    @FXML
    private TableColumn<Lecturers, String> colRank;

    @FXML
    private TableColumn<Lecturers, Integer> colEmployeeID;

    @FXML
    private TableColumn<Lecturers, String> colBuilding;

    @FXML
    private TableView<Lecturers> tvLecturers;

    @FXML
    private TableColumn<Lecturers, String> colLecturerName;

    @FXML
    private TableColumn<Lecturers, String> colDepartment;

    @FXML
    private ComboBox<String> filter1;

    @FXML
    private ComboBox<String> filter2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filter1.getItems().removeAll(filter1.getItems());
        filter1.getItems().addAll(
                "All", "Faculty", "Department", "Center", "Building", "Level"
        );
        showLecturers();
    }


    public void showLecturers(){
        ObservableList<Lecturers> list = getLecturersList();

        colEmployeeID.setCellValueFactory(new PropertyValueFactory<Lecturers, Integer>("lecturerID"));
        colLecturerName.setCellValueFactory(new PropertyValueFactory<Lecturers, String>("lecturerName"));
        colFaculty.setCellValueFactory(new PropertyValueFactory<Lecturers, String>("lecturerFaculty"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<Lecturers, String>("lecturerDepartment"));
        colCenter.setCellValueFactory(new PropertyValueFactory<Lecturers, String>("lecturerCenter"));
        colBuilding.setCellValueFactory(new PropertyValueFactory<Lecturers, String>("lecturerBuilding"));
        colLevel.setCellValueFactory(new PropertyValueFactory<Lecturers, Integer>("lecturerLevel"));
        colRank.setCellValueFactory(new PropertyValueFactory<Lecturers, String>("lecturerRank"));

        tvLecturers.setItems(list);
    }


    DatabaseHelper databaseHelper = new DatabaseHelper();

    public ObservableList<Lecturers> getLecturersList() {
        ObservableList<Lecturers> lecturerList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();


        if (filterType.equals("All")) {
            query = "SELECT * FROM lecturer";
        } else if (filterType.equals("Faculty")) {
            //query = "SELECT * from lecturer WHERE " +filterType+ " = '" +filterValue+ "'";
            query = "Select * from lecturer WHERE lecturerFaculty = '" + filterValue + "'";
        } else if (filterType.equals("Department")) {
            query = "Select * from lecturer WHERE lecturerDepartment = '" + filterValue + "'";
        } else if (filterType.equals("Center")) {
            query = "Select * from lecturer WHERE lecturerCenter = '" + filterValue + "'";
        } else if (filterType.equals("Building")) {
            query = "Select * from lecturer WHERE lecturerBuilding = '" + filterValue + "'";
        } else if (filterType.equals("Level")) {
            query = "Select * from lecturer WHERE lecturerLevel = '" + filterValue + "'";
        }


        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Lecturers lecturers;
            while (rs.next()) {
                lecturers = new Lecturers(
                        rs.getInt("lid"),
                        rs.getString("lecturerID"),
                        rs.getString("lecturerName"),
                        rs.getString("lecturerFaculty"),
                        rs.getString("lecturerDepartment"),
                        rs.getString("lecturerCenter"),
                        rs.getString("lecturerBuilding"),
                        rs.getInt("lecturerLevel"),
                        rs.getString("lecturerRank")
                );
                lecturerList.add(lecturers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lecturerList;
    }


    @FXML
    public void handleMouseClicked(MouseEvent mouseEvent) {

        try {
            Lecturers lecturers = tvLecturers.getSelectionModel().getSelectedItem();

            // pass values to PreferredRoomForSubjectController
            SuitableRoomForLecturerController suitableRoomForLecturerController = new SuitableRoomForLecturerController();
            suitableRoomForLecturerController.getNecessaryInformation(lecturers.getLid(),lecturers.getLecturerName());
            //suitableRoomForLecturerController.getNecessaryInformation(Integer.parseInt(lecturerID),lecturerName);
            System.out.println("Menura's part: \nlecture ID = " + lecturers.getLid() + "\nLecturer name = " + lecturers.getLecturerName());


            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/suitableRoomForLecturer.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();


                Stage stage = new Stage();
                stage.setTitle("Add suitable locations for a lecturer");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(lecturersPane.getScene().getWindow());
                stage.setResizable(false);
                stage.setScene(new Scene(root1));
                stage.show();

            }catch (Exception e){
                System.out.println("can't load new (Add suitable locations for a lecturer) window");
                e.printStackTrace();
            }

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error: No records selected !");
            alert.setContentText("No records found or selected in the table\nto set suitable locations.");
            alert.show();
            e.printStackTrace();
        }
    }

    public void selectFilterType(ActionEvent actionEvent) {
        filterType = filter1.getSelectionModel().getSelectedItem().toString();

        if (filterType.equals("All")) {
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.getItems().addAll(
                    "All"
            );
        } else if (filterType.equals("Faculty")) {
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.getItems().addAll(
                    "Faculty of Computing",
                    "Faculty of Business Management",
                    "Faculty of Engineering",
                    "Faculty of Humanities and sciences"
            );
        } else if (filterType.equals("Department")) {
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.getItems().addAll(
                    "Computer Science and Software Engineering",
                    "Information Technology",
                    "Computer Science and Network Engineering",
                    "Cyber Security",
                    "Information System Engineering",
                    "Civil Engineering",
                    "Electrical Engineering",
                    "Logistic",
                    "Human Resources",
                    "Mathematics",
                    "English",
                    "Law"
            );
        } else if (filterType.equals("Center")) {
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.getItems().addAll(
                    "Malabe", "Metro", "Matara", "Kandy", "Kurunegala", "Jaffna"
            );
        } else if (filterType.equals("Building")) {
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.getItems().addAll(
                    "New Building", "D-Block"
            );
        } else if (filterType.equals("Level")) {
            filter2.getItems().removeAll(filter2.getItems());
            filter2.setPromptText("Select");
            filter2.getItems().addAll(
                    "1", "2", "3", "4", "5", "6", "7"
            );
        }

    }

    public void getFilterValues(ActionEvent actionEvent) {
        filterValue = filter2.getSelectionModel().getSelectedItem().toString();
        filter2.setPromptText("Select");
        System.out.println(filterValue);
        showLecturers();
    }
}
