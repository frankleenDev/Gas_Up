package app.weconnect.gasappgasup;

public class SummaryClass {

    Long clients;
    Long products;
    Long deliveries;

    public SummaryClass() {
    }

    public SummaryClass(Long clients, Long products, Long deliveries) {
        this.clients = clients;
        this.products = products;
        this.deliveries = deliveries;
    }

    public Long getClients() {
        return clients;
    }

    public void setClients(Long clients) {
        this.clients = clients;
    }

    public Long getProducts() {
        return products;
    }

    public void setProducts(Long products) {
        this.products = products;
    }

    public Long getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(Long deliveries) {
        this.deliveries = deliveries;
    }
}
