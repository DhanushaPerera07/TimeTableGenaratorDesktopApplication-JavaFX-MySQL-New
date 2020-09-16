package TimeTableGeneratorDesktopApp.Extra.NotAvailableTime.GroupNATime.setNATimeGroup;

public class NATimeGroups {
    private int id;
    public String batchID;
    public String day;
    public String hour;

    public NATimeGroups(int id, String batchID, String day, String hour) {
        this.id = id;
        this.batchID = batchID;
        this.day = day;
        this.hour = hour;
    }

    public int getId() {
        return id;
    }

    public String getBatchID() {
        return batchID;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }
}
