package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    @FXML
    private RadioButton inHouse;

    @FXML
    private RadioButton outSourced;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField inventory;

    @FXML
    private TextField price;

    @FXML
    private TextField min;

    @FXML
    private TextField max;

    @FXML
    private TextField company;

    @FXML
    private Label inHouseToggleLbl;

    private Stage stage;

    private Parent scene;

    private final Inventory inv;

    private boolean isInHouse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.isInHouse = true;
        inHouse.setSelected(true);
        inHouseToggleLbl.setText("Machine ID");
        company.setPromptText("Machine ID");
    }

    public AddPartController(Inventory inv) {
        this.inv = inv;
    }

    //Actions

    @FXML
    public void onActionInHouse(ActionEvent event){
        if(inHouse.isSelected()){
            this.isInHouse = true;
            inHouse.setSelected(true);
            outSourced.setSelected(false);
            inHouseToggleLbl.setText("Machine ID");
            company.setPromptText("Machine ID");
        }
    }


    @FXML
    public void onActionOutSourced(ActionEvent event){
        if(outSourced.isSelected()){
            this.isInHouse = false;
            inHouse.setSelected(false);
            outSourced.setSelected(true);
            inHouseToggleLbl.setText("Company Name");
            company.setPromptText("Company Name");
        }
    }

    @FXML
    public void onActionSave(ActionEvent event) throws IOException{
        int txtId = Inventory.getAllPartsCount() + 1;
        String txtName = this.name.getText();
        int txtInv = Integer.parseInt(this.inventory.getText());
        double txtPrice = Double.parseDouble(this.price.getText());
        int txtMin = Integer.parseInt(this.min.getText());
        int txtMax = Integer.parseInt(this.max.getText());
        Part newPart;
        if(this.isInHouse){
            int txtMachineId= Integer.parseInt(this.company.getText());
            newPart = new InHouse(txtId, txtName, txtPrice, txtInv, txtMin, txtMax, txtMachineId);
        } else {
            String txtCompany = company.getText();
            newPart = new OutSourced(txtId, txtName, txtPrice, txtInv, txtMin, txtMax, txtCompany);
        }

        if( newPart.isValid(txtInv, txtMin, txtMax) ) {
            Inventory.addPart(newPart);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
            View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inv);
            loader.setController(controller);
            Parent root = loader.load();
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Inventory Error");
            alert.setContentText("Entered Stock Level: " + newPart.getStock() +"\r\nMinimum: " + newPart.getMin() +
                    "\r\nMaximum: " + newPart.getMax());
            alert.show();
        }
    }

    @FXML
    public void onActionCancel(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Add Part?");
        alert.setContentText("Exit & Return to Main Screen?");
        Optional<ButtonType> option = alert.showAndWait();
        if(option.get() == ButtonType.OK){
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


}
