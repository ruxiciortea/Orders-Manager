package Model;

public class OrderItem {

    int ID;
    int clientID;
    int productID;
    int quantity;

    public OrderItem() {
    }

    public OrderItem(int ID, int clientID, int productID, int quantity) {
        this.ID = ID;
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public OrderItem(int clientID, int productID, int quantity) {
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
