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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    @FXML
    private TextField modifyId;

    @FXML
    private TextField modifyName;

    @FXML
    private TextField modifyInventory;

    @FXML
    private TextField modifyPrice;

    @FXML
    private TextField modifyMin;

    @FXML
    private TextField modifyMax;

    @FXML
    private TextField modifyPartSearchTxt;

    @FXML
    private TableView modifyProductsTable;

    // Parts Search

    @FXML private TableView<Part> modifyAddPartsTableView = new TableView<Part>();
    @FXML private TableColumn<Part, Integer> modifyPartIdCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, String> modifyPartNameCol = new TableColumn<Part, String>();
    @FXML private TableColumn<Part, Integer> modifyPartStockCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, Double> modifyPartPriceCol = new TableColumn<Part, Double>();

    // Assoc Parts
    @FXML private TableView<Part> modifyAssocPartsTableView = new TableView<Part>();
    @FXML private TableColumn<Part, Integer> modifyAssocPartsIdCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, String> modifyAssocPartsNameCol = new TableColumn<Part, String>();
    @FXML private TableColumn<Part, Integer> modifyAssocPartsStockCol = new TableColumn<Part, Integer>();
    @FXML private TableColumn<Part, Double> modifyAssocPriceCol = new TableColumn<Part, Double>();

    private Product product;
    private final int index;
    private Stage stage;
    private Alert alert;

    @FXML
    void onActionModifyPartSearch(ActionEvent event) {
        String search = modifyPartSearchTxt.getText();
        ObservableList<Part> matches = Inventory.lookupPart(search);
        if( matches == null || matches.isEmpty() ||search.length() == 0 ){
            //Display Alert
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Search Results");
            alert.setContentText("Searched for: " + search + "\r\nNo Matching Parts Found");
            modifyAddPartsTableView.setItems(Inventory.getAllParts());
            alert.show();
        } else {
            modifyAddPartsTableView.setItems(matches);
        }
    }

    @FXML
    void onActionModifyAddPart(ActionEvent event) {
        Part addPart = modifyAddPartsTableView.getSelectionModel().getSelectedItem();
        if( addPart != null) {
            this.product.addAssociatedPart(addPart);
        } else {
            //Display Error Message
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Product Error");
            alert.setContentText("No Part selected for Add!");
            alert.show();
        }
    }

    @FXML
    void onActionModifyDeletePart(ActionEvent event) {
        Part removePart = modifyAssocPartsTableView.getSelectionModel().getSelectedItem();
        if( removePart != null) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part?");
            alert.setContentText("Are you sure you want to delete " + removePart.getName() + " from this product?");
            Optional<ButtonType> option = alert.showAndWait();
            if(option.isPresent() && option.get()  == ButtonType.OK) {
                this.product.deleteAssociatedPart(removePart);
            }
        } else {
            //Display Error Message
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Product Error");
            alert.setContentText("No Part selected for Delete!");
            alert.show();
        }
    }

    @FXML
    void onActionModifyAddProductSave(ActionEvent event) throws IOException{
        int newInventory = Integer.parseInt(modifyInventory.getText());
        int newMin = Integer.parseInt(modifyMin.getText());
        int newMax = Integer.parseInt(modifyMax.getText());
        double newPrice = Double.parseDouble(modifyPrice.getText());

        if( this.product.isValid(newInventory, newMin, newMax)) {
            this.product.setName(modifyName.getText());
            this.product.setStock(newInventory);
            this.product.setMin(newMin);
            this.product.setMax(newMax);
            this.product.setPrice(newPrice);
            Inventory.updateProduct(this.index, this.product);
            // Redirect to Main Screen
            mainScreen(event);
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Product Error");
            alert.setContentText("Entered Stock Level: " + newInventory +"\r\nMinimum: " + newMin +
                    "\r\nMaximum: " + newMax);
            alert.show();
        }
    }

    @FXML
    void onActionModifyProductCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Modify Part?");
        alert.setContentText("Exit & Return to Main Screen?");
        Optional<ButtonType> option = alert.showAndWait();
        if(option.isPresent() && option.get()  == ButtonType.OK) {
            mainScreen(event);
        }
        // Reset Assoc Parts on ZERO saved changes
        modifyAssocPartsTableView.setItems(this.product.getAllAssociatedParts());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modifyId.setText("Auto-Gen: " + this.product.getId());
        modifyName.setText(this.product.getName());
        modifyInventory.setText(Integer.toString(this.product.getStock()));
        modifyPrice.setText(Double.toString(this.product.getPrice()));
        modifyMin.setText(Integer.toString(this.product.getMin()));
        modifyMax.setText(Integer.toString(this.product.getMax()));

        // Parts Search
        modifyPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyAddPartsTableView.setItems(Inventory.getAllParts());

        //Assoc Parts
        modifyAssocPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyAssocPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyAssocPartsStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyAssocPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyAssocPartsTableView.setItems(this.product.getAllAssociatedParts());
    }

    public ModifyProductController(int selectedIndex, Product selectedProduct) {
        this.product = selectedProduct;
        this.index = selectedIndex;
    }

    private void mainScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController();
        loader.setController(controller);
        Parent root = loader.load();
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
