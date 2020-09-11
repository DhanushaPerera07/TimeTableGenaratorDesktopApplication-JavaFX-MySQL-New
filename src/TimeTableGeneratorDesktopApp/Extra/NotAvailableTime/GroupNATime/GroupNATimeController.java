package TimeTableGeneratorDesktopApp.Extra.NotAvailableTime.GroupNATime;

import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class GroupNATimeController implements Initializable {

    public static String batchID = "";
    public static int rawID ;
    @FXML
    Pane groupNATimePane;
    @FXML
    private TableColumn<StudentBatches, String> batColYear;

    @FXML
    private TableColumn<StudentBatches, String> batColSem;

    @FXML
    private TableColumn<StudentBatches, String> batColCen;
    @FXML
    private TableColumn<StudentBatches, String> batColIntake;

    @FXML
    private TableColumn<StudentBatches, String> batColFac;

    @FXML
    private TableColumn<StudentBatches, String> batColPro;

    @FXML
    private TableView<StudentBatches> tvBatches;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showBatches();
    }






    public ObservableList<StudentBatches> getBatchesList() {
        ObservableList<StudentBatches> studentBatchesList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM studentBatches ORDER BY year";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            StudentBatches studentBatch;
            while (rs.next()) {
                studentBatch = new StudentBatches(rs.getInt("id"), rs.getString("year"), rs.getString("semester"),
                        rs.getString("intake"), rs.getString("faculty"),
                        rs.getString("programme"),
                        rs.getString("center"),
                        rs.getInt("noofstd"), rs.getString("batchID"));
                studentBatchesList.add(studentBatch);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentBatchesList;
    }


    public void showBatches() {
        ObservableList<StudentBatches> list = getBatchesList();

        batColYear.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("year"));
        batColSem.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("semester"));
        batColIntake.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("batchID"));
        batColFac.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("faculty"));
        batColPro.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("programme"));
        batColCen.setCellValueFactory(new PropertyValueFactory<StudentBatches, String>("center"));


        tvBatches.setItems(list);


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


    @FXML
    void handleMouseAction(MouseEvent event) {
        StudentBatches batch = tvBatches.getSelectionModel().getSelectedItem();
        batchID = batch.getBatchID();
        rawID = batch.getId();


        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("setNATimeGroup/setNATimeGroup.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Not Available time");
            stage.initModality(Modality.WINDOW_MODAL);

            stage.initOwner(groupNATimePane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.getIcons().add(new Image("TimeTableGeneratorDesktopApp/icons/student.png"));
            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            batchID = "";
                            showBatches();
                        }

                    });
                }
            });
        }catch (Exception e){
            System.out.println("can't load new window");
        }
    }


}
