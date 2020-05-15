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

    private Alert alert;

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
            Part newPart;
            int stock = Integer.parseInt(modifyInventory.getText());
            int min = Integer.parseInt(modifyMin.getText());
            int max = Integer.parseInt(modifyMax.getText());
            if(this.isInHouse){
                newPart = new InHouse();
                setPart(newPart);
                ((InHouse) newPart).setMachineId(Integer.parseInt(modifyCompany.getText()));
            }
            else {
                newPart = new OutSourced();
                setPart(newPart);
                ((OutSourced) newPart).setCompanyName(modifyCompany.getText());
            }

            if(newPart.isValid(stock, min, max)){
                Inventory.updatePart(this.index, newPart);
                mainScreen(event);
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Inventory Error");
                alert.setContentText("Entered Stock Level: " + newPart.getStock() +"\r\nMinimum: " + newPart.getMin() +
                        "\r\nMaximum: " + newPart.getMax());
                alert.show();
            }

        }
        catch(Exception e){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Inventory Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void onActionModifyCancel(ActionEvent event) throws IOException {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Modify Part?");
        alert.setContentText("Exit & Return to Main Screen?");
        Optional<ButtonType> option = alert.showAndWait();
        if(option.isPresent() && option.get()  == ButtonType.OK) {
            mainScreen(event);
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

    private void setPart(Part modifyPart){
        modifyPart.setId(part.getId());
        modifyPart.setName(modifyName.getText());
        modifyPart.setStock(Integer.parseInt(modifyInventory.getText()));
        modifyPart.setPrice(Double.parseDouble(modifyPrice.getText()));
        modifyPart.setMin(Integer.parseInt(modifyMin.getText()));
        modifyPart.setMax(Integer.parseInt(modifyMax.getText()));
    }

    private void mainScreen(ActionEvent event) throws IOException{
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
