package TimeTableGeneratorDesktopApp.TimePeriods.TimeSlots;


import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import TimeTableGeneratorDesktopApp.TimePeriods.WorkingDaysAndHoursController;
//import TimeTableGeneratorDesktopApp.TimeTableGeneration.TimeTableGeneration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class TimeSlotsController implements Initializable {

    public int TimeCategory;

    public static String CategoryValuex;

    public double Timetype;

    public int hourID;
    public String hour;
    public String range;
    public String query;
    private double workingHours;

    private double timesloteCount;

    @FXML
    private Button timeSlotsBtn;

    @FXML
    private Pane paneex;

    @FXML
    private Button deleteBtn;

    @FXML
    private ComboBox<?> comboTimeCategory;

    @FXML
    private TextField CategoryValue;

    @FXML
    private Label heading;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hourID = WorkingDaysAndHoursController.hourid;
        hour= WorkingDaysAndHoursController.hour;
        range = String.valueOf(WorkingDaysAndHoursController.range);

        if(hourID >0){
            timeSlotsBtn.setText("Edit");
            CategoryValue.setText(hour.toString());
            comboTimeCategory.setPromptText(String.valueOf(range));

        }else{
            deleteBtn.setVisible(false);
            timeSlotsBtn.setText("Submit");
        }
        getTimeSlotsList();
        getWorkingHours();


    }

    public void getWorkingHours(){

        Connection conn = getConnection();
        String  query = "SELECT * FROM hours";

        Statement st;
        ResultSet rs;

        try {

            st = conn.createStatement();
            rs = st.executeQuery(query);
            TimeSlot timeSlot;
            while (rs.next()) {
                workingHours = rs.getDouble("hour1");
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When time slots data retrieving ");
            ex.printStackTrace();
        }
        System.out.println(workingHours);
    }

    @FXML
    public void deleteTimeSlot(){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete?");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            String query = "DELETE from timeslots WHERE slotsID =" +hourID+ "";
            executeQuery(query);

            Stage stage = (Stage) deleteBtn.getScene().getWindow();
            stage.close();
        }
    }

    public ObservableList<TimeSlot> getTimeSlotsList() {
        ObservableList<TimeSlot> timeSlotList = FXCollections.observableArrayList();
        Connection conn = getConnection();

          String  query = "SELECT * FROM timeslots";

        Statement st;
        ResultSet rs;

        try {
            timesloteCount = 0;
            st = conn.createStatement();
            rs = st.executeQuery(query);
            TimeSlot timeSlot;
            while (rs.next()) {
                timeSlot = new TimeSlot(
                        rs.getInt("slotsID"),
                        rs.getDouble("range_t"),
                        rs.getString("value_t")
                );

                double tsCount = rs.getDouble("range_t");
                timeSlotList.add(timeSlot);
                timesloteCount = timesloteCount + tsCount;
            }

        } catch (Exception ex) {
            System.out.println("Error - When time slots data retrieving ");
            ex.printStackTrace();
        }
        return timeSlotList;
    }

    @FXML
    private void addTimeSlot(ActionEvent event) {

        CategoryValuex= CategoryValue.getText().toString();
        int a = comboTimeCategory.getSelectionModel().getSelectedIndex();
        if(a==0){
            this.range= "0.50";
            System.out.println("half hour selected");
        }else if(a==1){
            this.range = "1.00";
            System.out.println("One hour selected");
        }

        if (this.timesloteCount + Double.parseDouble(range) <= workingHours){
            if(Double.parseDouble(range)>0){
                if(hourID>0){
                    query = "UPDATE timeslots SET range_t =" +range+",value_t =" + "'"+CategoryValuex +"' " + " WHERE  slotsID =" +hourID+ "";
                }else{
                    query = "INSERT INTO timeslots (range_t,value_t)  VALUES ('"+range+"', '"+CategoryValuex +"')";
                }
                executeQuery(query);
                Stage stage = (Stage) timeSlotsBtn.getScene().getWindow();
                stage.close();

            }else{
                CategoryValue.setText("");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Please select a range first");
                alert.show();

            }

        }else if (this.timesloteCount <= workingHours){

            if(Double.parseDouble(range)>0){
                if(hourID>0){
                    query = "UPDATE timeslots SET range_t =" +range+",value_t =" + "'"+CategoryValuex +"' "+ " WHERE  slotsID =" +hourID+ "";
                }else{
                    CategoryValue.setText("");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Your Working hours per week already reached...!");
                    alert.show();
                }
                executeQuery(query);
                Stage stage = (Stage) timeSlotsBtn.getScene().getWindow();
                stage.close();

            }else{
                CategoryValue.setText("");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Please select a range first");
                alert.show();

            }
        }else{
            CategoryValue.setText("");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Your Working hours per week already reached...!");
            alert.show();
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


}
