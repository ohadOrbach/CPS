/**
 * Sample Skeleton for 'SubscriptionsList.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.entities.ComplaintData;
import il.cshaifasweng.OCSFMediatorExample.entities.SubscriptionData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class SubscriptionsList {

    TableView<SubscriptionData> table = new TableView<SubscriptionData>();
    ObservableList<SubscriptionData> subscriptionsList = FXCollections.observableArrayList();

    @FXML // fx:id="idCol"
    private TableColumn<SubscriptionData, Integer> idCol; // Value injected by FXMLLoader

    @FXML // fx:id="endingTimeCol"
    private TableColumn<SubscriptionData, LocalDate> endingTimeCol; // Value injected by FXMLLoader

    @FXML // fx:id="LicencePlateCol"
    private TableColumn<SubscriptionData, String> LicencePlateCol; // Value injected by FXMLLoader

    @FXML // fx:id="ParkingLotCol"
    private TableColumn<SubscriptionData, String> ParkingLotCol; // Value injected by FXMLLoader

    @FXML // fx:id="typeCol"
    private TableColumn<SubscriptionData, String> typeCol; // Value injected by FXMLLoader

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="MainMenuButton"
    private Button MainMenuButton; // Value injected by FXMLLoader

    @FXML // fx:id="Vbox"
    private VBox Vbox; // Value injected by FXMLLoader

    @FXML // fx:id="idList"
    private ComboBox<String> idList; // Value injected by FXMLLoader

    @FXML // fx:id="renew"
    private Button renew; // Value injected by FXMLLoader

    @FXML // fx:id="subscriptions"
    private TableView<SubscriptionData> subscriptions; // Value injected by FXMLLoader

    @FXML
    void chooseFromIDList(ActionEvent event) {

    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.history.remove(App.history.size() - 1);
        App.setRoot(App.history.get(App.history.size() - 1));
    }

    @FXML
    void renew(ActionEvent event) throws IOException {
        if(idList.getValue().isEmpty())
        {
            return;
        }

        if(!idList.getValue().isEmpty())
        {
            SimpleClient myClient = SimpleClient.getClient();
            myClient.sendToServer("renew subscription:"+idList.getValue());
        }
    }

    @Subscribe
    public void renewAnswer(String event) throws IOException {

        System.out.println("renew success");

        String[] args = (event.split(":")[1]).split(",");
        LocalDate oldEnd = LocalDate.parse(args[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate newEnd = LocalDate.parse(args[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        HashMap<String, SubscriptionData> subMap = App.costumer.subscriptions.get(oldEnd);
        SubscriptionData sub = subMap.get(args[0]);
        sub.setEndingDate(newEnd);
        subMap.remove(args[0]);
        App.costumer.addSubscription(sub);

        App.setRoot("SubscriptionsList");
    }

    private void buildSubscriptionsTable() {
        idCol.setCellValueFactory(new PropertyValueFactory("subscriptionId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        LicencePlateCol.setCellValueFactory(new PropertyValueFactory<>("carNum"));
        ParkingLotCol.setCellValueFactory(new PropertyValueFactory<>("parkingLot"));
        endingTimeCol.setCellValueFactory(new PropertyValueFactory<>("endingDate"));

        table.getColumns().addAll(idCol,typeCol, LicencePlateCol, endingTimeCol,ParkingLotCol);
        table.setItems(subscriptionsList);
        Vbox.getChildren().clear();
        Vbox.getChildren().addAll(table);
        assert idList != null : "fx:id=\"idList\" was not injected: check your FXML file 'primary.fxml'.";

        if (subscriptionsList != null) {
            if (subscriptionsList != null) {
                idList.getItems().clear(); // clear previous items if any
                for (int i = 0; i < subscriptionsList.size(); i++) {
                    idList.getItems().add(subscriptionsList.get(i).getSubscriptionId()); // add subscription ID
                }
            }
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert LicencePlateCol != null : "fx:id=\"LicencePlateCol\" was not injected: check your FXML file 'SubscriptionsList.fxml'.";
        assert MainMenuButton != null : "fx:id=\"MainMenuButton\" was not injected: check your FXML file 'SubscriptionsList.fxml'.";
        assert ParkingLotCol != null : "fx:id=\"ParkingLotCol\" was not injected: check your FXML file 'SubscriptionsList.fxml'.";
        assert Vbox != null : "fx:id=\"Vbox\" was not injected: check your FXML file 'SubscriptionsList.fxml'.";
        assert endingTimeCol != null : "fx:id=\"endingTimeCol\" was not injected: check your FXML file 'SubscriptionsList.fxml'.";
        assert idCol != null : "fx:id=\"idCol\" was not injected: check your FXML file 'SubscriptionsList.fxml'.";
        assert idList != null : "fx:id=\"idList\" was not injected: check your FXML file 'SubscriptionsList.fxml'.";
        assert renew != null : "fx:id=\"renew\" was not injected: check your FXML file 'SubscriptionsList.fxml'.";
        assert subscriptions != null : "fx:id=\"subscriptions\" was not injected: check your FXML file 'SubscriptionsList.fxml'.";
        assert typeCol != null : "fx:id=\"typeCol\" was not injected: check your FXML file 'SubscriptionsList.fxml'.";

        EventBus.getDefault().register(this);
        table.setEditable(true);


        for(HashMap<String, SubscriptionData> subMap : App.costumer.getSubscriptions().values())
        {
            for(SubscriptionData sub : subMap.values())
            {
                subscriptionsList.add(sub);
                System.out.println(sub.getSubscriptionId());
            }
        }

        buildSubscriptionsTable();

    }

}
