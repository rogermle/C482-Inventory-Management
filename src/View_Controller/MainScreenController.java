package View_Controller;

import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {
    private Inventory inv;
    private Stage stage;
    private Parent scene;
    private Alert alert;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private TextField productSearchTxt;


    //Parts
    @FXML private TableView<Part> partsTableView = new TableView<Part>();
    @FXML private TableColumn<Part, Integer> partIdCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, String> partNameCol = new TableColumn<Part, String>();
    @FXML private TableColumn<Part, Integer> partStockCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, Integer> partInvCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, Double> partPriceCol = new TableColumn<Part, Double>();

    //Products
    @FXML private TableView<Product> productsTableView = new TableView<Product>();
    @FXML private TableColumn<Part, Integer> productIdCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, String> productNameCol = new TableColumn<Part, String>();
    @FXML private TableColumn<Part, Integer> productStockCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, Integer> productInvCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, Double> productPriceCol = new TableColumn<Part, Double>();

    //Buttons

    @FXML void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML void onActionPartAdd(ActionEvent event) throws IOException{
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddPart.fxml"));
        View_Controller.AddPartController controller = new View_Controller.AddPartController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        stage.setTitle("Add Part");
        stage.setScene(new Scene(root));
        stage.show();
        //System.out.println("Parts Add Button Clicked!");
    }

    @FXML void onActionPartSearch(ActionEvent event) throws IOException{
        String search = partSearchTxt.getText();
        ObservableList<Part> matches = Inventory.lookupPart(search);
        System.out.println(matches);
        if( matches == null || matches.isEmpty() ||search.length() == 0 ){
            //Display Alert
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Search Results");
            alert.setContentText("Searched for: " + search + "\r\nNo Matching Parts Found");
            partsTableView.setItems(Inventory.getAllParts());
            alert.show();
        } else {
            partsTableView.setItems(matches);
        }
    }

    @FXML void onActionProductAdd(ActionEvent event) throws IOException {
        System.out.println("Add Product Clicked!");
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddProduct.fxml"));
        View_Controller.AddProductController controller = new View_Controller.AddProductController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        stage.setTitle("Add Product");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML void onActionProductSearch(ActionEvent event) throws IOException{
        String search = productSearchTxt.getText();
        ObservableList<Product> matches = Inventory.lookupProduct(search);
        if( matches == null || matches.isEmpty() || search.length() == 0 ){
            //Display Alert
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Search Results");
            alert.setContentText("Searched for: " + search + "\r\nNo Matching Parts Found");
            productsTableView.setItems(Inventory.getAllProducts());
            alert.show();
        } else {
            productsTableView.setItems(matches);
        }
    }

    @FXML void onActionPartModify(ActionEvent event) throws IOException{
        try{
            Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
            int selectedIndex = Inventory.getAllParts().indexOf(selectedPart);
            System.out.println(selectedPart);
            System.out.println("Part Modify Clicked!");
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyPart.fxml"));
            View_Controller.ModifyPartController controller = new View_Controller.ModifyPartController(selectedIndex, selectedPart);
            loader.setController(controller);
            Parent root = loader.load();
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(IOException e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a valid Part");
            alert.setTitle("Selection Error");
            alert.show();
        }

    }

    @FXML void onActionPartDelete(ActionEvent event) throws Exception{
        System.out.println("Part Delete Clicked!");
        try{
            Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
            if( Inventory.deletePart(selectedPart) ){
                // @todo Display success message
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Deleted: " + selectedPart.getName());
                alert.show();
            } else if( selectedPart == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a valid Part");
                alert.setTitle("Selection Error");
                alert.show();
            }
            partsTableView.setItems(Inventory.getAllParts());
        }
        catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("No Product Selected Error");
            alert.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Parts
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partsTableView.setItems(Inventory.getAllParts());

        //Products
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("inv"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        productsTableView.setItems(Inventory.getAllProducts());
    }

    public MainScreenController(Inventory inv) {
        this.inv = inv;
    }
}
