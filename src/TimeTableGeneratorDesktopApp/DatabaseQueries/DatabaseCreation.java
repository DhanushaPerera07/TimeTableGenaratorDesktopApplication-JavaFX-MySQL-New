package TimeTableGeneratorDesktopApp.DatabaseQueries;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseConnection;
import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;


public class DatabaseCreation extends DatabaseHelper {

   /* // ===================== DATABASE PART - STARTS HERE =============================================================================

    *//**
     * get the database connection here
     *//*
    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:"+ DatabaseConnection.portNo +"/", ""+ DatabaseConnection.user +"", ""+ DatabaseConnection.password+"");
            return conn;
        } catch (Exception ex) {
            System.out.println("Error: getConnection() :::: " + ex.getMessage());
            return null;
        }
    }

    *//**
     * execute the query string
     *
     * @param query string is passed here
     *              this query will execute by this method
     *//*
    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/

    // ===================== DATABASE PART - STARTS HERE =============================================================================

    String query1 = "SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;\n";
    String query2 = "SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;";
    String query3 = "SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';";
    String query4 = "CREATE SCHEMA IF NOT EXISTS `"+DatabaseConnection.databaseName+"` DEFAULT CHARACTER SET utf8 ;\n";
    String query5 = "USE `"+DatabaseConnection.databaseName+"` ;";

    String query6 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`faculty` (\n" +
            "  `faculty_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `faculty_name` VARCHAR(60) NULL,\n" +
            "  `faculty_short_name` VARCHAR(10) NULL," +
            "  `faculty_specialized_for` VARCHAR(45) NULL," +
            "  `faculty_status` VARCHAR(10) NULL DEFAULT 'OK'," +
            "  `faculty_head_name` VARCHAR(45) NULL," +
            "  `faculty_delete_status` VARCHAR(2) NULL DEFAULT 'N'," +
           // "  `faculty_timestamp` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
           // "  `faculty_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP," +
            "  PRIMARY KEY (`faculty_id`))" +
            "ENGINE = InnoDB;";


    String query7 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`department` (\n" +
            "  `department_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `department_name` VARCHAR(60) NULL COMMENT 'department name',\n" +
            "  `department_short_name` VARCHAR(15) NULL COMMENT 'short name of the department',\n" +
            "  `department_floor_no` INT NULL COMMENT 'which floor the department is at',\n" +
            "  `department_specialized_for` VARCHAR(45) NULL,\n" +
            "  `department_head` VARCHAR(45) NULL COMMENT 'Lecturers ID is linked here as FK. this means the head of the department.',\n" +
            "  `department_building_id` INT NULL COMMENT 'FK - to find the building of the department',\n" +
            "  `department_delete_status` VARCHAR(2) NULL COMMENT 'possiable values are:\\nY = Yes --> Mark the Record as deleted\\nN = No --> Mark the Record not as deleted',\n" +
            "  `faculty_faculty_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`department_id`)" +
            ");";

    String query8 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`building` (\n" +
            "  `building_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `building_name` VARCHAR(45) NULL,\n" +
            "  `building_no_of_floors` INT NULL DEFAULT 1,\n" +
            "  `building_capacity` INT NULL,\n" +
            "  `building_center` VARCHAR(45) NULL,\n" +
            "  `building_condition` VARCHAR(20) NULL,\n" +
            "  `building_specialized_for` VARCHAR(45) NULL,\n" +
            "  `building_no_of_lecture_halls` INT NULL,\n" +
            "  `building_no_of_tutorial_halls` INT NULL,\n" +
            "  `building_no_of_labs` INT NULL,\n" +
            "  `building_delete_status` VARCHAR(2) NULL COMMENT 'possiable values:\\nY = Yes --> Mark as deleted\\nN= No --? Not deleted',\n" +
            "  `faculty_faculty_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`building_id`)" +
            ");";

    String query9 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`location` (\n" +
            "  `location_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `location_name` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `location_capacity` INT NULL DEFAULT NULL,\n" +
            "  `location_floor` INT NULL DEFAULT NULL,\n" +
            "  `location_condition` VARCHAR(10) NULL DEFAULT NULL,\n" +
            "  `location_delete_status` VARCHAR(3) NULL DEFAULT NULL,\n" +
            "  `building_building_id` INT NOT NULL,\n" +
            "  `tag_tag_id` INT NULL DEFAULT NULL,\n" +
            "  PRIMARY KEY (`location_id`)" +
            ");";


    String query10 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`tags` (\n" +
            "  `idtags` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `Tag` VARCHAR(45) NULL COMMENT 'name of the tag\\n\\nex: Lecture, Tutorial, Lab, Lecture+Tutorial, Evalution',\n" +
            "  PRIMARY KEY (`idtags`))\n" +
            "ENGINE = InnoDB;";



    String query11 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`lecturer` (" +
            "  `lid` int NOT NULL AUTO_INCREMENT," +
            "  `lecturerID` varchar(6) NOT NULL," +
            "  `lecturerName` varchar(45) NOT NULL," +
            "  `lecturerFaculty` varchar(45) NOT NULL," +
            "  `lecturerDepartment` varchar(45) NOT NULL," +
            "  `lecturerCenter` varchar(45) NOT NULL," +
            "  `lecturerBuilding` varchar(45) NOT NULL," +
            "  `lecturerLevel` int NOT NULL," +
            "  `lecturerRank` varchar(10) NOT NULL," +
            "  PRIMARY KEY (`lid`)" +
            ") ENGINE=InnoDB;";


    String query12 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`module` (" +
            "  `idmodule` int NOT NULL AUTO_INCREMENT," +
            "  `moduleName` varchar(45) NOT NULL," +
            "  `moduleCode` varchar(45) NOT NULL," +
            "  `offeredYear` varchar(45) NOT NULL," +
            "  `offeredSemester` varchar(45) NOT NULL," +
            "  `lecHour` int NOT NULL," +
            "  `tuteHour` int NOT NULL," +
            "  `labHour` int NOT NULL," +
            "  `evaluationHour` int NOT NULL," +
            "  PRIMARY KEY (`idmodule`)" +
            ") ENGINE=InnoDB;";




    String query13 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`studentbatches` (" +
            "  `id` int(5) NOT NULL AUTO_INCREMENT," +
            "  `year` varchar(45) NOT NULL," +
            "  `semester` varchar(45) NOT NULL," +
            "  `intake` varchar(45) NOT NULL," +
            "  `faculty` varchar(45) NOT NULL," +
            "  `programme` varchar(44) DEFAULT NULL," +
            "  `center` varchar(45) NOT NULL," +
            "  `noofstd` int(5) DEFAULT NULL," +
            "  `batchID` varchar(45) DEFAULT NULL," +
            "  PRIMARY KEY (`id`)" +
            ") ENGINE=InnoDB;";



    String query14 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`preferred_room_for_subject` (\n" +
            "  `preferred_room_for_subject_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `subject_subject_id` INT NOT NULL,\n" +
            "  `location_location_id` INT NOT NULL,\n" +
            "  `tag_tag_id` INT NOT NULL,\n" +
            "  `status_true` VARCHAR(3) NOT NULL DEFAULT 'Y',\n" +
            "  PRIMARY KEY (`preferred_room_for_subject_id`, `subject_subject_id`, `location_location_id`, `tag_tag_id`)" +"" +
            ");";

    String query15 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`batchstats` (\n" +
            "  `batch` INT NOT NULL,\n" +
            "  `nofStudents` INT NULL,\n" +
            "  `nofGrouped` INT NULL,\n" +
            "  `nofRemain` INT NULL,\n" +
            "  `nofGroups` INT NULL,\n" +
            "  PRIMARY KEY (`batch`))\n" +
            "ENGINE = InnoDB;";


    String query16 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`subgroups` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `subGroupId` VARCHAR(45) NULL,\n" +
            "  `NofStudents` INT NULL,\n" +
            "  `batchID` INT NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB;";

    String query17 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`subgroups` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `subGroupId` VARCHAR(45) NULL," +
            "  `NofStudents` INT NULL," +
            "  `batchID` INT NULL," +
            "  PRIMARY KEY (`id`))" +
            "ENGINE = InnoDB;";


    String query18 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`systemTags` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `systemTag` VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB;";


    String query19 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`tempTags` (\n" +
            "  `Tag` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`Tag`))\n" +
            "ENGINE = InnoDB;";


    String query20 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`daysname` (\n" +
            "  `id` INT NOT NULL,\n" +
            "  `day1name` VARCHAR(45) NULL,\n" +
            "  `day2name` VARCHAR(45) NULL,\n" +
            "  `day3name` VARCHAR(45) NULL,\n" +
            "  `day4name` VARCHAR(45) NULL,\n" +
            "  `day5name` VARCHAR(45) NULL,\n" +
            "  `day6name` VARCHAR(45) NULL,\n" +
            "  `day7name` VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB;";


    String query21 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`hours` (\n" +
            "  `id` INT NOT NULL,\n" +
            "  `hour1` VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB;";


    String query22 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`nodays` (\n" +
            "  `idno` INT NOT NULL,\n" +
            "  `noDays` INT NULL,\n" +
            "  PRIMARY KEY (`idno`))\n" +
            "ENGINE = InnoDB;";


    String query23 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`timeslots` (\n" +
            "  `slotsID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `range_t` VARCHAR(5) NULL,\n" +
            "  `value_t` VARCHAR(20) NULL,\n" +
            "  `timeslots_col` VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (`slotsID`))\n" +
            "ENGINE = InnoDB;";


 /*   String query24 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`lecturer_has_module` (\n" +
            "  `lecturer_lid` INT NOT NULL,\n" +
            "  `module_idmodule` INT NOT NULL,\n" +
            "  PRIMARY KEY (`lecturer_lid`, `module_idmodule`),\n" +
            "  INDEX `fk_lecturer_has_module_module1_idx` (`module_idmodule` ASC) VISIBLE,\n" +
            "  INDEX `fk_lecturer_has_module_lecturer1_idx` (`lecturer_lid` ASC) VISIBLE,\n" +
            "  CONSTRAINT `fk_lecturer_has_module_lecturer1`\n" +
            "    FOREIGN KEY (`lecturer_lid`)\n" +
            "    REFERENCES `timetabledb`.`lecturer` (`lid`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE,\n" +
            "  CONSTRAINT `fk_lecturer_has_module_module1`\n" +
            "    FOREIGN KEY (`module_idmodule`)\n" +
            "    REFERENCES `timetabledb`.`module` (`idmodule`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE)\n" +
            "ENGINE = InnoDB;";*/


    // added by Dhanusha
    String query25 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`suitable_room_for_lecturer` (\n" +
            "  `suitable_room_for_lecturer_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `location_location_id` INT NOT NULL,\n" +
            "  `lecturer_lid` INT NOT NULL,\n" +
            "  `status_true` VARCHAR(3) NOT NULL DEFAULT 'Y',\n" +
            "  PRIMARY KEY (`suitable_room_for_lecturer_id`, `location_location_id`, `lecturer_lid`),\n" +
           // "  INDEX `fk_location_has_lecturer_location1_idx` (`location_location_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `fk_location_has_lecturer_location1`\n" +
            "    FOREIGN KEY (`location_location_id`)\n" +
            "    REFERENCES `"+DatabaseConnection.databaseName+"`.`location` (`location_id`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE)\n" +
            "ENGINE = InnoDB;";


    String query26 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`time_table` (" +
            "  `Id` INT NOT NULL AUTO_INCREMENT," +
            "  `timeSlot` VARCHAR(50) NULL," +
            "  `Module` VARCHAR(50) NULL," +
            "  `tag` VARCHAR(25) NULL," +
            "  `Hall` VARCHAR(25) NULL," +
            "  `group` VARCHAR(25) NULL," +
            "  `lecturer` VARCHAR(25) NULL," +
            "  `sessionId` VARCHAR(100) NULL," +
            "  `duration` INT NULL," +
            "  `dayName` VARCHAR(25) NULL," +
            "  PRIMARY KEY (`Id`))";


    // add by Dhanusha
    String query27 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`suitable_room_for_tags` (\n" +
            "  `suitable_room_for_tags_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `location_location_id` INT NOT NULL,\n" +
            "  `tags_idtags` INT NOT NULL,\n" +
            "  `status_true` VARCHAR(3) NOT NULL DEFAULT 'Y',\n" +
            "  PRIMARY KEY (`suitable_room_for_tags_id`, `location_location_id`, `tags_idtags`),\n" +
           // "  INDEX `fk_location_has_tags_location1_idx` (`location_location_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `fk_location_has_tags_location1`\n" +
            "    FOREIGN KEY (`location_location_id`)\n" +
            "    REFERENCES `"+DatabaseConnection.databaseName+"`.`location` (`location_id`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE)\n" +
            "ENGINE = InnoDB;";

    String query28 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`suitable_room_for_student_batch` (\n" +
            "  `suitable_room_for_student_batch_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `location_location_id` INT NOT NULL,\n" +
            "  `studentbatches_id` INT NOT NULL,\n" +
            "  `status_true` VARCHAR(3) NOT NULL DEFAULT 'Y',\n" +
            "  PRIMARY KEY (`suitable_room_for_student_batch_id`, `location_location_id`, `status_true`, `studentbatches_id`),\n" +
            //"  INDEX `fk_suitable_room_for_student_batch_location_idx` (`location_location_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `fk_suitable_room_for_student_batch_location`\n" +
            "    FOREIGN KEY (`location_location_id`)\n" +
            "    REFERENCES `"+DatabaseConnection.databaseName+"`.`location` (`location_id`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE)\n" +
            "ENGINE = InnoDB;";

    String query29 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`suitable_room_for_student_subgroups` (\n" +
            "  `suitable_room_for_student_subgroups_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `location_location_id` INT NOT NULL,\n" +
            "  `subgroups_id` INT NOT NULL,\n" +
            "  `status_true` VARCHAR(3) NOT NULL DEFAULT 'Y',\n" +
            "  PRIMARY KEY (`suitable_room_for_student_subgroups_id`, `location_location_id`, `subgroups_id`),\n" +
           // "  INDEX `fk_suitable_room_for_student_subgroups_location1_idx` (`location_location_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `fk_suitable_room_for_student_subgroups_location1`\n" +
            "    FOREIGN KEY (`location_location_id`)\n" +
            "    REFERENCES `"+DatabaseConnection.databaseName+"`.`location` (`location_id`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE)\n" +
            "ENGINE = InnoDB;";

    String query30 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`suitable_room_for_session` (\n" +
            "  `suitable_room_for_session_id` int NOT NULL AUTO_INCREMENT,\n" +
            "  `location_location_id` int NOT NULL,\n" +
            "  `idsession` int NOT NULL,\n" +
            "  `status_true` varchar(3) NOT NULL DEFAULT 'Y',\n" +
            "  PRIMARY KEY (`suitable_room_for_session_id`,`location_location_id`,`idsession`)\n" +
            ");";


    String query31 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`session` (" +
            "  `idsession` int NOT NULL AUTO_INCREMENT," +
            "  `sessionID` varchar(100) NOT NULL," +
            "  `sessionTag` varchar(45) NOT NULL," +
            "  `sessionStudentGroup` varchar(45) NOT NULL," +
            "  `sessionSubject` varchar(45) NOT NULL," +
            "  `sessionNoOfStudents` varchar(45) NOT NULL," +
            "  `sessionDuration` varchar(45) NOT NULL," +
            "  `sessionModuleCode` varchar(45) NOT NULL," +
            "  PRIMARY KEY (`idsession`)" +
            ");";



    String query32 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`session_lecturer` (" +
            "  `idsession_lecturer` int NOT NULL AUTO_INCREMENT," +
            "  `sessionID` varchar(100) NOT NULL," +
            "  `sessionLecturerName` varchar(45) NOT NULL," +
            "  PRIMARY KEY (`idsession_lecturer`)" +
            ") ENGINE=InnoDB;";


    String query33 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`consecutive_session_in_same_room` (\n" +
            "  `consecutive_session_in_same_room_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `location_location_id` INT NOT NULL,\n" +
            "  `consecutive_session_id` INT NOT NULL,\n" +
            "  `status_true` VARCHAR(3) NOT NULL DEFAULT 'Y',\n" +
            "  PRIMARY KEY (`consecutive_session_in_same_room_id`, `location_location_id`, `consecutive_session_id`),\n" +
            "  CONSTRAINT `fk_ consecutive_session_in_same_room_location`\n" +
            "    FOREIGN KEY (`location_location_id`)\n" +
            "    REFERENCES `"+DatabaseConnection.databaseName+"`.`location` (`location_id`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE)\n" +
            "ENGINE = InnoDB;";

    String query34 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`consecetive_sessions` (\n" +
            "  `id` int NOT NULL AUTO_INCREMENT,\n" +
            "  `session1ID` varchar(100) DEFAULT NULL,\n" +
            "  `session2ID` varchar(100) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ")\n";

    String query35 = "CREATE TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`cannot_be_reserved_time_for_location` (\n" +
            "  `cannot_be_reserved_time_for_location_id` int NOT NULL AUTO_INCREMENT,\n" +
            "  `day` varchar(50) NOT NULL,\n" +
            "  `location_location_id` int NOT NULL,\n" +
            "  `timeslot_id` int NOT NULL,\n" +
            "  `status_true` varchar(3) NOT NULL DEFAULT 'Y',\n" +
            "  PRIMARY KEY (`cannot_be_reserved_time_for_location_id`)\n" +
            ");";


    String createTableQuery = "CREATE  TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`notavailabletimegroup` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `batchID` VARCHAR(45) NULL ," +
            "  `Day` VARCHAR(45) NULL ," +
            "  `Hour` VARCHAR(45) NULL ," +
            "  PRIMARY KEY (`id`) );";



    String createSubGroupTableQ ="CREATE  TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`notavailabletimesubgroup` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `batchID` VARCHAR(45) NULL ," +
            "  `subGroupID` VARCHAR(45) NULL ,"+
            "  `Day` VARCHAR(45) NULL ," +
            "  `Hour` VARCHAR(45) NULL ," +
            "  PRIMARY KEY (`id`) );";


    String createTableLecNAQuery = "CREATE  TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`lecturernatime` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `lecID` int NULL ," +
            "  `Day` VARCHAR(45) NULL ," +
            "  `Hour` VARCHAR(45) NULL ," +
            "  PRIMARY KEY (`id`) );";

    String createTableSessionsNA = "CREATE  TABLE IF NOT EXISTS `"+DatabaseConnection.databaseName+"`.`sessionsnatime` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `sessionID` VARCHAR(100) NULL ," +
            "  `Day` VARCHAR(45) NULL ," +
            "  `Hour` VARCHAR(45) NULL ," +
            "  PRIMARY KEY (`id`) );";



    public void createDatabase() {
        try {
            executeQuery(createTableQuery);
            executeQuery(createSubGroupTableQ);
            executeQuery(createTableLecNAQuery);
            executeQuery(createTableSessionsNA);


            executeQuery(query4);
            executeQuery(query5);
            executeQuery(query6);
            executeQuery(query7);
            executeQuery(query8);
            executeQuery(query9);
            executeQuery(query10);

            executeQuery(query11);
            executeQuery(query12);
            executeQuery(query13);

            executeQuery(query14);


            executeQuery(query15);
            executeQuery(query16);
            executeQuery(query17);
            executeQuery(query18);
            executeQuery(query19);


            executeQuery(query20);
            executeQuery(query21);
            executeQuery(query22);
            executeQuery(query23);
            //executeQuery(query24);
            executeQuery(query25);
            executeQuery(query26);
            executeQuery(query27); // suitable room for tag
            executeQuery(query28); // suitable room for student batch
            executeQuery(query29); // suitable room for student subgroup
            executeQuery(query30); // suitable room for session

            executeQuery(query31); // session
            executeQuery(query32); // session_lecturer

            executeQuery(query34); // consecutive sessions // soysa's table

            executeQuery(query33); // consecutive_session_in_same_room table //Added by Dhanusha
            executeQuery(query35); // cannot_be_reserved_time_for_location table //Added by Dhanusha



        } catch (Exception e) {
            System.out.println("Error :: Something went wrong when creating database\n");
            e.printStackTrace();
        }
    }
}
