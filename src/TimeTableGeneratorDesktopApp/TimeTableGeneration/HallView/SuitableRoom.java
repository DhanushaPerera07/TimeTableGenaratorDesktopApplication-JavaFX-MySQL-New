package TimeTableGeneratorDesktopApp.TimeTableGeneration.HallView;

public class SuitableRoom {

    private String lecturer;
    private String location;

    public SuitableRoom(String lecturer, String location) {
        this.lecturer = lecturer;
        this.location = location;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getLocation() {
        return location;
    }
}
