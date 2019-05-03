package app.weconnect.gasappgasup;

public class Products {

    private String desc;
    private String image;
    private String prod_id;
    private String refill;
    private String title;
    private String url;
    private String vendor;
    private String vendor_name;

    public Products() {}

    public Products(String title, String desc, String image, String prod_id, String url, String refill, String vendor, String vendor_name)
    {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.url = url;
        this.refill = refill;
        this.vendor = vendor;
        this.prod_id = prod_id;
        this.vendor_name = vendor_name;
    }

    public String getDesc()
    {
        return this.desc;
    }

    public String getImage()
    {
        return this.image;
    }

    public String getProd_id()
    {
        return this.prod_id;
    }

    public String getRefill()
    {
        return this.refill;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getUrl()
    {
        return this.url;
    }

    public String getVendor()
    {
        return this.vendor;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public void setProd_id(String prod_id)
    {
        this.prod_id = prod_id;
    }

    public void setRefill(String refill)
    {
        this.refill = refill;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setVendor(String vendor)
    {
        this.vendor = vendor;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }
}
