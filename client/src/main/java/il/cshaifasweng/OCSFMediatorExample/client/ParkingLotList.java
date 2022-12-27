
package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;
import il.cshaifasweng.OCSFMediatorExample.server.ParkingPrices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ParkingLotList {
    String updateType = "";
    int updateID = -1;

    TableView<ParkingLotData> table = new TableView<ParkingLotData>();

    @FXML // fx:id="idCol"
    private TableColumn<ParkingLotData, Integer> idCol; // Value injected by FXMLLoader

    @FXML // fx:id="casualCol"
    private TableColumn<ParkingLotData, Integer> rows; // Value injected by FXMLLoader

    @FXML // fx:id="fullSubCol"
    private TableColumn<ParkingLotData, Integer> size; // Value injected by FXMLLoader


    @FXML // fx:id="Vbox"
    private VBox Vbox; // Value injected by FXMLLoader

    @FXML
    private Button MainMenuButton;

    ObservableList<ParkingLotData> parkingList = FXCollections.observableArrayList();

    @Subscribe
    public void onReceivedParkingList(ReceivedParkingLotListEvent event) throws IOException{
        List<ParkingLotData> eventList = event.getParkingLotDataList();
        System.out.println("in !\n");
        for(int i = 0; i < eventList.size(); i++){
            parkingList.add(eventList.get(i));
            System.out.println("in !\n");
        }
        buildListTable();
    }

    private void buildListTable(){
        idCol.setCellValueFactory(new PropertyValueFactory("parkingLotId"));
        rows.setCellValueFactory(new PropertyValueFactory("rows"));
        size.setCellValueFactory(new PropertyValueFactory("size"));

        table.getColumns().addAll(idCol, rows, size);
        table.setItems(parkingList);
        Vbox.getChildren().clear();
        Vbox.getChildren().add(table);
    }


    @FXML
    void initialize() throws IOException {
        EventBus.getDefault().register(this);
    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

}