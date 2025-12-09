import java.util.ArrayList;

public class Storage {
    private ArrayList<Item> category;
    private String categoryName;


    public Storage(String n){
        category = new ArrayList<Item>();
        categoryName = n;
    }
    //removes an item from the category based on its name
    public boolean removeItem(String n){
        for (int i=0; i<category.size(); i++){
            if(category.get(i).getName().equals(n)){
                category.remove(i);
                return true;
            }
        }
        return false;
    }

    //Adds an item to the category
    public boolean addItem(String n, double p, int q, boolean r){
        for(Item y:category){
            if(y.getName().equals(n)){
                return false;
            }
        }
        category.add(new Item(n,p,q,r));
        
        return true;
    }

    public boolean modifyItem(String n, String newName, double p, int q){
        for(Item y:category){
            if(y.getName().equals(n)){
                y.setName(newName);
                y.setPrice(p);
                y.setQuantity(q);
                return true;
            }
        }
        return false;
    }
    //accessor method to return the category's name
    public String getCName(){
        return categoryName;
    }

    public ArrayList<Item> getCategory() {
        return category;
    }

    //mutator method to set the category's name
    public void setCName(String cName) {
        this.categoryName = cName;
    }
    

    
}
