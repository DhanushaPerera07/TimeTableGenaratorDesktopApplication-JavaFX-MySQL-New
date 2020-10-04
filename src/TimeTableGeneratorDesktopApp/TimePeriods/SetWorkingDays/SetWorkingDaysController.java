package TimeTableGeneratorDesktopApp.TimePeriods.SetWorkingDays;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import TimeTableGeneratorDesktopApp.TimePeriods.WorkingDaysAndHoursController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class SetWorkingDaysController implements Initializable {

    DatabaseHelper databaseHelper = new DatabaseHelper();

    public static int noDays;
    public static int   noDaysDB = 0;
    public static int noDaysIdDB;

    public String one;
    public String two;
    public String three;
    public String four;
    public String five;
    public String six;
    public String seven;

    public static String day1namex, day2namex, day3namex, day4namex, day5namex, day6namex, day7namex;

    public String day1;

    @FXML
    private TextField h1;



    @FXML
    private ComboBox<String> comboSelectDays;

    @FXML
    private ComboBox<String> comboSelectDays1;

    @FXML
    private Button populateBtn;

    @FXML
    private Button submitBtn;


    @FXML
    private ComboBox<String>  field1noDays;

    @FXML
    private ComboBox<String>  field2noDays;

    @FXML
    private ComboBox<String>  field3noDays;

    @FXML
    private ComboBox<String>  field4noDays;

    @FXML
    private ComboBox<String>  field5noDays;

    @FXML
    private ComboBox<String>  field6noDays;

    @FXML
    private ComboBox<String>  field7noDays;

    @FXML
    private Label label1noDays;

    @FXML
    private Label label2noDays;

    @FXML
    private Label label4noDays;

    @FXML
    private Label label3noDays;

    @FXML
    private Label label5noDays;

    @FXML
    private Label label6noDays;

    @FXML
    private Label label7noDays;

    @FXML
    private Button resetBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getDayNames();
        getWorkingDays();
        showTextFields();
        getHours();
        setComBoxes();
        createtable();

    }


    public void createtable(){
        String query20 = "CREATE TABLE IF NOT EXISTS daysname (" +
                "  `id` INT NOT NULL," +
                "  `day1name` VARCHAR(45) NULL," +
                "  `day2name` VARCHAR(45) NULL," +
                "  `day3name` VARCHAR(45) NULL," +
                "  `day4name` VARCHAR(45) NULL," +
                "  `day5name` VARCHAR(45) NULL," +
                "  `day6name` VARCHAR(45) NULL," +
                "  `day7name` VARCHAR(45) NULL," +
                "  PRIMARY KEY (`id`))" +
                "ENGINE = InnoDB;";

        databaseHelper.executeQuery(query20);
    }

    public void Visible(){


        field1noDays.setVisible(false);
        field2noDays.setVisible(false);
        field3noDays.setVisible(false);
        field4noDays.setVisible(false);
        field5noDays.setVisible(false);
        field6noDays.setVisible(false);
        field7noDays.setVisible(false);

        label1noDays.setVisible(false);
        label2noDays.setVisible(false);
        label3noDays.setVisible(false);
        label4noDays.setVisible(false);
        label5noDays.setVisible(false);
        label6noDays.setVisible(false);
        label7noDays.setVisible(false);

        h1.setVisible(false);


    }

    /*public Connection getConnection(){
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
    }*/

    @FXML
    public void selectWorkingDays(ActionEvent actionEvent) {

        noDays = comboSelectDays.getSelectionModel().getSelectedIndex() + 1;
        System.out.println(noDays);
        insertRecord();
    }

    @FXML
    private void deleteRecord(ActionEvent actionEvent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to reset?");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            String query = "DELETE from nodays WHERE idno =1 ";
            String query2 = "DELETE from hours WHERE id =1 ";
            String query3 = "DELETE from daysname WHERE id =1 ";
            String query4 = "DELETE from timeslots";

            databaseHelper.executeQuery(query);
            databaseHelper.executeQuery(query2);
            databaseHelper.executeQuery(query3);
            databaseHelper.executeQuery(query4);

//            Stage stage = (Stage) resetBtn.getScene().getWindow();
//            stage.close();
            noDaysDB = 0;

        }

        Stage stage = (Stage) resetBtn.getScene().getWindow();
        stage.close();
//        WorkingDaysAndHoursController.getDayNames();
    }
    @FXML
    public void insertRecord(){
        String query3 = "DELETE from daysname WHERE id =1 ";
        databaseHelper.executeQuery(query3);
        
        String query = "INSERT INTO nodays (noDays, idno)  VALUES (" +noDays +",1 )  ON DUPLICATE KEY UPDATE noDays ='" +noDays+ "'" ;
        databaseHelper.executeQuery(query);
        getWorkingDays();
        showTextFields();
    }

    public void insertHours(){
        String query = "INSERT INTO hours (id, hour1)  VALUES (1 ,'" +h1.getText() +"') " +
                " ON DUPLICATE KEY UPDATE hour1 = '" +h1.getText() +"' " ;
        databaseHelper.executeQuery(query);
        getWorkingDays();
        showTextFields();
    }

    public void showTextFields(){


        if(noDaysDB ==0){
            Visible();
        }

        if(noDaysDB ==1){

            Visible();
            field1noDays.setVisible(true);
            label1noDays.setVisible(true);
            h1.setVisible(true);
        }
        if(noDaysDB ==2){
            Visible();
            field1noDays.setVisible(true);
            field2noDays.setVisible(true);

            label1noDays.setVisible(true);
            label2noDays.setVisible(true);

            h1.setVisible(true);



        }
        if(noDaysDB ==3){
            Visible();
            field1noDays.setVisible(true);
            field2noDays.setVisible(true);
            field3noDays.setVisible(true);

            label1noDays.setVisible(true);
            label2noDays.setVisible(true);
            label3noDays.setVisible(true);

            h1.setVisible(true);


        }
        if(noDaysDB ==4){
            Visible();
            field1noDays.setVisible(true);
            field2noDays.setVisible(true);
            field3noDays.setVisible(true);
            field4noDays.setVisible(true);

            label1noDays.setVisible(true);
            label2noDays.setVisible(true);
            label3noDays.setVisible(true);
            label4noDays.setVisible(true);

            h1.setVisible(true);


        }
        if(noDaysDB ==5){
            Visible();
            field1noDays.setVisible(true);
            field2noDays.setVisible(true);
            field3noDays.setVisible(true);
            field4noDays.setVisible(true);
            field5noDays.setVisible(true);

            label1noDays.setVisible(true);
            label2noDays.setVisible(true);
            label3noDays.setVisible(true);
            label4noDays.setVisible(true);
            label5noDays.setVisible(true);

            h1.setVisible(true);


        }
        if(noDaysDB ==6){
            Visible();
            field1noDays.setVisible(true);
            field2noDays.setVisible(true);
            field3noDays.setVisible(true);
            field4noDays.setVisible(true);
            field5noDays.setVisible(true);
            field6noDays.setVisible(true);

            label1noDays.setVisible(true);
            label2noDays.setVisible(true);
            label3noDays.setVisible(true);
            label4noDays.setVisible(true);
            label5noDays.setVisible(true);
            label6noDays.setVisible(true);

            h1.setVisible(true);


        }
        if(noDaysDB ==7){
            field1noDays.setVisible(true);
            field2noDays.setVisible(true);
            field3noDays.setVisible(true);
            field4noDays.setVisible(true);
            field5noDays.setVisible(true);
            field6noDays.setVisible(true);
            field7noDays.setVisible(true);

            label1noDays.setVisible(true);
            label2noDays.setVisible(true);
            label3noDays.setVisible(true);
            label4noDays.setVisible(true);
            label5noDays.setVisible(true);
            label6noDays.setVisible(true);
            label7noDays.setVisible(true);

            h1.setVisible(true);

        }
    }


    public void getWorkingDays() {
        Connection conn = databaseHelper.getConnection();
        String query = "SELECT * FROM nodays";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                 noDaysDB= rs.getInt("noDays");
                noDaysIdDB = rs.getInt("idno");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    void submitDays(ActionEvent event) {

        if(noDaysDB==1){
            day1namex = field1noDays.getSelectionModel().getSelectedItem().toString();
        }else if(noDaysDB==2){
            day1namex = field1noDays.getSelectionModel().getSelectedItem().toString();
            day2namex = field2noDays.getSelectionModel().getSelectedItem().toString();
        }else if(noDaysDB==3){
            day1namex = field1noDays.getSelectionModel().getSelectedItem().toString();
            day2namex = field2noDays.getSelectionModel().getSelectedItem().toString();
            day3namex = field3noDays.getSelectionModel().getSelectedItem().toString();
        }else if(noDaysDB==4){
            day1namex = field1noDays.getSelectionModel().getSelectedItem().toString();
            day2namex = field2noDays.getSelectionModel().getSelectedItem().toString();
            day3namex = field3noDays.getSelectionModel().getSelectedItem().toString();
            day4namex = field4noDays.getSelectionModel().getSelectedItem().toString();
        }else if(noDaysDB==5){
            day1namex = field1noDays.getSelectionModel().getSelectedItem().toString();
            day2namex = field2noDays.getSelectionModel().getSelectedItem().toString();
            day3namex = field3noDays.getSelectionModel().getSelectedItem().toString();
            day4namex = field4noDays.getSelectionModel().getSelectedItem().toString();
            day5namex = field5noDays.getSelectionModel().getSelectedItem().toString();
        }else if(noDaysDB==6){
            day1namex = field1noDays.getSelectionModel().getSelectedItem().toString();
            day2namex = field2noDays.getSelectionModel().getSelectedItem().toString();
            day3namex = field3noDays.getSelectionModel().getSelectedItem().toString();
            day4namex = field4noDays.getSelectionModel().getSelectedItem().toString();
            day5namex = field5noDays.getSelectionModel().getSelectedItem().toString();
            day6namex = field6noDays.getSelectionModel().getSelectedItem().toString();
        }else if(noDaysDB==7){
            day1namex = field1noDays.getSelectionModel().getSelectedItem().toString();
            day2namex = field2noDays.getSelectionModel().getSelectedItem().toString();
            day3namex = field3noDays.getSelectionModel().getSelectedItem().toString();
            day4namex = field4noDays.getSelectionModel().getSelectedItem().toString();
            day5namex = field5noDays.getSelectionModel().getSelectedItem().toString();
            day6namex = field6noDays.getSelectionModel().getSelectedItem().toString();
            day7namex = field7noDays.getSelectionModel().getSelectedItem().toString();
        }else{
            System.out.println("Please select number of days");
        }


        System.out.println("day1 test"+day1namex);
        Connection conn = databaseHelper.getConnection();

        String query2 = "DELETE from daysname WHERE id =1 ";
        databaseHelper.executeQuery(query2);
        String query = "INSERT INTO daysname (id, day1name, day2name, day3name, day4name, day5name, day6name, day7name)  VALUES (1, '"+day1namex+"', '"+day2namex+"', '"+day3namex+"', '"+day4namex+"', '"+day5namex+"', '"+day6namex+"', '"+day7namex+"' ) " +
                "ON DUPLICATE KEY UPDATE day1name = '"+day1namex+"', day2name = '"+day2namex+"', day3name = '"+day3namex+"', day4name = '"+day4namex+"', day5name = '"+day5namex+"', day6name = '"+day6namex+"', day7name = '"+day7namex+"'";
        databaseHelper.executeQuery(query);

        insertHours();

        Stage stage = (Stage) submitBtn.getScene().getWindow();
        stage.close();



    }


    public void getDayNames() {
        Connection conn = databaseHelper.getConnection();
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


                field1noDays.setPromptText(one);
                field2noDays.setPromptText(two);
                field3noDays.setPromptText(three);
                field4noDays.setPromptText(four);
                field5noDays.setPromptText(five);
                field6noDays.setPromptText(six);
                field7noDays.setPromptText(seven);
            }

            System.out.println(one);
            System.out.println(two);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void setComBoxes(){
        field1noDays.getItems().removeAll(field1noDays.getItems());
        field1noDays.getItems().addAll(
                "Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );

        field2noDays.getItems().removeAll(field1noDays.getItems());
        field2noDays.getItems().addAll(
                "Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );

        field3noDays.getItems().removeAll(field1noDays.getItems());
        field3noDays.getItems().addAll(
                "Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );

        field4noDays.getItems().removeAll(field1noDays.getItems());
        field4noDays.getItems().addAll(
                "Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );

        field5noDays.getItems().removeAll(field1noDays.getItems());
        field5noDays.getItems().addAll(
                "Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );

        field6noDays.getItems().removeAll(field1noDays.getItems());
        field6noDays.getItems().addAll(
                "Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );

        field7noDays.getItems().removeAll(field1noDays.getItems());
        field7noDays.getItems().addAll(
                "Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );
    }


    public void getHours() {
        Connection conn = databaseHelper.getConnection();
        String query = "SELECT * FROM hours where id = 1";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {

                h1.setText(rs.getString("hour1"));

            }

            System.out.println(one);
            System.out.println(two);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
//
}
