package TimeTableGeneratorDesktopApp.LocationsLabsHalls.LocationsBuildingItem;

import TimeTableGeneratorDesktopApp.Controller;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class LocationsBuildingItemController implements Initializable {

    @FXML
    private VBox locationsBuildingItemVBOX;

    @FXML
    private ComboBox<String> onActionsBuildingComboBox;

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
        onActionsBuildingComboBox.getItems().addAll(
                "More options",
                "Add time that room can not be reserved",
                "Blah Blah"
        );
        onActionsBuildingComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown

    }

    // ======================================================================================




    public void openEditBuildingPopUp(MouseEvent mouseEvent) {
        System.out.println("Clicked - Open pop up to edit Locations - Building Record");

        // should open the pop up to edit Building

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsLabsHalls/LocationsBuildingPopUps/editLocationsBuildingPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Edit Building");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(locationsBuildingItemVBOX.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening editLocationsBuildingPopUp.fxml as a pop up ==========================");
            e.printStackTrace();
        }

    }

    public void openDeleteBuildingConfirmationAlertBoxPopUp(MouseEvent mouseEvent) {
        System.out.println("Clicked - Open Confirmation AlertBOX before deleting a Building Record");

        Alert deleteFacultyAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteFacultyAlert.setTitle("Confirmation");
        deleteFacultyAlert.setHeaderText("Give Confirmation to delete this Building");
        deleteFacultyAlert.setContentText("Do you want to delete the Building? \nClick Delete to Delete the Building, \nOtherwise click Cancel");

        ButtonType DeleteBtn = new ButtonType("Delete");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        deleteFacultyAlert.getButtonTypes().setAll(DeleteBtn,CancelBtn);

        Optional<ButtonType> result = deleteFacultyAlert.showAndWait();
        if (result.get() == DeleteBtn){
            System.out.println("Building is deleted successfully");
        } else {
            System.out.println("Clicked Cancel Button - (Deleting a Building)");
        }
    }


    public void openHallsLabsUI(MouseEvent mouseEvent) {

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/LocationsHallsInsideBuildings/LocationsHallsInsideBuildings.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Departments");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(locationsBuildingItemVBOX.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening LocationsHallsInsideBuildings.fxml as a pop up ==========================");
            e.printStackTrace();
        }


        // right hand side of the screen should be updated with the view of LocationsHallsInsideBuildings.fxml
        // then, LocationsHallsInsideBuildings.fxml lists out the corresponding halls and labs

        /*
        Controller controller = new Controller();
        controller.goToHallsAndLabsUI(10);

         */

        /*
        System.out.println("clicked View - Halls/Labs Button. Building ID: ");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("LocationsHallsInsideBuildings/LocationsHallsInsideBuildings");
        mainPane.setCenter(view);
        */

    }
}
