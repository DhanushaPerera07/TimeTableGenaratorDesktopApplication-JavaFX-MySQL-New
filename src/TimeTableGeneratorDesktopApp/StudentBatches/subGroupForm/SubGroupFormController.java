package TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class SubGroupFormController implements Initializable {

    DatabaseHelper databaseHelper = new DatabaseHelper();

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
    public static int subGroupRawID;
    public static int TempNoOfRemainUpdate;
    public static int TempNoOfGroupedUpdate;


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
    @FXML
    private Button sgBtn;
    @FXML
    private Button sgUpdateBtn;
    @FXML
    private Label subGroupFormHead;
    @FXML
    private Button deleteSub;

    TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController studentBatchesController = new studentBatchesController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sgBtn.setVisible(true);
        sgUpdateBtn.setVisible(false);
        deleteSub.setVisible(false);

        rowID = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.rowID;
        batchID = TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.batchID;
//        noOfStudents=TimeTableGeneratorDesktopApp.StudentBatches.studentBatchesController.noofstd;
        getStats();
        showStats();
        showSubGroups();
    }


    @FXML
    void handleMouseAction(MouseEvent event) {
        subGroups subGroups = tvSubGroups.getSelectionModel().getSelectedItem();
        subIDtf.setText("" +subGroups.getSubGroupId());
        sgtfNOF.setText(""+subGroups.getNofStudents());
        subGroupRawID = subGroups.getId();

        int NOF = Integer.parseInt(sgtfNOF.getText());

        TempNoOfRemainUpdate = NOF+noOfRemsStds;
        TempNoOfGroupedUpdate = noOfStudents - TempNoOfRemainUpdate;
        labelRemain.setText(""+TempNoOfRemainUpdate);
        labelGroup.setText(""+TempNoOfGroupedUpdate);



        sgBtn.setVisible(false);
        sgUpdateBtn.setVisible(true);
        subGroupFormHead.setText("Edit sub group");
        deleteSub.setVisible(true);


//        sgtfGN.setText(""+subGroups.get());
    }

    @FXML
    void handleLabelSG(MouseEvent event){

        sgBtn.setVisible(true);
        sgUpdateBtn.setVisible(false);
        deleteSub.setVisible(false);
        subIDtf.setText("");
        sgtfNOF.setText("");
        sgtfGN.setText("");
        subGroupFormHead.setText("Create a sub Group");
    }

    @FXML
    private void updateRecord(){
        String query = "UPDATE subgroups SET subGroupId = '" +subIDtf.getText() + "', NofStudents = '" +sgtfNOF.getText() +
                "'  WHERE id = " + subGroupRawID+ "";
        databaseHelper.executeQuery(query);
    }


    private void deleteRecord(){
        String query = "DELETE from subgroups WHERE id =" +subGroupRawID+ "";
        databaseHelper.executeQuery(query);
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
            sgtfGN.setText("");
            sgtfNOF.setText("");
            subIDtf.setText("");

        }

    }

    @FXML
    public void updateSubGroup(ActionEvent actionEvent){
        int NOF = Integer.parseInt(sgtfNOF.getText());

        if(NOF > TempNoOfRemainUpdate){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("The entered value is higher than the remaining students");
            alert.show();
        }
        else{
            noOfGrpsStds = TempNoOfGroupedUpdate+NOF;
            noOfRemsStds = TempNoOfRemainUpdate - NOF;
            updateStats();
            getStats();
            showStats();
            updateRecord();
            showSubGroups();
            sgtfGN.setText("");
            sgtfNOF.setText("");
            subIDtf.setText("");


            sgBtn.setVisible(true);
            sgUpdateBtn.setVisible(false);
            subGroupFormHead.setText("Create a sub Group");
        }



    }

    @FXML
    public void deleteSubGroup(ActionEvent actionEvent){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            noOfRemsStds = TempNoOfRemainUpdate ;
            noOfGrpsStds=TempNoOfGroupedUpdate;

            updateStats();
            getStats();
            showStats();
            deleteRecord();
            showSubGroups();
            sgtfGN.setText("");
            sgtfNOF.setText("");
            subIDtf.setText("");


            sgBtn.setVisible(true);
            sgUpdateBtn.setVisible(false);
            subGroupFormHead.setText("Create a sub Group");


        }


    }


    @FXML
    public void generateSubID(ActionEvent actionEvent){
        String subNo = sgtfGN.getText();
        subGroupID = batchID+"."+ subNo;
        subIDtf.setText(subGroupID);
    }


    public void insertRecord(){
        String query = "INSERT INTO subgroups (subGroupId,NofStudents,batchID) " +
                "VALUES ('" +subGroupID+ "'," +sgtfNOF.getText()+ ",'" +rowID+"') ";
        databaseHelper.executeQuery(query);
    }


    public void showStats(){
        subGrooupsTitle.setText("Manage sub groups of batch " +batchID );
        getStats();
    }



    public void updateStats(){
        String queryBatchStats = "UPDATE batchstats SET nofGrouped = " +noOfGrpsStds+
                ", nofRemain = " +noOfRemsStds+ " WHERE batch = " +rowID+ "";
        databaseHelper.executeQuery(queryBatchStats);
    }




    public ObservableList<batchstats> getStats() {

        ObservableList<batchstats> statsList = FXCollections.observableArrayList();
        Connection conn = databaseHelper.getConnection();

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
                noOfRemsStds=noOfStudents-noOfGrpsStds;

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
        Connection conn = databaseHelper.getConnection();

        String  query = "SELECT * FROM subgroups WHERE batchID =" +rowID;

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




   /* public Connection getConnection(){
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
