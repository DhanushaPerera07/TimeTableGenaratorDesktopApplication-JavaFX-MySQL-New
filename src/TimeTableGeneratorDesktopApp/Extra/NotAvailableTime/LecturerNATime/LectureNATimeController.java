package TimeTableGeneratorDesktopApp.Extra.NotAvailableTime.LecturerNATime;

import TimeTableGeneratorDesktopApp.Extra.NotAvailableTime.SubGroupNATime.setNATimeSubGroup.NATimeSubGroups;
import TimeTableGeneratorDesktopApp.Lecturers.Lecturers;
import TimeTableGeneratorDesktopApp.Sessions.Sessions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class LectureNATimeController implements Initializable {
    public static int lecID;
    public static String lecName;


    @FXML
    private TableColumn<Lecturers, String> idCol;

    @FXML
    private TableView<NATLecturers> timeTV;

    @FXML
    private ComboBox<String> dayCB;

    @FXML
    private TableColumn<Lecturers, String> nameCol;

    @FXML
    private TableColumn<NATLecturers, String> dayCol;

    @FXML
    private ComboBox<String> hourCB;

    @FXML
    private TableColumn<NATLecturers, String> hourCol;

    @FXML
    private TableView<Lecturers> lecTV;

    @FXML
    private Button addBtn;

    @FXML
    private Pane pane;

    @FXML
    private TextField searchBox;

    @FXML
    private Label nameLabel;

    public LectureNATimeController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setVisible(false);
        showLecturers();
        setValuesCombo();
        createTable();
    }


    @FXML
    public void insertRecord(){
        String day =  dayCB.getSelectionModel().getSelectedItem().toString();
        String hour = hourCB.getSelectionModel().getSelectedItem().toString();

        String query = "INSERT INTO lecturernatime (lecID,DayTM,Hour)" +
                "VALUES ('"+lecID+"','" +day+ "','" +hour+ "') ";
        executeQuery(query);
        showLecturersNATimes();
        setValuesCombo();

    }

    public ObservableList<NATLecturers> getLecturersNATImeList(){
        ObservableList<NATLecturers> lecturersNATimeList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM lecturernatime WHERE lecID = '" +lecID+"'";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            NATLecturers naTimeLecturers;
            while (rs.next()) {
                naTimeLecturers = new NATLecturers(rs.getInt("id"),rs.getString("lecID"),rs.getString("DayTM"),rs.getString("Hour"));
                lecturersNATimeList.add(naTimeLecturers);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lecturersNATimeList;
    }

    private void showLecturersNATimes() {
        ObservableList<NATLecturers> list = getLecturersNATImeList();
        dayCol.setCellValueFactory(new PropertyValueFactory<NATLecturers,String>("DayTM"));
        hourCol.setCellValueFactory(new PropertyValueFactory<NATLecturers,String>("Hour"));
        timeTV.setItems(list);


    }


    public void createTable(){
        String createTableQuery = "CREATE  TABLE IF NOT EXISTS `timetabledb`.`lecturernatime` (" +
                "  `id` INT NOT NULL AUTO_INCREMENT," +
                "  `lecID` int NULL ," +
                "  `DayTM` VARCHAR(45) NULL ," +
                "  `Hour` VARCHAR(45) NULL ," +
                "  PRIMARY KEY (`id`) );";

        executeQuery(createTableQuery);
    }

    @FXML
    private void deleteRecord(MouseEvent mouseEvent){

        NATLecturers timeLecturer = timeTV.getSelectionModel().getSelectedItem();
        int rowID = timeLecturer.getId();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete?");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            String query = "DELETE from lecturernatime WHERE id ="+rowID+"";
            executeQuery(query);
            showLecturersNATimes();
        }

    }

    @FXML
    public void searchRecord(KeyEvent ke) {
        FilteredList<Lecturers> filterData = new FilteredList<>(getLecturersList(), p -> true);
        searchBox.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
            filterData.setPredicate(lec -> {
                if (newvalue == null || newvalue.isEmpty()) {
                    return true;
                }
                String typedText = newvalue.toLowerCase();
                if (lec.getLecturerName().toLowerCase().indexOf(typedText) != -1) {
                    return true;
                }   if (lec.getLecturerFaculty().toLowerCase().indexOf(typedText) != -1) {
                    return true;
                }   if (lec.getLecturerDepartment().toLowerCase().indexOf(typedText) != -1) {
                    return true;
                }   if (lec.getLecturerBuilding().toLowerCase().indexOf(typedText) != -1) {
                    return true;
                }   if (lec.getLecturerCenter().toLowerCase().indexOf(typedText) != -1) {
                    return true;
                }   if (lec.getLecturerID().toLowerCase().indexOf(typedText) != -1) {
                    return true;
                }
                return false;
            });
            SortedList<Lecturers> sortedList = new SortedList<>(filterData);
            sortedList.comparatorProperty().bind(lecTV.comparatorProperty());
            lecTV.setItems(sortedList);

        });
    }

    @FXML
    void handleMouseAction(MouseEvent event) {
        pane.setVisible(true);
        Lecturers lecturers = lecTV.getSelectionModel().getSelectedItem();
        lecID = lecturers.getLid();
        lecName = lecturers.getLecturerName();
        nameLabel.setText(lecName);

        try{

            showLecturersNATimes();

        }catch (Exception e){
            System.out.println("can't load new window");
        }
    }

    private void setValuesCombo(){
        dayCB.getItems().removeAll(dayCB.getItems());
        dayCB.setPromptText("Select");
        dayCB.getItems().addAll(
                "Monday", "Tuesday" , "Wednesday", "Friday" , "Saturday", "Sunday"
        );


        hourCB.getItems().removeAll(hourCB.getItems());
        hourCB.setPromptText("Select");
        hourCB.getItems().addAll(
                "8.00", "9.00" , "10.00", "11.00" , "12.00", "13.00","14.00","15.00","16.00"
        );
    }


    public ObservableList<Lecturers> getLecturersList() {
        ObservableList<Lecturers> lecturersList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM lecturer ORDER BY lecturerName";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Lecturers lecturers;
            while (rs.next()) {
                lecturers = new Lecturers(rs.getInt("lid"),
                        rs.getString("lecturerID"),
                        rs.getString("lecturerName"),
                        rs.getString("lecturerFaculty"),
                        rs.getString("lecturerDepartment"),
                        rs.getString("lecturerCenter"),
                        rs.getString("lecturerBuilding"),
                        rs.getInt("lecturerLevel"),
                        rs.getString("lecturerRank"));
                lecturersList.add(lecturers);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lecturersList;
    }


    public void showLecturers() {
        ObservableList<Lecturers> list = getLecturersList();

        idCol.setCellValueFactory(new PropertyValueFactory<Lecturers, String>("lecturerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Lecturers, String>("lecturerName"));
        lecTV.setItems(list);


    }


    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetabledb", "root","root");
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

}
