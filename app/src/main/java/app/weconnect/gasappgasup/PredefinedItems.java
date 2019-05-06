package app.weconnect.gasappgasup;


public class PredefinedItems {

    private String title;
    private String desc;
    private String image;
    private String refill;
    private String check_box;

    public PredefinedItems(String title, String desc, String image, String refill, String check_box) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.refill = refill;
        this.check_box = check_box;
    }

    public PredefinedItems(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRefill() {
        return refill;
    }

    public void setRefill(String refill) {
        this.refill = refill;
    }

    public String getCheck_box() {
        return check_box;
    }

    public void setCheck_box(String check_box) {
        this.check_box = check_box;
    }
}
