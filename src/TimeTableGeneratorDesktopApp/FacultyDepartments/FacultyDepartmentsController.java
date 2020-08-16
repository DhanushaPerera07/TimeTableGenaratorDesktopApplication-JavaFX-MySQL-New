package TimeTableGeneratorDesktopApp.FacultyDepartments;

import TimeTableGeneratorDesktopApp.Departments.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class FacultyDepartmentsController implements  Initializable{

    // components in the UI
    @FXML
    private VBox facultyVBox=null;

    // @Menura used a Pane here, but I used a boarder pane, So I take it as boarder pane
    // then, I changed
    // stage.initOwner(subjectPane.getScene().getWindow());
    // TO
    // stage.initOwner(borderPaneFacultyMain.getScene().getWindow());
    @FXML
    private BorderPane borderPaneFacultyMain;

    @FXML
    private TextField facultySearchTxtBox;

    @FXML
    private Button btnSearchFaculty;

    @FXML
    private ComboBox<String> facultyFilterByComboBox;


    @FXML
    private Button btnAddFaculty;

    @FXML
    private ComboBox<String> facultyMoreComboBox;



    // ===================== VARIABLES CREATED FOR THE DATABASE PART =================================================================

    String query = "";
    String filterType = "";
    String filterValue = "";

    // ===================== VARIABLES CREATED FOR THE DATABASE PART =================================================================



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // filter by combobox
        facultyFilterByComboBox.getItems().addAll(
                "Select ALL",
                "IT",
                "Blah Blah"
        );
        facultyFilterByComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown

        // More combobox
        facultyMoreComboBox.getItems().addAll(
                "Print",
                "Do something new",
                "Blah Blah"
        );

        // prompt text
        facultyMoreComboBox.setPromptText("More"); // I use this drop down, if I have to deal with a new function

        // Populate the rows like a table
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
    } // end - initialize

    // ADD BUTTON - opens the pop up window, so user can add a faculty
    // Action event on ADD BUTTON
    public void openAddFacultyPopUp(ActionEvent actionEvent) {
        System.out.println("Clicked - Open Add Faculty Pop Up");

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyPopUps/addFacultyPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add a Faculty");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(borderPaneFacultyMain.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception / Error - When Opening addFacultyPopUp.fxml as a pop up ==========================");
            e.printStackTrace();
        }
    }

    public void setOnActionBtnSearch(MouseEvent mouseEvent) {
        System.out.println("Clicked - Search Button on Faculty Screen");
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



    /**
     * this method is to get all the departments in the department table...
     * returns departmentList;
     * */
    public ObservableList<Faculty> getDepartmentsList() {
        ObservableList<Faculty> facultyList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        // if the filter by combo box value is set as ALL, get all the departments
        if(facultyFilterByComboBox.equals("Select ALL")){
            query = "SELECT * FROM faculty ORDER BY faculty_name";
        }else{
            query = "SELECT * FROM faculty ORDER BY faculty_name";
            // query = "SELECT * from faculty WHERE " +filterType+ " = '" +filterValue+ "'";
        }

//        String query = "SELECT * FROM department";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Faculty faculty;
            while (rs.next()) {
                faculty = new Faculty(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("shortName"),
                        rs.getInt("specializedFor"),
                        rs.getString("status"),
                        rs.getInt("head")
                );
                facultyList.add(faculty);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return facultyList;
    }


}
