package TimeTableGeneratorDesktopApp.TimeTableGeneration.HallView;


public class Hall {

    private int location_id;
    private String location_name;
    private int location_capacity;
    private int location_floor;
    private String location_condition;
    private String location_delete_status;
    private int building_building_id;
    private int tag_tag_id;

    public Hall(int location_id, String location_name, int location_capacity, int location_floor, String location_condition, String location_delete_status, int building_building_id, int tag_tag_id) {
        this.location_id = location_id;
        this.location_name = location_name;
        this.location_capacity = location_capacity;
        this.location_floor = location_floor;
        this.location_condition = location_condition;
        this.location_delete_status = location_delete_status;
        this.building_building_id = building_building_id;
        this.tag_tag_id = tag_tag_id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public int getLocation_capacity() {
        return location_capacity;
    }

    public int getLocation_floor() {
        return location_floor;
    }

    public String getLocation_condition() {
        return location_condition;
    }

    public String getLocation_delete_status() {
        return location_delete_status;
    }

    public int getBuilding_building_id() {
        return building_building_id;
    }

    public int getTag_tag_id() {
        return tag_tag_id;
    }


}
