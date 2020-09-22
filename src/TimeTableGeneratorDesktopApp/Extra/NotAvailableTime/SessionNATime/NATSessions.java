package TimeTableGeneratorDesktopApp.Extra.NotAvailableTime.SessionNATime;

public class NATSessions {
    private int sessionID;
    private String sessionGenID;
    private String Day;
    private String Hour;


    public NATSessions(int sessionID, String sessionGenID, String day, String hour) {
        this.sessionID = sessionID;
        this.sessionGenID = sessionGenID;
        Day = day;
        Hour = hour;
    }


    public int getSessionID() {
        return sessionID;
    }

    public String getSessionGenID() {
        return sessionGenID;
    }

    public String getDay() {
        return Day;
    }

    public String getHour() {
        return Hour;
    }
}
