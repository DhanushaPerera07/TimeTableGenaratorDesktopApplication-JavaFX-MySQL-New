package TimeTableGeneratorDesktopApp.TimePeriods.TimeSlots;

public class TimeSlot {

    private int slotsID;
    private double range_t;
    private String value_t;


    //default constructor
    public TimeSlot() {
    }

    public TimeSlot(int slotsID, double range_t, String value_t) {
        this.slotsID = slotsID;
        this.range_t = range_t;
        this.value_t = value_t;
    }


    public int getSlotsID() {
        return slotsID;
    }

    public void setSlotsID(int slotsID) {
        this.slotsID = slotsID;
    }

    public double getRange_t() {
        return range_t;
    }

    public void setRange_t(double range_t) {
        this.range_t = range_t;
    }

    public String getValue_t() {
        return value_t;
    }

    public void setValue_t(String value_t) {
        this.value_t = value_t;
    }
}
