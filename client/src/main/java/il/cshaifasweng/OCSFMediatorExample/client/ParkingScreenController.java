package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParkingScreenController {
    @FXML
    private ComboBox<String> parkingLotComboBox;

    @FXML
    private Button updateButton;
    @FXML
    private VBox parkingVbox;
    @FXML
    private HBox hb1;
    @FXML
    private GridPane parkingGridPane;

    private String selectedParkingLotData;
    ObservableList<ParkingLotData> parkingLotList = FXCollections.observableArrayList();
    ObservableList<ParkingData> parkingList = FXCollections.observableArrayList();


    @Subscribe
    public void onReceivedParkingList(ReceivedParkingLotListEvent event) throws IOException{
        List<ParkingLotData> eventList = event.getParkingLotDataList();
        for(int i = 0; i < eventList.size(); i++){
            parkingLotList.add(eventList.get(i));
        }
        buildScreen();
    }

    public void buildScreen(){
        assert parkingLotComboBox != null : "fx:id=\"parkingLotComboBox\" was not injected: check your FXML file 'primary.fxml'.";
        for(ParkingLotData parkingLotData: parkingLotList){
            parkingLotComboBox.getItems().add(parkingLotData.getParkingLotName());
        }
        updateButton.setOnAction(event -> updateParkingLot());
    }
    @FXML
    void initialize() throws IOException {
        EventBus.getDefault().register(this);
        try {
            SimpleClient.getClient().sendToServer("#request: parking lots list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void updateParkingLot() {
        selectedParkingLotData = (String) parkingLotComboBox.getSelectionModel().getSelectedItem();
        System.out.println("name is"+selectedParkingLotData+"\n");
        SimpleClient myclient = SimpleClient.getClient();
        myclient.getParkings(selectedParkingLotData);

    }
    @Subscribe
    public void updateScreen(ReceivedParkings event) throws IOException{
        List<ParkingData> eventList = event.getParkingDataList();
        for(int i = 0; i < eventList.size(); i++){
            parkingList.add(eventList.get(i));
        }
        parkingGridPane.getChildren().clear();
        for (int i = 0; i < parkingList.size(); i++) {
            ParkingData parkingData = parkingList.get(i);
            Label label = new Label(String.valueOf(parkingData.getId()));
            label.setMinSize(50, 50);
            label.setMaxSize(50, 50);
            if (parkingData.getStatus() == 0) {
                label.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (parkingData.getStatus() == 1) {
                label.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (parkingData.getStatus() == 2) {
                label.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                label.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            int cols = parkingList.size();//need to divide by 9 after parking lots wil be correct
            parkingGridPane.add(label, i % cols, i / cols);
        }
    }
}

