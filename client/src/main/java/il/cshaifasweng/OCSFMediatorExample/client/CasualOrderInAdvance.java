package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.OrderData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import il.cshaifasweng.OCSFMediatorExample.client.MainMenuOrder;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.PrimaryController.isLightMode;

// TODO add TESTS fof inputs //
public class CasualOrderInAdvance {
    @FXML
    private Button Back;

    @FXML
    private Button BookParking;

    @FXML
    private TextField CarNumText;

    @FXML
    private TextField DepartureTimeText;

    @FXML
    private TextField EmailText;

    @FXML
    private TextField ParkingLotText;

    @FXML
    private TextField IdText;

    @FXML
    private TextField timeTF;

    @FXML
    private Button btnMode;

    @FXML
    private ImageView imMode;

    @FXML
    private AnchorPane parent;

    @FXML
    ChoiceBox choiceBox;

    String parkingLot;

    @FXML
    private VBox parkingVbox;

    @FXML
    private DatePicker arrivalDate;

    @FXML
    private DatePicker leavingData;

    @FXML
    private TextField ArrivalTF;


    ObservableList<ParkingLotData> parkingList = FXCollections.observableArrayList();

    @Subscribe
    public void onReceivedParkingList(ReceivedParkingLotListEvent event) throws IOException{
        List<ParkingLotData> eventList = event.getParkingLotDataList();
        for(int i = 0; i < eventList.size(); i++){
            parkingList.add(eventList.get(i));
        }
        buildParkingList();
    }


    private void buildParkingList() {
        for(ParkingLotData parkingLotData: parkingList){
            choiceBox.getItems().add(parkingLotData.getParkingLotName());
        }
        choiceBox.setOnAction((event) -> {
            Object selectedItem = choiceBox.getSelectionModel().getSelectedItem();
            parkingLot = selectedItem.toString();
        });
        parkingVbox.getChildren().add(choiceBox);
    }


    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.history.remove(App.history.size()-1);
        App.setRoot(App.history.get(App.history.size()-1));
    }

    @FXML
    void initialize() {
        if(!isLightMode){
            PrimaryController.setDarkMode(parent, imMode);
        }
        EventBus.getDefault().register(this);
        try {
            SimpleClient.getClient().sendToServer("#request: parking lots list");
        } catch (IOException e) {
            e.printStackTrace();
        }
        EmailText.clear();
        CarNumText.clear();
        DepartureTimeText.clear();
        IdText.clear();
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
    public void changeMode(ActionEvent event){
        PrimaryController.ChangeForAll(parent, imMode);
    }

    @FXML
    void sendAdvOrder(ActionEvent event) {
        try{
            System.out.println("sending oreder");
            OrderData orderData =
                    new OrderData(IdText.getText(), CarNumText.getText(), arrivalDate.getValue(), ArrivalTF.getText(), leavingData.getValue(), DepartureTimeText.getText(),
                            EmailText.getText(), "true", parkingLot);
            SimpleClient.getClient().sendToServer(orderData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
