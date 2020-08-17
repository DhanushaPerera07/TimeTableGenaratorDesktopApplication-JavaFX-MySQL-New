package TimeTableGeneratorDesktopApp.Departments.DepartmentsItem;

import TimeTableGeneratorDesktopApp.Departments.Department;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeptItemController implements Initializable {


    // holds the department id
    public Department departmentInstance;
    public int departmentID;

    @FXML
    private VBox departmentItemVBOX;

    @FXML
    private FontAwesomeIcon btnEditIcon;

    @FXML
    private FontAwesomeIcon btnDeleteIcon;

    @FXML
    private Label txtDepartmentName;

    @FXML
    private Label txtDepartmentUnderFacultyOf;

    @FXML
    private Label txtDepartmentHead;

    @FXML
    private Label txtDepartmentFloor;

    @FXML
    private Label txtDepartmentBuilding;

    @FXML
    private Label txtDepartmentSpecializedFor;

    @FXML
    private Label txtDepartmentShortName;


    // ============================================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // ============================================================================================================

    public void openEditDepartmentPopUp(MouseEvent mouseEvent) {

        System.out.println("Clicked - Open pop up to edit Department Record");

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Departments/DepartmentsPopUps/editDepartmentPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Edit Department Details");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(departmentItemVBOX.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception - When Opening editDepartmentPopUp.fxml as a pop up ");
            e.printStackTrace();
        }
    }

    public void openDeleteDepartmentConfirmationAlertBoxPopUp(MouseEvent mouseEvent) {

        System.out.println("Clicked - Open Confirmation AlertBOX before deleting a Department Record");

        Alert deleteFacultyAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteFacultyAlert.setTitle("Confirmation");
        deleteFacultyAlert.setHeaderText("Give Confirmation to delete this Department");
        deleteFacultyAlert.setContentText("Do you want to delete the Department?\nClick Delete to Delete the Department,\nOtherwise click Cancel");

        ButtonType DeleteBtn = new ButtonType("Delete");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        deleteFacultyAlert.getButtonTypes().setAll(DeleteBtn,CancelBtn);

        Optional<ButtonType> result = deleteFacultyAlert.showAndWait();
        if (result.get() == DeleteBtn){
            System.out.println("Department is deleted successfully");
        } else {
            System.out.println("Clicked Cancel Button - (Deleting a Department)");
        }
    }


    /**
     * We get the department instance as the parameter from DepartmentsController.java and
     * we update the DepartmentItem.fxml UI using those data
     * @param department
     */
    public void showInformation(Department department) {
        // we get the department obj as the parameter.

        // hold the department obj/instance, then we can use this instance when we open the edit pop up
        this.departmentInstance = department;
        this.departmentID = department.getId();

        // using those data we update the DepartmentItem.fxml
        txtDepartmentName.setText(department.getName());
        txtDepartmentShortName.setText(department.getShortName());
        txtDepartmentHead.setText(department.getHead());
        txtDepartmentFloor.setText(Integer.toString(department.getFloor()));
        txtDepartmentBuilding.setText(Integer.toString(department.getBuildingID()));
        txtDepartmentSpecializedFor.setText(department.getSpecializedFor());
        txtDepartmentUnderFacultyOf.setText(Integer.toString(department.getId()));
        
    }
}
