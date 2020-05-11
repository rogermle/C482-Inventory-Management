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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    private Inventory inv;

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
            System.out.println("InHouse clicked!");
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
            System.out.println("OutSourced clicked!");
            this.isInHouse = false;
            inHouse.setSelected(false);
            outSourced.setSelected(true);
            inHouseToggleLbl.setText("Company Name");
            company.setPromptText("Company Name");
        }
    }

    @FXML
    public void onActionSave(ActionEvent event) throws IOException{
        int txtId = Inventory.getAllPartsCount();
        System.out.println(txtId);
        String txtName = this.name.getText();
        System.out.println(txtName);
        int txtInv = Integer.parseInt(this.inventory.getText());
        System.out.println(txtInv);
        Double txtPrice = Double.parseDouble(this.price.getText());
        System.out.println(txtPrice);
        int txtMin = Integer.parseInt(this.min.getText());
        System.out.println(txtMin);
        int txtMax = Integer.parseInt(this.max.getText());
        System.out.println(txtMax);

        if(this.isInHouse){
            int txtMachineId= Integer.parseInt(this.company.getText());
            Inventory.addPart(new InHouse(txtId, txtName, txtPrice, txtInv, txtMin, txtMax, txtMachineId));
            this.reset();
            System.out.println("Save InHouse!");
        } else {
            String txtCompany = company.getText();
            Inventory.addPart(new OutSourced(txtId, txtName, txtPrice, txtInv, txtMin, txtMax, txtCompany));
            this.reset();
            System.out.println("Save OutSourced!");
        }
        System.out.println("Save clicked!");
    }

    @FXML
    public void onActionCancel(ActionEvent event) throws IOException{
        System.out.println("Cancel clicked!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        System.out.println(inv);
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void reset() {
        this.name.setText("");
        this.inventory.setText("");
        this.price.setText("");
        this.min.setText("");
        this.max.setText("");
        this.company.setText("");
    }


}
