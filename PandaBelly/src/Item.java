public class Item {
    private String name;
    private double price;
    private int quantity;
    private boolean restockNeeded;


    public Item(String n, double p, int q, boolean r){
        name=n;
        price=p;
        quantity=q;
        restockNeeded=r;

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
    public boolean isRestockNeeded() {
        return restockNeeded;
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
    public void setRestockNeeded(boolean restockNeeded) {
        this.restockNeeded = restockNeeded;
    }

}