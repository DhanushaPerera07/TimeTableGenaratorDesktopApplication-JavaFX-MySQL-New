package TimeTableGeneratorDesktopApp.TimePeriods.SetWorkingDays;

public class WorkingDays {

    private int idno;
    private int noDays;

    public WorkingDays(int idno, int noDays) {
        this.idno = idno;
        this.noDays = noDays;
    }

    public int getIdno() {
        return idno;
    }

    public int getNoDays() {
        return noDays;
    }
}
