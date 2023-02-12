package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;


public class ParkingLotList {
    String updateType = "";
    int updateID = -1;

    TableView<ParkingLotData> table = new TableView<ParkingLotData>();
    ObservableList<ParkingLotData> parkingList = FXCollections.observableArrayList();
    @FXML // fx:id="idCol"
    private TableColumn<ParkingLotData, Integer> idCol; // Value injected by FXMLLoader
    @FXML
    private TableColumn<ParkingLotData, Integer> rowsNum; // Value injected by FXMLLoader
    @FXML
    private TableColumn<ParkingLotData, Integer> size; // Value injected by FXMLLoader
    @FXML // fx:id="Vbox"
    private VBox Vbox; // Value injected by FXMLLoader
    @FXML
    private Button MainMenuButton;

    @Subscribe
    public void onReceivedParkingList(ReceivedParkingLotListEvent event) throws IOException {
        List<ParkingLotData> eventList = event.getParkingLotDataList();
        for (int i = 0; i < eventList.size(); i++) {
            parkingList.add(eventList.get(i));
        }
        buildListTable();
    }

    private void buildListTable() {
        idCol.setCellValueFactory(new PropertyValueFactory("parkingLotId"));
        rowsNum.setCellValueFactory(new PropertyValueFactory("rowsNum"));
        size.setCellValueFactory(new PropertyValueFactory("size"));

        table.getColumns().addAll(idCol, rowsNum, size);
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
        App.history.remove(App.history.size() - 1);
        App.setRoot(App.history.get(App.history.size() - 1));
    }

}