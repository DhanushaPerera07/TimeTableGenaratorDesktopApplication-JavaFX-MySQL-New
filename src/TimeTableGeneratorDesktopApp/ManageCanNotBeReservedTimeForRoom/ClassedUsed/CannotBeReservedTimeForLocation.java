package TimeTableGeneratorDesktopApp.ManageCanNotBeReservedTimeForRoom.ClassedUsed;

import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import TimeTableGeneratorDesktopApp.TimePeriods.TimeSlots.TimeSlot;

public class CannotBeReservedTimeForLocation {

    private String dayName;
    private TimeSlot timeSlot;
    private LocationHallLab locationHallLab;
    private Boolean status_true;
    //private CannotBeReservedTimeForLocationTM cannotBeReservedTimeForLocationTM;

    public CannotBeReservedTimeForLocation() {
    }

    public CannotBeReservedTimeForLocation(String dayName, TimeSlot timeSlot, LocationHallLab locationHallLab, Boolean status_true) {
        this.dayName = dayName;
        this.timeSlot = timeSlot;
        this.locationHallLab = locationHallLab;
        this.status_true = status_true;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public LocationHallLab getLocationHallLab() {
        return locationHallLab;
    }

    public void setLocationHallLab(LocationHallLab locationHallLab) {
        this.locationHallLab = locationHallLab;
    }

    public Boolean getStatus_true() {
        return status_true;
    }

    public void setStatus_true(Boolean status_true) {
        this.status_true = status_true;
    }


    @Override
    public String toString() {
        return "CannotBeReservedTimeForLocation{" +
                "dayName='" + dayName + '\'' +
                ", timeSlot=" + timeSlot +
                ", locationHallLab=" + locationHallLab +
                ", status_true=" + status_true +
                '}';
    }
}
