package TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyItem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FacultyItemController implements Initializable {

    // components in the UI
    @FXML
    private FontAwesomeIcon btnEditIcon;

    @FXML
    private FontAwesomeIcon btnDeleteIcon;

    @FXML
    private Button btnViewDepartments;

    @FXML
    private VBox facultyItemVBOX;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

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
    }
}
