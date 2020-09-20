package TimeTableGeneratorDesktopApp.TimeTableGeneration;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class timeTableGenerationController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private Button hallViewBtn;

    @FXML
    private Button lecturerViewBtn;

    @FXML
    private Button studentViewBtn;
    @FXML
    private Pane timeTablePane;

    @FXML
    void hallView(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HallView/HallView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Time Table for Hall View");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(timeTablePane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));

            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Enter here What you want to do in the window Closing...
                        }

                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void lecturerView(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LecturerView/LecturerView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Time Table for Lecturer View");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(timeTablePane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));

            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Enter here What you want to do in the window Closing...
                        }

                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void studentView(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentView/StudentView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Time Table for Student View");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(timeTablePane.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));

            stage.show();

            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Enter here What you want to do in the window Closing...
                        }

                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
