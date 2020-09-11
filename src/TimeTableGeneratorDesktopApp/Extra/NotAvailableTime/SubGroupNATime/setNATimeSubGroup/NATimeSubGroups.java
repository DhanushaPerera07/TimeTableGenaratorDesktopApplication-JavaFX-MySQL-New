package TimeTableGeneratorDesktopApp.Extra.NotAvailableTime.SubGroupNATime.setNATimeSubGroup;

public class NATimeSubGroups {
    private int id;
    private String batchID;
    private String subGroupID;
    private String Day;
    private String Hour;

    public NATimeSubGroups(int id, String batchID, String subGroupID, String day, String hour) {
        this.id = id;
        this.batchID = batchID;
        this.subGroupID = subGroupID;
        Day = day;
        Hour = hour;
    }

    public int getId() {
        return id;
    }

    public String getBatchID() {
        return batchID;
    }

    public String getSubGroupID() {
        return subGroupID;
    }

    public String getDay() {
        return Day;
    }

    public String getHour() {
        return Hour;
    }
}
