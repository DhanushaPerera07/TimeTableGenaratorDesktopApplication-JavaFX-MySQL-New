package TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed;

public class SuitableLocationForLecturer {

    private int suitable_room_for_lecturer_id;
    private int location_building_building_id;
    private int lecturer_lid;
    private String status_true;


    // default constructor
    public SuitableLocationForLecturer() {
    }

    // constructor with parameters
    public SuitableLocationForLecturer(int suitable_room_for_lecturer_id, int location_building_building_id, int lecturer_lid) {
        this.suitable_room_for_lecturer_id = suitable_room_for_lecturer_id;
        this.location_building_building_id = location_building_building_id;
        this.lecturer_lid = lecturer_lid;
    }

    // constructor with parameters
    public SuitableLocationForLecturer(int suitable_room_for_lecturer_id, int location_building_building_id, int lecturer_lid, String status_true) {
        this.suitable_room_for_lecturer_id = suitable_room_for_lecturer_id;
        this.location_building_building_id = location_building_building_id;
        this.lecturer_lid = lecturer_lid;
        this.status_true = status_true;
    }


    //setters and getters


    public int getSuitable_room_for_lecturer_id() {
        return suitable_room_for_lecturer_id;
    }

    public void setSuitable_room_for_lecturer_id(int suitable_room_for_lecturer_id) {
        this.suitable_room_for_lecturer_id = suitable_room_for_lecturer_id;
    }

    public int getLocation_building_building_id() {
        return location_building_building_id;
    }

    public void setLocation_building_building_id(int location_building_building_id) {
        this.location_building_building_id = location_building_building_id;
    }

    public int getLecturer_lid() {
        return lecturer_lid;
    }

    public void setLecturer_lid(int lecturer_lid) {
        this.lecturer_lid = lecturer_lid;
    }

    public String getStatus_true() {
        return status_true;
    }

    public void setStatus_true(String status_true) {
        this.status_true = status_true;
    }

    @Override
    public String toString() {
        return "SuitableLocationForLecturer{" +
                "suitable_room_for_lecturer_id=" + suitable_room_for_lecturer_id +
                ", location_building_building_id=" + location_building_building_id +
                ", lecturer_lid=" + lecturer_lid +
                ", status_true=" + status_true +
                '}';
    }
}
