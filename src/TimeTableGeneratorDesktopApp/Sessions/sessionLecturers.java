package TimeTableGeneratorDesktopApp.Sessions;

public class sessionLecturers {
    public String sessionID;
    public String lecturerName;


    public sessionLecturers(String sessionID, String lecturerName) {
        this.sessionID = sessionID;
        this.lecturerName = lecturerName;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getLecturerName() {
        return lecturerName;
    }
}


