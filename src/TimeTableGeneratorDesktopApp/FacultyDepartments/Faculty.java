package TimeTableGeneratorDesktopApp.FacultyDepartments;

public class Faculty {

    private int id;
    private String name;
    private String shortName;
    private String specializedFor;
    private String status;
    private String head; // FK

    public Faculty() {
    }

    // constructor with parameters
    public Faculty(int id, String name, String shortName, String specializedFor, String status, String head) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.specializedFor = specializedFor;
        this.status = status;
        this.head = head;
    }


    // setters and getters


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

    public String getSpecializedFor() {
        return specializedFor;
    }

    public void setSpecializedFor(String specializedFor) {
        this.specializedFor = specializedFor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    // toString method

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", specializedFor='" + specializedFor + '\'' +
                ", status='" + status + '\'' +
                ", head='" + head + '\'' +
                '}';
    }
}
