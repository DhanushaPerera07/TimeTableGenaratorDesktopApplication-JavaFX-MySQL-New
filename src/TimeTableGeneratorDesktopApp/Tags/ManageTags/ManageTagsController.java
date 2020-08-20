package TimeTableGeneratorDesktopApp.Tags.ManageTags;

import TimeTableGeneratorDesktopApp.Tags.TagsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageTagsController implements Initializable {
    public static  String tag;

    @FXML
    private Button submitMTag;

    @FXML
    private AnchorPane MTagPane;

    @FXML
    private ListView<String> listMTag;

    @FXML
    private TextField tfMTag;

    @FXML
    private Button updateMTag;

    @FXML
    private Button deleteMTag;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deleteMTag.setVisible(false);
        updateMTag.setVisible(false);
        submitMTag.setVisible(true);
        getValues();

    }


    public void insertRecord(){
        String tag = tfMTag.getText();

        String query = "INSERT INTO systemTags (systemTag) " +
                "VALUES ('" +tag+ "') ";
        executeQuery(query);

    }

    public void selectTag(MouseEvent event){
        deleteMTag.setVisible(true);
        submitMTag.setVisible(false);
        updateMTag.setVisible(true);
        tag = listMTag.getSelectionModel().getSelectedItem();
        tfMTag.setText(tag);
    }

    @FXML
    public void update(){
        String query = "Update systemTags set systemTag = '" +tfMTag.getText()+ "' where systemTag = '" +tag+ "'";
        executeQuery(query);
        getValues();
        tfMTag.setText("");
        TagsController tagsController = new TagsController();
        tagsController.getValues();
    }


    public void delete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK){
            String query = "Delete from systemTags where systemTag = '" +tag+ "'";
            executeQuery(query);
            getValues();
            tfMTag.setText("");
            TagsController tagsController = new TagsController();
            tagsController.getValues();
        }
    }

    @FXML
    public void submitDelete(MouseEvent event) {
        delete();
    }


    public void submitClick(MouseEvent event) {
        insertRecord();
        getValues();
        tfMTag.setText("");
        TagsController tagsController = new TagsController();
        tagsController.getValues();
    }

    public void getValues(){
        ObservableList<String> list = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String  query2 = "SELECT * FROM systemTags";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query2);
            String tags;
            while (rs.next()) {
                tags =rs.getString("systemTag");
                list.add(tags);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        listMTag.setItems(list);
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
