package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class InventoryProgram extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Inventory inv = new Inventory();
        addTestData(inv);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        TableView<Part> partsTableView = new TableView<Part>();
        TableView<Product> productsTableView = new TableView<Product>();
        TableColumn<Part, Integer> partIdCol = new TableColumn<Part, Integer>();
        TableColumn<Part, String> partNameCol = new TableColumn<Part, String>();

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        partsTableView.setItems(inv.getAllParts());
        productsTableView.setItems(inv.getAllProducts());

        //System.out.println(inv.getAllParts());
    }

    public static void main(String[] args) {
        launch(args);
    }

    void addTestData(Inventory inv) {
        //Add InHouse Parts
        Part a1 = new InHouse(1, "Part A1", 2.99, 10, 5, 100, 101);
        Part a2 = new InHouse(3, "Part A2", 4.99, 11, 5, 100, 103);
        Part b = new InHouse(2, "Part B", 3.99, 9, 5, 100, 102);
        inv.addPart(a1);
        inv.addPart(b);
        inv.addPart(a2);
        //Add OutSourced Parts
        Part o1 = new OutSourced(6, "Part 01", 2.99, 10, 5, 100, "ACME Co.");
        Part p = new OutSourced(7, "Part P", 3.99, 9, 5, 100, "ACME Co.");
        Part q = new OutSourced(8, "Part Q", 2.99, 10, 5, 100, "FLORIDA Co.");
        inv.addPart(o1);
        inv.addPart(p);
        inv.addPart(q);

        //Add Product

        Product p1 = new Product(1, "BFG 9000", 99.99, 1, 1, 1);
        Product p2 = new Product(2, "Rail Gun", 99.99, 1, 1, 1);
        Product p3 = new Product(3, "Glock 19", 99.99, 1, 1, 1);

        inv.addProduct(p1);
        inv.addProduct(p2);
        inv.addProduct(p3);
        System.out.println(inv.getAllProducts());
    }
}
