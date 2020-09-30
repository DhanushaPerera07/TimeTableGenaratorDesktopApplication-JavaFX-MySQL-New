package TimeTableGeneratorDesktopApp.ConsecutiveSessionsInSameLocation;

import TimeTableGeneratorDesktopApp.Sessions.Sessions;

public class ConsecutiveSessionViewModel {

    private int id;
    private Sessions session1; // String session1ID;
    private Sessions session2; // String session2ID;
    private int suitableRoomTrue;

    // default constructor
    public ConsecutiveSessionViewModel() {
    }

    // constructor with parameters
    public ConsecutiveSessionViewModel(int id, Sessions session1, Sessions session2) {
        this.id = id;
        this.session1 = session1;
        this.session2 = session2;
    }


    // constructor with parameters
    public ConsecutiveSessionViewModel(int id, Sessions session1, Sessions session2, int suitableRoomTrue) {
        this.id = id;
        this.session1 = session1;
        this.session2 = session2;
        this.suitableRoomTrue = suitableRoomTrue;
    }


    // getter and setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sessions getSession1() {
        return session1;
    }

    public void setSession1(Sessions session1) {
        this.session1 = session1;
    }

    public Sessions getSession2() {
        return session2;
    }

    public void setSession2(Sessions session2) {
        this.session2 = session2;
    }

    public int getSuitableRoomTrue() {
        return suitableRoomTrue;
    }

    public void setSuitableRoomTrue(int suitableRoomTrue) {
        this.suitableRoomTrue = suitableRoomTrue;
    }

    @Override
    public String toString() {
        return "ConsecutiveSessionViewModel{" +
                "id=" + id +
                ", session1=" + session1 +
                ", session2=" + session2 +
                ", suitableRoomTrue=" + suitableRoomTrue +
                '}';
    }
}
