package TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed;

public class SuitableLocationForSession{

    private int suitable_room_for_session_id;
    private int location_location_id;
    private int idsession;
    private String status_true;


    // default constructor
    public SuitableLocationForSession() {
    }

    //constructor with parameter
    public SuitableLocationForSession(int suitable_room_for_session_id, int location_location_id, int idsession) {
        this.suitable_room_for_session_id = suitable_room_for_session_id;
        this.location_location_id = location_location_id;
        this.idsession = idsession;
    }

    //constructor with parameter
    public SuitableLocationForSession(int suitable_room_for_session_id, int location_location_id, int idsession, String status_true) {
        this.suitable_room_for_session_id = suitable_room_for_session_id;
        this.location_location_id = location_location_id;
        this.idsession = idsession;
        this.status_true = status_true;
    }

    // getter and setter
    public int getSuitable_room_for_session_id() {
        return suitable_room_for_session_id;
    }

    public void setSuitable_room_for_session_id(int suitable_room_for_session_id) {
        this.suitable_room_for_session_id = suitable_room_for_session_id;
    }

    public int getLocation_location_id() {
        return location_location_id;
    }

    public void setLocation_location_id(int location_location_id) {
        this.location_location_id = location_location_id;
    }

    public int getIdsession() {
        return idsession;
    }

    public void setIdsession(int idsession) {
        this.idsession = idsession;
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
        return "SuitableLocationForSession{" +
                "suitable_room_for_session_id=" + suitable_room_for_session_id +
                ", location_location_id=" + location_location_id +
                ", idsession=" + idsession +
                ", status_true='" + status_true + '\'' +
                '}';
    }
}
