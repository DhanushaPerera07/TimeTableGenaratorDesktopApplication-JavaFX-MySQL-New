package TimeTableGeneratorDesktopApp.LocationsLabsHalls.LocationsBuildingItem;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LocationsBuildingItemController implements Initializable {

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

    }

    public void openDeleteHallConfirmationAlertBoxPopUp(MouseEvent mouseEvent) {
        System.out.println("Clicked - Open Confirmation AlertBOX before deleting a Hall/ Labs Record");

        Alert deleteFacultyAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteFacultyAlert.setTitle("Confirmation");
        deleteFacultyAlert.setHeaderText("Give Confirmation to delete this Hall / Lab");
        deleteFacultyAlert.setContentText("Do you want to delete the Hall / Lab? Click Delete to Delete the Hall / Lab, otherwise click Cancel");

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
