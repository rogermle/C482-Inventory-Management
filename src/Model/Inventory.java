package Model;

import Model.*;
import javafx.beans.value.ObservableListValue;
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
        allPartsCount = 1;
        allProductsCount = 1;
    }
    public static void addPart(Part newPart) {
        if(newPart != null) {
            allParts.add(newPart);
            allPartsCount++;
        }

    }

    public static void addProduct(Product newProduct) {
        if(newProduct != null) {
            allProducts.add(newProduct);
            allProductsCount++;
        }
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matches = FXCollections.observableArrayList();
        if(!allParts.isEmpty() && !partName.equals(""))
        {
            for (Part allPart : allParts) {
                if (allPart.getName().contains(partName)) {
                    matches.add(allPart);
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
            for (Product allProduct : allProducts) {
                if (allProduct.getName().contains(productName)) {
                    matches.add(allProduct);
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
                    allPartsCount--;
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
                    allProductsCount--;
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

    public static int getAllPartsCount() {
        return allPartsCount - 1;
    }

    public static int getAllProductsCount() {
        return allProductsCount -1;
    }






}
