package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.swing.*;
import java.io.Console;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;


public class StastisticalInformation {

    TableView<StastisticalInformationData> table = new TableView<StastisticalInformationData>();
    ObservableList<StastisticalInformationData> stastisticalInformationList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<StastisticalInformationData, Integer> CancledOrdersCol;
    @FXML
    private TableColumn<StastisticalInformationData, Integer> LateParkingCol;
    @FXML
    private TableColumn<StastisticalInformationData, Integer> actualOrdersCol;
    @FXML
    private TableColumn<StastisticalInformationData, Integer> idCol;
    @FXML
    private TableColumn<StastisticalInformationData, Integer> parkingLotIdCol;
    @FXML
    private TableColumn<StastisticalInformationData, String> parkingLotNameCol;
    @FXML
    private Button MainMenuButton;
    @FXML
    private ChoiceBox<String> comboBox;
    private String[] options = {"average","median"};
    @FXML
    private VBox Vbox;

    private List<StastisticalInformationData> eventList;

    @Subscribe
    public void onReceivedParkingList(ReceivedStastisticalInformationEvent event) throws IOException {
        eventList = event.getstastisticalInformationDataListDataList();
        for (int i = 0; i < eventList.size(); i++) {
            stastisticalInformationList.add(eventList.get(i));
        }
        buildListTable();
    }

    private void buildListTable() {
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        parkingLotIdCol.setCellValueFactory(new PropertyValueFactory("parkingLotId"));
        parkingLotNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        CancledOrdersCol.setCellValueFactory(new PropertyValueFactory("canceledOrders"));
        LateParkingCol.setCellValueFactory(new PropertyValueFactory("parkingLateNum"));
        actualOrdersCol.setCellValueFactory(new PropertyValueFactory("actualOrders"));


        table.getColumns().addAll(idCol, parkingLotIdCol, parkingLotNameCol, CancledOrdersCol, LateParkingCol, actualOrdersCol);
        table.setItems(stastisticalInformationList);
        Vbox.getChildren().clear();
        Vbox.getChildren().add(table);
    }


    @FXML
    void initialize() throws IOException {
        EventBus.getDefault().register(this);
        comboBox.getItems().addAll(options);
        comboBox.setOnAction(this::getOption);
    }

    public void getOption(ActionEvent event){
        String myOption = comboBox.getValue();
        List<StastisticalInformationData> newList;
        if(myOption.compareTo("average") == 0){
            newList = calcDailyAvgForThisWeek(eventList);
        }
        else{
            newList = calcDailyMedianForThisWeek(eventList);
        }
        stastisticalInformationList.clear();
        for (int i = 0; i < newList.size(); i++) {
            stastisticalInformationList.add(newList.get(i));
            System.out.println("heyyyyyy   "  + stastisticalInformationList.get(i).getActualOrders());
        }
        table.setItems(stastisticalInformationList);
        Vbox.getChildren().clear();
        Vbox.getChildren().add(table);
    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.history.remove(App.history.size() - 1);
        App.setRoot(App.history.get(App.history.size() - 1));
    }

    private List<StastisticalInformationData> calcDailyMedianForThisWeek(List<StastisticalInformationData> stastisticalInformationDataList){
        LocalDate now = LocalDate.now();
        List<StastisticalInformationData> weeklyAvgStastisticalInformationList = new ArrayList<>();
        List<Double> currentActualOrders = new ArrayList<>();
        List<Double> currentCancledOrders = new ArrayList<>();
        List<Double> currentLateParking = new ArrayList<>();
        StastisticalInformationData i=stastisticalInformationDataList.get(0);    //parking lot id counter
        for(StastisticalInformationData stastisticalInformation : stastisticalInformationDataList){
            if(stastisticalInformation.getParkingLotId() != i.getParkingLotId()){
                StastisticalInformationData s1 = new StastisticalInformationData(0, i.getParkingLotId(),i.getName(),i.getDate(),currentActualOrders.get((currentActualOrders.size()+1)/2-1),currentCancledOrders.get((currentCancledOrders.size()+1)/2-1),currentLateParking.get((currentLateParking.size()+1)/2-1));
                weeklyAvgStastisticalInformationList.add(s1);
                i = stastisticalInformation;
                currentActualOrders.clear();
                currentCancledOrders.clear();
                currentLateParking.clear();
            }
            if(DAYS.between(stastisticalInformation.getDate(),now) <= 7){
                System.out.println("nahardaDasd");
                currentActualOrders.add(stastisticalInformation.getActualOrders());
                Collections.sort(currentActualOrders);
                currentCancledOrders.add(stastisticalInformation.getCanceledOrders());
                Collections.sort(currentCancledOrders);
                currentLateParking.add(stastisticalInformation.getParkingLateNum());
                Collections.sort(currentLateParking);
            }
        }
        //add last statistical information
        StastisticalInformationData s1 = new StastisticalInformationData(0, i.getParkingLotId(),i.getName(),i.getDate(),currentActualOrders.get((currentActualOrders.size()+1)/2-1),currentCancledOrders.get((currentCancledOrders.size()+1)/2-1),currentLateParking.get((currentLateParking.size()+1)/2-1));
        weeklyAvgStastisticalInformationList.add(s1);

        return weeklyAvgStastisticalInformationList;

    }

    private List<StastisticalInformationData> calcDailyAvgForThisWeek(List<StastisticalInformationData> stastisticalInformationDataList){
        LocalDate now = LocalDate.now();
        List<StastisticalInformationData> weeklyAvgStastisticalInformationList = new ArrayList<>();
        StastisticalInformationData i=stastisticalInformationDataList.get(0);    //parking lot id counter
        double sumActualOrders=0;
        double sumCancledOrders =0;
        double sumLateParking =0;
        for(StastisticalInformationData stastisticalInformation : stastisticalInformationDataList){
            if(stastisticalInformation.getParkingLotId() != i.getParkingLotId()){
                //StastisticalInformationData s1 = new StastisticalInformationData(0, i.getParkingLotId(),i.getName(),i.getDate(),sumActualOrders/7,sumCancledOrders/7,sumLateParking/7);
                StastisticalInformationData s1 = new StastisticalInformationData(0, i.getParkingLotId(),i.getName(),i.getDate(),sumActualOrders/7,sumCancledOrders/7,sumLateParking/7);
                weeklyAvgStastisticalInformationList.add(s1);
                i = stastisticalInformation;
                sumActualOrders=0;
                sumCancledOrders =0;
                sumLateParking =0;
            }
            if(DAYS.between(stastisticalInformation.getDate(),now) <= 7){
                System.out.println("nahardaDasd");
                sumActualOrders+=stastisticalInformation.getActualOrders();
                sumCancledOrders+=stastisticalInformation.getCanceledOrders();
                sumLateParking+=stastisticalInformation.getParkingLateNum();
            }
        }
        //add last statistical information
        StastisticalInformationData s1 = new StastisticalInformationData(0, i.getParkingLotId(),i.getName(),i.getDate(),sumActualOrders/7,sumCancledOrders/7,sumLateParking/7);
        weeklyAvgStastisticalInformationList.add(s1);

        return weeklyAvgStastisticalInformationList;
    }


}