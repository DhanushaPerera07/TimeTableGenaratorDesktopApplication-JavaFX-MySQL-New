package TimeTableGeneratorDesktopApp.ManageSuitableRooms.ClassesUsed;

public class SuitableLocationForTag {

    private int suitable_room_for_tags_id;
    private int location_location_id;
    private int tags_idtags;
    private String status_true;


    public SuitableLocationForTag() {
    }

    public SuitableLocationForTag(int suitable_room_for_tags_id, int location_location_id, int tags_idtags, String status_true) {
        this.suitable_room_for_tags_id = suitable_room_for_tags_id;
        this.location_location_id = location_location_id;
        this.tags_idtags = tags_idtags;
        this.status_true = status_true;
    }

    public int getSuitable_room_for_tags_id() {
        return suitable_room_for_tags_id;
    }

    public void setSuitable_room_for_tags_id(int suitable_room_for_tags_id) {
        this.suitable_room_for_tags_id = suitable_room_for_tags_id;
    }

    public int getLocation_location_id() {
        return location_location_id;
    }

    public void setLocation_location_id(int location_location_id) {
        this.location_location_id = location_location_id;
    }

    public int getTags_idtags() {
        return tags_idtags;
    }

    public void setTags_idtags(int tags_idtags) {
        this.tags_idtags = tags_idtags;
    }

    public String getStatus_true() {
        return status_true;
    }

    public void setStatus_true(String status_true) {
        this.status_true = status_true;
    }
}
