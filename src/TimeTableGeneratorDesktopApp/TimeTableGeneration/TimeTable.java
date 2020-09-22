package TimeTableGeneratorDesktopApp.TimeTableGeneration;

public class TimeTable {

    private int Id;
    private String timeSlot;
    private String Module;
    private String tag;
    private String Hall;
    private String group;
    private String lecturer;
    private String sessionId;

    public TimeTable(int id, String timeSlot, String module, String tag, String hall, String group, String lecturer, String sessionId) {
        Id = id;
        this.timeSlot = timeSlot;
        Module = module;
        this.tag = tag;
        Hall = hall;
        this.group = group;
        this.lecturer = lecturer;
        this.sessionId = sessionId;
    }

    public int getId() {
        return Id;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public String getModule() {
        return Module;
    }

    public String getTag() {
        return tag;
    }

    public String getHall() {
        return Hall;
    }

    public String getGroup() {
        return group;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getSessionId() {
        return sessionId;
    }
}
