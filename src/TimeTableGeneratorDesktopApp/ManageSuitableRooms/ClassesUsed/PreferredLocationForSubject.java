package TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed;

import TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings.LocationHallLab;

public class PreferredLocationForSubject {
    private LocationHallLab locationHallLab;
    private PreferredLocation preferredLocation;

    // default constructor
    public PreferredLocationForSubject() {

    }

    // constructor with parameters
    public PreferredLocationForSubject(LocationHallLab locationHallLab, PreferredLocation preferredLocation) {
        this.locationHallLab = locationHallLab;
        this.preferredLocation = preferredLocation;
    }

    public LocationHallLab getLocationHallLab() {
        return locationHallLab;
    }

    public void setLocationHallLab(LocationHallLab locationHallLab) {
        this.locationHallLab = locationHallLab;
    }

    public PreferredLocation getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(PreferredLocation preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    @Override
    public String toString() {
        return "PreferredLocationForSubject{" +
                "locationHallLab=" + locationHallLab +
                ", preferredLocation=" + preferredLocation +
                '}';
    }
}
