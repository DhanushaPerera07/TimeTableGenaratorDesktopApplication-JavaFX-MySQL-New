package TimeTableGeneratorDesktopApp.LocationsHallsInsideBuildings;

public class LocationHallLab {

    private int locationID;
    private String locationName;
    private int locationCapacity;
    private int locationFloor;
    private String locationCondition;
    private int buildingID;
    private int tagID;

    public LocationHallLab() {
    }

    public LocationHallLab(int locationID, String locationName, int locationCapacity, int locationFloor, String locationCondition, int buildingID, int tagID) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.locationCapacity = locationCapacity;
        this.locationFloor = locationFloor;
        this.locationCondition = locationCondition;
        this.buildingID = buildingID;
        this.tagID = tagID;
    }


    // setter and getter

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getLocationCapacity() {
        return locationCapacity;
    }

    public void setLocationCapacity(int locationCapacity) {
        this.locationCapacity = locationCapacity;
    }

    public int getLocationFloor() {
        return locationFloor;
    }

    public void setLocationFloor(int locationFloor) {
        this.locationFloor = locationFloor;
    }

    public String getLocationCondition() {
        return locationCondition;
    }

    public void setLocationCondition(String locationCondition) {
        this.locationCondition = locationCondition;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }



    //toString

    @Override
    public String toString() {
        return "LocationHallLab{" +
                "locationID=" + locationID +
                ", locationName='" + locationName + '\'' +
                ", locationCapacity=" + locationCapacity +
                ", locationFloor=" + locationFloor +
                ", locationCondition='" + locationCondition + '\'' +
                ", buildingID=" + buildingID +
                ", tagID=" + tagID +
                '}';
    }
}
