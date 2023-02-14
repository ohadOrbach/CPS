package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.PrimaryController.isLightMode;

public class Kiosk {

    String parkingLot;
    ObservableList<ParkingLotData> parkingList = FXCollections.observableArrayList();
    @FXML
    private Button MainMenuButton;
    @FXML
    private TextField ClientId;
    @FXML
    private TextField CarId;
    @FXML
    private Button OrderParkingBtn;
    @FXML
    private Button TakeInCarBtn;
    @FXML
    private Button TakeOutCarBtn;
    @FXML
    private Button btnMode;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private ImageView imMode;
    @FXML
    private AnchorPane parent;
    @FXML
    private TextField timeTF;

    @Subscribe
    public void onReceivedParkingList(ReceivedParkingLotListEvent event) throws IOException {
        List<ParkingLotData> eventList = event.getParkingLotDataList();
        for (int i = 0; i < eventList.size(); i++) {
            parkingList.add(eventList.get(i));
        }
        buildParkingList();
    }

    private void buildParkingList() {
        for (ParkingLotData parkingLotData : parkingList) {
            choiceBox.getItems().add(parkingLotData.getParkingLotName());
        }
        choiceBox.setOnAction((event) -> {
            Object selectedItem = choiceBox.getSelectionModel().getSelectedItem();
            parkingLot = selectedItem.toString();
        });
    }

    @FXML
    void initialize() {
        if (!isLightMode) {
            PrimaryController.setDarkMode(parent, imMode);
        }
        EventBus.getDefault().register(this);
        try {
            SimpleClient.getClient().sendToServer("#request: parking lots list");
        } catch (IOException e) {
            e.printStackTrace();
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            timeTF.setText(currentTime.format(dtf));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.history.remove(App.history.size() - 1);
        App.setRoot(App.history.get(App.history.size() - 1));
    }


    @FXML
    public void changeMode(ActionEvent event) {
        PrimaryController.ChangeForAll(parent, imMode);
    }

    @FXML
    void makeClientOrder(ActionEvent event) throws IOException {

        App.history.add("CasualOrder");
        App.setRoot("CasualOrder");

    }

    @FXML
    void takeInClientCar(ActionEvent event) {
        try {
            System.out.println("Take in Client Car \n");
            String clientId = ClientId.getText();
            String carId = CarId.getText();
            SimpleClient.getClient().sendToServer("#Take in client car:" + clientId + "," + carId + "," + parkingLot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void takeOutClientCar(ActionEvent event) {
        try {
            System.out.println("Take out Client Car \n");
            String clientId = ClientId.getText();
            String carId = CarId.getText();
            SimpleClient.getClient().sendToServer("#Take out client car:" + clientId + "," + carId + "," + parkingLot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
