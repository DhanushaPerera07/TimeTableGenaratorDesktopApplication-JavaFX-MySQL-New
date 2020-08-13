package TimeTableGeneratorDesktopApp.StudentBatches;

public class StudentBatches {
    private int id;
    private String year;
    private String semester;
    private String intake;
    private String faculty;
    private String programme;
    private String center;
    private int noofstd;
    private String batchID;


    public StudentBatches(int id, String year, String semester, String intake, String faculty, String programme, String center, int noofstd, String batchID) {
        this.id = id;
        this.year = year;
        this.semester = semester;
        this.intake = intake;
        this.faculty = faculty;
        this.programme = programme;
        this.center = center;
        this.noofstd = noofstd;
        this.batchID = batchID;
    }


    public int getId() {
        return id;
    }

    public String getYear() {
        return year;
    }

    public String getSemester() {
        return semester;
    }

    public String getIntake() {
        return intake;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getProgramme() {
        return programme;
    }

    public String getCenter() {
        return center;
    }
    public String getBatchID() {
        return batchID;
    }

    public int getNoofstd() {
        return noofstd;
    }
}
