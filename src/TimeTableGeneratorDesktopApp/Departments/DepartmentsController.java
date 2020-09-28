package TimeTableGeneratorDesktopApp.Departments;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DepartmentDatabaseHelper;
import TimeTableGeneratorDesktopApp.Departments.DepartmentsItem.DeptItemController;
import TimeTableGeneratorDesktopApp.Departments.DepartmentsPopUps.AddDeptPopUpController;
import TimeTableGeneratorDesktopApp.FacultyDepartments.Faculty;
import TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyItem.FacultyItemController;
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
import javafx.scene.control.Label;
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

    // holds the value retrieved from the Faculty
    public int facultyID = 0;
    public String facultyName = "";

    @FXML
    private BorderPane borderPaneDepartmentMain;

    @FXML
    private Label lblFilterBy;

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

    @FXML
    private Label txtHeaderFaculty;


    // ===================== VARIABLES CREATED FOR THE DATABASE PART =================================================================

    String query = "";
    String filterType = "faculty_id";
    String filterValue = "";

    // ===================== VARIABLES CREATED FOR THE DATABASE PART =================================================================


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        initializeComboBoxes();


    }


    public void initializeComboBoxes(){

        // some UI components are disabled
        lblFilterBy.setVisible(false);
        departmentFilterByComboBox.setVisible(false);
        departmentMoreComboBox.setVisible(false);
        btnSearchDepartment.setVisible(false);

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

        departmentSearchTxtBox.textProperty().addListener((v,oldValue,newValue) -> {
            if(newValue.trim().equals("") || newValue == null){
                populateRows();
            } else {
                populateRowsAccordingToSearchBoxValue(newValue);
            }
        });
    }


    DepartmentDatabaseHelper departmentDatabaseHelper = new DepartmentDatabaseHelper();

    private void populateRows() {

        DepartmentsVBox.getChildren().clear();

        ObservableList<Department> departmentsList = departmentDatabaseHelper.getDepartmentsList(this.facultyID);

        for (Department department : departmentsList){
            // sysout check
            System.out.println("faculty table rec: " + department.toString());
        }

        /**\
         * Dynamically change the rows by getting data from the database
         * facultyItem.fxml is used as the UI, it acts as a customized data row
         * I pass the faculty object to the facultyItem.fxml and populate the view
         */
        // Populate the rows like a table
        Node[] nodes = new Node[departmentsList.size()];

        if(departmentsList.size() != 0) {
            for (int i = 0; i < departmentsList.size(); i++) {
                try {
                    //nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/Departments/DepartmentsItem/DepartmentItem.fxml"));
                    //DepartmentsVBox.getChildren().add(nodes[i]);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/Departments/DepartmentsItem/DepartmentItem.fxml"));

                    nodes[i] = (Node) loader.load();
                    DeptItemController deptItemController = loader.getController();
                    deptItemController.showInformation(departmentsList.get(i),this.facultyID,this.facultyName);

                    DepartmentsVBox.getChildren().addAll(nodes[i]);

                } catch (IOException e) {
                    System.out.println("Error - DepartmentItem Loading ======================================");
                    e.printStackTrace();
                }
            }
        } else {
            // that means departmentList is empty, so no departments to display
            // then we have to display that no departments found to display
            System.out.println("Departments - No Department Found to display");
            try {
                Node nodeSaysThatDepartmentListIsEmpty;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/Departments/DepartmentsItem/DepartmentItemNoContent.fxml"));
                nodeSaysThatDepartmentListIsEmpty = (Node) loader.load();
                DepartmentsVBox.getChildren().addAll(nodeSaysThatDepartmentListIsEmpty);

            } catch (IOException e) {
                System.out.println("Error - DepartmentItemNoContent Loading ======================================");
                e.printStackTrace();
            }
        }

    }//


    private void populateRowsAccordingToSearchBoxValue(String newValue) {

        DepartmentsVBox.getChildren().clear();

        ObservableList<Department> departmentsList = departmentDatabaseHelper.getDepartmentListByDepartmentName(newValue,this.facultyID);

        for (Department department : departmentsList){
            // sysout check
            System.out.println("faculty table rec: " + department.toString());
        }

        /**\
         * Dynamically change the rows by getting data from the database
         * facultyItem.fxml is used as the UI, it acts as a customized data row
         * I pass the faculty object to the facultyItem.fxml and populate the view
         */
        // Populate the rows like a table
        Node[] nodes = new Node[departmentsList.size()];

        if(departmentsList.size() != 0) {
            for (int i = 0; i < departmentsList.size(); i++) {
                try {
                    //nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/Departments/DepartmentsItem/DepartmentItem.fxml"));
                    //DepartmentsVBox.getChildren().add(nodes[i]);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/Departments/DepartmentsItem/DepartmentItem.fxml"));

                    nodes[i] = (Node) loader.load();
                    DeptItemController deptItemController = loader.getController();
                    deptItemController.showInformation(departmentsList.get(i),this.facultyID,this.facultyName);

                    DepartmentsVBox.getChildren().addAll(nodes[i]);

                } catch (IOException e) {
                    System.out.println("Error - DepartmentItem Loading ======================================");
                    e.printStackTrace();
                }
            }
        } else {
            // that means departmentList is empty, so no departments to display
            // then we have to display that no departments found to display
            System.out.println("Departments - No Department Found to display");
            try {
                Node nodeSaysThatDepartmentListIsEmpty;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/Departments/DepartmentsItem/DepartmentItemNoContent.fxml"));
                nodeSaysThatDepartmentListIsEmpty = (Node) loader.load();
                DepartmentsVBox.getChildren().addAll(nodeSaysThatDepartmentListIsEmpty);

            } catch (IOException e) {
                System.out.println("Error - DepartmentItemNoContent Loading ======================================");
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

            // pass necessary details to AddDeptPopUpController
            AddDeptPopUpController addDeptPopUpController = fxmlLoader.getController();
            addDeptPopUpController.getNecessaryDetails(this.facultyID,this.facultyName);

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

    /**
     * This method will get the faculty ID and faculty name from the faculty screen
     * then we can use this id to show the related departments under than faculty.
     * @param facultyID
     * @param facultyName
     */
    public  void getFacultyIdFromFacultyScreen(int facultyID,String facultyName){
        // find the relevant department under that faculty ID
        this.facultyID = facultyID;
        this.facultyName = facultyName;
        txtHeaderFaculty.setText(facultyName);
        if(facultyName==null || facultyName=="") {
            System.out.println("Department - Error: Faculty name is null or empty");
        }else {
            System.out.println("Department - Faculty Name shows as the header successfully");
        }

        populateRows();
    }



    // ===================== DATABASE PART - STARTS HERE =============================================================================


    /**
     * this method is to get all the departments in the department table...
     * returns departmentList;
     * *//*
    public ObservableList<Department> getDepartmentsList() {
        ObservableList<Department> departmentList = FXCollections.observableArrayList();
        Connection conn = getConnection();

        // if the filter by combo box value is set as ALL, get all the departments
        if(departmentFilterByComboBox.equals("Select ALL")){
            // query = "SELECT * FROM department ORDER BY department_name";
            query = "SELECT * FROM department WHERE department_delete_status = 'N' AND faculty_faculty_id = "+this.facultyID+" ORDER BY department_name";
        }else{
            query = "SELECT * FROM department WHERE department_delete_status = 'N' AND faculty_faculty_id = "+this.facultyID+" ORDER BY department_name";
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
                        rs.getInt("department_id"),
                        rs.getString("department_name"),
                        rs.getString("department_short_name"),
                        rs.getInt("department_floor_no"),
                        rs.getString("department_specialized_for"),
                        rs.getString("department_head"),
                        rs.getInt("department_building_id"),
                        rs.getInt("faculty_faculty_id")
                );
                departmentList.add(department);
            }

        } catch (Exception ex) {
            // if an error occurs print an error...
            System.out.println("Error - When department data retrieving ");
            ex.printStackTrace();
        }
        return departmentList;
    }*/



}
