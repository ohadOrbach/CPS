package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationData;
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
import java.util.Date;
import java.util.List;



public class StastisticalInformation {

    TableView<StastisticalInformationData> table = new TableView<StastisticalInformationData>();

    @FXML
    private TableColumn<StastisticalInformationData, Double> CancledOrdersCol;

    @FXML
    private TableColumn<StastisticalInformationData, Double> LateParkingCol;

    @FXML
    private TableColumn<StastisticalInformationData, Double> actualOrdersCol;

    @FXML
    private TableColumn<StastisticalInformationData, Integer> idCol;

    @FXML
    private TableColumn<StastisticalInformationData, Integer> parkingLotIdCol;

    @FXML
    private TableColumn<StastisticalInformationData, String> parkingLotNameCol;

    @FXML
    private Button MainMenuButton;

    @FXML
    private VBox Vbox;

    ObservableList<StastisticalInformationData> stastisticalInformationList = FXCollections.observableArrayList();

    @Subscribe
    public void onReceivedParkingList(ReceivedStastisticalInformationEvent event) throws IOException {
        List<StastisticalInformationData> eventList = event.getstastisticalInformationDataListDataList();
        for(int i = 0; i < eventList.size(); i++){
            stastisticalInformationList.add(eventList.get(i));
        }
        buildListTable();
    }

    private void buildListTable(){
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        parkingLotIdCol.setCellValueFactory(new PropertyValueFactory("parkingLotId"));
        parkingLotNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        CancledOrdersCol.setCellValueFactory(new PropertyValueFactory("canceledOrders"));
        LateParkingCol.setCellValueFactory(new PropertyValueFactory("parkingLateNum"));
        actualOrdersCol.setCellValueFactory(new PropertyValueFactory("actualOrders"));


        table.getColumns().addAll(idCol, parkingLotIdCol, parkingLotNameCol, CancledOrdersCol,LateParkingCol, actualOrdersCol);
        table.setItems(stastisticalInformationList);
        Vbox.getChildren().clear();
        Vbox.getChildren().add(table);
    }


    @FXML
    void initialize() throws IOException {
        EventBus.getDefault().register(this);
    }


    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.history.remove(App.history.size()-1);
        App.setRoot(App.history.get(App.history.size()-1));
    }

}
