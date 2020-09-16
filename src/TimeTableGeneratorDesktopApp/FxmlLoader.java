package TimeTableGeneratorDesktopApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class FxmlLoader {
    private Pane view;

    public Pane getPane(String fileName){

        try{
            URL fileURL = Main.class.getResource("/TimeTableGeneratorDesktopApp/" + fileName + ".fxml");

            System.out.println("fileURL: " + fileURL);
            System.out.println("filename: " + fileName);
            if (fileURL == null) {
                throw new java.io.FileNotFoundException("FXML File can not be found");
            }

            view = new FXMLLoader().load(fileURL);

        } catch (Exception e){
            System.out.println("No page " + fileName + ", please check FxmlLoader.java");
        }

        return view;
    }
}
