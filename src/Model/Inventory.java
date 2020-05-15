package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int nextPartId = 1;
    private static int nextProductId = 1;

    public Inventory() {

    }
    public static void addPart(Part newPart) {
        if(newPart != null) {
            allParts.add(newPart);
            nextPartId++;
        }

    }

    public static void addProduct(Product newProduct) {
        if(newProduct != null) {
            allProducts.add(newProduct);
            nextProductId++;
        }
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matches = FXCollections.observableArrayList();
        if(!allParts.isEmpty() && !partName.equals(""))
        {
            for (Part part : allParts) {
                if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                    matches.add(part);
                }
            }
            return matches;
        }
        return null;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matches = FXCollections.observableArrayList();
        if(!allProducts.isEmpty() && !productName.equals(""))
        {
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                    matches.add(product);
                }
            }
            return matches;
        }
        return null;
    }

    public static void updatePart(int index, Part selectedPart) {
        if(selectedPart != null){
            allParts.set(index, selectedPart);
        }
    }

    public static void updateProduct(int index, Product selectedProduct) {
        if(selectedProduct != null){
            allProducts.set(index, selectedProduct);
        }
    }

    public static Boolean deletePart(Part delPart) {
        if(delPart != null){
            for(int i = 0; i < allParts.size(); i++) {
                if(allParts.get(i) == delPart){
                    allParts.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public static Boolean deleteProduct(Product delProduct) {
        if(delProduct != null){
            for(int i = 0; i < allProducts.size(); i++) {
                if(allProducts.get(i) == delProduct){
                    allProducts.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static int getNextPartId() { return nextPartId;}

    public static int getNextProductId() { return nextProductId;}






}
