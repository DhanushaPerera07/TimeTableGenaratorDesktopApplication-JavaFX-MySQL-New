package TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation;

public class ConsecutiveSessionInSameLocation {

    private int consecutive_session_in_same_room_id;
    private int location_location_id;
    private String consecutive_session_id;
    private int consecutive_session_int_id;
    private String status_true;


    // default constructor
    public ConsecutiveSessionInSameLocation() {
    }

    // constructor with parameters

    public ConsecutiveSessionInSameLocation(int consecutive_session_in_same_room_id, int location_location_id, String consecutive_session_id) {
        this.consecutive_session_in_same_room_id = consecutive_session_in_same_room_id;
        this.location_location_id = location_location_id;
        this.consecutive_session_id = consecutive_session_id;
    }


    // constructor with parameters

    public ConsecutiveSessionInSameLocation(int consecutive_session_in_same_room_id, int location_location_id, String consecutive_session_id, String status_true) {
        this.consecutive_session_in_same_room_id = consecutive_session_in_same_room_id;
        this.location_location_id = location_location_id;
        this.consecutive_session_id = consecutive_session_id;
        this.status_true = status_true;
    }


    // constructor with parameters

    public ConsecutiveSessionInSameLocation(int consecutive_session_in_same_room_id, int location_location_id, int consecutive_session_int_id, String status_true) {
        this.consecutive_session_in_same_room_id = consecutive_session_in_same_room_id;
        this.location_location_id = location_location_id;
        this.consecutive_session_int_id = consecutive_session_int_id;
        this.status_true = status_true;
    }


    // getter and setter


    public int getConsecutive_session_in_same_room_id() {
        return consecutive_session_in_same_room_id;
    }

    public void setConsecutive_session_in_same_room_id(int consecutive_session_in_same_room_id) {
        this.consecutive_session_in_same_room_id = consecutive_session_in_same_room_id;
    }

    public int getLocation_location_id() {
        return location_location_id;
    }

    public void setLocation_location_id(int location_location_id) {
        this.location_location_id = location_location_id;
    }

    public String getConsecutive_session_id() {
        return consecutive_session_id;
    }

    public void setConsecutive_session_id(String consecutive_session_id) {
        this.consecutive_session_id = consecutive_session_id;
    }


    // int
    public int getConsecutive_session_int_id() {
        return consecutive_session_int_id;
    }

    public void setConsecutive_session_int_id(int consecutive_session_int_id) {
        this.consecutive_session_int_id = consecutive_session_int_id;
    }

    public String getStatus_true() {
        return status_true;
    }

    public void setStatus_true(String status_true) {
        this.status_true = status_true;
    }


    //toString


    @Override
    public String toString() {
        return "ConsecutiveSessionInSameLocation{" +
                "consecutive_session_in_same_room_id=" + consecutive_session_in_same_room_id +
                ", location_location_id=" + location_location_id +
                ", consecutive_session_id='" + consecutive_session_id + '\'' +
                ", consecutive_session_int_id=" + consecutive_session_int_id +
                ", status_true='" + status_true + '\'' +
                '}';
    }
}
