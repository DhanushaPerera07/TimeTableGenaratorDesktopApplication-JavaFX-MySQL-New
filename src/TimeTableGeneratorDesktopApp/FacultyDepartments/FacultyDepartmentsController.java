package TimeTableGeneratorDesktopApp.FacultyDepartments;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;
import TimeTableGeneratorDesktopApp.DatabaseHelper.FacultyDatabaseHelper;
import TimeTableGeneratorDesktopApp.FacultyDepartments.FacultyItem.FacultyItemController;
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
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class FacultyDepartmentsController implements  Initializable{

    // Logger
    public static final Logger log = Logger.getLogger(FacultyDepartmentsController.class.getName());

/*    public static Boolean variable = false;*/

    //variables used to hold values
    String searchText;

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
    private Label lblFilterBy;

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

        initializeComboBoxes();
        populateRows();


    } // end - initialize

    // ------------------------------------------------------------------------------------------------------------------

    /**
     * this method initialize the combobox(s) with the provided values
     */
    private void initializeComboBoxes() {

        // hide unwanted UI components
        btnSearchFaculty.setVisible(false);
        lblFilterBy.setVisible(false);
        facultyFilterByComboBox.setVisible(false);
        facultyMoreComboBox.setVisible(false);


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

        facultySearchTxtBox.textProperty().addListener((v,oldValue,newValue) -> {
            if(newValue.trim().equals("") || newValue == null){
                populateRows();
            } else {
                populateRowsAccordingToSearchBoxValue(newValue);
            }
        });

    }


    private void populateRows(){

        facultyVBox.getChildren().clear();

        FacultyDatabaseHelper  facultyDatabaseHelper = new FacultyDatabaseHelper();
        ObservableList<Faculty> facultyList = facultyDatabaseHelper.getFacultyList();

        for (Faculty faculty : facultyList){
            // sysout check
            //System.out.println("faculty table rec: " + faculty.toString());
            log.info("faculty table rec: " + faculty.toString());
        }

        /**\
         * Dynamically change the rows by getting data from the database
         * facultyItem.fxml is used as the UI, it acts as a customized data row
         * I pass the faculty object to the facultyItem.fxml and populate the view
         */
        // Populate the rows like a table
        Node [] nodes = new Node[facultyList.size()];

        if (facultyList.size() != 0) {
            for (int i = 0; i < facultyList.size(); i++) {
                try {
                    //nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItem.fxml"));
                    //facultyVBox.getChildren().addAll(nodes[i]);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItem.fxml"));
                    //Parent newRoot = loader.load();
                    //Scene scene = new Scene(newRoot);
                    nodes[i] = (Node) loader.load();
                    FacultyItemController facultyItemController = loader.getController();
                    facultyItemController.showInformation(facultyList.get(i));
                    //facultyItemController = nodes[i].getController;
                    //nodes[i] = (Node) loader.load();

                    facultyVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - FacultyItem Loading ======================================");
                    e.printStackTrace();
                }
            }
        } else {
            // this means that no faculty is found
            // so we gonna display that no faculties are found
            Node nodeThatSaysNoFacultyFound;
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItemNoContent.fxml"));
                nodeThatSaysNoFacultyFound = (Node) loader.load();
                facultyVBox.getChildren().addAll(nodeThatSaysNoFacultyFound);
            } catch (IOException e) {
                System.out.println("Error - FacultyItemNoContent Loading ======================================");
                e.printStackTrace();
            }
        }
    }//


    // --------------------------------------------------------------------------------------------------------

    private void populateRowsAccordingToSearchBoxValue(String value) {

        facultyVBox.getChildren().clear();

        FacultyDatabaseHelper  facultyDatabaseHelper = new FacultyDatabaseHelper();
        ObservableList<Faculty> facultyList = facultyDatabaseHelper.getFacultyList(value);

        for (Faculty faculty : facultyList){
            // sysout check
            //System.out.println("faculty table rec: " + faculty.toString());
            log.info("faculty table rec: " + faculty.toString());
        }

        /**\
         * Dynamically change the rows by getting data from the database
         * facultyItem.fxml is used as the UI, it acts as a customized data row
         * I pass the faculty object to the facultyItem.fxml and populate the view
         */
        // Populate the rows like a table
        Node [] nodes = new Node[facultyList.size()];

        if (facultyList.size() != 0) {
            for (int i = 0; i < facultyList.size(); i++) {
                try {
                    //nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItem.fxml"));
                    //facultyVBox.getChildren().addAll(nodes[i]);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItem.fxml"));
                    //Parent newRoot = loader.load();
                    //Scene scene = new Scene(newRoot);
                    nodes[i] = (Node) loader.load();
                    FacultyItemController facultyItemController = loader.getController();
                    facultyItemController.showInformation(facultyList.get(i));
                    //facultyItemController = nodes[i].getController;
                    //nodes[i] = (Node) loader.load();

                    facultyVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - FacultyItem Loading ======================================");
                    e.printStackTrace();
                }
            }
        } else {
            // this means that no faculty is found
            // so we gonna display that no faculties are found
            Node nodeThatSaysNoFacultyFound;
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItemNoContent.fxml"));
                nodeThatSaysNoFacultyFound = (Node) loader.load();
                facultyVBox.getChildren().addAll(nodeThatSaysNoFacultyFound);
            } catch (IOException e) {
                System.out.println("Error - FacultyItemNoContent Loading ======================================");
                e.printStackTrace();
            }
        }

    }//


    // --------------------------------------------------------------------------------------------------------


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

    // calling database helper and get the database connection
    DatabaseHelper databaseHelper = new DatabaseHelper();

    public void populateAndRefreshFacultyDataRow() {

        facultyVBox.getChildren().clear();

        FacultyDatabaseHelper  facultyDatabaseHelper = new FacultyDatabaseHelper();
        ObservableList<Faculty> facultyList = facultyDatabaseHelper.getFacultyList();

        for (Faculty faculty : facultyList){
            // sysout check
            //System.out.println("faculty table rec: " + faculty.toString());
            log.info("faculty table rec: " + faculty.toString());
        }

        /**\
         * Dynamically change the rows by getting data from the database
         * facultyItem.fxml is used as the UI, it acts as a customized data row
         * I pass the faculty object to the facultyItem.fxml and populate the view
         */
        // Populate the rows like a table
        Node [] nodes = new Node[facultyList.size()];

        if (facultyList.size() != 0) {
            for (int i = 0; i < facultyList.size(); i++) {
                try {
                    //nodes[i] = FXMLLoader.load(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItem.fxml"));
                    //facultyVBox.getChildren().addAll(nodes[i]);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItem.fxml"));
                    //Parent newRoot = loader.load();
                    //Scene scene = new Scene(newRoot);
                    nodes[i] = (Node) loader.load();
                    FacultyItemController facultyItemController = loader.getController();
                    facultyItemController.showInformation(facultyList.get(i));
                    //facultyItemController = nodes[i].getController;
                    //nodes[i] = (Node) loader.load();

                    facultyVBox.getChildren().addAll(nodes[i]);
                } catch (IOException e) {
                    System.out.println("Error - FacultyItem Loading ======================================");
                    e.printStackTrace();
                }
            }
        } else {
            // this means that no faculty is found
            // so we gonna display that no faculties are found
            Node nodeThatSaysNoFacultyFound;
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/TimeTableGeneratorDesktopApp/FacultyDepartments/FacultyItem/FacultyItemNoContent.fxml"));
                nodeThatSaysNoFacultyFound = (Node) loader.load();
                facultyVBox.getChildren().addAll(nodeThatSaysNoFacultyFound);
            } catch (IOException e) {
                System.out.println("Error - FacultyItemNoContent Loading ======================================");
                e.printStackTrace();
            }
        }
    }



    // -------------------------------------------------------------------------------------------------



    // refreshing and populate rows

/*    public void changeVariableStatusToTrue(){
        variable = true;
    }*/




    // --------------------------------------------------------------------------------------------------

}
