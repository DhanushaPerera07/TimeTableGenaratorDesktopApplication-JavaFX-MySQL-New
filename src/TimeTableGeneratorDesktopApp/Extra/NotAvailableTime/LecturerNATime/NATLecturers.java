package TimeTableGeneratorDesktopApp.Extra.NotAvailableTime.LecturerNATime;

public class NATLecturers {
    private int id;
    private String lecID;
    private String Day;
    private String Hour;

    public NATLecturers(int id, String lecID, String day, String hour) {
        this.id = id;
        this.lecID = lecID;
        Day = day;
        Hour = hour;
    }

    public int getId() {
        return id;
    }

    public String getLecID() {
        return lecID;
    }

    public String getDay() {
        return Day;
    }

    public String getHour() {
        return Hour;
    }
}
