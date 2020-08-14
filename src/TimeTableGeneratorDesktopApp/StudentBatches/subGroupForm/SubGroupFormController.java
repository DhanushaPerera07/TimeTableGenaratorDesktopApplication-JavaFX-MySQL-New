package TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm;

import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import TimeTableGeneratorDesktopApp.StudentBatches.SubGroups.SubGroups;
import TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController;
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

public class SubGroupFormController implements Initializable {
    public static int rowID = 0;
    public static String year = "";
    public static String semester = "";
    public static String intake = "";
    public static String faculty = "";
    public static String programme = "";
    public static String center = "";
    public static String batchID = "";
    public static int noOfStudents = 0;
    public static int noOfGrpsStds = 0;
    public static int noOfRemsStds = 0;
    public static String subGroupID = "";
    public static String tempo;



    @FXML
    private Label labelGroup;
    @FXML
    private Label labelAll;
    @FXML
    private Label labelRemain;


    @FXML
    private Label subGrooupsTitle;

    @FXML
    private TableView<subGroups> tvSubGroups;


    @FXML
    private TableColumn<subGroups, String> colSubId;

    @FXML
    private TableColumn<subGroups, Integer> colNoS;

    @FXML
    private TextField sgtfNOF;

    @FXML
    private TextField sgtfGN;
    @FXML
    private TextField subIDtf;


    TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController studentBatchesController = new studentBatchesController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rowID = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.rowID;
        batchID = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.batchID;
        getStats();
        showStats();
        showSubGroups();
    }


    @FXML
    public void createSubGroup(ActionEvent actionEvent){
        int NOF = Integer.parseInt(sgtfNOF.getText());
        int TempNoOfRemain = noOfStudents-noOfGrpsStds;

        if(NOF > TempNoOfRemain){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("The entered value is higher than the remaining students");
            alert.show();
        }
        else{

            noOfGrpsStds = noOfGrpsStds + NOF;

            noOfRemsStds = noOfStudents - noOfGrpsStds;


            updateStats();

            getStats();

            insertRecord();

            showStats();

            showSubGroups();

        }



    }

    @FXML
    public void generateSubID(ActionEvent actionEvent){

        String subNo = sgtfGN.getText();
        subGroupID = batchID+"."+ subNo;

        subIDtf.setText(subGroupID);
    }

    public void insertRecord(){

        String query = "INSERT INTO subGroups (subGroupId,NofStudents,batchID) " +
                "VALUES ('" +subGroupID+ "'," +sgtfNOF.getText()+ ",'" +rowID+"') ";

        executeQuery(query);

    }

    public void showStats(){
        subGrooupsTitle.setText("Manage sub groups of batch " +batchID );
        getStats();
    }



    public void updateStats(){
        String queryBatchStats = "UPDATE batchStats SET nofGrouped = " +noOfGrpsStds+
                ", nofRemain = " +noOfRemsStds+ " WHERE batch = " +rowID+ "";
        executeQuery(queryBatchStats);

    }




    public ObservableList<batchstats> getStats() {

        ObservableList<batchstats> statsList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String  query = "SELECT * FROM batchstats WHERE batch =" +rowID;

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            batchstats batchstats;
            while (rs.next()) {
                batchstats = new batchstats(rs.getInt("batch"), rs.getInt("nofStudents"),
                            rs.getInt("nofGrouped"), rs.getInt("nofRemain"), rs.getInt("nofGroups"));
                statsList.add(batchstats);

                String  a = rs.getString("batch");
                noOfStudents = rs.getInt("nofStudents");
                noOfGrpsStds=rs.getInt("nofGrouped");
                noOfRemsStds=rs.getInt("nofRemain");

                labelAll.setText(String.valueOf(noOfStudents));
                labelGroup.setText(String.valueOf(noOfGrpsStds));
                labelRemain.setText(String.valueOf(noOfRemsStds));

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return statsList;
    }


    public ObservableList<subGroups> getSubGroupList() {

        ObservableList<subGroups> subGroupList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        String  query = "SELECT * FROM subGroups WHERE batchID =" +rowID;

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            subGroups subGroups;
            while (rs.next()) {
                subGroups = new subGroups(rs.getInt("id"),rs.getString("subGroupId"), rs.getInt("NofStudents"),rs.getInt("batchID"));
                subGroupList.add(subGroups);

//                tempo = rs.getString("subGroupId");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return subGroupList;
    }



    public void showSubGroups() {
        ObservableList<subGroups> list = getSubGroupList();
        colSubId.setCellValueFactory(new PropertyValueFactory<subGroups, String>("subGroupId"));
        colNoS.setCellValueFactory(new PropertyValueFactory<subGroups, Integer>("NofStudents"));
        tvSubGroups.setItems(list);
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


    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


}