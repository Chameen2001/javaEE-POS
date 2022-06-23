package entity;

import java.time.LocalDate;

public class Orders {
    private String oid;
    private LocalDate date;
    private String customerId;

    public Orders() {
    }

    public Orders(String oid, LocalDate date, String customerId) {
        this.oid = oid;
        this.date = date;
        this.customerId = customerId;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
