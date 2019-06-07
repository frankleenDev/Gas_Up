package app.weconnect.gasappgasup;

public class VendorClass {

    String order_no;
    String customer;
    String nature;
    String phone_no;
    String quantity;
    String time;
    String total;
    String location;
    String product;
    String recorded_date;


    public VendorClass() {
    }

    public VendorClass(String order_no, String customer, String nature, String phone_no, String quantity, String location, String time, String total, String product, String recorded_date) {
        this.order_no      = order_no;
        this.customer      = customer;
        this.nature        = nature;
        this.phone_no      = phone_no;
        this.quantity      = quantity;
        this.time          = time;
        this.location      = location;
        this.total         = total;
        this.product       = product;
        this.recorded_date = recorded_date;
    }


    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getRecorded_date() {
        return recorded_date;
    }

    public void setRecorded_date(String recorded_date) {
        this.recorded_date = recorded_date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
