package TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyPopUps;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.LecturerDatabaseHelper;
import TimeTableGeneratorDesktopApp.Lecturers.Lecturers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddFacultyPopUpController implements Initializable {

    // components
/*    @FXML
    private TextField txtFacultyName;

        @FXML
    private TextField txtFacultyShortName;*/

    @FXML
    private ComboBox<String> comboBoxFacultyName;


    @FXML
    private Label txtFacultyShortName;

    @FXML
    private ComboBox<String> facultySpecializedForComboBox;

    @FXML
    private ComboBox<String> facultyHeadComboBox;

    @FXML
    private ComboBox<String> facultyStatusComboBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeComboBox();

    }

    // ---------------------------------------------------------------------------------------------------------------

    private void initializeComboBox() {

        /** ============== COMBO BOXES ====================================================================
         */

        ObservableList<String> facultyName = FXCollections.observableArrayList();
        facultyName.addAll(
                "Faculty of Computing",
                "Faculty of Business",
                "Faculty of Engineering",
                "Faculty of Humanities & Sciences",
                "Faculty of Graduate Studies & Research",
                "School of Architecture",
                "School of Law",
                "School of Hospitality & Culinary"
        );
        comboBoxFacultyName.setItems(facultyName);
        comboBoxFacultyName.setPromptText("Select Faculty Name");

        comboBoxFacultyName.getSelectionModel().selectedItemProperty().addListener((c,oldValue,newValue) -> {


            for (int i = 0; i < facultyName.size(); i++) {
                if (newValue.equals(facultyName.get(i))){
                    if (newValue.equals("Faculty of Computing")) {
                        txtFacultyShortName.setText("FOC");
                    } else if (newValue.equals("Faculty of Business")){
                        txtFacultyShortName.setText("FOB");
                    }else if (newValue.equals("Faculty of Engineering")){
                        txtFacultyShortName.setText("FOE");
                    }else if (newValue.equals("Faculty of Humanities & Sciences")){
                        txtFacultyShortName.setText("FHS");
                    }else if (newValue.equals("Faculty of Graduate Studies & Research")){
                        txtFacultyShortName.setText("FGSR");
                    }else if (newValue.equals("School of Architecture")){
                        txtFacultyShortName.setText("SOA");
                    }else if (newValue.equals("School of Law")){
                        txtFacultyShortName.setText("SOL");
                    } else if (newValue.equals("School of Hospitality & Culinary")){
                        txtFacultyShortName.setText("SOHC");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Error: Something wrong with the faculty short name");
                        alert.setContentText("Selected faculty name and faculty short name mismatched\nor error occurred");
                        alert.show();
                    }
                }
            }
        });

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

        LecturerDatabaseHelper lecturerDatabaseHelper = new LecturerDatabaseHelper();

        ObservableList<Lecturers> lecturersObservableList = lecturerDatabaseHelper.getLecturersList();

        if (lecturersObservableList.size() > 0) {
            // facultyHeadComboBox combobox
            for (Lecturers lecturer : lecturersObservableList) {
                facultyHeadComboBox.getItems().add(lecturer.getLecturerName());
            }
            facultyHeadComboBox.setPromptText("Select head of the faculty");
        } else {
            facultyHeadComboBox.setPromptText("No Lecturers found");
            facultyHeadComboBox.setDisable(true);
        }

        // prompt text
        //facultyHeadComboBox.setPromptText("John Doe");

        // facultyHeadComboBox combobox
        facultyStatusComboBox.getItems().addAll(
                "Active",
                "Not Active"
        );
        // prompt text
        facultyStatusComboBox.setPromptText("Select status");

    }//


    // ---------------------------------------------------------------------------------------------------------------

    public void ActionEventAddFacultyPopUp(ActionEvent actionEvent) {
        // pop up - add a faculty action event on ADD BUTTON
        System.out.println("clicked pop up - add a faculty action event on ADD BUTTON");
        getPermissionToAddTheRecordFromConfirmBox();

    }


    // ===================== DATABASE PART - STARTS HERE =============================================================================


    DatabaseHelper databaseHelper = new DatabaseHelper();


    public void getPermissionToAddTheRecordFromConfirmBox() {
        System.out.println("Clicked - Open Confirmation AlertBOX before adding a Faculty Record");

        Alert addFacultyAlert = new Alert(Alert.AlertType.CONFIRMATION);
        addFacultyAlert.setTitle("Confirmation");
        addFacultyAlert.setHeaderText("Add a new Faculty");
        addFacultyAlert.setContentText("Do you want to add the faculty?\nClick add to add the faculty,\nOtherwise click Cancel");

        ButtonType AddBtn = new ButtonType("ADD");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        addFacultyAlert.getButtonTypes().setAll(AddBtn, CancelBtn);

        Optional<ButtonType> result = addFacultyAlert.showAndWait();
        if (result.get() == AddBtn) {
            insertRecord();
            //System.out.println("Faculty is added successfully");
        } else {
            System.out.println("Clicked Cancel Button - (Adding a faculty)");
        }
    }


    /**
     * This method is used to insert a new faculty
     */
    public void insertRecord() {

        // get user input
        //int lecturer_emp_id = 1;

        try {
            String faculty_name = comboBoxFacultyName.getValue().trim();
            String faculty_short_name = txtFacultyShortName.getText().trim();
            String faculty_specialized_for = facultySpecializedForComboBox.getValue().trim();
            String faculty_status = facultyStatusComboBox.getValue().trim();
            String faculty_head_name = facultyHeadComboBox.getValue().trim();
            String faculty_delete_status = "N";

            if (faculty_name.equals("") || faculty_short_name.equals("") || faculty_specialized_for.equals("") || faculty_status.equals("") || faculty_head_name.equals("")) {
                new Alert(Alert.AlertType.ERROR,"Error: Empty / Not selected field found.\nAll fields are required!").show();
            } else if (comboBoxFacultyName.getValue() == null || txtFacultyShortName.getText() == null || facultySpecializedForComboBox.getValue() == null || facultyStatusComboBox.getValue() == null || facultyHeadComboBox.getValue() == null){
                new Alert(Alert.AlertType.ERROR,"Error: Empty / Not selected field found.\nAll fields are required!").show();
            } else {

                try {
                    // insert query
                    String query = "INSERT INTO `faculty` (`faculty_name`,`faculty_short_name`,`faculty_specialized_for`,`faculty_status`,`faculty_head_name`,`faculty_delete_status`) VALUES ('" + faculty_name + "', '" + faculty_short_name + "', '" + faculty_specialized_for + "', '" + faculty_status + "','" + faculty_head_name + "','" + faculty_delete_status + "')";

                    // execute the insert query
                    databaseHelper.executeQuery(query);
                    closeAddFacultyPopUpForm();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR,"Error: Something went wrong when inserting data").show();
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.ERROR,"Error NullPointerException: Empty / Not selected field found.\nAll fields are required!").show();
            e.printStackTrace();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Error: Something wrong with selected data,\nEmpty / Not selected field found.\nAll fields are required!").show();
            e.printStackTrace();
        }

    }

    private void closeAddFacultyPopUpForm() {
        // just used the txtFacultyName here to close the pop up when the record insertion is successfully done.

        Stage stage = (Stage) comboBoxFacultyName.getScene().getWindow();
        System.out.println("Succeed insertion of a new faculty - closing pop up form");
        stage.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Insertion successfully!");
        alert.setContentText("Record Insertion successfully!\nPlease refresh the screen to view changes");
        alert.show();
    }


}
