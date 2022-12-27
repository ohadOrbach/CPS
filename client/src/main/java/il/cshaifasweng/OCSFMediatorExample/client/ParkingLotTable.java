
package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;
import il.cshaifasweng.OCSFMediatorExample.server.ParkingPrices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ParkingLotTable implements Initializable {
    String updateType = "";
    int updateID = -1;

    TableView<ParkingPricesData> table = new TableView<ParkingPricesData>();

    public Button MainMenuButton;
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

    ObservableList<ParkingPricesData> pricesList;

    @Subscribe
    public void onReceivedPrices(ReceivedParkingPricesEvent event) throws IOException{
        ObservableList<ParkingPricesData> pricesList = null;
        List<ParkingPricesData> eventList = event.getPricesList();
        for(int i = 0; i < eventList.size(); i++){
            pricesList.add(eventList.get(i));
        }
        this.pricesList = FXCollections.observableArrayList(pricesList);
        table.setItems(pricesList);
        for (int i = 0; i < pricesList.size(); i++) {
            idList.getItems().add((pricesList.get(i)).getParkingLotId());
        }
        System.out.println("Received prices table\n");
    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }
    private ObservableList<ParkingPricesData> getUserList() {

//        // need to add creation of Observable<ParkingPrinces> list from the database
//
//        // only for example, should be deleted later
//        ParkingPrices ParkLot1 = new ParkingPrices(1, 8, 7);
//        ParkingPrices ParkLot2 = new ParkingPrices(2, 7, 7);
//        ParkingPrices ParkLot3 = new ParkingPrices(3, 7, 6);
//        ObservableList<ParkingPrices> list = FXCollections.observableArrayList(ParkLot1, ParkLot2, ParkLot3);
//        //end of example

        return pricesList;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<ParkingPricesData> pricesList =  FXCollections.observableArrayList(
        new ParkingPricesData(1, 10, 12),
        new ParkingPricesData(2, 10, 12));
        table.setItems(pricesList);
        idCol =  new TableColumn<>("parkingLotId");
        idCol.setCellValueFactory(new PropertyValueFactory("parkingLotId"));
        casualCol =  new TableColumn<>("parkingPrice");
        casualCol.setCellValueFactory(new PropertyValueFactory("parkingPrice"));
        orderedCol =  new TableColumn<>("orderedParkingPrice");
        orderedCol.setCellValueFactory(new PropertyValueFactory("orderedParkingPrice"));
        regSubCol =  new TableColumn<>("regularSubscriptionPrice");
        regSubCol.setCellValueFactory(new PropertyValueFactory("regularSubscriptionPrice"));
        multyCol =  new TableColumn<>("regularSubscriptionMultiCarsPrice");
        multyCol.setCellValueFactory(new PropertyValueFactory("regularSubscriptionMultiCarsPrice"));
        fullSubCol =  new TableColumn<>("fullySubscriptionPrice");
        fullSubCol.setCellValueFactory(new PropertyValueFactory("fullySubscriptionPrice"));

        table.getColumns().addAll(idCol, casualCol, orderedCol, regSubCol,multyCol ,fullSubCol );
        Vbox.getChildren().removeAll();
        Vbox.getChildren().addAll(table);


        assert typeList != null : "fx:id=\"typeList\" was not injected: check your FXML file 'primary.fxml'.";
        assert idList != null : "fx:id=\"idList\" was not injected: check your FXML file 'primary.fxml'.";

        typeList.getItems().add("Casual");
        typeList.getItems().add("Ordered");
    }

    @FXML
    void updatePrices(ActionEvent event) throws IOException {

        //database should be updated here

        ObservableList<ParkingPricesData> list = getUserList();

        // only for example, should be deleted later:
        for (ParkingPricesData park: list){
            if (park.getParkingLotId() == -1){
                throw new RuntimeException("Error: please choose id");
            }
            if (park.getParkingLotId() == updateID){
                if (updateType.equals("")){
                    throw new RuntimeException("Error: please choose type");
                }
                if(updateType.equals("Casual")){
                    park.setParkingPrice(Integer.parseInt(newPriceTxt.getText()));
                } else{
                    park.setOrderedParkingPrice(Integer.parseInt(newPriceTxt.getText()));
                }
            }
        }
        //end of example

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