package TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation;

import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;
import TimeTableGeneratorDesktopApp.Sessions.Sessions;

public class ConsecutiveSessionViewAll {

    private int consecutive_session_in_same_room_id;
    private LocationHallLab locationHallLab;
    private Sessions sessions1;
    private Sessions sessions2;

    // default constructor
    public ConsecutiveSessionViewAll() {
    }

    // constructor with parameters
    public ConsecutiveSessionViewAll(LocationHallLab locationHallLab, Sessions sessions1, Sessions sessions2) {
        this.locationHallLab = locationHallLab;
        this.sessions1 = sessions1;
        this.sessions2 = sessions2;
    }

    // constructor with parameters

    public ConsecutiveSessionViewAll(int consecutive_session_in_same_room_id, LocationHallLab locationHallLab, Sessions sessions1, Sessions sessions2) {
        this.consecutive_session_in_same_room_id = consecutive_session_in_same_room_id;
        this.locationHallLab = locationHallLab;
        this.sessions1 = sessions1;
        this.sessions2 = sessions2;
    }


    // setter and getter

    public int getConsecutive_session_in_same_room_id() {
        return consecutive_session_in_same_room_id;
    }

    public void setConsecutive_session_in_same_room_id(int consecutive_session_in_same_room_id) {
        this.consecutive_session_in_same_room_id = consecutive_session_in_same_room_id;
    }

    public LocationHallLab getLocationHallLab() {
        return locationHallLab;
    }

    public void setLocationHallLab(LocationHallLab locationHallLab) {
        this.locationHallLab = locationHallLab;
    }

    public Sessions getSessions1() {
        return sessions1;
    }

    public void setSessions1(Sessions sessions1) {
        this.sessions1 = sessions1;
    }

    public Sessions getSessions2() {
        return sessions2;
    }

    public void setSessions2(Sessions sessions2) {
        this.sessions2 = sessions2;
    }


    @Override
    public String toString() {
        return "ConsecutiveSessionViewAll{" +
                "consecutive_session_in_same_room_id=" + consecutive_session_in_same_room_id +
                ", locationHallLab=" + locationHallLab +
                ", sessions1=" + sessions1 +
                ", sessions2=" + sessions2 +
                '}';
    }
}
