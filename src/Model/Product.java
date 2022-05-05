package Model;

public class Product {

    private int ID;
    private String name;
    private int quantity;
    private int unitPrice;

    public Product() {
    }

    public Product(int ID, String name, int quantity, int unitPrice) {
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Product(String name, int quantity, int unitPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

}
