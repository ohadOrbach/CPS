package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.entities.SubscriptionData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CostumerMainMenu {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button MakeSubscription;

    @FXML
    private Button OrderParkingSpace;

    @FXML
    private Text costumerId;

    @FXML
    private Button logOut;

    @FXML
    void goToOrder(ActionEvent event) {

    }

    @FXML
    void goToSubscription(ActionEvent event) throws IOException {
        App.history.add("NewSubscription");
        App.setRoot("NewSubscription");
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        SimpleClient myClient = SimpleClient.getClient();
        myClient.sendToServer("logout costumer:"+App.costumer.getId());
        App.setRoot("InitialWindow");
    }

    @FXML
    void initialize() {
        assert MakeSubscription != null : "fx:id=\"MakeSubscription\" was not injected: check your FXML file 'CostumerMainMenu.fxml'.";
        assert OrderParkingSpace != null : "fx:id=\"OrderParkingSpace\" was not injected: check your FXML file 'CostumerMainMenu.fxml'.";
        assert costumerId != null : "fx:id=\"costumerId\" was not injected: check your FXML file 'CostumerMainMenu.fxml'.";
        assert logOut != null : "fx:id=\"logOut\" was not injected: check your FXML file 'CostumerMainMenu.fxml'.";

        costumerId.setText("Costumer Id: "+App.costumer.getId());

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

}
