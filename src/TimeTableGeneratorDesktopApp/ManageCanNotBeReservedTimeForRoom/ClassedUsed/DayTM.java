package TimeTableGeneratorDesktopApp.ManageCanNotBeReservedTimeForRoom.ClassedUsed;

public class DayTM {

    private int id;
    private String day1;
    private String day2;
    private String day3;
    private String day4;
    private String day5;
    private String day6;
    private String day7;

    // default constructor

    public DayTM() {
    }

    public DayTM(int id, String day1, String day2, String day3, String day4, String day5, String day6, String day7) {
        this.id = id;
        this.day1 = day1;
        this.day2 = day2;
        this.day3 = day3;
        this.day4 = day4;
        this.day5 = day5;
        this.day6 = day6;
        this.day7 = day7;
    }

    public String getDay1() {
        return day1;
    }

    public void setDay1(String day1) {
        this.day1 = day1;
    }

    public String getDay2() {
        return day2;
    }

    public void setDay2(String day2) {
        this.day2 = day2;
    }

    public String getDay3() {
        return day3;
    }

    public void setDay3(String day3) {
        this.day3 = day3;
    }

    public String getDay4() {
        return day4;
    }

    public void setDay4(String day4) {
        this.day4 = day4;
    }

    public String getDay5() {
        return day5;
    }

    public void setDay5(String day5) {
        this.day5 = day5;
    }

    public String getDay6() {
        return day6;
    }

    public void setDay6(String day6) {
        this.day6 = day6;
    }

    public String getDay7() {
        return day7;
    }

    public void setDay7(String day7) {
        this.day7 = day7;
    }

    @Override
    public String toString() {
        return "DayTM{" +
                "day1='" + day1 + '\'' +
                ", day2='" + day2 + '\'' +
                ", day3='" + day3 + '\'' +
                ", day4='" + day4 + '\'' +
                ", day5='" + day5 + '\'' +
                ", day6='" + day6 + '\'' +
                ", day7='" + day7 + '\'' +
                '}';
    }
}
