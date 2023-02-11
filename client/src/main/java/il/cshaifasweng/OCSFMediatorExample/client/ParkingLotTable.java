
package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;
import il.cshaifasweng.OCSFMediatorExample.server.ParkingPrices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ParkingLotTable {
    String updateType = "";
    int updateID = -1;
    int newPrice = 0;

    TableView<ParkingPricesData> table = new TableView<ParkingPricesData>();

    @FXML // fx:id="idCol"
    private TableColumn<ParkingPricesData, Integer> idCol; // Value injected by FXMLLoader

    @FXML // fx:id="casualCol"
    private TableColumn<ParkingPricesData, Double> casualCol; // Value injected by FXMLLoader

    @FXML // fx:id="fullSubCol"
    private TableColumn<ParkingPricesData, Double> fullSubCol; // Value injected by FXMLLoader

    @FXML // fx:id="multyCol"
    private TableColumn<ParkingPricesData, Double> multyCol; // Value injected by FXMLLoader

    @FXML // fx:id="orderedCol"
    private TableColumn<ParkingPricesData, Double> orderedCol; // Value injected by FXMLLoader

    @FXML // fx:id="regSubCol"
    private TableColumn<ParkingPricesData, Double> regSubCol; // Value injected by FXMLLoader

    @FXML // fx:id="updateBtn"
    private Button updateBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Vbox"
    private VBox Vbox; // Value injected by FXMLLoader

    @FXML // fx:id="typeList"
    private ComboBox<String> typeList; // Value injected by FXMLLoader

    @FXML // fx:id="idList"
    private ComboBox<Integer> idList; // Value injected by FXMLLoader

    @FXML // fx:id="newPriceTxt"
    private TextField newPriceTxt; // Value injected by FXMLLoader

    ObservableList<ParkingPricesData> pricesList = FXCollections.observableArrayList();

    @FXML
    private Button MainMenuButton;

    @Subscribe
    public void onReceivedPrices(ReceivedParkingPricesEvent event) throws IOException{
        List<ParkingPricesData> eventList = event.getParkingPrices();
        for(int i = 0; i < eventList.size(); i++){
            pricesList.add(eventList.get(i));
        }
        buildPricesTable();
    }

    private void buildPricesTable(){
        idCol.setCellValueFactory(new PropertyValueFactory("parkingLotId"));
        casualCol.setCellValueFactory(new PropertyValueFactory("parkingPrice"));
        orderedCol.setCellValueFactory(new PropertyValueFactory(" "));
        regSubCol.setCellValueFactory(new PropertyValueFactory("regularSubscriptionPrice"));
        multyCol.setCellValueFactory(new PropertyValueFactory("regularSubscriptionMultiCarsPrice"));
        fullSubCol.setCellValueFactory(new PropertyValueFactory("fullySubscriptionPrice"));

        table.getColumns().addAll(idCol, casualCol, orderedCol, regSubCol,multyCol ,fullSubCol);
        table.setItems(pricesList);
        Vbox.getChildren().clear();
        Vbox.getChildren().add(table);
        assert typeList != null : "fx:id=\"typeList\" was not injected: check your FXML file 'primary.fxml'.";
        assert idList != null : "fx:id=\"idList\" was not injected: check your FXML file 'primary.fxml'.";

        typeList.getItems().add("Casual");
        typeList.getItems().add("Ordered");

        if(pricesList != null) {
            for (int i = 0; i < pricesList.size(); i++) {
                idList.getItems().add((pricesList.get(i)).getParkingLotId());
            }
        }
    }

    //update function
    public void changeCasualPrice(TableColumn.CellEditEvent editedCell){
        ParkingPricesData priceSelected = table.getSelectionModel().getSelectedItem();
        priceSelected.setParkingPrice(Double.parseDouble(editedCell.getNewValue().toString()));
    }

    public void changeOrderPrice(TableColumn.CellEditEvent editedCell){
        ParkingPricesData priceSelected = table.getSelectionModel().getSelectedItem();
        priceSelected.setOrderedParkingPrice(Double.parseDouble(editedCell.getNewValue().toString()));
    }



    private ObservableList<ParkingPricesData> getUserList() {
        return pricesList;
    }

    @FXML
    void initialize() throws IOException {
        EventBus.getDefault().register(this);

        //allow to change cells
        table.setEditable(true);
        casualCol.setCellFactory(TextFieldTableCell.<ParkingPricesData, Double>forTableColumn(new DoubleStringConverter()));
        orderedCol.setCellFactory(TextFieldTableCell.<ParkingPricesData, Double>forTableColumn(new DoubleStringConverter()));

    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.history.remove(App.history.size()-1);
        App.setRoot(App.history.get(App.history.size()-1));
    }

    @FXML
    void updatePrices(ActionEvent event) throws IOException {
        try{
            newPrice = Integer.parseInt(newPriceTxt.getText());
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        SimpleClient myclient=SimpleClient.getClient();
        System.out.format("Sending update to the server \n");
        myclient.changePrice(newPrice, updateID, updateType);
        //database should be updated here

        ObservableList<ParkingPricesData> list = getUserList();

        // only for example, should be deleted later:

        table.setItems(list);
        Vbox.getChildren().addAll(table);

    }

    @FXML
    void chooseFromIDList(ActionEvent event) {
        int intChosen = idList.getSelectionModel().getSelectedItem(); //id combo box
        updateID = intChosen;
    }

    @FXML
    void chooseFromTypeList(ActionEvent event) {
        String TypeChosen = typeList.getSelectionModel().getSelectedItem();// type combo box
        updateType = TypeChosen;
    }

}