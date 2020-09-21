package TimeTableGeneratorDesktopApp.TimeTableGeneration;

public class TimeTable {

    private int sessionId;
    private String timeSlot;
    private String Module;
    private String tag;
    private String Hall;
    private String group;
    private String lecturer;

    public TimeTable(int sessionId, String timeSlot, String module, String tag, String hall, String group, String lecturer) {
        this.sessionId = sessionId;
        this.timeSlot = timeSlot;
        Module = module;
        this.tag = tag;
        Hall = hall;
        this.group = group;
        this.lecturer = lecturer;
    }

    public int getSessionId() {
        return sessionId;
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
}
