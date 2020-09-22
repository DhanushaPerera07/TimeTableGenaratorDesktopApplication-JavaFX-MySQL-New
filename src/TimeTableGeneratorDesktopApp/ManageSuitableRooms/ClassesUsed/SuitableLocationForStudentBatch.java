package TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed;

public class SuitableLocationForStudentBatch {

    private int suitable_room_for_student_batch_id;
    private int location_location_id;
    private int studentbatches_id;
    private String status_true;

    // default constructor
    public SuitableLocationForStudentBatch() {
    }

    // constructor with parameter
    public SuitableLocationForStudentBatch(int suitable_room_for_student_batch_id, int location_location_id, int studentbatches_id) {
        this.suitable_room_for_student_batch_id = suitable_room_for_student_batch_id;
        this.location_location_id = location_location_id;
        this.studentbatches_id = studentbatches_id;
    }

    // constructor with parameter
    public SuitableLocationForStudentBatch(int suitable_room_for_student_batch_id, int location_location_id, int studentbatches_id, String status_true) {
        this.suitable_room_for_student_batch_id = suitable_room_for_student_batch_id;
        this.location_location_id = location_location_id;
        this.studentbatches_id = studentbatches_id;
        this.status_true = status_true;
    }

    //setter and getter
    public int getSuitable_room_for_student_batch_id() {
        return suitable_room_for_student_batch_id;
    }

    public void setSuitable_room_for_student_batch_id(int suitable_room_for_student_batch_id) {
        this.suitable_room_for_student_batch_id = suitable_room_for_student_batch_id;
    }

    public int getLocation_location_id() {
        return location_location_id;
    }

    public void setLocation_location_id(int location_location_id) {
        this.location_location_id = location_location_id;
    }

    public int getStudentbatches_id() {
        return studentbatches_id;
    }

    public void setStudentbatches_id(int studentbatches_id) {
        this.studentbatches_id = studentbatches_id;
    }

    public String getStatus_true() {
        return status_true;
    }

    public void setStatus_true(String status_true) {
        this.status_true = status_true;
    }

    @Override
    public String toString() {
        return "SuitableLocationForStudentBatch{" +
                "suitable_room_for_student_batch_id=" + suitable_room_for_student_batch_id +
                ", location_location_id=" + location_location_id +
                ", studentbatches_id=" + studentbatches_id +
                ", status_true='" + status_true + '\'' +
                '}';
    }
}
