package TimeTableGeneratorDesktopApp.DatabaseHelper;

public class DashboardDatabaseHelper extends DatabaseHelper {

    public int countStudentBatches(){

        StudentBatchesDatabaseHelper studentBatchesDatabaseHelper = new StudentBatchesDatabaseHelper();

        return studentBatchesDatabaseHelper.getStudentBatchCount();
    }

    public int countStudentSubgroups(){
        StudentBatchesDatabaseHelper studentBatchesDatabaseHelper = new StudentBatchesDatabaseHelper();

        return studentBatchesDatabaseHelper.getStudentSubGroupCount();
    }

    public int countLecturers(){
        LecturerDatabaseHelper lecturerDatabaseHelper = new LecturerDatabaseHelper();
        return  lecturerDatabaseHelper.getLecturerCount();
    }

    public int countSubjects(){
        ModulesDatabaseHelper modulesDatabaseHelper = new ModulesDatabaseHelper();
        return  modulesDatabaseHelper.getSubjectCount();
    }

    public int countLectureHalls(){
        LocationHallLabDatabaseHelper locationHallLabDatabaseHelper = new LocationHallLabDatabaseHelper();
        return  locationHallLabDatabaseHelper.getLectureHallCount();
    }

    public int countTutorialHalls(){
        LocationHallLabDatabaseHelper locationHallLabDatabaseHelper = new LocationHallLabDatabaseHelper();
        return  locationHallLabDatabaseHelper.getTutorialHallCount();
    }

    public int countLabs(){
        LocationHallLabDatabaseHelper locationHallLabDatabaseHelper = new LocationHallLabDatabaseHelper();
        return  locationHallLabDatabaseHelper.getLabCount();
    }

    public int countFaculties(){

        FacultyDatabaseHelper facultyDatabaseHelper = new FacultyDatabaseHelper();

        return facultyDatabaseHelper.getFacultyCount();
    }


    public int countDepartments(){
        DepartmentDatabaseHelper departmentDatabaseHelper = new DepartmentDatabaseHelper();
        return  departmentDatabaseHelper.getDepartmentCount();
    }


}
