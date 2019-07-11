package task.examination.com.examinationtask.models;

public class Order_Products
{
    private int ID;
    private int OrderID;
    private int ProductID;
    private int ProductQuantity;

    public Order_Products(int ID, int orderID, int productID, int productQuantity) {
        this.ID = ID;
        OrderID = orderID;
        ProductID = productID;
        ProductQuantity = productQuantity;
    }

    public Order_Products() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public int getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        ProductQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "Order_Products{" +
                "ID=" + ID +
                ", OrderID=" + OrderID +
                ", ProductID=" + ProductID +
                ", ProductQuantity=" + ProductQuantity +
                '}';
    }
}
