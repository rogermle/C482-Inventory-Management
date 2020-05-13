package View_Controller;

import Model.Inventory;
import Model.Product;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    private Product product;
    private final int index;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public ModifyProductController(int selectedIndex, Product selectedProduct) {
        this.product = selectedProduct;
        this.index = selectedIndex;
    }
}
