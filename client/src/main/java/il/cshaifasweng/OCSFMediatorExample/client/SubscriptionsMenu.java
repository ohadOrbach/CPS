/**
 * Sample Skeleton for 'SubscriptionsMenu.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static il.cshaifasweng.OCSFMediatorExample.client.PrimaryController.isLightMode;

public class SubscriptionsMenu {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnMode"
    private Button btnMode; // Value injected by FXMLLoader

    @FXML // fx:id="goToLogIn"
    private Button goToLogIn; // Value injected by FXMLLoader

    @FXML // fx:id="imMode"
    private ImageView imMode; // Value injected by FXMLLoader

    @FXML // fx:id="joinSub"
    private Button joinSub; // Value injected by FXMLLoader

    @FXML // fx:id="mySub"
    private Button mySub; // Value injected by FXMLLoader

    @FXML // fx:id="parent"
    private AnchorPane parent; // Value injected by FXMLLoader

    @FXML // fx:id="timeTF"
    private TextField timeTF; // Value injected by FXMLLoader



    @FXML
    void LogOut(ActionEvent event) throws IOException {
        App.history.remove(App.history.size() - 1);
        App.setRoot(App.history.get(App.history.size() - 1));

    }

    @FXML
    void changeMode(ActionEvent event) {

    }

    @FXML
    void joinSubscription(ActionEvent event) throws IOException {
        App.history.add("NewSubscription");
        App.setRoot("NewSubscription");

    }

    @FXML
    void showSubscriptions(ActionEvent event) throws IOException {
        App.history.add("SubscriptionsList");
        App.setRoot("SubscriptionsList");

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnMode != null : "fx:id=\"btnMode\" was not injected: check your FXML file 'SubscriptionsMenu.fxml'.";
        assert goToLogIn != null : "fx:id=\"goToLogIn\" was not injected: check your FXML file 'SubscriptionsMenu.fxml'.";
        assert imMode != null : "fx:id=\"imMode\" was not injected: check your FXML file 'SubscriptionsMenu.fxml'.";
        assert joinSub != null : "fx:id=\"joinSub\" was not injected: check your FXML file 'SubscriptionsMenu.fxml'.";
        assert mySub != null : "fx:id=\"mySub\" was not injected: check your FXML file 'SubscriptionsMenu.fxml'.";
        assert parent != null : "fx:id=\"parent\" was not injected: check your FXML file 'SubscriptionsMenu.fxml'.";
        assert timeTF != null : "fx:id=\"timeTF\" was not injected: check your FXML file 'SubscriptionsMenu.fxml'.";
        if(!isLightMode){ PrimaryController.setDarkMode(parent, imMode);}

    }

}

