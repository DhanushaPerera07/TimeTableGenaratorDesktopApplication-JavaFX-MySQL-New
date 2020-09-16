package TimeTableGeneratorDesktopApp.DatabaseQueries;

import TimeTableGeneratorDesktopApp.DatabaseHelper.DatabaseHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseCreation {

    // ===================== DATABASE PART - STARTS HERE =============================================================================

    /**
     * get the database connection here
     */
    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
            return conn;
        } catch (Exception ex) {
            System.out.println("Error: getConnection() :::: " + ex.getMessage());
            return null;
        }
    }

    /**
     * execute the query string
     *
     * @param query string is passed here
     *              this query will execute by this method
     */
    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ===================== DATABASE PART - STARTS HERE =============================================================================

    String query1 = "SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;\n";
    String query2 = "SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;";
    String query3 = "SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';";
    String query4 = "CREATE SCHEMA IF NOT EXISTS `timetabledb` DEFAULT CHARACTER SET utf8 ;\n";
    String query5 = "USE `timetabledb` ;";

    String query6 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`faculty` (\n" +
            "  `faculty_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `faculty_name` VARCHAR(60) NULL,\n" +
            "  `faculty_short_name` VARCHAR(10) NULL,\n" +
            "  `faculty_specialized_for` VARCHAR(45) NULL,\n" +
            "  `faculty_status` VARCHAR(10) NULL DEFAULT 'OK',\n" +
            "  `faculty_head_name` VARCHAR(45) NULL,\n" +
            "  `faculty_delete_status` VARCHAR(2) NULL DEFAULT 'N',\n" +
            "  `faculty_timestamp` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
            "  `faculty_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "  PRIMARY KEY (`faculty_id`))\n" +
            "ENGINE = InnoDB;\n";


    String query7 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`department` (\n" +
            "  `department_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `department_name` VARCHAR(60) NULL COMMENT 'department name',\n" +
            "  `department_short_name` VARCHAR(15) NULL COMMENT 'short name of the department',\n" +
            "  `department_floor_no` INT NULL COMMENT 'which floor the department is at',\n" +
            "  `department_specialized_for` VARCHAR(45) NULL,\n" +
            "  `department_head` VARCHAR(45) NULL COMMENT 'Lecturers ID is linked here as FK. this means the head of the department.',\n" +
            "  `department_building_id` INT NULL COMMENT 'FK - to find the building of the department',\n" +
            "  `department_delete_status` VARCHAR(2) NULL COMMENT 'possiable values are:\\nY = Yes --> Mark the Record as deleted\\nN = No --> Mark the Record not as deleted',\n" +
            "  `department_timestamp` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'each record when gets updated; datetime is marked',\n" +
            "  `department_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "  `faculty_faculty_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`department_id`),\n" +
            "  INDEX `fk_department_faculty_idx` (`faculty_faculty_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `fk_department_faculty`\n" +
            "    FOREIGN KEY (`faculty_faculty_id`)\n" +
            "    REFERENCES `timetabledb`.`faculty` (`faculty_id`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE)\n" +
            "ENGINE = InnoDB;";

    String query8 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`building` (\n" +
            "  `building_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `building_name` VARCHAR(45) NULL,\n" +
            "  `building_no_of_floors` INT NULL DEFAULT 1,\n" +
            "  `building_capacity` INT NULL,\n" +
            "  `building_center` VARCHAR(45) NULL,\n" +
            "  `building_condition` VARCHAR(10) NULL,\n" +
            "  `building_specialized_for` VARCHAR(45) NULL,\n" +
            "  `building_no_of_lecture_halls` INT NULL,\n" +
            "  `building_no_of_tutorial_halls` INT NULL,\n" +
            "  `building_no_of_labs` INT NULL,\n" +
            "  `building_timestamp` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
            "  `building_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "  `building_delete_status` VARCHAR(2) NULL COMMENT 'possiable values:\\nY = Yes --> Mark as deleted\\nN= No --? Not deleted',\n" +
            "  `faculty_faculty_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`building_id`),\n" +
            "  INDEX `fk_building_faculty1_idx` (`faculty_faculty_id` ASC) VISIBLE,\n" +
            "  INDEX `building_name_idx` (`building_name` ASC) VISIBLE,\n" +
            "  CONSTRAINT `fk_building_faculty1`\n" +
            "    FOREIGN KEY (`faculty_faculty_id`)\n" +
            "    REFERENCES `timetabledb`.`faculty` (`faculty_id`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE)\n" +
            "ENGINE = InnoDB;";

    String query9 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`location` (\n" +
            "  `location_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `location_name` VARCHAR(45) NULL,\n" +
            "  `location_capacity` INT NULL,\n" +
            "  `location_floor` INT NULL,\n" +
            "  `location_condition` VARCHAR(10) NULL,\n" +
            "  `location_delete_status` VARCHAR(2) NULL,\n" +
            "  `location_timestamp` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
            "  `location_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "  `building_building_id` INT NOT NULL,\n" +
            "  PRIMARY KEY (`location_id`, `building_building_id`),\n" +
            "  INDEX `fk_location_building1_idx` (`building_building_id` ASC) VISIBLE,\n" +
            "  INDEX `location_name_idx` (`location_name` ASC) VISIBLE,\n" +
            "  CONSTRAINT `fk_location_building1`\n" +
            "    FOREIGN KEY (`building_building_id`)\n" +
            "    REFERENCES `timetabledb`.`building` (`building_id`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE)\n" +
            "ENGINE = InnoDB;";


    String query10 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`tags` (\n" +
            "  `idtags` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `Tag` VARCHAR(45) NULL COMMENT 'name of the tag\\n\\nex: Lecture, Tutorial, Lab, Lecture+Tutorial, Evalution',\n" +
            "  PRIMARY KEY (`idtags`))\n" +
            "ENGINE = InnoDB;";


    String query11 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`lecturer` (\n" +
            "  `lid` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `lecturerID` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `lecturerName` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `lecturerFaculty` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `lecturerDepartment` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `lecturerCenter` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `lecturerBuilding` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `lecturerLevel` INT NULL DEFAULT NULL,\n" +
            "  `lecturerRank` VARCHAR(45) NULL DEFAULT NULL,\n" +
            "  `lecturer_delete_status` VARCHAR(3) NULL DEFAULT 'N' COMMENT 'Y=yes\\\\nN= no',\n" +
            "  `lecturer_timestamp` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
            "  `lecturer_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "  `faculty_faculty_id` INT NULL DEFAULT NULL,\n" +
            "  PRIMARY KEY (`lid`),\n" +
            "  INDEX `fk_lecturer_faculty1_idx` (`faculty_faculty_id` ASC) VISIBLE,\n" +
            "  INDEX `lecturer_name_idx` (`lecturerID` ASC) VISIBLE,\n" +
            "  CONSTRAINT `fk_lecturer_faculty1`\n" +
            "    FOREIGN KEY (`faculty_faculty_id`)\n" +
            "    REFERENCES `timetabledb`.`faculty` (`faculty_id`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE)\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8";


    String query12 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`module` (\n" +
            "  `idmodule` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `moduleName` VARCHAR(45) NULL,\n" +
            "  `moduleCode` VARCHAR(45) NULL,\n" +
            "  `offeredYear` VARCHAR(45) NULL,\n" +
            "  `offeredSemester` VARCHAR(45) NULL,\n" +
            "  `lecHour` INT NULL,\n" +
            "  `tuteHour` INT NULL,\n" +
            "  `labHour` INT NULL,\n" +
            "  `evaluationHour` INT NULL,\n" +
            "  `subject_delete_status` VARCHAR(3) NULL DEFAULT 'N',\n" +
            "  `subject_timestamp` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
            "  `subject_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,\n" +
            "  PRIMARY KEY (`idmodule`))\n" +
            "ENGINE = InnoDB;";


    String query13 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`studentbatches` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `year` VARCHAR(45) NULL,\n" +
            "  `semester` VARCHAR(45) NULL COMMENT 'Y3S1.1\\nthis .1 is the part we are considering in here.\\n* and group can be sorted out using this column. just for the easyness.\\n\\npossiable values:\\n1\\n2\\n3\\n.\\n.',\n" +
            "  `intake` VARCHAR(45) NULL,\n" +
            "  `faculty` VARCHAR(45) NULL,\n" +
            "  `programme` VARCHAR(45) NULL,\n" +
            "  `center` VARCHAR(45) NULL,\n" +
            "  `noofstd` INT NULL,\n" +
            "  `batchID` VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB;";


    String query14 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`preferred_room_for_subject` (\n" +
            "  `preferred_room_for_subject_id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `subject_subject_id` INT NOT NULL,\n" +
            "  `location_location_id` INT NOT NULL,\n" +
            "  `tag_tag_id` INT NOT NULL,\n" +
            "  `status_true` VARCHAR(3) NOT NULL DEFAULT 'Y',\n" +
            "  PRIMARY KEY (`preferred_room_for_subject_id`, `subject_subject_id`, `location_location_id`, `tag_tag_id`),\n" +
            "  INDEX `fk_location_has_subject_subject1_idx` (`subject_subject_id` ASC) VISIBLE,\n" +
            "  INDEX `fk_location_has_subject_location1_idx` (`location_location_id` ASC) VISIBLE,\n" +
            "  INDEX `fk_preferred_room_for_subject_tags1_idx` (`tag_tag_id` ASC) VISIBLE,\n" +
            "  CONSTRAINT `fk_location_has_subject_location1`\n" +
            "    FOREIGN KEY (`location_location_id`)\n" +
            "    REFERENCES `timetabledb`.`location` (`location_id`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE,\n" +
            "  CONSTRAINT `fk_location_has_subject_subject1`\n" +
            "    FOREIGN KEY (`subject_subject_id`)\n" +
            "    REFERENCES `timetabledb`.`module` (`idmodule`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE,\n" +
            "  CONSTRAINT `fk_preferred_room_for_subject_tags1`\n" +
            "    FOREIGN KEY (`tag_tag_id`)\n" +
            "    REFERENCES `timetabledb`.`tags` (`idtags`)\n" +
            "    ON DELETE CASCADE\n" +
            "    ON UPDATE CASCADE)\n" +
            "ENGINE = InnoDB;";

    String query15 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`batchstats` (\n" +
            "  `batch` INT NOT NULL,\n" +
            "  `nofStudents` INT NULL,\n" +
            "  `nofGrouped` INT NULL,\n" +
            "  `nofRemain` INT NULL,\n" +
            "  `nofGroups` INT NULL,\n" +
            "  PRIMARY KEY (`batch`))\n" +
            "ENGINE = InnoDB;";


    String query16 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`subgroups` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `subGroupId` VARCHAR(45) NULL,\n" +
            "  `NofStudents` INT NULL,\n" +
            "  `batchID` INT NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB;";

    String query17 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`subgroups` (" +
            "  `id` INT NOT NULL AUTO_INCREMENT," +
            "  `subGroupId` VARCHAR(45) NULL," +
            "  `NofStudents` INT NULL," +
            "  `batchID` INT NULL," +
            "  PRIMARY KEY (`id`))" +
            "ENGINE = InnoDB;";


    String query18 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`systemtags` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `systemTag` VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB;";


    String query19 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`temptags` (\n" +
            "  `Tag` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`Tag`))\n" +
            "ENGINE = InnoDB;";


    String query20 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`daysname` (\n" +
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


    String query21 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`hours` (\n" +
            "  `id` INT NOT NULL,\n" +
            "  `hour1` VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB;";


    String query22 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`nodays` (\n" +
            "  `idno` INT NOT NULL,\n" +
            "  `noDays` INT NULL,\n" +
            "  PRIMARY KEY (`idno`))\n" +
            "ENGINE = InnoDB;";


    String query23 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`timeslots` (\n" +
            "  `slotsID` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `range_t` VARCHAR(5) NULL,\n" +
            "  `value_t` VARCHAR(20) NULL,\n" +
            "  `timeslots_col` VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (`slotsID`))\n" +
            "ENGINE = InnoDB;";


    String query24 = "CREATE TABLE IF NOT EXISTS `timetabledb`.`lecturer_has_module` (\n" +
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
            "ENGINE = InnoDB;";
    String query25 = "SET SQL_MODE=@OLD_SQL_MODE;";
    String query26 = "SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;";
    String query27 = "SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;";

    public void createDatabase() {
        try {

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
            executeQuery(query24);




        } catch (Exception e) {
            System.out.println("Error :: Something went wrong when creating database\n");
            e.printStackTrace();
        }
    }
}