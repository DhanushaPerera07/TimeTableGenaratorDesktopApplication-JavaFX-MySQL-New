package TimeTableGeneratorDesktopApp.LocationsLabsHalls;

public class Building {

    private int buildingID;
    private String buildingName;
    private int buildingNoOfFloors;
    private int buildingCapacity;
    private String buildingCenter;
    private String buildingCondition;
    private String buildingSpecializedFor;
    private int buildingNoOfLectureHalls;
    private int buildingNoOfTutorialHalls;
    private int buildingNoOfLabs;
    private int facultyFacultyId;


    // default constructors
    public Building() {
    }

    // constructors
    public Building(int buildingID, String buildingName, int buildingNoOfFloors, int buildingCapacity, String buildingCenter, String buildingCondition, String buildingSpecializedFor, int buildingNoOfLectureHalls, int buildingNoOfTutorialHalls, int buildingNoOfLabs, int facultyFacultyId) {
        this.buildingID = buildingID;
        this.buildingName = buildingName;
        this.buildingNoOfFloors = buildingNoOfFloors;
        this.buildingCapacity = buildingCapacity;
        this.buildingCenter = buildingCenter;
        this.buildingCondition = buildingCondition;
        this.buildingSpecializedFor = buildingSpecializedFor;
        this.buildingNoOfLectureHalls = buildingNoOfLectureHalls;
        this.buildingNoOfTutorialHalls = buildingNoOfTutorialHalls;
        this.buildingNoOfLabs = buildingNoOfLabs;
        this.facultyFacultyId = facultyFacultyId;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getBuildingNoOfFloors() {
        return buildingNoOfFloors;
    }

    public void setBuildingNoOfFloors(int buildingNoOfFloors) {
        this.buildingNoOfFloors = buildingNoOfFloors;
    }

    public int getBuildingCapacity() {
        return buildingCapacity;
    }

    public void setBuildingCapacity(int buildingCapacity) {
        this.buildingCapacity = buildingCapacity;
    }

    public String getBuildingCenter() {
        return buildingCenter;
    }

    public void setBuildingCenter(String buildingCenter) {
        this.buildingCenter = buildingCenter;
    }

    public String getBuildingCondition() {
        return buildingCondition;
    }

    public void setBuildingCondition(String buildingCondition) {
        this.buildingCondition = buildingCondition;
    }

    public String getBuildingSpecializedFor() {
        return buildingSpecializedFor;
    }

    public void setBuildingSpecializedFor(String buildingSpecializedFor) {
        this.buildingSpecializedFor = buildingSpecializedFor;
    }

    public int getBuildingNoOfLectureHalls() {
        return buildingNoOfLectureHalls;
    }

    public void setBuildingNoOfLectureHalls(int buildingNoOfLectureHalls) {
        this.buildingNoOfLectureHalls = buildingNoOfLectureHalls;
    }

    public int getBuildingNoOfTutorialHalls() {
        return buildingNoOfTutorialHalls;
    }

    public void setBuildingNoOfTutorialHalls(int buildingNoOfTutorialHalls) {
        this.buildingNoOfTutorialHalls = buildingNoOfTutorialHalls;
    }

    public int getBuildingNoOfLabs() {
        return buildingNoOfLabs;
    }

    public void setBuildingNoOfLabs(int buildingNoOfLabs) {
        this.buildingNoOfLabs = buildingNoOfLabs;
    }

    public int getFacultyFacultyId() {
        return facultyFacultyId;
    }

    public void setFacultyFacultyId(int facultyFacultyId) {
        this.facultyFacultyId = facultyFacultyId;
    }

    // toString
    @Override
    public String toString() {
        return "Building{" +
                "buildingID=" + buildingID +
                ", buildingName='" + buildingName + '\'' +
                ", buildingNoOfFloors=" + buildingNoOfFloors +
                ", buildingCapacity=" + buildingCapacity +
                ", buildingCenter='" + buildingCenter + '\'' +
                ", buildingCondition='" + buildingCondition + '\'' +
                ", buildingSpecializedFor='" + buildingSpecializedFor + '\'' +
                ", buildingNoOfLectureHalls=" + buildingNoOfLectureHalls +
                ", buildingNoOfTutorialHalls=" + buildingNoOfTutorialHalls +
                ", buildingNoOfLabs=" + buildingNoOfLabs +
                ", facultyFacultyId=" + facultyFacultyId +
                '}';
    }
}
