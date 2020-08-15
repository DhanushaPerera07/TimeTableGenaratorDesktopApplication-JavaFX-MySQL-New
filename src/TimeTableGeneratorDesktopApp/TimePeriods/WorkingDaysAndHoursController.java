package TimeTableGeneratorDesktopApp.TimePeriods;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class WorkingDaysAndHoursController implements Initializable {


    @FXML
    private Button addWorkingDaysBtn;


    @FXML
    private Pane workingPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
