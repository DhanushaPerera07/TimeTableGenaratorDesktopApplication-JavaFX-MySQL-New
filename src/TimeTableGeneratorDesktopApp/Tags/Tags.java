package TimeTableGeneratorDesktopApp.Tags;

public class Tags {
    int tagID;
    String Tag;

    //default constructor // created by Dhanusha
    public Tags() {
    }

    // constructor with parameters // created by Dhanusha
    public Tags(int tagID, String tag) {
        this.tagID = tagID;
        Tag = tag;
    }

    //setters and getters
    public int getTagID() { // created by Dhanusha
        return tagID;
    }

    public void setTagID(int tagID) { // created by Dhanusha
        this.tagID = tagID;
    }

    public void setTag(String tag) { // created by Dhanusha
        Tag = tag;
    }



    // zoysa's codes
    public Tags(String tag) {
        Tag = tag;
    }

    public String getTag() {
        return Tag;
    }
}
