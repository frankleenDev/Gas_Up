package app.weconnect.gasappgasup;

public class Orders {

    String image_url;
    String location;
    String nature;
    String quantity;
    String time_ordered;
    String total;
    String type;
    String vendor;
    String status;

    public Orders() {}

    public Orders(String type, String nature, String quantity, String vendor, String location, String total, String image_url, String time_ordered, String status)
    {
        this.type = type;
        this.nature = nature;
        this.quantity = quantity;
        this.vendor = vendor;
        this.location = location;
        this.total = total;
        this.image_url = image_url;
        this.time_ordered = time_ordered;
        this.status = status;
    }

    public String getImage_url()
    {
        return this.image_url;
    }

    public String getLocation()
    {
        return this.location;
    }

    public String getNature()
    {
        return this.nature;
    }

    public String getQuantity()
    {
        return this.quantity;
    }

    public String getTime_ordered()
    {
        return this.time_ordered;
    }

    public String getTotal()
    {
        return this.total;
    }

    public String getType()
    {
        return this.type;
    }

    public String getVendor()
    {
        return this.vendor;
    }

    public void setImage_url(String image_url)
    {
        this.image_url = image_url;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public void setNature(String nature)
    {
        this.nature = nature;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public void setTime_ordered(String time_ordered)
    {
        this.time_ordered = time_ordered;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setVendor(String vendor)
    {
        this.vendor = vendor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
