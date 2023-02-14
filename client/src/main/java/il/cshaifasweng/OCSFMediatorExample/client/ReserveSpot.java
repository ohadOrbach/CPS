package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

public class ReserveSpot {
    int row = -1;
    int col = -1;
    int lvl = -1;
    String update = "";
    String id = "";
    ParkingLotData parkingLot;
    ParkingData parkingSpace;

    @FXML // fx:id="BackToMain"
    private Button BackToMain; // Value injected by FXMLLoader

    @FXML // fx:id="UpdateBtn"
    private Button UpdateBtn; // Value injected by FXMLLoader

    @FXML // fx:id="btnMode"
    private Button btnMode; // Value injected by FXMLLoader

    @FXML // fx:id="columnCombobox"
    private ComboBox<Integer> columnCombobox; // Value injected by FXMLLoader

    @FXML // fx:id="errorLoginMassage"
    private Label errorLoginMassage; // Value injected by FXMLLoader

    @FXML // fx:id="idCombobox"
    private ComboBox<String> idCombobox; // Value injected by FXMLLoader

    @FXML // fx:id="imMode"
    private ImageView imMode; // Value injected by FXMLLoader

    @FXML // fx:id="levelCombobox"
    private ComboBox<Integer> levelCombobox; // Value injected by FXMLLoader

    @FXML // fx:id="parent"
    private AnchorPane parent; // Value injected by FXMLLoader

    @FXML // fx:id="rowCombobox"
    private ComboBox<Integer> rowCombobox; // Value injected by FXMLLoader

    @FXML // fx:id="timeTF"
    private TextField timeTF; // Value injected by FXMLLoader

    @FXML // fx:id="updateSpaceCombobox"
    private ComboBox<String> updateSpaceCombobox; // Value injected by FXMLLoader

    ObservableList<ParkingLotData> parkingLotList = FXCollections.observableArrayList();
    ObservableList<ParkingData> parkingList = FXCollections.observableArrayList();


    @Subscribe
    public void onReceivedParkingLotList(ReceivedParkingLotListEvent event) throws IOException {
        List<ParkingLotData> eventList = event.getParkingLotDataList();
        for(int i = 0; i < eventList.size(); i++){
            parkingLotList.add(eventList.get(i));
        }
        assert idCombobox != null : "fx:id=\"parkingLotComboBox\" was not injected: check your FXML file 'primary.fxml'.";
        for(ParkingLotData parkingLotData: parkingLotList){
            idCombobox.getItems().add(parkingLotData.getParkingLotName());
        }
    }
    @Subscribe
    public void onReceivedParkingList(ReceivedParkings event) throws IOException {
        List<ParkingData> eventList = event.getParkingDataList();
        for(int i = 0; i < eventList.size(); i++){
            parkingList.add(eventList.get(i));

        }

    }

    public void findParkingSpace()  throws IOException {
        if ((row == -1) || (col == -1) || (lvl == -1)){
            return;
        }
        for (ParkingData parkingData: parkingList) {
            if ((parkingData.getRow() == row) & (parkingData.getColumn() == col) & (parkingData.getDepth() == lvl)){
                parkingSpace = parkingData;
                return;
            }
        }
    }

    @FXML
    void initialize()
    {
        EventBus.getDefault().register(this);
        try {
            SimpleClient.getClient().sendToServer("#request: parking lots list");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!isLightMode){
            PrimaryController.setDarkMode(parent, imMode);
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
        id = App.employee.getBranch();
        for(int i = 1; i <= 3; i++){
            rowCombobox.getItems().add(i);
            levelCombobox.getItems().add(i);
        }
        updateSpaceCombobox.getItems().add("Working");
        updateSpaceCombobox.getItems().add("Reserved");
    }

    @FXML
    public void changeMode(ActionEvent event){
        PrimaryController.ChangeForAll(parent, imMode);
    }


    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.history.remove(App.history.size()-1);
        App.setRoot(App.history.get(App.history.size()-1));
    }

    @FXML
    void UpdateSpace(ActionEvent event) throws IOException {
        if(id.equals("")){
            errorLoginMassage.setText("Parking lot not selected");
            return;
        } else {
            for (ParkingLotData parkingLotData: parkingLotList){
                if (parkingLotData.getParkingLotName().equals(id)){
                    parkingLot = parkingLotData;
                }
            }

        }
        if(row == -1){
            errorLoginMassage.setText("Parking space's raw not selected");
            return;
        }
        if(col == -1){
            errorLoginMassage.setText("Parking space's column not selected");
            return;
        }
        if(lvl == -1){
            errorLoginMassage.setText("Parking space's level not selected");
            return;
        }
        if(update.equals("")){
            errorLoginMassage.setText("Parking space's update status not selected");
            return;
        }
        findParkingSpace();
        if (parkingSpace == null){
            errorLoginMassage.setText("Parking space not found");
        }

        if (parkingSpace.getStatus() == 1){
            errorLoginMassage.setText("Parking space is already occupied, need to remove the car first before set space to faulty");
            return;
        }
        if (parkingSpace.getStatus() == 1){
            errorLoginMassage.setText("Parking space is broken, please pick different spot");
            return;
        }
        if (update.equals("Working") & (parkingSpace.getStatus() == 0)){
            errorLoginMassage.setText("Parking space already is working");
            return;
        }
        if (update.equals("Reserved") & (parkingSpace.getStatus() == 2)){
            errorLoginMassage.setText("Parking space is already reserved");
            return;
        }
        SimpleClient myclient = SimpleClient.getClient();

        myclient.changeParkingStatus(id, row,  col,  lvl, update);

        App.setRoot("ReserveSpot");
    }

    @FXML
    void chooseUpdate(ActionEvent event) {
        update = updateSpaceCombobox.getSelectionModel().getSelectedItem();
    }

    @FXML
    void selectColumn(ActionEvent event) {
        col = columnCombobox.getSelectionModel().getSelectedItem();
    }

    @FXML
    void selectLevel(ActionEvent event) {
        lvl = levelCombobox.getSelectionModel().getSelectedItem();
    }

    @FXML
    void selectParkingID(ActionEvent event) {
        id = idCombobox.getSelectionModel().getSelectedItem();
        columnCombobox.getItems().clear();
        col = -1;
        for (ParkingLotData parkingLotData: parkingLotList){
            if (parkingLotData.getParkingLotName().equals(id)){
                parkingLot = parkingLotData;
            }
        }
        for (int i = 1; i <= parkingLot.getRowsNum(); i++){
            columnCombobox.getItems().add(i);
        }
        parkingList.clear();
        SimpleClient.getClient().getParkings(id);
    }

    @FXML
    void selectRow(ActionEvent event) {
        row = rowCombobox.getSelectionModel().getSelectedItem();
    }
}
