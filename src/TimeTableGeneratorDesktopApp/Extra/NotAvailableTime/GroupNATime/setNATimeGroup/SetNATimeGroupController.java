package TimeTableGeneratorDesktopApp.Extra.NotAvailableTime.GroupNATime.setNATimeGroup;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.Extra.NotAvailableTime.GroupNATime.GroupNATimeController;
import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm.batchstats;
import TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm.subGroups;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class SetNATimeGroupController implements Initializable {


    DatabaseHelper databaseHelper = new DatabaseHelper();

    public static String batchID;
    public static String tableName;
    public static String day;
    public static String hour;
    public static int rowID;
    public static int batchRawID;
    public static String subGroupTableName;

    @FXML
    private Label batchIDLabel;
    @FXML
    private ComboBox<String> dayCB;

    @FXML
    private ComboBox<String> hourCB;



    @FXML
    private TableColumn<NATimeGroups, String> dayCol;


    @FXML
    private TableView<NATimeGroups> tabledayhour;

    @FXML
    private TableColumn<NATimeGroups, String> hourCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        batchID = GroupNATimeController.batchID;
        batchRawID = GroupNATimeController.rawID;

        batchIDLabel.setText(batchID);
        tableName="notavailabletimegroup";
        subGroupTableName ="notavailabletimesubgroup";
        createTable();
        setValuesCombo();
        showData();
    }


    public ObservableList<NATimeGroups> getTimeList() {
        ObservableList<NATimeGroups> NATTimeGroupList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();
        String query = "SELECT * FROM notavailabletimegroup WHERE batchID = '" +batchID+"'";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            NATimeGroups naTimeGroups;
            while (rs.next()) {
                naTimeGroups = new NATimeGroups(rs.getInt("id"), rs.getString("batchID"), rs.getString("Day"),rs.getString("Hour"));
                NATTimeGroupList.add(naTimeGroups);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return NATTimeGroupList;
    }

    public void setSubGroupInformation() {
        System.out.println("Query executed");
        System.out.println(batchRawID);
        Connection conn = databaseHelper.getConnection();
        String query = "SELECT * FROM subgroups WHERE batchID = " +batchRawID+"";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
//            subGroups subGroups;
            while (rs.next()) {
//                subGroups = new subGroups(rs.getInt("id"),rs.getString("subGroupId"),rs.getInt("NofStudents"),rs.getInt("batchID"));

                String subGroupID = rs.getString("subGroupId");

                String subGroupInsertQuery = "INSERT INTO notavailabletimesubgroup (batchID,Day,Hour,subGroupID)" +
                        "VALUES ('" +batchID+ "','" +day+ "','" +hour+ "','" +subGroupID+ "') ";
                databaseHelper.executeQuery(subGroupInsertQuery);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void showData() {
        ObservableList<NATimeGroups> list = getTimeList();
        dayCol.setCellValueFactory(new PropertyValueFactory<NATimeGroups, String>("Day"));
        hourCol.setCellValueFactory(new PropertyValueFactory<NATimeGroups, String>("Hour"));
        tabledayhour.setItems(list);
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


    private void createTable() {
        String createTableQuery = "CREATE  TABLE IF NOT EXISTS `timetabledb`.`"+tableName+"` (" +
                "  `id` INT NOT NULL AUTO_INCREMENT," +
                "  `batchID` VARCHAR(45) NULL ," +
                "  `Day` VARCHAR(45) NULL ," +
                "  `Hour` VARCHAR(45) NULL ," +
                "  PRIMARY KEY (`id`) );";



        String createSubGroupTableQ ="CREATE  TABLE IF NOT EXISTS `timetabledb`.`"+subGroupTableName+"` (" +
                "  `id` INT NOT NULL AUTO_INCREMENT," +
                "  `batchID` VARCHAR(45) NULL ," +
                "  `subGroupID` VARCHAR(45) NULL ,"+
                "  `Day` VARCHAR(45) NULL ," +
                "  `Hour` VARCHAR(45) NULL ," +
                "  PRIMARY KEY (`id`) );";

        databaseHelper.executeQuery(createTableQuery);
        databaseHelper.executeQuery(createSubGroupTableQ);
    }

    public void insertRecord(){
        day =  dayCB.getSelectionModel().getSelectedItem().toString();
        hour = hourCB.getSelectionModel().getSelectedItem().toString();

        String query = "INSERT INTO notavailabletimegroup (batchID,Day,Hour)" +
                "VALUES ('" +batchID+ "','" +day+ "','" +hour+ "') ";
        databaseHelper.executeQuery(query);
        setSubGroupInformation();

        showData();
        setValuesCombo();
        day = "";
        hour= "";
    }


    @FXML
    private void deleteRecord(MouseEvent mouseEvent){

        NATimeGroups timeGroups = tabledayhour.getSelectionModel().getSelectedItem();
        rowID = timeGroups.getId();
        String batchID = timeGroups.getBatchID();
        String day = timeGroups.getDay();
        String hour = timeGroups.getHour();


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete?");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            String query = "DELETE from notavailabletimegroup WHERE id ="+rowID+"";
            databaseHelper.executeQuery(query);
            String query1 = "DELETE from notavailabletimesubgroup WHERE batchID ='"+batchID+"' AND Day = '" +day+ "' AND Hour ='"+hour+"'";
            databaseHelper.executeQuery(query1);
            showData();
        }

    }


    /*public Connection getConnection(){
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
    }*/


}
