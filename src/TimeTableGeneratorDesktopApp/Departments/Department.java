package TimeTableGeneratorDesktopApp.Departments;

public class Department {

    private int id;
    private String name;
    private String shortName;
    private int floor;
    private String head;
    private int buildingID; // FK
    private int facultyID;  // FK

    public Department(int id, String name, String shortName, int floor, String head, int buildingID, int facultyID) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.floor = floor;
        this.head = head;
        this.buildingID = buildingID;
        this.facultyID = facultyID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public int getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(int facultyID) {
        this.facultyID = facultyID;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", floor=" + floor +
                ", head='" + head + '\'' +
                ", buildingID=" + buildingID +
                ", facultyID=" + facultyID +
                '}';
    }
}
