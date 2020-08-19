package TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationsHallsInsideBuildingsItem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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

public class LocationsHallsInsideBuildingsItemController implements Initializable {

    @FXML
    private VBox locationsHallsInsideItemVBOX;

    @FXML
    private ComboBox<String> onActionsHallsComboBox;

    @FXML
    private Button btnViewHallTimeTable;


    @FXML
    private FontAwesomeIcon btnEditIcon;

    @FXML
    private FontAwesomeIcon btnPrintIcon;

    @FXML
    private FontAwesomeIcon btnDeleteIcon;

    // ======================================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // filter by combobox
        onActionsHallsComboBox.getItems().addAll(
                "More options",
                "Add suitable room(s) for Lecturer",
                "Add suitable room(s) for Group",
                "Add suitable room(s) for Session",
                "Add consecutive sessions in the same room",
                "Add time that room can not be reserved",
                "Blah Blah"
        );
        onActionsHallsComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown

    }

    // ======================================================================================

    public void openTimeTableOfTheHall(MouseEvent mouseEvent) {
        System.out.println("Clicked - Open Time Table of the Hall or Lab");
    }

    public void openEditHallPopUp(MouseEvent mouseEvent) {
        System.out.println("Clicked - Open pop up to edit Locations - Hall/ Labs Record");

        // should open the pop up to edit  the Hall / Lab

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsHallsInsideBuildings/LocationsHallsInsideBuildingsPopUps/editLocationsHallsPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Edit Hall/Lab");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(locationsHallsInsideItemVBOX.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening editLocationsHallsPopUp.fxml as a pop up ==========================");
            e.printStackTrace();
        }

    }

    public void openDeleteHallConfirmationAlertBoxPopUp(MouseEvent mouseEvent) {
        System.out.println("Clicked - Open Confirmation AlertBOX before deleting a Hall/ Labs Record");

        Alert deleteFacultyAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteFacultyAlert.setTitle("Confirmation");
        deleteFacultyAlert.setHeaderText("Give Confirmation to delete this Hall / Lab");
        deleteFacultyAlert.setContentText("Do you want to delete the Hall / Lab? \nClick Delete to Delete the Hall / Lab, \nOtherwise click Cancel");

        ButtonType DeleteBtn = new ButtonType("Delete");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        deleteFacultyAlert.getButtonTypes().setAll(DeleteBtn,CancelBtn);

        Optional<ButtonType> result = deleteFacultyAlert.showAndWait();
        if (result.get() == DeleteBtn){
            System.out.println("Hall/ Lab is deleted successfully");
        } else {
            System.out.println("Clicked Cancel Button - (Deleting a Hall/ Labs)");
        }
    }

    public void printTimeTableOfTheHall(MouseEvent mouseEvent) {
        System.out.println("Clicked - print Locations - Hall/ Labs Record");
    }
}
