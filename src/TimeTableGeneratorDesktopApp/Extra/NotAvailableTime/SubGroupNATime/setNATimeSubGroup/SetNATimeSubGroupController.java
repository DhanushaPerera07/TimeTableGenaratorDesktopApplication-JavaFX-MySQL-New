package TimeTableGeneratorDesktopApp.Extra.NotAvailableTime.SubGroupNATime.setNATimeSubGroup;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.Extra.NotAvailableTime.SubGroupNATime.SubGroupNATimeController;
import TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm.subGroups;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class SetNATimeSubGroupController implements Initializable {

    DatabaseHelper databaseHelper = new DatabaseHelper();

    public static String batchID;
    public static int batchRawID;
    public static int subGroupRawID;
    public static String subGroupID;

    @FXML
    private Label dayLabel;

    @FXML
    private Label hourLabel;

    @FXML
    private Button addBtn;

    @FXML
    private ComboBox<String> dayCB;

    @FXML
    private ComboBox<String> hourCB;

    @FXML
    private Rectangle rectangle;
    @FXML
    private TableColumn<NATimeSubGroups, String> dayCol;

    @FXML
    private TableView<NATimeSubGroups> NATtv;

    @FXML
    private TableColumn<NATimeSubGroups, String> hourCol;

    @FXML
    private TableColumn<subGroups, String> subGroupCol;

    @FXML
    private Label head;

    @FXML
    private TableView<subGroups> subGroupsTV;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        batchID = SubGroupNATimeController.batchID;
        batchRawID = SubGroupNATimeController.rawID;
        showData();
        invisible();
        setValuesCombo();
    }

    public void invisible() {
        NATtv.setVisible(false);
        rectangle.setVisible(false);
        dayCB.setVisible(false);
        hourCB.setVisible(false);
        dayLabel.setVisible(false);
        hourLabel.setVisible(false);
        addBtn.setVisible(false);
    }

    public void visible() {
        NATtv.setVisible(true);
        rectangle.setVisible(true);
        dayCB.setVisible(true);
        hourCB.setVisible(true);
        dayLabel.setVisible(true);
        hourLabel.setVisible(true);
        addBtn.setVisible(true);
    }

    @FXML
    public void insertRecord() {
        String day = dayCB.getSelectionModel().getSelectedItem().toString();
        String hour = hourCB.getSelectionModel().getSelectedItem().toString();

        String query = "INSERT INTO notavailabletimesubgroup (batchID,subGroupID,Day,Hour)" +
                "VALUES ('" + batchID + "','" + subGroupID + "','" + day + "','" + hour + "') ";
        databaseHelper.executeQuery(query);

        showData();
        showSubNATimes();
        setValuesCombo();

    }

    /*private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
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
    }*/


    private void setValuesCombo() {
        dayCB.getItems().removeAll(dayCB.getItems());
        dayCB.setPromptText("Select");
        dayCB.getItems().addAll(
                "Monday", "Tuesday", "Wednesday", "Friday", "Saturday", "Sunday"
        );


        hourCB.getItems().removeAll(hourCB.getItems());
        hourCB.setPromptText("Select");
        hourCB.getItems().addAll(
                "8.00", "9.00", "10.00", "11.00", "12.00", "13.00", "14.00", "15.00", "16.00"
        );
    }


    public ObservableList<subGroups> getSubGroupsList() {
        ObservableList<subGroups> studentSubGroupsList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();
        String query = "SELECT * FROM subgroups WHERE batchID = " + batchRawID + "";


/*        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {


            subGroups subGroups;
            while (rs.next()) {
                subGroups = new subGroups(rs.getInt("id"), rs.getString("subGroupId"), rs.getInt("NofStudents"), rs.getInt("batchID"));
                studentSubGroupsList.add(subGroups);

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentSubGroupsList;
    }

    public ObservableList<NATimeSubGroups> getSubGroupsNATImeList() {
        ObservableList<NATimeSubGroups> studentSubGroupsNATimeList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();
        String query = "SELECT * FROM notavailabletimesubgroup WHERE batchID = '" + batchID + "' AND subGroupID ='" + subGroupID + "'";
        System.out.println(batchID);

/*        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);*/

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {


            NATimeSubGroups naTimeSubGroups;
            while (rs.next()) {
                naTimeSubGroups = new NATimeSubGroups(rs.getInt("id"), rs.getString("batchID"), rs.getString("subGroupID"), rs.getString("Day"), rs.getString("Hour"));
                studentSubGroupsNATimeList.add(naTimeSubGroups);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return studentSubGroupsNATimeList;
    }

    public void showData() {
        ObservableList<subGroups> list = getSubGroupsList();
        subGroupCol.setCellValueFactory(new PropertyValueFactory<subGroups, String>("subGroupId"));
        subGroupsTV.setItems(list);
        if (list.isEmpty()) {
            head.setText("There are no sub groups");
            subGroupsTV.setVisible(false);
        }
    }

    public void showSubNATimes() {
        ObservableList<NATimeSubGroups> list = getSubGroupsNATImeList();
        dayCol.setCellValueFactory(new PropertyValueFactory<NATimeSubGroups, String>("Day"));
        hourCol.setCellValueFactory(new PropertyValueFactory<NATimeSubGroups, String>("Hour"));
        NATtv.setItems(list);
    }


    @FXML
    void handleMouseAction(MouseEvent event) {
        subGroups subGroups = subGroupsTV.getSelectionModel().getSelectedItem();
        subGroupRawID = subGroups.getId();
        subGroupID = subGroups.getSubGroupId();

        try {
            head.setText("Add not Available Time for the sub group");
            showSubNATimes();
            visible();

        } catch (Exception e) {
            System.out.println("can't load new window");
        }
    }

    @FXML
    private void deleteRecord(MouseEvent mouseEvent) {

        NATimeSubGroups timeSubGroups = NATtv.getSelectionModel().getSelectedItem();
        int rowID = timeSubGroups.getId();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            String query = "DELETE from notavailabletimesubgroup WHERE id =" + rowID + "";
            databaseHelper.executeQuery(query);
            showData();
            showSubNATimes();
        }

    }


}
