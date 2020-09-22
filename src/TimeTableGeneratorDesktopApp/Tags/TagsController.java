package TimeTableGeneratorDesktopApp.Tags;

import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
import TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm.subGroups;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class    TagsController implements Initializable {
    public static String tester="";

    @FXML
    private Button tagSubmit;

    @FXML
    private ListView<String> tagsList2;

    @FXML
    private ListView<String> tagsList1;

    @FXML
    private ListView<String> tagsList3;

    @FXML
    private AnchorPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getTags();
        getValues();
        //createTables();
    }

    public void createTables(){
        String  systemTagsTable= "CREATE TABLE IF NOT  EXISTS   (" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                "  `systemTag` varchar(45) DEFAULT NULL," +
                "  PRIMARY KEY (`id`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8";
        executeQuery(systemTagsTable);

        String  tagsTable= "CREATE TABLE IF NOT  EXISTS tags (" +
                "  `idtags` int(11) NOT NULL AUTO_INCREMENT," +
                "  `Tag` varchar(45) DEFAULT NULL," +
                "  PRIMARY KEY (`idtags`)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8";
        executeQuery(tagsTable);

        String tempTags= "CREATE TABLE IF NOT  EXISTS temptags (" +
                "  `Tag` varchar(45) NOT NULL," +
                "  PRIMARY KEY (`Tag`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
        executeQuery(tempTags);

    }


    public void getValues(){
        ObservableList<String> list = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String  query2 = "SELECT * FROM systemTags order by systemTag";
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

        tagsList1.setItems(list);
    }

    @FXML
    public void submitTags(){
        setTagList3();
        getTags();
        tagsList2.getItems().clear();
    }

    @FXML
    void ActionEventManageTags(MouseEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Tags/ManageTags/manageTags.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Manage Tags");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainPane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.getIcons().add(new Image("TimeTableGeneratorDesktopApp/icons/tag.png"));
            stage.show();
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            getTags();
                            getValues();
                        }
                    });
                }
            });
        }catch (Exception e){
            System.out.println("can't load new window");
            e.printStackTrace();
        }
    }
    @FXML
    public void setTagsList2(MouseEvent event){
        String a = tagsList1.getSelectionModel().getSelectedItem();
        String query = "INSERT INTO tempTags (Tag) " +
                "VALUES ('" +a+ "') ";
        executeQuery(query);
        getTempValues();
    }

    @FXML
    public void deleteTags(MouseEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure");
        Optional<ButtonType> action = alert.showAndWait();

        if(action.get() == ButtonType.OK) {
            String a = tagsList3.getSelectionModel().getSelectedItem();
            System.out.println(a);
            String query = "DELETE from tags Where Tag = '" + a + "'";
            executeQuery(query);
            getTags();
        }
    }


    public void setTagList3(){
        Connection conn = getConnection();
        String  query2 = "SELECT * FROM tempTags";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query2);
            String tags;
            while (rs.next()) {
                tags = rs.getString("Tag");
                String query = "INSERT INTO tags (Tag) " +
                        "VALUES ('" +tags+ "') ON DUPLICATE KEY UPDATE tag ='" +tags+ "'";
                executeQuery(query);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String query = "Delete from tempTags";
        executeQuery(query);
    }


    public void getTags(){
        ObservableList<String> TagsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String  query2 = "SELECT * FROM tags order by tag";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query2);
            String tags;
            while (rs.next()) {
                tags = rs.getString("Tag");
                TagsList.add(tags);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        tagsList3.setItems(TagsList);
    }



    public void getTempValues(){
        ObservableList<String> tempTagsList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String  query2 = "SELECT * FROM tempTags";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query2);
            String tags;
            while (rs.next()) {
                tags = rs.getString("Tag");
                tempTagsList.add(tags);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        tagsList2.setItems(tempTagsList);
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
