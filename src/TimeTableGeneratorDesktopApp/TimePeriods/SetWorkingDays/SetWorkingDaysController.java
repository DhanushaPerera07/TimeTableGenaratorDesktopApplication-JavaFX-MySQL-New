package TimeTableGeneratorDesktopApp.TimePeriods.SetWorkingDays;

import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SetWorkingDaysController implements Initializable {

    public static int noDays;
    public static int noDaysDB;
    public static int noDaysIdDB;

    public String day1;

    @FXML
    private ComboBox<String> comboSelectDays;

    @FXML
    private ComboBox<String> comboSelectDays1;

    @FXML
    private Button populateBtn;

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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelnoDays.setVisible(false);
        zoysa();

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


    public void selectWorkingDays(ActionEvent actionEvent) {
        noDays = comboSelectDays.getSelectionModel().getSelectedIndex() + 1;
        System.out.println(noDays);
        insertRecord();
    }

    @FXML
    public void insertRecord(){
        String query = "INSERT INTO nodays (noDays) " +
                "VALUES (" +noDays +") ";
        executeQuery(query);

        getWorkingDays();
        labelnoDays.setText(String.valueOf(noDaysIdDB));

        labelnoDays.setVisible(true);

        if(noDays ==1){

            zoysa();
            field1noDays.setVisible(true);
            label1noDays.setVisible(true);
        }

        if(noDays ==2){
            zoysa();
            field1noDays.setVisible(true);
            field2noDays.setVisible(true);

            label1noDays.setVisible(true);
            label2noDays.setVisible(true);


        }
        if(noDays ==3){
            zoysa();
            field1noDays.setVisible(true);
            field2noDays.setVisible(true);
            field3noDays.setVisible(true);

            label1noDays.setVisible(true);
            label2noDays.setVisible(true);
            label3noDays.setVisible(true);
        }
        if(noDays ==4){
            zoysa();
            field1noDays.setVisible(true);
            field2noDays.setVisible(true);
            field3noDays.setVisible(true);
            field4noDays.setVisible(true);

            label1noDays.setVisible(true);
            label2noDays.setVisible(true);
            label3noDays.setVisible(true);
            label4noDays.setVisible(true);
        }
        if(noDays ==5){
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

        }
        if(noDays ==6){
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

        }
        if(noDays ==7){
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

    private void deleteRecord(){
        String query = "DELETE FROM nodays WHERE idno =" + noDaysIdDB + "";
        executeQuery(query);
    }


}
