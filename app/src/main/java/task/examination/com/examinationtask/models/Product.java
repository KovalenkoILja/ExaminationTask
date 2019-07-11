package task.examination.com.examinationtask.models;

public class Product
{
    private int ID;
    private String Name;
    private String Description;
    private double Price;
    private int Quantity;

    public Product(int ID, String name, String description, double price) {
        this.ID = ID;
        Name = name;
        Description = description;
        Price = price;
    }

    public Product() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
    @Override
    public String toString() {
        return "Product{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", Price=" + Price +
                '}';
    }
}
