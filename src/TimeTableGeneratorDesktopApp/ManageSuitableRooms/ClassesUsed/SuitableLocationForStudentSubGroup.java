package TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed;

public class SuitableLocationForStudentSubGroup {
    private int suitable_room_for_student_subgroups_id;
    private int location_location_id;
    private int subgroups_id;
    private String status_true;

    //default constructor
    public SuitableLocationForStudentSubGroup() {
    }

    // constructor with parameters
    public SuitableLocationForStudentSubGroup(int suitable_room_for_student_subgroups_id, int location_location_id, int subgroups_id) {
        this.suitable_room_for_student_subgroups_id = suitable_room_for_student_subgroups_id;
        this.location_location_id = location_location_id;
        this.subgroups_id = subgroups_id;
    }


    // constructor with parameters
    public SuitableLocationForStudentSubGroup(int suitable_room_for_student_subgroups_id, int location_location_id, int subgroups_id, String status_true) {
        this.suitable_room_for_student_subgroups_id = suitable_room_for_student_subgroups_id;
        this.location_location_id = location_location_id;
        this.subgroups_id = subgroups_id;
        this.status_true = status_true;
    }


    // getter and setter


    public int getSuitable_room_for_student_subgroups_id() {
        return suitable_room_for_student_subgroups_id;
    }

    public void setSuitable_room_for_student_subgroups_id(int suitable_room_for_student_subgroups_id) {
        this.suitable_room_for_student_subgroups_id = suitable_room_for_student_subgroups_id;
    }

    public int getLocation_location_id() {
        return location_location_id;
    }

    public void setLocation_location_id(int location_location_id) {
        this.location_location_id = location_location_id;
    }

    public int getSubgroups_id() {
        return subgroups_id;
    }

    public void setSubgroups_id(int subgroups_id) {
        this.subgroups_id = subgroups_id;
    }

    public String getStatus_true() {
        return status_true;
    }

    public void setStatus_true(String status_true) {
        this.status_true = status_true;
    }

    @Override
    public String toString() {
        return "SuitableLocationForStudentSubGroup{" +
                "suitable_room_for_student_subgroups_id=" + suitable_room_for_student_subgroups_id +
                ", location_location_id=" + location_location_id +
                ", subgroups_id=" + subgroups_id +
                ", status_true='" + status_true + '\'' +
                '}';
    }
}
