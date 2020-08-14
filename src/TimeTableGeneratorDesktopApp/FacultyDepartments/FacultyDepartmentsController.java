package TimeTableGeneratorDesktopApp.FacultyDepartments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FacultyDepartmentsController implements  Initializable{

    // components in the UI
    @FXML
    private VBox facultyVBox=null;

    // @Menura used a Pane here, but I used a boarder pane, So I take it as boarder pane
    // then, I changed
    // stage.initOwner(subjectPane.getScene().getWindow());
    // TO
    // stage.initOwner(borderPaneFacultyMain.getScene().getWindow());
    @FXML
    private BorderPane borderPaneFacultyMain;

    @FXML
    private TextField facultySearchTxtBox;

    @FXML
    private Button btnSearchFaculty;

    @FXML
    private ComboBox<String> facultyFilterByComboBox;


    @FXML
    private Button btnAddFaculty;

    @FXML
    private ComboBox<String> facultyMoreComboBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // filter by combobox
        facultyFilterByComboBox.getItems().addAll(
                "Select ALL",
                "IT",
                "Blah Blah"
        );
        facultyFilterByComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown

        // More combobox
        facultyMoreComboBox.getItems().addAll(
                "Print",
                "Do something new",
                "Blah Blah"
        );

        // prompt text
        facultyMoreComboBox.setPromptText("More"); // I use this drop down, if I have to deal with a new function

        // Populate the rows like a table
        Node [] nodes = new Node[10];

        for (int i = 0;i< nodes.length;i++){
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItem.fxml"));
                facultyVBox.getChildren().add(nodes[i]);
            } catch (IOException e) {
                System.out.println("Error - FacultyItem Loading ======================================");
                e.printStackTrace();
            }
        }
    } // end - initialize

    // ADD BUTTON - opens the pop up window, so user can add a faculty
    // Action event on ADD BUTTON
    public void openAddFacultyPopUp(ActionEvent actionEvent) {
        System.out.println("Clicked - Open Add Faculty Pop Up");

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FacultyPopUps/addFacultyPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add a Faculty");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(borderPaneFacultyMain.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening addFacultyPopUp.fxml as a pop up ==========================");
            e.printStackTrace();
        }
    }

    public void setOnActionBtnSearch(MouseEvent mouseEvent) {
        System.out.println("Clicked - Search Button on Faculty Screen");
    }
}
