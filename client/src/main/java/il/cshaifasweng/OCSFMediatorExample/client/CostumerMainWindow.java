/**
 * Sample Skeleton for 'CostumerMainWindow.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class CostumerMainWindow {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnMode"
    private Button btnMode; // Value injected by FXMLLoader

    @FXML // fx:id="button1"
    private Button button1; // Value injected by FXMLLoader

    @FXML // fx:id="button2"
    private Button button2; // Value injected by FXMLLoader

    @FXML // fx:id="button3"
    private Button button3; // Value injected by FXMLLoader

    @FXML // fx:id="button4"
    private Button button4; // Value injected by FXMLLoader

    @FXML // fx:id="button5"
    private Button button5; // Value injected by FXMLLoader

    @FXML // fx:id="button6"
    private Button button6; // Value injected by FXMLLoader

    @FXML // fx:id="goToLogIn"
    private Button goToLogIn; // Value injected by FXMLLoader

    @FXML // fx:id="imMode"
    private ImageView imMode; // Value injected by FXMLLoader

    @FXML // fx:id="timeTF"
    private TextField timeTF; // Value injected by FXMLLoader

    @FXML
    void changeMode(ActionEvent event) {

    }

    @FXML
    void get(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnMode != null : "fx:id=\"btnMode\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert button1 != null : "fx:id=\"button1\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert button2 != null : "fx:id=\"button2\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert button3 != null : "fx:id=\"button3\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert button4 != null : "fx:id=\"button4\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert button5 != null : "fx:id=\"button5\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert button6 != null : "fx:id=\"button6\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert goToLogIn != null : "fx:id=\"goToLogIn\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert imMode != null : "fx:id=\"imMode\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert timeTF != null : "fx:id=\"timeTF\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";

    }

}
