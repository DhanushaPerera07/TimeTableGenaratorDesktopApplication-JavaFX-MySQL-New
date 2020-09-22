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
    private GridPane gridSession;


    @FXML
    void setOnActionGridTag(MouseEvent event) {
        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Extra/AddSuitableLocations/showAllTags.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add suitable location for a tag");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening showAllTags.fxml as a pop up ==========================");
            e.printStackTrace();
        }

    }


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
    void setOnActionGridSession(MouseEvent event) {
        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Extra/AddSuitableLocations/showAllSessions.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add suitable location for session tag");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening showAllSessions.fxml as a pop up ==========================");
            e.printStackTrace();
        }

    }


    @FXML
    void setOnActionGridLecturer(MouseEvent event) {
        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Extra/AddSuitableLocations/showAllLecturers.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add suitable location for a lecturer");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening showAllLecturers.fxml as a pop up ==========================");
            e.printStackTrace();
        }

    }

    @FXML
    void setOnActionGridStudentGroup(MouseEvent event) {

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Extra/AddSuitableLocations/showAllStudentGroups.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add suitable location for a student groups");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening showAllStudentGroups.fxml as a pop up ==========================");
            e.printStackTrace();
        }

    }

    @FXML
    void setOnActionGridStudentSubGroup(MouseEvent event) {

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Extra/AddSuitableLocations/showAllStudentSubGroups.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add suitable location for a student sub-group");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening showAllStudentSubGroups.fxml as a pop up ==========================");
            e.printStackTrace();
        }

    }

}
