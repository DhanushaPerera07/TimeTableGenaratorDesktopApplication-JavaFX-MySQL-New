package TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyItem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FacultyItemController implements Initializable {

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
    public void openDeleteFacultyConfirmationAlertBoxPopUp(MouseEvent mouseEvent) {
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
            System.out.println("Faculty is deleted successfully");
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
}
