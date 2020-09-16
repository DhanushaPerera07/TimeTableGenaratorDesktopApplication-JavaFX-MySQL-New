package TimeTableGeneratorDesktopApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("TimeTableGeneratorMainView.fxml"));
        primaryStage.setTitle("Time Table Generator -  Code 4V2");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("TimeTableGeneratorDesktopApp/icons/mainIcon.png"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
