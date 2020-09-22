package TimeTableGeneratorDesktopApp.Extra.AddSuitableLocations;

import TimeTableGeneratorDesktopApp.DatabaseHelper.TagsDatabaseHelper;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.PreferredRoomForSubjectController;
import TimeTableGeneratorDesktopApp.ManageSuitableRooms.SuitableRoomForTagController;
import TimeTableGeneratorDesktopApp.Subjects.Subjects;
import TimeTableGeneratorDesktopApp.Tags.Tags;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowAllTagsController implements Initializable {


    @FXML
    private Pane pane;

    @FXML
    private TableView<Tags> tblTags;

    @FXML
    private TableColumn<Tags, String> colTag;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeTableData();
    }

    private void initializeTableData() {

        TagsDatabaseHelper tagsDatabaseHelper = new TagsDatabaseHelper();
        ObservableList<Tags> tagList = tagsDatabaseHelper.getTagList();

        colTag.setCellValueFactory(new PropertyValueFactory<Tags, String>("Tag"));


        tblTags.setItems(tagList);
    }



    @FXML
    void handleMouseAction(MouseEvent event) {

        System.out.println("Clicked a record in Tags table");

        try {
            Tags tag = tblTags.getSelectionModel().getSelectedItem();

            System.out.println("Test sout of selected, tagID: " + tag.getTagID() + ", tag name: " + tag.getTag());


            SuitableRoomForTagController suitableRoomForTagController = new SuitableRoomForTagController();
            suitableRoomForTagController.getInformationFromShowAllTagUI(tag);

            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/ManageSuitableRooms/suitableRoomForTag.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();


                Stage stage = new Stage();
                stage.setTitle("Add suitable locations for a tag");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(pane.getScene().getWindow());
                stage.setResizable(false);
                stage.setScene(new Scene(root1));
                stage.show();

            }catch (Exception e){
                System.out.println("can't load new window");
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Error: No records selected !");
            alert.setContentText("No records found or selected in the table to set suitable locations.");
            alert.show();
            e.printStackTrace();
        }


    }
}
