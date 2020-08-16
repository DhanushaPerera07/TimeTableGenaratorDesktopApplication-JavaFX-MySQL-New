package TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyPopUps;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    public String faculty_id_to_update;

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

    @FXML   // button
    private Button btnEditFacultyPopUp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // get the record id and assign it to the variable: faculty_id_to_update

    }

    public void ActionEventEditFacultyPopUp(ActionEvent actionEvent) {
        // pop up - edit a faculty action event on EDIT BUTTON
        System.out.println("clicked pop up - edit a faculty action event on EDIT BUTTON");

        getPermissionToEditTheRecordFromConfirmBox(1100);
    }



    // ===================== DATABASE PART - STARTS HERE =============================================================================

    /** get the database connection here
     */
    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetabledb", "root","root");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: getConnection() :::: " + ex.getMessage());
            return null;
        }
    }

    /** execute the query string
     * @param query string is passed here
     * this query will execute by this method
     */
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

        // get user input
        //int lecturer_emp_id = 1;

        //int faculty_id = 1100; // this is the id of the record which is to be updated
        String faculty_name = txtFacultyName.getText();
        String faculty_short_name = txtFacultyShortName.getText();
        String faculty_specialized_for = facultySpecializedForComboBox.getValue();
        String faculty_status = facultyStatusComboBox.getValue();
        String faculty_head_name = facultyHeadComboBox.getValue();
        // String faculty_delete_status = "N"; // this is not used in here

        // insert query
        //String query = "INSERT INTO `faculty` (`faculty_name`,`faculty_short_name`,`faculty_specialized_for`,`faculty_status`,`faculty_head_name`,`faculty_delete_status`) VALUES ('"+faculty_name+"', '"+faculty_short_name+"', '"+faculty_specialized_for+"', '"+faculty_status+"','"+faculty_head_name+"','"+faculty_delete_status+"')";

        // update query
        String query = "UPDATE `faculty` SET faculty_name = '" +faculty_name+ "', faculty_short_name = '" +faculty_short_name+
                "', faculty_specialized_for = '" +faculty_specialized_for+ "', faculty_status = '" +faculty_status+ "', faculty_head_name = '"
                +faculty_head_name+
                "' WHERE id = " +facultyID+ "";


        // execute the insert query
        executeQuery(query);
        closeEditFacultyPopUpForm();

    }

    private void closeEditFacultyPopUpForm() {
        // just used the txtFacultyName here to close the pop up when the record editing/update is successfully done.

        Stage stage = (Stage) txtFacultyName.getScene().getWindow();
        System.out.println("Succeed edit/update of the faculty - closing pop up form");
        stage.close();
    }

}
