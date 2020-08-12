package TimeTableGeneratorDesktopApp.FacultyDepartments;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FacultyDepartmentsController implements  Initializable{

    // components in the UI
    @FXML
    private VBox facultyVBox=null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Node [] nodes = new Node[10];

        for (int i = 0;i< nodes.length;i++){
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItem.fxml"));
                facultyVBox.getChildren().add(nodes[i]);
            } catch (IOException e) {
                System.out.println("Error - FacultyItem Loading ======================================");
                e.printStackTrace();
            }
        }
    }
}
