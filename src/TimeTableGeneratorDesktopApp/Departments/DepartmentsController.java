package TimeTableGeneratorDesktopApp.Departments;

import TimeTableGeneratorDesktopApp.StudentBatches.StudentBatches;
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

public class DepartmentsController implements Initializable {

    @FXML
    private BorderPane borderPaneDepartmentMain;

    @FXML
    private TextField departmentSearchTxtBox;

    @FXML
    private Button btnSearchDepartment;

    @FXML
    private ComboBox<String> departmentFilterByComboBox;

    @FXML
    private Button btnAddDepartment;

    @FXML
    private ComboBox<String> departmentMoreComboBox;

    @FXML
    private VBox DepartmentsVBox;


    // ===================== VARIABLES CREATED FOR THE DATABASE PART =================================================================

    String query = "";
    String filterType = "faculty_id";
    String filterValue = "";

    // ===================== VARIABLES CREATED FOR THE DATABASE PART =================================================================


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // filter by combobox
        departmentFilterByComboBox.getItems().addAll(
                "Select ALL",
                "IT",
                "Blah Blah"
        );
        departmentFilterByComboBox.getSelectionModel().selectFirst(); // selects the first one in the dropdown

        // More combobox
        departmentMoreComboBox.getItems().addAll(
                "Print",
                "Do something new",
                "Blah Blah"
        );

        // prompt text
        departmentMoreComboBox.setPromptText("More"); // I use this drop down, if I have to deal with a new function

        // Populate the rows like a table
        Node[] nodes = new Node[10];

        for (int i = 0;i< nodes.length;i++){
            try {
                nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/Departments/DepartmentsItem/DepartmentItem.fxml"));
                DepartmentsVBox.getChildren().add(nodes[i]);
            } catch (IOException e) {
                System.out.println("Error - DepartmentItem Loading ======================================");
                e.printStackTrace();
            }
        }

    }

    public void setOnActionBtnSearch(MouseEvent mouseEvent) {
    }

    public void openAddDepartmentPopUp(MouseEvent mouseEvent) {

        System.out.println("Clicked - Open pop up to edit Department Record");

        // open up the POP UP
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TimeTableGeneratorDesktopApp/Departments/DepartmentsPopUps/addDepartmentPopUp.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Add Department");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(borderPaneDepartmentMain.getScene().getWindow());
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (Exception e){
            System.out.println("Exception - When Opening addDepartmentPopUp.fxml as a pop up ");
            e.printStackTrace();
        }

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
     * @param query (String) is passed here
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
    public ObservableList<Department> getDepartmentsList() {
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        // if the filter by combo box value is set as ALL, get all the departments
        if(departmentFilterByComboBox.equals("Select ALL")){
            query = "SELECT * FROM department ORDER BY department_name";
        }else{
            query = "SELECT * from department WHERE " +filterType+ " = '" +filterValue+ "'";
        }

//        String query = "SELECT * FROM department";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Department department;
            while (rs.next()) {
                department = new Department(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("shortName"),
                        rs.getInt("floor"),
                        rs.getString("head"),
                        rs.getInt("buildingID"),
                        rs.getInt("facultyID")
                );
                departmentList.add(department);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            ex.printStackTrace();
        }
        return departmentList;
    }



}
