package View_Controller;

import Model.Inventory;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    private Inventory inv;
    private Stage stage;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public AddProductController(Inventory inv) {
        this.inv = inv;
    }
}
