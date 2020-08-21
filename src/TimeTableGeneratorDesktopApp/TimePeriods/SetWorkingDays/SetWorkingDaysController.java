package TimeTableGeneratorDesktopApp.TimePeriods.SetWorkingDays;

import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
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
    private TextField h2;

    @FXML
    private TextField h3;

    @FXML
    private TextField h4;

    @FXML
    private TextField h5;

    @FXML
    private TextField h6;

    @FXML
    private TextField h7;

    @FXML
    private ComboBox<String> comboSelectDays;

    @FXML
    private ComboBox<String> comboSelectDays1;

    @FXML
    private Button populateBtn;

    @FXML
    private Button submitBtn;

    @FXML
    private Label labelnoDays;

    @FXML
    private TextField field1noDays;

    @FXML
    private TextField field2noDays;

    @FXML
    private TextField field3noDays;

    @FXML
    private TextField field4noDays;

    @FXML
    private TextField field5noDays;

    @FXML
    private TextField field6noDays;

    @FXML
    private TextField field7noDays;

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
        labelnoDays.setVisible(false);
        getWorkingDays();
        showTextFields();
        getHours();

    }

    public void zoysa(){


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
        h2.setVisible(false);
        h3.setVisible(false);
        h4.setVisible(false);
        h5.setVisible(false);
        h6.setVisible(false);
        h7.setVisible(false);

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

            executeQuery(query2);
            executeQuery(query3);
            executeQuery(query);
//            Stage stage = (Stage) resetBtn.getScene().getWindow();
//            stage.close();
            noDaysDB = 0;
            h1.setText("");
            h2.setText("");
            h3.setText("");
            h4.setText("");
            h5.setText("");
            h6.setText("");
            h7.setText("");
            field7noDays.setText("");
            field6noDays.setText("");
            field5noDays.setText("");
            field4noDays.setText("");
            field3noDays.setText("");
            field1noDays.setText("");
            field2noDays.setText("");

            showTextFields();
        }

    }
    @FXML
    public void insertRecord(){
        String query = "INSERT INTO nodays (noDays, idno)  VALUES (" +noDays +",1 )  ON DUPLICATE KEY UPDATE noDays ='" +noDays+ "'" ;
        executeQuery(query);
        getWorkingDays();
        showTextFields();
    }

    public void insertHours(){
        String query = "INSERT INTO hours (id, hour1, hour2, hour3, hour4, hour5, hour6, hour7)  VALUES (1 ,'" +h1.getText() +"','" +h2.getText()+"','" +h3.getText()+"','" +h4.getText()+"','" +h5.getText()+"','" +h6.getText()+"','" +h7.getText()+"') " +
                " ON DUPLICATE KEY UPDATE hour1 = '" +h1.getText() +"', hour2 = '" +h2.getText()+"', hour3 = '" +h3.getText()+"', hour4 = '" +h4.getText()+"', hour5 = '" +h5.getText()+ "' , hour6 = '" +h6.getText()+"', hour7 =  '" +h7.getText()+"' " ;
        executeQuery(query);
        getWorkingDays();
        showTextFields();
    }

    public void showTextFields(){
        labelnoDays.setText(String.valueOf(noDaysDB));

        labelnoDays.setVisible(true);

        if(noDaysDB ==0){
            zoysa();
        }

        if(noDaysDB ==1){

            zoysa();
            field1noDays.setVisible(true);
            label1noDays.setVisible(true);
            h1.setVisible(true);
        }
        if(noDaysDB ==2){
            zoysa();
            field1noDays.setVisible(true);
            field2noDays.setVisible(true);

            label1noDays.setVisible(true);
            label2noDays.setVisible(true);

            h1.setVisible(true);
            h2.setVisible(true);


        }
        if(noDaysDB ==3){
            zoysa();
            field1noDays.setVisible(true);
            field2noDays.setVisible(true);
            field3noDays.setVisible(true);

            label1noDays.setVisible(true);
            label2noDays.setVisible(true);
            label3noDays.setVisible(true);

            h1.setVisible(true);
            h2.setVisible(true);
            h3.setVisible(true);

        }
        if(noDaysDB ==4){
            zoysa();
            field1noDays.setVisible(true);
            field2noDays.setVisible(true);
            field3noDays.setVisible(true);
            field4noDays.setVisible(true);

            label1noDays.setVisible(true);
            label2noDays.setVisible(true);
            label3noDays.setVisible(true);
            label4noDays.setVisible(true);

            h1.setVisible(true);
            h2.setVisible(true);
            h3.setVisible(true);
            h4.setVisible(true);

        }
        if(noDaysDB ==5){
            zoysa();
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
            h2.setVisible(true);
            h3.setVisible(true);
            h4.setVisible(true);
            h5.setVisible(true);

        }
        if(noDaysDB ==6){
            zoysa();
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
            h2.setVisible(true);
            h3.setVisible(true);
            h4.setVisible(true);
            h5.setVisible(true);
            h6.setVisible(true);

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
            h2.setVisible(true);
            h3.setVisible(true);
            h4.setVisible(true);
            h5.setVisible(true);
            h6.setVisible(true);
            h7.setVisible(true);
        }
    }


    public void getWorkingDays() {
        Connection conn = getConnection();
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

//    private void deleteRecord(){
//        String query = "DELETE FROM nodays WHERE idno =" + noDaysIdDB + "";
//        executeQuery(query);
//    }



    @FXML
    void submitDays(ActionEvent event) {

        day1namex = field1noDays.getText().toString();
        day2namex = field2noDays.getText().toString();
        day3namex = field3noDays.getText().toString();
        day4namex = field4noDays.getText().toString();
        day5namex = field5noDays.getText().toString();
        day6namex = field6noDays.getText().toString();
        day7namex = field7noDays.getText().toString();

        String query2 = "Delete From daysname where id = 1";
        executeQuery(query2);
        String query = "INSERT INTO daysname (id, day1name, day2name, day3name, day4name, day5name, day6name, day7name)  VALUES (1, '"+day1namex+"', '"+day2namex+"', '"+day3namex+"', '"+day4namex+"', '"+day5namex+"', '"+day6namex+"', '"+day7namex+"' ) " +
                "ON DUPLICATE KEY UPDATE day1name = '"+day1namex+"', day2name = '"+day2namex+"', day3name = '"+day3namex+"', day4name = '"+day4namex+"', day5name = '"+day5namex+"', day6name = '"+day6namex+"', day7name = '"+day7namex+"'";
        executeQuery(query);

        insertHours();

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


                field1noDays.setText(one);
                field2noDays.setText(two);
                field3noDays.setText(three);
                field4noDays.setText(four);
                field5noDays.setText(five);
                field6noDays.setText(six);
                field7noDays.setText(seven);
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

                h1.setText(rs.getString("hour1"));
                h2.setText(rs.getString("hour2"));
                h3.setText(rs.getString("hour3"));
                h4.setText(rs.getString("hour4"));
                h5.setText(rs.getString("hour5"));
                h6.setText(rs.getString("hour6"));
                h7.setText(rs.getString("hour7"));

            }

            System.out.println(one);
            System.out.println(two);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
//
}
