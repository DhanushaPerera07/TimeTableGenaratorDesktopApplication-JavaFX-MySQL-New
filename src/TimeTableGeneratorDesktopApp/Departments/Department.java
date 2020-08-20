package TimeTableGeneratorDesktopApp.Departments;

public class Department {

    private int id;
    private String name;
    private String shortName;
    private int floor;
    private String specializedFor;
    private String head;
    private int buildingID; // FK
    private int facultyID;  // FK

    // constructor

    public Department(int id, String name, String shortName, int floor, String specializedFor, String head, int buildingID, int facultyID) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.floor = floor;
        this.specializedFor = specializedFor;
        this.head = head;
        this.buildingID = buildingID;
        this.facultyID = facultyID;
    }

    // getter and setter

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

    public String getSpecializedFor() {
        return specializedFor;
    }

    public void setSpecializedFor(String specializedFor) {
        this.specializedFor = specializedFor;
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


    /**
     * to String method
     * @return
     */
    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", floor=" + floor +
                ", specializedFor='" + specializedFor + '\'' +
                ", head='" + head + '\'' +
                ", buildingID=" + buildingID +
                ", facultyID=" + facultyID +
                '}';
    }
}
