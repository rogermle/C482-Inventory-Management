package Model;

import javafx.collections.ObservableList;

/**
 * @author Roger Le
 */
public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price = 0.0;
    private int stock = 0;
    private int min;
    private int max;
    private double cost;

    public Product(int id, String name, double price, int stock, int min, int max) {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getStock() {
        return this.stock;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public void addAssociatedPart(Part part) {
        if(part != null) {
            associatedParts.add(part);
        }
    }

    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if(selectedAssociatedPart != null) {
            if (!associatedParts.isEmpty()) {
                for (int i = 0; i < associatedParts.size(); i++) {
                    if (associatedParts.get(i) == selectedAssociatedPart) {
                        associatedParts.remove(i);
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


}
