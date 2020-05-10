package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    private Stage stage;

    private Parent scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //Actions

    @FXML
    public void onActionInHouse(ActionEvent event){
        System.out.println("InHouse clicked!");
    }

    @FXML
    public void onActionOutSourced(ActionEvent event){
        System.out.println("OutSourced clicked!");
    }

    @FXML
    public void onActionSave(ActionEvent event){
        System.out.println("Save clicked!");
    }

    @FXML
    public void onActionCancel(ActionEvent event) throws IOException{
        System.out.println("Cancel clicked!");
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(scene));
        stage.show();
    }


}
