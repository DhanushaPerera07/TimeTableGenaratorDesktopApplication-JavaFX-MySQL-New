package TimeTableGeneratorDesktopApp.Sessions;

public class Sessions {

    private int sessionID;
    private String sessionModule;
    private String sessionModuleCode;
    private String sessionTag;
    private String sessionGroupID;
    private int sessionStudentCount;
    private int sessionDuration;
    private String sessionGenID;


    public Sessions(int sessionID, String sessionModule, String sessionModuleCode, String sessionTag, String sessionGroupID, int sessionStudentCount, int sessionDuration, String sessionGenID) {
        this.sessionID = sessionID;
        this.sessionModule = sessionModule;
        this.sessionModuleCode = sessionModuleCode;
        this.sessionTag = sessionTag;
        this.sessionGroupID = sessionGroupID;
        this.sessionStudentCount = sessionStudentCount;
        this.sessionDuration = sessionDuration;
        this.sessionGenID = sessionGenID;
    }

    public String getSessionGenID() {
        return sessionGenID;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }


    public String getSessionModule() {
        return sessionModule;
    }

    public void setSessionModule(String sessionModule) {
        this.sessionModule = sessionModule;
    }

    public String getSessionModuleCode() {
        return sessionModuleCode;
    }

    public void setSessionModuleCode(String sessionModuleCode) {
        this.sessionModuleCode = sessionModuleCode;
    }

    public String getSessionTag() {
        return sessionTag;
    }

    public void setSessionTag(String sessionTag) {
        this.sessionTag = sessionTag;
    }

    public String getSessionGroupID() {
        return sessionGroupID;
    }

    public void setSessionGroupID(String sessionGroupID) {
        this.sessionGroupID = sessionGroupID;
    }

    public int getSessionStudentCount() {
        return sessionStudentCount;
    }

    public void setSessionStudentCount(int sessionStudentCount) {
        this.sessionStudentCount = sessionStudentCount;
    }

    public int getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(int sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    @Override
    public String toString() {
        return "Sessions{" +
                "sessionID=" + sessionID +
                ", sessionModule='" + sessionModule + '\'' +
                ", sessionModuleCode='" + sessionModuleCode + '\'' +
                ", sessionTag='" + sessionTag + '\'' +
                ", sessionGroupID='" + sessionGroupID + '\'' +
                ", sessionStudentCount=" + sessionStudentCount +
                ", sessionDuration=" + sessionDuration +
                ", sessionGenID='" + sessionGenID + '\'' +
                '}';
    }
}
