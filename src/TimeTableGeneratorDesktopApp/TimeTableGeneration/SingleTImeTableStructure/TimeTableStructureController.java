package TimeTableGeneratorDesktopApp.TimeTableGeneration.SingleTImeTableStructure;

import TimeTableGeneratorDesktopApp.ManageSuitableRooms.Location;
import TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm.subGroups;
import TimeTableGeneratorDesktopApp.TimePeriods.SetWorkingDays.WorkingDays;
import TimeTableGeneratorDesktopApp.TimePeriods.TimeSlots.TimeSlot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TimeTableStructureController implements Initializable {

    subGroups subGroup;
    int subGroupID;

    @FXML
    private VBox TimeTableVBox;

    @FXML
    private Label structureTblHeader;

    @FXML
    private TableView<?> TimeTableStructureTbl;

    @FXML
    private TableColumn<TimeSlot, String> StructureTimeSlots;

    @FXML
    private TableColumn<WorkingDays, String> StrructureC1;

    @FXML
    private TableColumn<WorkingDays, String> StrructureC2;

    @FXML
    private TableColumn<WorkingDays, String> StrructureC3;

    @FXML
    private TableColumn<WorkingDays, String> StrructureC4;

    @FXML
    private TableColumn<WorkingDays, String> StrructureC5;

    @FXML
    private TableColumn<WorkingDays, String> StrructureC6;

    @FXML
    private TableColumn<WorkingDays, String> StrructureC7;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showSubGroups(subGroups subGroup) {

        this.subGroup = subGroup;
//        this.subGroupID = subGroupID;

        System.out.println("test location: " + this.subGroup.toString());
        structureTblHeader.setText(subGroup.getSubGroupId());
    }
}
