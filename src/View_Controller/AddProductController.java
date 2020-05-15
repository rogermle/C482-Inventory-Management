package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    @FXML
    private TextField addName;

    @FXML
    private TextField addStock;

    @FXML
    private TextField addPrice;

    @FXML
    private TextField addMin;

    @FXML
    private TextField addMax;

    @FXML
    private TextField partSearchTxt;

    // Parts Search

    @FXML private TableView<Part> addPartsTableView = new TableView<Part>();
    @FXML private TableColumn<Part, Integer> partIdCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, String> partNameCol = new TableColumn<Part, String>();
    @FXML private TableColumn<Part, Integer> partStockCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, Double> partPriceCol = new TableColumn<Part, Double>();

    // Assoc Parts
    @FXML private TableView<Part> assocPartsTableView = new TableView<Part>();
    @FXML private TableColumn<Part, Integer> assocPartsIdCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, String> assocPartsNameCol = new TableColumn<Part, String>();
    @FXML private TableColumn<Part, Integer> assocPartsStockCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, Double> assocPriceCol = new TableColumn<Part, Double>();

    private Inventory inv;
    private Stage stage;
    private Product newProduct;
    private Alert alert;

    @FXML
    void onActionAddPartSearch(ActionEvent event) {
        String search = partSearchTxt.getText();
        ObservableList<Part> matches = Inventory.lookupPart(search);
        System.out.println(matches);
        if( matches == null || matches.isEmpty() ||search.length() == 0 ){
            //Display Alert
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Search Results");
            alert.setContentText("Searched for: " + search + "\r\nNo Matching Parts Found");
            addPartsTableView.setItems(Inventory.getAllParts());
            alert.show();
        } else {
            addPartsTableView.setItems(matches);
        }
    }

    @FXML
    void onActionAddPart(ActionEvent event) {
        Part addPart = addPartsTableView.getSelectionModel().getSelectedItem();
        newProduct.addAssociatedPart(addPart);
        assocPartsTableView.setItems(newProduct.getAllAssociatedParts());
    }
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part assocPart = assocPartsTableView.getSelectionModel().getSelectedItem();
        if(assocPart != null) {
            newProduct.deleteAssociatedPart(assocPart);
            assocPartsTableView.setItems(newProduct.getAllAssociatedParts());
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Associated Part Error");
            alert.setContentText("No Associated Part(s) selected!");
            alert.show();
        }
    }

    @FXML
    void onActionAddProductCancel(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Modify Part?");
        alert.setContentText("Exit & Return to Main Screen?");
        Optional<ButtonType> option = alert.showAndWait();
        if(option.isPresent() && option.get()  == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
            View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inv);
            loader.setController(controller);
            Parent root = loader.load();
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    void onActionAddProductSave(ActionEvent event) throws IOException {
        int newStock = Integer.parseInt(addStock.getText());
        int newMin = Integer.parseInt(addMin.getText());
        int newMax = Integer.parseInt(addMax.getText());
        double newPrice = Double.parseDouble(addPrice.getText());
        // Assumes ALL user input is valid
        newProduct.setId(Inventory.getNextProductId());
        newProduct.setName(addName.getText());
        newProduct.setStock(newStock);
        newProduct.setMin(newMin);
        newProduct.setMax(newMax);
        newProduct.setPrice(newPrice);
        if( newProduct.isValid(newStock, newMin, newMax)) {
            Inventory.addProduct(newProduct);
            // Redirect to Main Screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
            View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inv);
            loader.setController(controller);
            Parent root = loader.load();
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Product Error");
            alert.setContentText("Entered Stock Level: " + newProduct.getStock() +"\r\nMinimum: " + newProduct.getMin() +
                    "\r\nMaximum: " + newProduct.getMax());
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Parts Search
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addPartsTableView.setItems(Inventory.getAllParts());

        newProduct = new Product();

        // Assoc Parts
        assocPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartsStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public AddProductController(Inventory inv) {
        this.inv = inv;
    }
}
