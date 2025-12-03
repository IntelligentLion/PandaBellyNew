public class Item {
    private String name;
    private double price;
    private int quantity;
    private String SKU;

    public Item(String n, double p, int q, String s){
        name=n;
        price=p;
        quantity=q;
        SKU=s;
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
    public String getSKU(){
        return SKU;
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
    public void setSKU(String s){
        SKU=s;
    }
}