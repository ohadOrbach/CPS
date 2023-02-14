package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.OrderData;
import il.cshaifasweng.OCSFMediatorExample.entities.TrackingOrderData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import static il.cshaifasweng.OCSFMediatorExample.client.PrimaryController.isLightMode;

public class TrackingOrder {

    @FXML
    private Button BackBtn;

    @FXML
    private TextField CarNumberTF;

    @FXML
    private TextField IdTF;

    @FXML
    private Button SendBtn;

    @FXML
    private Button btnMode;

    @FXML
    private ImageView imMode;

    @FXML
    private AnchorPane parent;


    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        EventBus.getDefault().unregister(this);
        App.history.remove(App.history.size()-1);
        App.setRoot(App.history.get(App.history.size()-1));
    }

    @FXML
    void initialize() {
        EventBus.getDefault().register(this);
        if(!isLightMode){ PrimaryController.setDarkMode(parent, imMode);}
    }

    @FXML
    void sendTracking() {
        try{
            String carNumber = CarNumberTF.getText();

            //Track by ID only
            if(carNumber.isEmpty())
                carNumber = "-1";

            System.out.println("car num is: " + carNumber + "\n");
            TrackingOrderData trackingOrder =
                    new TrackingOrderData(Integer.parseInt(IdTF.getText()), Integer.parseInt(carNumber));
            SimpleClient.getClient().sendToServer(trackingOrder);
        } catch (IOException e) {
            e.printStackTrace();}
    }

    @FXML
    public void changeMode(ActionEvent event){
        PrimaryController.ChangeForAll(parent, imMode);
    }


    @Subscribe
    public void onTrackingEvent1(ReceivedOrderList orderList) {
        ArrayList<String> trackingInfo = orderList.getInfo();
        VBox vBox = new VBox();
        for (String infoStr : trackingInfo){
            Label infoText = new Label(infoStr);
            vBox.getChildren().add(infoText);
        }
        Scene scene = new Scene(vBox, 800, 200);
        Stage choiceStage = new Stage();
        choiceStage.setScene(scene);
        choiceStage.show();
        }


    // error alert message.
    @Subscribe
    public void sendTextError(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    String.format("%s", msg));
            alert.show();
        });
    }


}

