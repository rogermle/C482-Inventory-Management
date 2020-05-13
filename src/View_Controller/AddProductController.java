package View_Controller;

import Model.Inventory;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    private final Inventory inv;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public AddProductController(Inventory inv) {
        this.inv = inv;
    }
}
