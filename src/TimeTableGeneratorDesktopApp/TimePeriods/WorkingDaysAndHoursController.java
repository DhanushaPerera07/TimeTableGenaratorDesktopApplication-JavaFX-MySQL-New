package TimeTableGeneratorDesktopApp.TimePeriods;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class WorkingDaysAndHoursController implements Initializable {

    public String one;
    public String two;
    public String three;
    public String four;
    public String five;
    public String six;
    public String seven;


    public static String selectedDay = "";

    @FXML
    private GridPane listViewDays;

    @FXML
    private Label hl1;

    @FXML
    private Label hl2;

    @FXML
    private Label hl3;

    @FXML
    private Label hl4;

    @FXML
    private Label hl5;

    @FXML
    private Label hl6;

    @FXML
    private Label hl7;

    @FXML
    private Label oneL;

    @FXML
    private Label twoL;

    @FXML
    private Label threeL;

    @FXML
    private Label fourL;

    @FXML
    private Label fiveL;

    @FXML
    private Label sixL;

    @FXML
    private Label sevenL;

    @FXML
    private Button addWorkingDaysBtn;


    @FXML
    private Pane workingPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getDayNames();
        getHours();
    }

    public void addWorkingDaysAction(ActionEvent actionEvent) {

        System.out.println("Add Lecturer Button Clicked");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SetWorkingDays/SetWorkingDaysForm.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Set Working Days");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(workingPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addTimeSlots1(MouseEvent actionEvent) {

        System.out.println("Time slots Clicked");
        try {
            selectedDay = "day1";
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimeSlots/TimeSlots.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Set Time Slots");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(workingPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void addTimeSlots2(MouseEvent actionEvent) {

        System.out.println("Time ");
        try {
            selectedDay = "day1";

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimeSlots/TimeSlots.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Set Time Slots");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(workingPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getDayNames() {
        Connection conn = getConnection();
        String query = "SELECT * FROM daysname where id = 1";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                one = rs.getString("day1name");
                two = rs.getString("day2name");
                three = rs.getString("day3name");
                four = rs.getString("day4name");
                five = rs.getString("day5name");
                six = rs.getString("day6name");
                seven = rs.getString("day7name");

                oneL.setText(one);
                twoL.setText(two);
                threeL.setText(three);
                fourL.setText(four);
                fiveL.setText(five);
                sixL.setText(six);
                sevenL.setText(seven);
            }

            System.out.println(one);
            System.out.println(two);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void getHours() {
        Connection conn = getConnection();
        String query = "SELECT * FROM hours where id = 1";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {

                hl1.setText(rs.getString("hour1"));
                hl2.setText(rs.getString("hour2"));
                hl3.setText(rs.getString("hour3"));
                hl4.setText(rs.getString("hour4"));
                hl5.setText(rs.getString("hour5"));
                hl6.setText(rs.getString("hour6"));
                hl7.setText(rs.getString("hour7"));

            }

            System.out.println(one);
            System.out.println(two);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
//
//    public void addTimeSlots2(MouseEvent mouseEvent) {
//    }
//
//    public void addTimeSlots(javafx.scene.input.MouseEvent mouseEvent) {
//    }
}
