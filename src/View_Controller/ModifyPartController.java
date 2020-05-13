package View_Controller;

import Model.InHouse;
import Model.OutSourced;
import Model.Inventory;
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

public class ModifyPartController implements Initializable {

    @FXML
    private RadioButton modifyInHouse;

    @FXML
    private RadioButton modifyOutSourced;

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
    private TextField modifyCompany;

    @FXML
    private Label modifyInHouseToggleLbl;

    private Stage stage;

    private Parent scene;

    private Inventory inv;

    private boolean isInHouse;

    private final Part part;

    private final int index;

    @FXML
    void onActionModifyInHouse(ActionEvent event) {
        if(modifyInHouse.isSelected()){
            this.isInHouse = true;
            modifyInHouse.setSelected(true);
            modifyOutSourced.setSelected(false);
            modifyInHouseToggleLbl.setText("Machine ID");
            modifyCompany.setPromptText("Machine ID");
        }
    }

    @FXML
    void onActionModifyOutSourced(ActionEvent event){
        if(modifyOutSourced.isSelected()){
            this.isInHouse = false;
            modifyInHouse.setSelected(false);
            modifyOutSourced.setSelected(true);
            modifyInHouseToggleLbl.setText("Company Name");
            modifyCompany.setPromptText("Company Name");
        }
    }

    @FXML
    void onActionModifySave(ActionEvent event){
        try{
            if(modifyInHouse.isSelected()){
                InHouse newPart = new InHouse();
                newPart.setId(part.getId());
                newPart.setName(modifyName.getText());
                newPart.setStock(Integer.parseInt(modifyInventory.getText()));
                newPart.setPrice(Double.parseDouble(modifyPrice.getText()));
                newPart.setMin(Integer.parseInt(modifyMin.getText()));
                newPart.setMax(Integer.parseInt(modifyMax.getText()));
                newPart.setMachineId(Integer.parseInt(modifyCompany.getText()));
                Inventory.updatePart(this.index, newPart);
            }
            else {
                OutSourced newPart = new OutSourced();
                newPart.setId(part.getId());
                newPart.setName(modifyName.getText());
                newPart.setStock(Integer.parseInt(modifyInventory.getText()));
                newPart.setPrice(Double.parseDouble(modifyPrice.getText()));
                newPart.setMin(Integer.parseInt(modifyMin.getText()));
                newPart.setMax(Integer.parseInt(modifyMax.getText()));
                newPart.setCompanyName(modifyCompany.getText());
                Inventory.updatePart(this.index, newPart);
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
            View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inv);
            loader.setController(controller);
            Parent root = loader.load();
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onActionModifyCancel(ActionEvent event) throws IOException {
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

    public ModifyPartController(int index, Part part){
        this.part = part;
        this.index = index;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modifyId.setText("Auto-Gen: " + part.getId());
        modifyName.setText(part.getName());
        modifyInventory.setText(Integer.toString(part.getStock()));
        modifyPrice.setText(Double.toString(part.getPrice()));
        modifyMin.setText(Integer.toString(part.getMin()));
        modifyMax.setText(Integer.toString(part.getMax()));

        if(part instanceof InHouse){
            modifyInHouse.setSelected(true);
            modifyInHouseToggleLbl.setText("Machine ID");
            modifyCompany.setText(Integer.toString(((InHouse) part).getMachineId()));
        } else {
            modifyOutSourced.setSelected(true);
            modifyInHouseToggleLbl.setText("Company Name");
            modifyCompany.setText(((OutSourced) part).getCompanyName());
        }

    }
}
