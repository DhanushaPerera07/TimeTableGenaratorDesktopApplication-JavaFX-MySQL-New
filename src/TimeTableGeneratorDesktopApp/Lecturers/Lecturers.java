package TimeTableGeneratorDesktopApp.Lecturers;

public class Lecturers {

    private int lid;
    private int lecturerID;
    private String lecturerName;
    private String lecturerFaculty;
    private String lecturerDepartment;
    private String lecturerCenter;
    private String lecturerBuilding;
    private int lecturerLevel;
    private String lecturerRank;

    public Lecturers(int lid, int lecturerID, String lecturerName, String lecturerFaculty, String lecturerDepartment, String lecturerCenter, String lecturerBuilding, int lecturerLevel, String lecturerRank) {
        this.lid = lid;
        this.lecturerID = lecturerID;
        this.lecturerName = lecturerName;
        this.lecturerFaculty = lecturerFaculty;
        this.lecturerDepartment = lecturerDepartment;
        this.lecturerCenter = lecturerCenter;
        this.lecturerBuilding = lecturerBuilding;
        this.lecturerLevel = lecturerLevel;
        this.lecturerRank = lecturerRank;
    }

    public int getLid() {
        return lid;
    }

    public int getLecturerID() {
        return lecturerID;
    }

    public String getLecturerName() {
        return lecturerName;
    }



    public String getLecturerFaculty() {
        return lecturerFaculty;
    }

    public String getLecturerDepartment() {
        return lecturerDepartment;
    }

    public String getLecturerCenter() {
        return lecturerCenter;
    }

    public String getLecturerBuilding() {
        return lecturerBuilding;
    }

    public int getLecturerLevel() {
        return lecturerLevel;
    }

    public String getLecturerRank() {
        return lecturerRank;
    }
}
