package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.App;
import il.cshaifasweng.OCSFMediatorExample.client.PrimaryController;
import il.cshaifasweng.OCSFMediatorExample.client.ReceivedParkingLotListEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.PrimaryController.isLightMode;

public class Reports {
    String chosenCategory = "";
    int chosenID = -1;

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="categoryCombo"
    private ComboBox<String> categoryCombo; // Value injected by FXMLLoader

    @FXML // fx:id="finishDate"
    private DatePicker finishDate; // Value injected by FXMLLoader

    @FXML
    private ImageView imMode;

    @FXML
    private AnchorPane parent;

    @FXML // fx:id="reviewChart"
    private LineChart<String, Integer> reviewChart; // Value injected by FXMLLoader

    @FXML
    private ComboBox<Integer> parkingLotCombo;

    @FXML
    private Button updateBtm;

    @FXML // fx:id="startDate"
    private DatePicker startDate; // Value injected by FXMLLoader

    @FXML
    private TextField timeTF;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    ObservableList<ParkingLotData> parkingList = FXCollections.observableArrayList();

    @Subscribe
    public void onReceivedParkingList(ReceivedParkingLotListEvent event) throws IOException{
        List<ParkingLotData> eventList = event.getParkingLotDataList();
        for(int i = 0; i < eventList.size(); i++){
            parkingList.add(eventList.get(i));
        }
    }

    @FXML
    void initialize() {
        if(!isLightMode){
            PrimaryController.setDarkMode(parent, imMode);
        }
        categoryCombo.getItems().add("Complaints");
        categoryCombo.getItems().add("Orders");
        categoryCombo.getItems().add("Malfunctions");
        if(parkingList != null) {
            for (int i = 0; i < parkingList.size(); i++) {
                parkingLotCombo.getItems().add((parkingList.get(i)).getParkingLotId());
            }
        }
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            timeTF.setText(currentTime.format(dtf));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    public void changeMode(ActionEvent event){
        PrimaryController.ChangeForAll(parent, imMode);
    }

    @FXML
    void chooseParkingLot(ActionEvent event) {
        chosenID = parkingLotCombo.getSelectionModel().getSelectedItem();
    }
    @FXML
    void chooseFromCategory(ActionEvent event) {
        chosenCategory = categoryCombo.getSelectionModel().getSelectedItem(); //category combo box
    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.setRoot("PrimaryController");
    }

    @FXML
    void updateGraph(ActionEvent event) {
        DateTimeFormatter ldf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // ldf - (Local date format)
        reviewChart.getData().clear();
        if (chosenCategory.equals("Complaints")){

            //need to add tests
            XYChart.Series series = new XYChart.Series();
            reviewChart.setTitle("Complaints report from " + startDate.getValue().format(ldf) + " to "
                    + finishDate.getValue().format(ldf));
            xAxis.setLabel("Date");
            yAxis.setLabel("Quantity");
            series.setName("Number of Complaints");

            series.getData().add(new XYChart.Data("Jan", 23));
            series.getData().add(new XYChart.Data("Feb", 14));
            series.getData().add(new XYChart.Data("Mar", 15));
            series.getData().add(new XYChart.Data("Apr", 24));
            series.getData().add(new XYChart.Data("May", 34));
            series.getData().add(new XYChart.Data("Jun", 36));
            series.getData().add(new XYChart.Data("Jul", 22));
            series.getData().add(new XYChart.Data("Aug", 45));
            series.getData().add(new XYChart.Data("Sep", 43));
            series.getData().add(new XYChart.Data("Oct", 17));
            series.getData().add(new XYChart.Data("Nov", 29));
            series.getData().add(new XYChart.Data("Dec", 25));
            reviewChart.getData().add(series);

        } else if (chosenCategory.equals("Orders")){

            //need to add tests
            XYChart.Series casualSeries = new XYChart.Series();
            XYChart.Series orderedSeries = new XYChart.Series();


            reviewChart.setTitle("Orders report " + startDate.getValue().format(ldf) + " to "
                    + finishDate.getValue().format(ldf));
            xAxis.setLabel("Date");
            yAxis.setLabel("Quantity");
            casualSeries.setName("Number of casual orders");
            orderedSeries.setName("Number of one-time orders in advance");


            casualSeries.getData().add(new XYChart.Data("Jan", 24));
            casualSeries.getData().add(new XYChart.Data("Feb", 14));
            casualSeries.getData().add(new XYChart.Data("Mar", 15));
            casualSeries.getData().add(new XYChart.Data("Apr", 24));
            casualSeries.getData().add(new XYChart.Data("May", 34));
            casualSeries.getData().add(new XYChart.Data("Jun", 36));
            casualSeries.getData().add(new XYChart.Data("Jul", 22));
            casualSeries.getData().add(new XYChart.Data("Aug", 45));
            casualSeries.getData().add(new XYChart.Data("Sep", 43));
            casualSeries.getData().add(new XYChart.Data("Oct", 17));
            casualSeries.getData().add(new XYChart.Data("Nov", 29));
            casualSeries.getData().add(new XYChart.Data("Dec", 25));
            reviewChart.getData().add(casualSeries);

            orderedSeries.getData().add(new XYChart.Data("Jan", 12));
            orderedSeries.getData().add(new XYChart.Data("Feb", 25));
            orderedSeries.getData().add(new XYChart.Data("Mar", 45));
            orderedSeries.getData().add(new XYChart.Data("Apr", 6));
            orderedSeries.getData().add(new XYChart.Data("May", 15));
            orderedSeries.getData().add(new XYChart.Data("Jun", 47));
            orderedSeries.getData().add(new XYChart.Data("Jul", 32));
            orderedSeries.getData().add(new XYChart.Data("Aug", 24));
            orderedSeries.getData().add(new XYChart.Data("Sep", 28));
            orderedSeries.getData().add(new XYChart.Data("Oct", 15));
            orderedSeries.getData().add(new XYChart.Data("Nov", 29));
            orderedSeries.getData().add(new XYChart.Data("Dec", 13));
            reviewChart.getData().add(orderedSeries);

        } else if(chosenCategory.equals("Malfunctions")){
            //need to add tests

            XYChart.Series series = new XYChart.Series();
            reviewChart.setTitle("Malfunctions report "  + startDate.getValue().format(ldf) + " to "
                    + finishDate.getValue().format(ldf));

            xAxis.setLabel("Date");
            yAxis.setLabel("Quantity");
            series.setName("Number of Malfunctions parking spaces");

            series.getData().add(new XYChart.Data("Jan", 25));
            series.getData().add(new XYChart.Data("Feb", 14));
            series.getData().add(new XYChart.Data("Mar", 15));
            series.getData().add(new XYChart.Data("Apr", 24));
            series.getData().add(new XYChart.Data("May", 34));
            series.getData().add(new XYChart.Data("Jun", 36));
            series.getData().add(new XYChart.Data("Jul", 22));
            series.getData().add(new XYChart.Data("Aug", 45));
            series.getData().add(new XYChart.Data("Sep", 43));
            series.getData().add(new XYChart.Data("Oct", 17));
            series.getData().add(new XYChart.Data("Nov", 29));
            series.getData().add(new XYChart.Data("Dec", 25));
            reviewChart.getData().add(series);

        } else {
            //need to add "" case
        }
    }

}