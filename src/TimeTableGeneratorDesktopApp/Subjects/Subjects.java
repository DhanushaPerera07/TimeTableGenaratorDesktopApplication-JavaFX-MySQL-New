package TimeTableGeneratorDesktopApp.Subjects;

public class Subjects {

    private int idmodule;
    private String moduleName;
    private String moduleCode;
    private String offeredYear;
    private String offeredSemester;
    private int lecHour;
    private int tuteHour;
    private int labHour;
    private int evaluationHour;

    public Subjects(int idmodule, String moduleName, String moduleCode, String offeredYear, String offeredSemester, int lecHour, int tuteHour, int labHour, int evaluationHour) {
        this.idmodule = idmodule;
        this.moduleName = moduleName;
        this.moduleCode = moduleCode;
        this.offeredYear = offeredYear;
        this.offeredSemester = offeredSemester;
        this.lecHour = lecHour;
        this.tuteHour = tuteHour;
        this.labHour = labHour;
        this.evaluationHour = evaluationHour;
    }

    public int getIdmodule() {
        return idmodule;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getOfferedYear() {
        return offeredYear;
    }

    public String getOfferedSemester() {
        return offeredSemester;
    }

    public int getLecHour() {
        return lecHour;
    }

    public int getTuteHour() {
        return tuteHour;
    }

    public int getLabHour() {
        return labHour;
    }

    public int getEvaluationHour() {
        return evaluationHour;
    }
}
