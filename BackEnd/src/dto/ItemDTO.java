package dto;

import java.io.Serializable;

public class ItemDTO implements Serializable {
    private String id;
    private String name;
    private double unitPrice;
    private int qtyOnHand;

    public ItemDTO() {
    }

    public ItemDTO(String id, String name, int qtyOnHand, double unitPrice) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "code='" + id + '\'' +
                ", description='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }
}
