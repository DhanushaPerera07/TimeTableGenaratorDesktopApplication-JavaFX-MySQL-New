package TimeTableGeneratorDesktopApp.Extra.NotAvailableTime;

import TimeTableGeneratorDesktopApp.FxmlLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NotAvailableTimeController implements Initializable {
    @FXML
    Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void handleBackButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("clicked NA sessions back Button");
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPane("TimeTableGeneratorDesktopApp/Extra/Extra");
        pane.getChildren().add(view);
    }

    @FXML
    void ActionEventLecturerSection(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LecturerNATime/LectureNATime.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Lecturer not available time");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.getIcons().add(new Image("TimeTableGeneratorDesktopApp/icons/student.png"));
            stage.show();

        }catch (Exception e){
            System.out.println("can't load new window");
            e.printStackTrace();
        }
    }


    @FXML
    void ActionEventSessionSection(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SessionNATime/SessionNATime.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Sessions not available time");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.getIcons().add(new Image("TimeTableGeneratorDesktopApp/icons/student.png"));
            stage.show();

        }catch (Exception e){
            System.out.println("can't load new window");
            e.printStackTrace();
        }
    }

    @FXML
    void ActionEventGroupsSection(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GroupNATime/GroupNATime.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Groups not available time");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1,800,600));
            stage.getIcons().add(new Image("TimeTableGeneratorDesktopApp/icons/student.png"));
            stage.show();


        }catch (Exception e){
            System.out.println("can't load new window");
            e.printStackTrace();
        }
    }

    @FXML
    void ActionEventsubGroupSection(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SubGroupNATime/SubGroupNATime.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Sub Groups not available time");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(pane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.getIcons().add(new Image("TimeTableGeneratorDesktopApp/icons/student.png"));
            stage.show();

        }catch (Exception e){
            System.out.println("can't load new window");
            e.printStackTrace();
        }
    }

}
