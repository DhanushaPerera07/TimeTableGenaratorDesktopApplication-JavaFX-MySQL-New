package TimeTableGeneratorDesktopApp.StudentBatches.subGroupForm;

public class batchstats {
    private int batch;
    private int nofStudents;
    private int nofGrouped;
    private int nofRemain;
    private int nofGroups;

//    public batchstats(int batchid) {
//        this.batch= batchid;
//    }

    public batchstats(int batch, int nofStudents, int nofGrouped, int nofRemain, int nofGroups) {
        this.batch = batch;
        this.nofStudents = nofStudents;
        this.nofGrouped = nofGrouped;
        this.nofRemain = nofRemain;
        this.nofGroups = nofGroups;
    }


    public int getBatch() {
        return batch;
    }

    public int getNofStudents() {
        return nofStudents;
    }

    public int getNofGrouped() {
        return nofGrouped;
    }

    public int getNofRemain() {
        return nofRemain;
    }

    public int getNofGroups() {
        return nofGroups;
    }
}
