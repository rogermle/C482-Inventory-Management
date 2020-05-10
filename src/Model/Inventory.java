package Model;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts;
    private static ObservableList<Product> allProducts;
    private static int allPartsCount;
    private static int allProductsCount;

    public Inventory() {
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
        allPartsCount = 0;
        allProductsCount = 0;
    }
    public void addPart(Part newPart) {
        if(newPart != null) {
            allParts.add(newPart);
            allPartsCount++;
        }

    }

    public void addProduct(Product newProduct) {
        if(newProduct != null) {
            allProducts.add(newProduct);
            allProductsCount++;
        }
    }

    public Part lookupPart(int partId) {
        if(!allParts.isEmpty())
        {
            for(int i = 0; i < allParts.size(); i++) {
                if(allParts.get(i).getId() == partId) {
                    return allParts.get(i);
                }
            }
            return null;
        }
        return null;
    }

    public Product lookupProduct(int productId) {
        if(!allProducts.isEmpty())
        {
            for(int i = 0; i < allProducts.size(); i++) {
                if(allProducts.get(i).getId() == productId){
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }

    public ObservableList<Part> lookupPart(String partName) {
        return allParts;
    }

    public ObservableList<Product> lookupProduct() {
        return allProducts;
    }

    public void updatePart() {

    }

    public void updateProduct() {

    }

    public Boolean deletePart() {
        return true;
    }

    public Boolean deleteProduct() {
        return true;
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public int getAllPartsCount() {
        return allPartsCount;
    }

    public int getAllProductsCount() {
        return allProductsCount;
    }






}
