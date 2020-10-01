package TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyPopUps;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.LecturerDatabaseHelper;
import TimeTableGeneratorDesktopApp.FacultyDepartments.Faculty;
import TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyDepartmentsController;
import TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyItem.FacultyItemController;
import TimeTableGeneratorDesktopApp.Lecturers.Lecturers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditFacultyPopUpController implements Initializable {

    // variable to keep the id of the faculty record (faculty id)
    private Faculty facultyInstance;
    public int facultyIdToUpdate;


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

    @FXML   // button
    private Button btnEditFacultyPopUp;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initializeComboBoxes();
        initializeComboBox();

        // get the record id and assign it to the variable: faculty_id_to_update

    }



    // -------------------------------------------------------------------------------------------------------------



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




    // -------------------------------------------------------------------------------------------------------------

    /*public void initializeComboBoxes(){


        // facultySpecializedForComboBox combobox
        facultySpecializedForComboBox.getItems().addAll(
                "IT",
                "BM",
                "Engineering",
                "HTM"
                *//*
                "Faculty of Computing",
                "Faculty of Business Management",
                "Faculty of Engineering"
                *//*
        );

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

        // faculty Status combobox
        facultyStatusComboBox.getItems().addAll(
                "Active",
                "Not Active"
        );
    }*/


    // ------------------------------------------------------------------------------------------------------------




    public void ActionEventEditFacultyPopUp(ActionEvent actionEvent) {
        // pop up - edit a faculty action event on EDIT BUTTON
        System.out.println("clicked pop up - edit a faculty action event on EDIT BUTTON");

        getPermissionToEditTheRecordFromConfirmBox(this.facultyIdToUpdate);
    }

    /**
     * here we are getting the faculty instance which is selected by the user to edit
     * @param faculty
     */
    public void getFacultyInstance(Faculty faculty){
        this.facultyInstance = faculty;
        this.facultyIdToUpdate = faculty.getId();
        setFormFieldsToExistingValues();
    }

    /**
     *  set the form fields to existing values
     */
    public void setFormFieldsToExistingValues(){
        // set the form fields to existing values
        //txtFacultyName.setText(this.facultyInstance.getName());
        comboBoxFacultyName.getSelectionModel().select(this.facultyInstance.getName());
        txtFacultyShortName.setText(this.facultyInstance.getShortName()); // faculty short name label
        facultySpecializedForComboBox.getSelectionModel().select(this.facultyInstance.getSpecializedFor());
        facultyHeadComboBox.getSelectionModel().select(this.facultyInstance.getHead());
        facultyStatusComboBox.getSelectionModel().select(this.facultyInstance.getStatus());

    }



    // ===================== DATABASE PART - STARTS HERE =============================================================================

    DatabaseHelper databaseHelper = new DatabaseHelper();

    /**
     * Get the permission from the user to EDIT OR UPDATE the record.
     * confirmation dialog box is used here.
     * if confirmation dialog: UPDATE button clicked by the user then, record should be updated,
     * otherwise it should not be
     */

    private void getPermissionToEditTheRecordFromConfirmBox(int facultyID) {

        System.out.println("Clicked - Open Confirmation AlertBOX before UPDATING a Faculty Record");

        Alert editFacultyAlert = new Alert(Alert.AlertType.CONFIRMATION);
        editFacultyAlert.setTitle("Confirmation");
        editFacultyAlert.setHeaderText("Edit / Update a new Faculty");
        editFacultyAlert.setContentText("Do you want to Edit/Update the faculty?\nClick Update to Edit/Update the faculty,\nOtherwise click Cancel");

        ButtonType EditBtn = new ButtonType("CONFIRM UPDATE");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        editFacultyAlert.getButtonTypes().setAll(EditBtn,CancelBtn);

        Optional<ButtonType> result = editFacultyAlert.showAndWait();
        if (result.get() == EditBtn){
            editUpdateRecord(facultyID); // parameter: facultyID is passed here, it is used to update the record.
            System.out.println("Faculty is edited/updated successfully");
        } else {
            System.out.println("Clicked Cancel Button - (edit/update a faculty)");
        }
    }


    /**
     * This method is used to insert a new faculty
     */
    public void editUpdateRecord(int facultyID){

        // local variables created to get the user input from the pop up form

        try {
            // get user input
            String faculty_name = comboBoxFacultyName.getValue();
            //String faculty_name = txtFacultyName.getText();
            String faculty_short_name = txtFacultyShortName.getText();
            //String faculty_short_name = txtFacultyShortName.getText();
            String faculty_specialized_for = facultySpecializedForComboBox.getValue();
            String faculty_status = facultyStatusComboBox.getValue();
            String faculty_head_name = facultyHeadComboBox.getValue();
            // String faculty_delete_status = "N"; // this is not used in here

            if (faculty_name.equals("") || faculty_short_name.equals("") || faculty_specialized_for.equals("") || faculty_status.equals("") || faculty_head_name.equals("")) {
                new Alert(Alert.AlertType.ERROR,"Error: Empty / Not selected field found.\nAll fields are required!").show();
            } else if (comboBoxFacultyName.getValue() == null || txtFacultyShortName.getText() == null || facultySpecializedForComboBox.getValue() == null || facultyStatusComboBox.getValue() == null || facultyHeadComboBox.getValue() == null){
                new Alert(Alert.AlertType.ERROR,"Error: Empty / Not selected field found.\nAll fields are required!").show();
            } else {
                try {
                    // update query
                    String query = "UPDATE `faculty` SET faculty_name = '" +faculty_name+ "', faculty_short_name = '" +faculty_short_name+
                            "', faculty_specialized_for = '" +faculty_specialized_for+ "', faculty_status = '" +faculty_status+ "', faculty_head_name = '"
                            +faculty_head_name+
                            "' WHERE faculty_id = " +facultyID+ "";


                    // execute the insert query
                    databaseHelper.executeQuery(query);
                    closeEditFacultyPopUpForm();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR,"Error: Something went wrong when updating data").show();
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

    private void closeEditFacultyPopUpForm() {
        // just used the txtFacultyName here to close the pop up when the record editing/update is successfully done.

/*        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyDepartments.fxml"));

        FacultyDepartmentsController facultyDepartmentsController = loader.getController();
        facultyDepartmentsController.populateAndRefreshFacultyDataRow();*/

        Stage stage = (Stage) comboBoxFacultyName.getScene().getWindow();
        System.out.println("Succeed edit/update of the faculty - closing pop up form");
        stage.close();

    }

}
