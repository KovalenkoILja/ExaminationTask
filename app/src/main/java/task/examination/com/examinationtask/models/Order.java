package task.examination.com.examinationtask.models;

public class Order
{
    private int ID;
    private String Datetime;

    public Order(int ID, String datetime) {
        this.ID = ID;
        Datetime = datetime;
    }

    public Order() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDatetime() {
        return Datetime;
    }

    public void setDatetime(String datetime) {
        Datetime = datetime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "ID=" + ID +
                ", Datetime='" + Datetime + '\'' +
                '}';
    }
}
