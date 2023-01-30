
/**
 * Sample Skeleton for 'NewSubscription.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

        import java.io.IOException;
        import java.net.URL;
        import java.time.LocalDate;
        import java.time.format.DateTimeFormatter;
        import java.util.ArrayList;
        import java.util.ResourceBundle;
        import java.util.Vector;

        import il.cshaifasweng.OCSFMediatorExample.entities.SubscriptionData;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.CheckBox;
        import javafx.scene.control.ChoiceBox;
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.TextField;
        import javafx.scene.text.Text;
        import org.greenrobot.eventbus.EventBus;
        import org.greenrobot.eventbus.Subscribe;

public class NewSubscription {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="CarLicencePlateText"
    private Text CarLicencePlateText; // Value injected by FXMLLoader

    @FXML // fx:id="Check"
    private CheckBox regular; // Value injected by FXMLLoader

    @FXML // fx:id="ParkingLotText"
    private Text ParkingLotText; // Value injected by FXMLLoader

    @FXML // fx:id="Register"
    private Button Register; // Value injected by FXMLLoader

    @FXML // fx:id="expectedDailyLeavingTime"
    private ChoiceBox<String> expectedDailyLeavingTime; // Value injected by FXMLLoader

    @FXML // fx:id="expectedDailyLeavingTimeText"
    private Text expectedDailyLeavingTimeText; // Value injected by FXMLLoader

    @FXML // fx:id="full"
    private CheckBox full; // Value injected by FXMLLoader

    @FXML // fx:id="licencePlate"
    private TextField licencePlate; // Value injected by FXMLLoader

    @FXML // fx:id="parkingLot"
    private ChoiceBox<?> parkingLot; // Value injected by FXMLLoader

    @FXML // fx:id="subscriptionStartTimeText"
    private Text subscriptionStartTimeText; // Value injected by FXMLLoader

    @FXML // fx:id="time"
    private DatePicker time; // Value injected by FXMLLoader

    @FXML
    private Text subResult;

    @FXML
    private Button back;

    @FXML
    void full(ActionEvent event)
    {
        full.setSelected(true);
        regular.setSelected(false);
        expectedDailyLeavingTimeText.setVisible(false);
        expectedDailyLeavingTime.setVisible(false);
        parkingLot.setVisible(false);
        ParkingLotText.setVisible(false);
    }

    @FXML
    void getBack(ActionEvent event) throws IOException {
        App.history.remove(App.history.size()-1);
        App.setRoot(App.history.get(App.history.size()-1));
    }

    @FXML
    void subscribeAttempt(ActionEvent event) throws IOException {
        SimpleClient myClient = SimpleClient.getClient();
        String dateString = time.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if(full.isSelected())
        {
            myClient.sendToServer("new subscription full:"+App.costumer.getId()+","+licencePlate.getText()+","+dateString);
        }
        else
        {
            myClient.sendToServer("new subscription regular:"+App.costumer.getId()+","+licencePlate.getText()+
                    ","+dateString+","+parkingLot.getValue()+","+expectedDailyLeavingTime.getValue());
        }
    }

    @Subscribe
    public void subscriptionAttempt(SubscriptionData event) throws IOException
    {
        App.costumer.addSubscription(event);
        subResult.setText("registration succeeded");

    }

    @FXML
    void regular(ActionEvent event)
    {
        full.setSelected(false);
        regular.setSelected(true);
        expectedDailyLeavingTimeText.setVisible(true);
        expectedDailyLeavingTime.setVisible(true);
        parkingLot.setVisible(true);
        ParkingLotText.setVisible(true);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert CarLicencePlateText != null : "fx:id=\"CarLicencePlateText\" was not injected: check your FXML file 'NewSubscription.fxml'.";
        assert regular != null : "fx:id=\"Check\" was not injected: check your FXML file 'NewSubscription.fxml'.";
        assert ParkingLotText != null : "fx:id=\"ParkingLotText\" was not injected: check your FXML file 'NewSubscription.fxml'.";
        assert Register != null : "fx:id=\"Register\" was not injected: check your FXML file 'NewSubscription.fxml'.";
        assert expectedDailyLeavingTime != null : "fx:id=\"expectedDailyLeavingTime\" was not injected: check your FXML file 'NewSubscription.fxml'.";
        assert expectedDailyLeavingTimeText != null : "fx:id=\"expectedDailyLeavingTimeText\" was not injected: check your FXML file 'NewSubscription.fxml'.";
        assert full != null : "fx:id=\"full\" was not injected: check your FXML file 'NewSubscription.fxml'.";
        assert licencePlate != null : "fx:id=\"licencePlate\" was not injected: check your FXML file 'NewSubscription.fxml'.";
        assert parkingLot != null : "fx:id=\"parkingLot\" was not injected: check your FXML file 'NewSubscription.fxml'.";
        assert subscriptionStartTimeText != null : "fx:id=\"subscriptionStartTimeText\" was not injected: check your FXML file 'NewSubscription.fxml'.";
        assert time != null : "fx:id=\"time\" was not injected: check your FXML file 'NewSubscription.fxml'.";
        assert subResult != null : "fx:id=\"subResult\" was not injected: check your FXML file 'NewSubscription.fxml'.";
        EventBus.getDefault().register(this);

        String[] hours = new String[24];
        for(int i = 0; i < 24; i++)
        {
            hours[i]=(String.format("%02d:00", i));
        }

        expectedDailyLeavingTime.getItems().addAll(hours);


    }

}

