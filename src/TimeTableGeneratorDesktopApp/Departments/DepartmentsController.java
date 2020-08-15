package TimeTableGeneratorDesktopApp.Departments;

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

public class DepartmentsController implements Initializable {

    @FXML
    private BorderPane borderPaneDepartmentMain;

    @FXML
    private TextField departmentSearchTxtBox;

    @FXML
    private Button btnSearchDepartment;

    @FXML
    private ComboBox<String> departmentFilterByComboBox;

    @FXML
    private Button btnAddDepartment;

    @FXML
    private ComboBox<String> departmentMoreComboBox;

    @FXML
    private VBox DepartmentsVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // filter by combobox
        departmentFilterByComboBox.getItems().addAll(
                "Select ALL",
                "IT",
                "Blah Blah"
        );
        departmentFilterByComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown

        // More combobox
        departmentMoreComboBox.getItems().addAll(
                "Print",
                "Do something new",
                "Blah Blah"
        );

        // prompt text
        departmentMoreComboBox.setPromptText("More"); // I use this drop down, if I have to deal with a new function

        // Populate the rows like a table
        Node[] nodes = new Node[10];

        for (int i = 0;i< nodes.length;i++){
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/Departments/DepartmentsItem/DepartmentItem.fxml"));
                DepartmentsVBox.getChildren().add(nodes[i]);
            } catch (IOException e) {
                System.out.println("Error - DepartmentItem Loading ======================================");
                e.printStackTrace();
            }
        }

    }

    public void setOnActionBtnSearch(MouseEvent mouseEvent) {
    }

    public void openAddDepartmentPopUp(MouseEvent mouseEvent) {

        System.out.println("Clicked - Open pop up to edit Department Record");

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Departments/DepartmentsPopUps/addDepartmentPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add Department");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(borderPaneDepartmentMain.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception - When Opening addDepartmentPopUp.fxml as a pop up ");
            e.printStackTrace();
        }

    }
}
