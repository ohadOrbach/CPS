/**
 * Sample Skeleton for 'CostumerMainWindow.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.SubscriptionData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class CostumerMainWindow {

    @FXML
    private Text CostumerId;

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

    @FXML
    void goToComplain(ActionEvent event) throws IOException {
        App.history.add("Complaint");
        App.setRoot("Complaint");
    }

    @FXML
    void goToOrders(ActionEvent event) throws IOException {
        App.history.add("MainMenuOrder");
        App.setRoot("MainMenuOrder");
    }

    @FXML
    void goToSubscriptions(ActionEvent event) throws IOException {
        App.history.add("NewSubscription");
        App.setRoot("NewSubscription");
    }

    @FXML
    void LogOut(ActionEvent event) throws IOException {
        SimpleClient myClient = SimpleClient.getClient();
        myClient.sendToServer("logout costumer:"+App.costumer.getId());
        App.history.remove(App.history.size()-1);
        App.setRoot(App.history.get(App.history.size()-1));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnMode != null : "fx:id=\"btnMode\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert button1 != null : "fx:id=\"button1\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert button2 != null : "fx:id=\"button2\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert button3 != null : "fx:id=\"button3\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert goToLogIn != null : "fx:id=\"goToLogIn\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert imMode != null : "fx:id=\"imMode\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";
        assert timeTF != null : "fx:id=\"timeTF\" was not injected: check your FXML file 'CostumerMainWindow.fxml'.";

        CostumerId.setTextContent("Costumer Id: "+App.costumer.getId());


        LocalDate currentDate = LocalDate.now();
        if(!((App.costumer.getSubscriptions().isEmpty())))
        {
            Optional<SubscriptionData> expiredSubscription = App.costumer.getSubscriptions().keySet().stream()
                    .filter(date -> date.isBefore(currentDate))
                    .map(App.costumer.getSubscriptions()::get)
                    .findFirst();
            if (expiredSubscription.isPresent())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Expired Subscription");
                alert.setHeaderText("One of your subscriptions has expired");
                alert.setContentText("Please renew or cancel your subscription");
                alert.showAndWait();
            }

        }

    }




/*    SimpleClient myClient = SimpleClient.getClient();
        myClient.sendToServer("logout costumer:"+App.costumer.getId());
        App.setRoot("InitialWindow");*/



}
