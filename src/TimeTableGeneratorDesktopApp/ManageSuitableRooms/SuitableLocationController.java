package TimeTableGeneratorDesktopApp.ManageSuitableRooms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SuitableLocationController {

    @FXML
    private Pane pane;

    @FXML
    private GridPane gridTag;

    @FXML
    private GridPane gridSubjectAndTag;

    @FXML
    void setOnActionGridSubjectAndTag(MouseEvent event) {
        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Extra/AddSuitableLocations/showAllSubjects.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add suitable location for subject and relevant tag");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening showAllSubjects.fxml as a pop up ==========================");
            e.printStackTrace();
        }
    }

    @FXML
    void setOnActionGridTag(MouseEvent event) {

    }
}
