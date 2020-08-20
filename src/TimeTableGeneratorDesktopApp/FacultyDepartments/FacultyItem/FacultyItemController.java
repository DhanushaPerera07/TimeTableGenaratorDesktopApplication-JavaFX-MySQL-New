package TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyItem;

import TimeTableGeneratorDesktopApp.Departments.DepartmentsController;
import TimeTableGeneratorDesktopApp.FacultyDepartments.Faculty;
import TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyDepartmentsController;
import TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyPopUps.EditFacultyPopUpController;
import TimeTableGeneratorDesktopApp.FxmlLoader;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class FacultyItemController implements Initializable {


    // variable for the faculty ID
    public Faculty facultyInstance;
    public int facultyID;
    public String facultyName;

    // components in the UI
    @FXML
    private FontAwesomeIcon btnEditIcon;

    @FXML
    private FontAwesomeIcon btnDeleteIcon;

    @FXML
    private Button btnViewDepartments;

    // @Menura used a Pane here, I have use a VBOX here...
    // this VBOX is used here, stage.initOwner(facultyItemVBOX.getScene().getWindow());
    @FXML
    private VBox facultyItemVBOX;

    // label text fields to be replaced by data fetched from database
    @FXML
    private Label txtFacultyName;

    @FXML
    private Label txtFacultyHead;

    @FXML
    private Label txtFacultySpecializedFor;

    @FXML
    private Label txtFacultyShortName;

    @FXML
    private Label txtFacultyStatus;

    @FXML
    private Label txtFacultyNoOfDepartment;

    // =========================================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // =========================================================================================================

    // Opens up the pop up then, user can edit and update the faculty records - EditFacultyPopUp.fxml
    public void openEditFacultyPopUp(MouseEvent mouseEvent) {
        System.out.println("Clicked - Open pop up to edit Faculty Record");

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyPopUps/editFacultyPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            EditFacultyPopUpController editFacultyPopUpController = fxmlLoader.getController();
            editFacultyPopUpController.getFacultyInstance(facultyInstance);

            stage.setTitle("Edit Faculty Details");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(facultyItemVBOX.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception - When Opening editFacultyPopUp.fxml as a pop up ");
            e.printStackTrace();
        }
    }

    // Opens up the Alert BOX then, user can Delete the faculty records - _____.fxml
    public void openDeleteFacultyConfirmationAlertBoxPopUp(MouseEvent mouseEvent) throws IOException {
        System.out.println("Clicked - Open Confirmation AlertBOX before deleting a Faculty Record");

        Alert deleteFacultyAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteFacultyAlert.setTitle("Confirmation");
        deleteFacultyAlert.setHeaderText("Give Confirmation to delete this Faculty");
        deleteFacultyAlert.setContentText("Do you want to delete the faculty?\nClick Delete to Delete the faculty,\nOtherwise click Cancel");

        ButtonType DeleteBtn = new ButtonType("Delete");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        deleteFacultyAlert.getButtonTypes().setAll(DeleteBtn,CancelBtn);

        Optional<ButtonType> result = deleteFacultyAlert.showAndWait();
        if (result.get() == DeleteBtn){
            deleteFacultyRecord(this.facultyInstance.getId());
        } else {
            System.out.println("Clicked Cancel Button - (Deleting a faculty)");
        }
    }


    /**
     * open department screen as a pop by this button event,
     * we have to pass the faculty id when we open the pop up.
     * @param actionEvent
     */
    public void openDepartmentScreen(ActionEvent actionEvent) {

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Departments/Departments.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            DepartmentsController departmentsController = fxmlLoader.getController();
            departmentsController.getFacultyIdFromFacultyScreen(this.facultyID,this.facultyName);

            Stage stage = new Stage();

            stage.setTitle("Departments");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(facultyItemVBOX.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening Departments.fxml as a pop up ==========================");
            e.printStackTrace();
        }
    }

    public void showInformation(Faculty faculty){
        this.facultyInstance = faculty; // facultyInstance holds the faculty obj here, then we use that when we edit or delete it
        txtFacultyName.setText(faculty.getName());
        txtFacultyShortName.setText(faculty.getShortName());
        txtFacultyHead.setText(faculty.getHead());
        txtFacultyNoOfDepartment.setText("Working on that");
        txtFacultySpecializedFor.setText(faculty.getSpecializedFor());
        txtFacultyStatus.setText(faculty.getStatus());
        facultyID = faculty.getId();
        facultyName = faculty.getName();
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
     * This method is used to delete a faculty.
     * faculty_delete_status is set to Y in order to mark that record is deleted.
     * It is done as a database good practise.
     * Any record is not deleted from the database.
     */
    public void deleteFacultyRecord(int facultyID) throws IOException {

        // delete query
        String query = "UPDATE `faculty` SET faculty_delete_status = 'Y' WHERE faculty_id = "+facultyID+"";

        // execute the insert query
        executeQuery(query);

        /* // ERROR in this code segment
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyDepartments.fxml"));
        loader.load();
        FacultyDepartmentsController facultyDepartmentsController = loader.getController();
        facultyDepartmentsController.populateAndRefreshFacultyDataRow();
         */

        System.out.println("Faculty is deleted successfully");

    }

}
