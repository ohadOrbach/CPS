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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

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
        EventBus.getDefault().unregister(this);
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
        arrivalDate.setValue(LocalDate.now());
        leavingData.setValue(LocalDate.now());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            timeTF.setText(currentTime.format(dtf));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        leavingData.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        arrivalDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }



    @FXML
    public void changeMode(ActionEvent event){
        PrimaryController.ChangeForAll(parent, imMode);
    }

    @FXML
    void sendAdvOrder(ActionEvent event) {
        try{
            if(!testInput())
                return;
            System.out.println("sending oreder");
            OrderData orderData =
                    new OrderData(IdText.getText(), CarNumText.getText(), arrivalDate.getValue(), ArrivalTF.getText(), leavingData.getValue(), DepartureTimeText.getText(),
                            EmailText.getText(), "true", parkingLot);
            SimpleClient.getClient().sendToServer(orderData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean testInput() {
        // check time format HH:MM
        if (!Pattern.matches("^([0-1][0-9]|2[0-3]):[0-5][0-9]$", DepartureTimeText.getText())) {
            sendTextError("Incorrect Departure Time format, please try again (HH:mm)");
            return false;
        }

        else if (!Pattern.matches("^([0-1][0-9]|2[0-3]):[0-5][0-9]$", ArrivalTF.getText())) {
            sendTextError("Incorrect Arrival Time format, please try again (HH:mm)");
            return false;
        }

        LocalTime parsedTime = LocalTime.parse(DepartureTimeText.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime arrivalTime = LocalTime.parse(ArrivalTF.getText(), DateTimeFormatter.ofPattern("HH:mm"));
        // test ID - 9 digits.
        if (!Pattern.matches("[0-9]{9}", IdText.getText())) {
            sendTextError("Incorrect ID, please try again (9 digits ONLY)");
            return false;
        }

            // test carNum - only digits.
        else if (!Pattern.matches("[0-9]+", CarNumText.getText())) {
            sendTextError("Incorrect car number, please try again (numbers ONLY)");
            return false;
        }

            // test email - chars + @ + dom name.
        else if (!Pattern.matches("^(.+)@(\\S+)$", EmailText.getText())) {
            sendTextError("Incorrect Email, please try again (enter (username)@(domain Name..) format)");
            return false;
        }

            // check that leaving time > current time.
        else if(parsedTime.isBefore(LocalTime.now()) && leavingData.getValue().equals(LocalDate.now())) {
            sendTextError("Oh no! The departure time is before now, are you planning time travel?");
            return false;
        }

            // test that arrival time < leaving time.
        else if(parsedTime.isBefore(arrivalTime) && leavingData.getValue().isEqual(arrivalDate.getValue())) {
            sendTextError("Oh no! The arrival time is before departure time, are you planning time travel?");
            return false;
        }

            // test that arrival date >= leaving date.
        else if(leavingData.getValue().isBefore(arrivalDate.getValue())) {
            sendTextError("Oh no! The departure date is before the arrival date, the system does not support time travel");
            return false;
        }

        return true;

    }

    public void sendTextError(String text) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    String.format("%s", text), ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
        });
    }

}