
package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPrices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ParkingLotTable {
    String updateType = "";
    int updateID = -1;

    TableView<ParkingPrices> table = new TableView<ParkingPrices>();

    @FXML // fx:id="idCol"
    private TableColumn<ParkingPrices, Integer> idCol; // Value injected by FXMLLoader

    @FXML // fx:id="casualCol"
    private TableColumn<ParkingPrices, Double> casualCol; // Value injected by FXMLLoader

    @FXML // fx:id="fullSubCol"
    private TableColumn<ParkingPrices, Double> fullSubCol; // Value injected by FXMLLoader

    @FXML // fx:id="multyCol"
    private TableColumn<ParkingPrices, Double> multyCol; // Value injected by FXMLLoader

    @FXML // fx:id="orderedCol"
    private TableColumn<ParkingPrices, Double> orderedCol; // Value injected by FXMLLoader

    @FXML // fx:id="regSubCol"
    private TableColumn<ParkingPrices, Double> regSubCol; // Value injected by FXMLLoader

    @FXML // fx:id="updateBtn"
    private Button updateBtn; // Value injected by FXMLLoader

    @FXML // fx:id="Vbox"
    private VBox Vbox; // Value injected by FXMLLoader

    @FXML // fx:id="ParkingTable"
    private TableView<ParkingLotTable> ParkingTable; // Value injected by FXMLLoader

    @FXML // fx:id="typeList"
    private ComboBox<String> typeList; // Value injected by FXMLLoader

    @FXML // fx:id="idList"
    private ComboBox<Integer> idList; // Value injected by FXMLLoader

    @FXML // fx:id="newPriceTxt"
    private TextField newPriceTxt; // Value injected by FXMLLoader

    private ObservableList<ParkingPrices> getUserList() {

        // need to add creation of Observable<ParkingPrinces> list from the database

        // only for example, should be deleted later
        ParkingPrices ParkLot1 = new ParkingPrices(1, 8, 7);
        ParkingPrices ParkLot2 = new ParkingPrices(2, 7, 7);
        ParkingPrices ParkLot3 = new ParkingPrices(3, 7, 6);
        ObservableList<ParkingPrices> list = FXCollections.observableArrayList(ParkLot1, ParkLot2, ParkLot3);
        //end of example

        return list;
    }

    @FXML
    void initialize() throws IOException {

        idCol.setCellValueFactory(new PropertyValueFactory("parkingLotId"));
        casualCol.setCellValueFactory(new PropertyValueFactory("parkingPrice"));
        orderedCol.setCellValueFactory(new PropertyValueFactory("orderedParkingPrice"));
        regSubCol.setCellValueFactory(new PropertyValueFactory("regularSubscriptionPrice"));
        multyCol.setCellValueFactory(new PropertyValueFactory("regularSubscriptionMultiCarsPrice"));
        fullSubCol.setCellValueFactory(new PropertyValueFactory("fullySubscriptionPrice"));

        ObservableList<ParkingPrices> list = getUserList();
        table.setItems(list);
        table.getColumns().addAll(idCol, casualCol, orderedCol, regSubCol,multyCol ,fullSubCol );
        Vbox.getChildren().addAll(table);

        assert typeList != null : "fx:id=\"typeList\" was not injected: check your FXML file 'primary.fxml'.";
        assert idList != null : "fx:id=\"idList\" was not injected: check your FXML file 'primary.fxml'.";

        typeList.getItems().add("Casual");
        typeList.getItems().add("Ordered");
        for(int i = 0; i < list.size(); i++){
            idList.getItems().add((list.get(i)).getParkingLotId());
        }
    }

    @FXML
    void updatePrices(ActionEvent event) throws IOException {

        //database should be updated here

        ObservableList<ParkingPrices> list = getUserList();

        // only for example, should be deleted later:
        for (ParkingPrices park: list){
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