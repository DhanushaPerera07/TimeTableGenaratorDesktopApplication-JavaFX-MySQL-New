package TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyPopUps;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddFacultyPopUpController implements Initializable {

    // components
    @FXML
    private TextField txtFacultyName;

    @FXML
    private TextField txtFacultyShortName;

    @FXML
    private ComboBox<String> facultySpecializedForComboBox;

    @FXML
    private ComboBox<String> facultyHeadComboBox;

    @FXML
    private ComboBox<String> facultyStatusComboBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /** ============== COMBO BOXES ====================================================================
         */
        // facultySpecializedForComboBox combobox
        facultySpecializedForComboBox.getItems().addAll(
                "IT",
                "BM",
                "Engineering",
                "HTM"
                /*
                "Faculty of Computing",
                "Faculty of Business Management",
                "Faculty of Engineering"
                */
        );
        // prompt text
        facultySpecializedForComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown

        // facultyHeadComboBox combobox
        facultyHeadComboBox.getItems().addAll(
                "John Doe",
                "Oliver Cruise"
        );
        // prompt text
        facultyHeadComboBox.setPromptText("John Doe"); // selects the first one in the dropdown

        // facultyHeadComboBox combobox
        facultyStatusComboBox.getItems().addAll(
                "Active",
                "Not Active"
        );
        // prompt text
        facultyStatusComboBox.setPromptText("Active"); // selects the first one in the dropdown

    }

    public void ActionEventAddFacultyPopUp(ActionEvent actionEvent) {
        // pop up - add a faculty action event on ADD BUTTON
        System.out.println("clicked pop up - add a faculty action event on ADD BUTTON");
        getPermissionToAddTheRecordFromConfirmBox();

    }



    // ===================== DATABASE PART - STARTS HERE =============================================================================

    /** get the database connection here
     */
    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/timetabledb", "root","root");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: getConnection() :::: " + ex.getMessage());
            return null;
        }
    }

    /** execute the query string
     * @param query string is passed here
     * this query will execute by this method
     */
    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void getPermissionToAddTheRecordFromConfirmBox(){
        System.out.println("Clicked - Open Confirmation AlertBOX before adding a Faculty Record");

        Alert addFacultyAlert = new Alert(Alert.AlertType.CONFIRMATION);
        addFacultyAlert.setTitle("Confirmation");
        addFacultyAlert.setHeaderText("Add a new Faculty");
        addFacultyAlert.setContentText("Do you want to add the faculty?\nClick add to add the faculty,\nOtherwise click Cancel");

        ButtonType AddBtn = new ButtonType("ADD");
        ButtonType CancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        addFacultyAlert.getButtonTypes().setAll(AddBtn,CancelBtn);

        Optional<ButtonType> result = addFacultyAlert.showAndWait();
        if (result.get() == AddBtn){
            insertRecord();
            System.out.println("Faculty is added successfully");
        } else {
            System.out.println("Clicked Cancel Button - (Adding a faculty)");
        }
    }


    /**
     * This method is used to insert a new faculty
     */
    public void insertRecord(){

        /*
        String query = "INSERT INTO faculty (year,semester,intake,faculty,programme,center,noofstd,batchID) " +
                "VALUES ('" +year+ "','" +semester+ "','" +intake+ "','" +faculty+
                "','" +programme+ "','" +center+ "'," +tfNoOfStd.getText()+
                ",'" +tfBatchID.getText()+ "') ";
        */

        // local variables created to get the user input from the pop up form
        /*
        int lecturer_emp_id = 1;
        String faculty_name = "Faculty of Computing";
        String faculty_short_name = "FOC";
        String faculty_specialized_for = "IT";
        String faculty_status = "OK";
        String faculty_delete_status = "N";
        */

        // get user input
        //int lecturer_emp_id = 1;
        String faculty_name = txtFacultyName.getText();
        String faculty_short_name = txtFacultyShortName.getText();
        String faculty_specialized_for = facultySpecializedForComboBox.getValue();
        String faculty_status = facultyStatusComboBox.getValue();
        String faculty_head_name = facultyHeadComboBox.getValue();
        String faculty_delete_status = "N";

        // insert query
        String query = "INSERT INTO `faculty` (`faculty_name`,`faculty_short_name`,`faculty_specialized_for`,`faculty_status`,`faculty_head_name`,`faculty_delete_status`) VALUES ('"+faculty_name+"', '"+faculty_short_name+"', '"+faculty_specialized_for+"', '"+faculty_status+"','"+faculty_head_name+"','"+faculty_delete_status+"')";

        // execute the insert query
        executeQuery(query);
        closeAddFacultyPopUpForm();

    }

    private void closeAddFacultyPopUpForm() {
        // just used the txtFacultyName here to close the pop up when the record insertion is successfully done.

        Stage stage = (Stage) txtFacultyName.getScene().getWindow();
        System.out.println("Succeed insertion of a new faculty - closing pop up form");
        stage.close();
    }


}
