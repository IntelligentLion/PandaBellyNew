public class Item {
    private String name;
    private double price;
    private int quantity;


    public Item(String n, double p, int q){
        name=n;
        price=p;
        quantity=q;

    }
    //accessors y
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public int getQuantity(){
        return quantity;
    }


    //setters
    public void setName(String n){
        name=n;
    }
    public void setPrice(double x){
        price=x;
    }
    public void setQuantity(int q){
        quantity=q;
    }

}