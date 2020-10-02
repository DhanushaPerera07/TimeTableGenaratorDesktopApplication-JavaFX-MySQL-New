package TimeTableGeneratorDesktopApp.ManageCanNotBeReservedTimeForRoom.ClassedUsed;

public class CannotBeReservedTimeForLocationTM {

    private int cannot_be_reserved_time_for_location_id;
    private String day;
    private int timeslot_id;
    private int location_location_id;
    private Boolean status_true;

    // default constructor
    public CannotBeReservedTimeForLocationTM() {
    }

    public CannotBeReservedTimeForLocationTM(int cannot_be_reserved_time_for_location_id, String day, int timeslot_id, int location_location_id) {
        this.cannot_be_reserved_time_for_location_id = cannot_be_reserved_time_for_location_id;
        this.day = day;
        this.timeslot_id = timeslot_id;
        this.location_location_id = location_location_id;
    }

    public CannotBeReservedTimeForLocationTM(int cannot_be_reserved_time_for_location_id, String day, int timeslot_id, int location_location_id, Boolean status_true) {
        this.cannot_be_reserved_time_for_location_id = cannot_be_reserved_time_for_location_id;
        this.day = day;
        this.timeslot_id = timeslot_id;
        this.location_location_id = location_location_id;
        this.status_true = status_true;
    }

    public int getCannot_be_reserved_time_for_location_id() {
        return cannot_be_reserved_time_for_location_id;
    }

    public void setCannot_be_reserved_time_for_location_id(int cannot_be_reserved_time_for_location_id) {
        this.cannot_be_reserved_time_for_location_id = cannot_be_reserved_time_for_location_id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getTimeslot_id() {
        return timeslot_id;
    }

    public void setTimeslot_id(int timeslot_id) {
        this.timeslot_id = timeslot_id;
    }

    public int getLocation_location_id() {
        return location_location_id;
    }

    public void setLocation_location_id(int location_location_id) {
        this.location_location_id = location_location_id;
    }

    public Boolean getStatus_true() {
        return status_true;
    }

    public void setStatus_true(Boolean status_true) {
        this.status_true = status_true;
    }

    @Override
    public String toString() {
        return "CannotBeReservedTimeForLocationTM{" +
                "cannot_be_reserved_time_for_location_id=" + cannot_be_reserved_time_for_location_id +
                ", day='" + day + '\'' +
                ", timeslot_id=" + timeslot_id +
                ", location_location_id=" + location_location_id +
                ", status_true=" + status_true +
                '}';
    }
}
