package TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyPopUps;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddFacultyPopUpController implements Initializable {

    // components
    @FXML
    private TextField txtFacultyName;

    @FXML
    private TextField txtFacultyShortName;

    @FXML
    private ComboBox<String> facultySpecializedForComboBox;

    @FXML
    private ComboBox<String> facultyHeadComboBox;

    @FXML
    private ComboBox<String> facultyStatusComboBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /** ============== COMBO BOXES ====================================================================
         */
        // facultySpecializedForComboBox combobox
        facultySpecializedForComboBox.getItems().addAll(
                "IT",
                "BM",
                "Engineering",
                "HTM"
                /*
                "Faculty of Computing",
                "Faculty of Business Management",
                "Faculty of Engineering"
                */
        );
        // prompt text
        facultySpecializedForComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown

        // facultyHeadComboBox combobox
        facultyHeadComboBox.getItems().addAll(
                "Dr. Nuwan",
                "Dr.Asela",
                "Dr. Nimali",
                "Dr. Kumaraswami",
                "John Doe",
                "Oliver Cruise",
                "Max Born",
                "Lise Meitner",
                "Jane Goodall",
                "Jacqueline K. Barton",
                "Dorothy Hodgkin",
                "Melissa Franklin",
                "Sarah Boysen"
        );
        // prompt text
        facultyHeadComboBox.setPromptText("John Doe"); // selects the first one in the dropdown

        // facultyHeadComboBox combobox
        facultyStatusComboBox.getItems().addAll(
                "Active",
                "Not Active"
        );
        // prompt text
        facultyStatusComboBox.setPromptText("Active"); // selects the first one in the dropdown

    }

    public void ActionEventAddFacultyPopUp(ActionEvent actionEvent) {
        // pop up - add a faculty action event on ADD BUTTON
        System.out.println("clicked pop up - add a faculty action event on ADD BUTTON");
        getPermissionToAddTheRecordFromConfirmBox();

    }



    // ===================== DATABASE PART - STARTS HERE =============================================================================


    DatabaseHelper databaseHelper = new DatabaseHelper();


    public void getPermissionToAddTheRecordFromConfirmBox(){
        System.out.println("Clicked - Open Confirmation AlertBOX before adding a Faculty Record");

        Alert addFacultyAlert = new Alert(Alert.AlertType.CONFIRMATION);
        addFacultyAlert.setTitle("Confirmation");
        addFacultyAlert.setHeaderText("Add a new Faculty");
        addFacultyAlert.setContentText("Do you want to add the faculty?\nClick add to add the faculty,\nOtherwise click Cancel");

        ButtonType AddBtn = new ButtonType("ADD");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        addFacultyAlert.getButtonTypes().setAll(AddBtn,CancelBtn);

        Optional<ButtonType> result = addFacultyAlert.showAndWait();
        if (result.get() == AddBtn){
            insertRecord();
            System.out.println("Faculty is added successfully");
        } else {
            System.out.println("Clicked Cancel Button - (Adding a faculty)");
        }
    }


    /**
     * This method is used to insert a new faculty
     */
    public void insertRecord(){


        // get user input
        //int lecturer_emp_id = 1;
        String faculty_name = txtFacultyName.getText();
        String faculty_short_name = txtFacultyShortName.getText();
        String faculty_specialized_for = facultySpecializedForComboBox.getValue();
        String faculty_status = facultyStatusComboBox.getValue();
        String faculty_head_name = facultyHeadComboBox.getValue();
        String faculty_delete_status = "N";

        // insert query
        String query = "INSERT INTO `faculty` (`faculty_name`,`faculty_short_name`,`faculty_specialized_for`,`faculty_status`,`faculty_head_name`,`faculty_delete_status`) VALUES ('"+faculty_name+"', '"+faculty_short_name+"', '"+faculty_specialized_for+"', '"+faculty_status+"','"+faculty_head_name+"','"+faculty_delete_status+"')";

        // execute the insert query
        databaseHelper.executeQuery(query);
        closeAddFacultyPopUpForm();

    }

    private void closeAddFacultyPopUpForm() {
        // just used the txtFacultyName here to close the pop up when the record insertion is successfully done.

        Stage stage = (Stage) txtFacultyName.getScene().getWindow();
        System.out.println("Succeed insertion of a new faculty - closing pop up form");
        stage.close();
    }


}
