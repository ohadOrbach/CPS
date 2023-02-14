package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;
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
import java.time.LocalDateTime;
import java.util.List;


public class PricesToConfirm {

    int updateID = -1;
    int compValue = 0;

    TableView<ParkingPricesData> table = new TableView<ParkingPricesData>();
    ObservableList<ParkingPricesData> plist = FXCollections.observableArrayList();
    @FXML // fx:id="TimeCol"
    private TableColumn<ParkingPricesData, Integer> ParkIdCol; // Value injected by FXMLLoader
    @FXML // fx:id="TimeCol"
    private TableColumn<ParkingPricesData, Double> ParkPriceCol; // Value injected by FXMLLoader
    @FXML // fx:id="DescCol"
    private TableColumn<ParkingPricesData, Double> OrderedParkPriceCol; // Value injected by FXMLLoader
    @FXML // fx:id="statusCol"
    private TableColumn<ParkingPricesData, Double> RSub; // Value injected by FXMLLoader
    @FXML // fx:id="statusCol"
    private TableColumn<ParkingPricesData, Double> MultiRSub; // Value injected by FXMLLoader
    @FXML // fx:id="statusCol"
    private TableColumn<ParkingPricesData, Double> FSub; // Value injected by FXMLLoader
    @FXML // fx:id="closeBtn"
    private Button closeBtn; // Value injected by FXMLLoader
    @FXML // fx:id="Vbox"
    private VBox Vbox; // Value injected by FXMLLoader
    @FXML // fx:id="idList"
    private ComboBox<Integer> idList; // Value injected by FXMLLoader
    @FXML
    private Button MainMenuButton;

    @Subscribe
    public void onReceivedComplaints(ReceivedConfirmaitnDataEvent event) throws IOException {
        plist.clear();
        List<ParkingPricesData> eventList = event.getPricesDataList();
        for (int i = 0; i < eventList.size(); i++) {
            plist.add(eventList.get(i));
        }
        buildComplaintsTable();
    }

    private void buildComplaintsTable() {
        table.setItems(FXCollections.observableArrayList());
        table.getColumns().clear();
        ParkIdCol.setCellValueFactory(new PropertyValueFactory("parkingLotId"));
        ParkPriceCol.setCellValueFactory(new PropertyValueFactory("parkingPrice"));
        OrderedParkPriceCol.setCellValueFactory(new PropertyValueFactory("orderedParkingPrice"));
        RSub.setCellValueFactory(new PropertyValueFactory("regularSubscriptionPrice"));
        MultiRSub.setCellValueFactory(new PropertyValueFactory("regularSubscriptionMultiCarsPrice"));
        FSub.setCellValueFactory(new PropertyValueFactory("fullySubscriptionPrice"));

        table.getColumns().addAll(ParkIdCol, ParkPriceCol, OrderedParkPriceCol, RSub, MultiRSub, FSub);
        table.setItems(plist);
        Vbox.getChildren().clear();
        Vbox.getChildren().add(table);
        assert idList != null : "fx:id=\"idList\" was not injected: check your FXML file 'primary.fxml'.";

        if (plist != null) {
            for (int i = 0; i < plist.size(); i++) {
                idList.getItems().add((plist.get(i)).getParkingLotId());
            }
        }
    }

    private ObservableList<ParkingPricesData> getUserList() {
        return plist;
    }

    @FXML
    void initialize() throws IOException {
        EventBus.getDefault().register(this);
        table.setEditable(true);
    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.history.remove(App.history.size() - 1);
        App.setRoot(App.history.get(App.history.size() - 1));
    }

    @FXML
    void closeComp(ActionEvent event) throws IOException {
        SimpleClient myclient = SimpleClient.getClient();
        System.out.format("Sending update to the server \n");
        myclient.approveChange(updateID);

    }

    @FXML
    void chooseFromIDList(ActionEvent event) {
        int intChosen = idList.getSelectionModel().getSelectedItem(); //id combo box
        updateID = intChosen;
    }


}