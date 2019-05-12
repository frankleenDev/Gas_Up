package app.weconnect.gasappgasup;

public class SummaryClass {

    String clients;
    String products;
    String deliveries;

    public SummaryClass() {
    }

    public SummaryClass(String clients, String products, String deliveries) {
        this.clients = clients;
        this.products = products;
        this.deliveries = deliveries;
    }

    public String getClients() {
        return clients;
    }

    public void setClients(String clients) {
        this.clients = clients;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(String deliveries) {
        this.deliveries = deliveries;
    }
}
