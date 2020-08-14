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

    @FXML
    private ComboBox<String> comboSelectDays;

    @FXML
    private Button populateBtn;

    @FXML
    private TextField labelnoDays;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelnoDays.setVisible(false);

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
    }

    @FXML
    public void insertRecord(ActionEvent actionEvent){
        String query = "INSERT INTO nodays (noDays) " +
                "VALUES (" +noDays +") ";
        executeQuery(query);

        getWorkingDays();
        labelnoDays.setText(String.valueOf("No of working days : " +noDaysDB));

        if(noDays ==1){

            labelnoDays.setVisible(true);
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
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}
