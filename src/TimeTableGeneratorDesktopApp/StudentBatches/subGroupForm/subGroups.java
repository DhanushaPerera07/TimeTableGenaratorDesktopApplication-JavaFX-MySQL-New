package TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm;

public class subGroups {
    private int id;
    private String subGroupId;
    private int NofStudents;
    private int batchID;

    public subGroups(int id, String subGroupId, int nofStudents, int batchID) {
        this.id = id;
        this.subGroupId = subGroupId;
        NofStudents = nofStudents;
        this.batchID = batchID;
    }

    public subGroups() {

    }


    public int getId() {
        return id;
    }

    public String getSubGroupId() {
        return subGroupId;
    }

    public int getNofStudents() {
        return NofStudents;
    }

    public int getBatchID() {
        return batchID;
    }
}
