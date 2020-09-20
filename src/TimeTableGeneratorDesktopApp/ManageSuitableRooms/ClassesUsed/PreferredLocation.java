package TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed;

public class PreferredLocation {

    int preferredRoomForSubjectID;
    int subjectSubjectID;
    int locationLocationID;
    int tagTagID;
    String statusTrue;

    // default constructor
    public PreferredLocation() {
    }

    public PreferredLocation(int preferredRoomForSubjectID, int subjectSubjectID, int locationLocationID, int tagTagID, String statusTrue) {
        this.preferredRoomForSubjectID = preferredRoomForSubjectID;
        this.subjectSubjectID = subjectSubjectID;
        this.locationLocationID = locationLocationID;
        this.tagTagID = tagTagID;
        this.statusTrue = statusTrue;
    }

    // setters and getters

    public int getPreferredRoomForSubjectID() {
        return preferredRoomForSubjectID;
    }

    public void setPreferredRoomForSubjectID(int preferredRoomForSubjectID) {
        this.preferredRoomForSubjectID = preferredRoomForSubjectID;
    }

    public int getSubjectSubjectID() {
        return subjectSubjectID;
    }

    public void setSubjectSubjectID(int subjectSubjectID) {
        this.subjectSubjectID = subjectSubjectID;
    }

    public int getLocationLocationID() {
        return locationLocationID;
    }

    public void setLocationLocationID(int locationLocationID) {
        this.locationLocationID = locationLocationID;
    }

    public int getTagTagID() {
        return tagTagID;
    }

    public void setTagTagID(int tagTagID) {
        this.tagTagID = tagTagID;
    }

    public String getStatusTrue() {
        return statusTrue;
    }

    public void setStatusTrue(String statusTrue) {
        this.statusTrue = statusTrue;
    }

}
