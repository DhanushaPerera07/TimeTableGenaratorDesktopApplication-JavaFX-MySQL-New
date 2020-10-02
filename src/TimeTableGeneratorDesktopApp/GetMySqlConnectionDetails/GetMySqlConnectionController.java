package TimeTableGeneratorDesktopApp.GetMySqlConnectionDetails;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GetMySqlConnectionController implements Initializable {


    String portNo,user,password;

    @FXML
    private TextField txtPortNo;

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSet;

    @FXML
    private Button btnDefaults;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void setOnActionSetConnectionSettings(ActionEvent event) {

        try {
            int portNumber = Integer.parseInt(txtPortNo.getText().trim());
            portNo = Integer.toString(portNumber);
            user = txtUser.getText().trim();
            password = txtPassword.getText().trim();

            if (portNo.equals("") || user.equals("") || password.equals("")){
                new Alert(Alert.AlertType.ERROR,"Error: Invalid configurations.\nRe-check and enter valid inputs.\nAll fields are required!").show();
            } else {
                DatabaseConnection.portNo = portNo;
                DatabaseConnection.user = user;
                DatabaseConnection.password = password;

                closeAddMySQLConfigurationsPopUpForm();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR,"Error: Invalid configurations.\nPort Number should be a number\nRe-check and enter valid inputs.\nAll fields are required!").show();
            e.printStackTrace();
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Error: Invalid configurations.\nRe-check and enter valid inputs.\nAll fields are required!").show();
            e.printStackTrace();
        }
    }

    @FXML
    void setOnActionSetDefaults(ActionEvent event) {
        portNo = "3306";
        txtPortNo.setText(portNo);
    }

    private void closeAddMySQLConfigurationsPopUpForm() {
        // just used the txtFacultyName here to close the pop up when the record insertion is successfully done.

        Stage stage = (Stage) txtPortNo.getScene().getWindow();
        System.out.println("Succeed setting up MySQL configurations - closing pop up form");
        stage.close();

        new Alert(Alert.AlertType.INFORMATION,"Configurations are set successfully !").show();

    }

}
