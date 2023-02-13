package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationData;
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
import javafx.scene.control.*;
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
    String chosenID = "";
    ObservableList<ParkingLotData> parkingLotList = FXCollections.observableArrayList();
    ObservableList<StastisticalInformationData> stastisticalInformationList = FXCollections.observableArrayList();
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
    private ComboBox<String> parkingLotCombo;
    @FXML
    private Label errorLoginMassage;
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

    @Subscribe
    public void onReceivedParkingList(ReceivedParkingLotListEvent event) throws IOException {
        List<ParkingLotData> eventList = event.getParkingLotDataList();
        for (int i = 0; i < eventList.size(); i++) {
            parkingLotList.add(eventList.get(i));
        }
        assert parkingLotCombo != null : "fx:id=\"parkingLotComboBox\" was not injected: check your FXML file 'primary.fxml'.";
        for (ParkingLotData parkingLotData : parkingLotList) {
            parkingLotCombo.getItems().add(parkingLotData.getParkingLotName());
        }
    }

    @Subscribe
    public void onReceivedStastisticalInformationEvent(ReceivedStastisticalInformationEvent event) throws IOException {
        List<StastisticalInformationData> eventList = event.getstastisticalInformationDataListDataList();
        for (int i = 0; i < eventList.size(); i++) {
            stastisticalInformationList.add(eventList.get(i));
        }
    }

    @FXML
    void initialize() {
        if (!isLightMode) {
            PrimaryController.setDarkMode(parent, imMode);
        }
        EventBus.getDefault().register(this);
        try {
            SimpleClient.getClient().sendToServer("#request: parking lots list");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            SimpleClient.getClient().sendToServer("#request: stastistical information list");
        } catch (IOException e) {
            e.printStackTrace();
        }
        categoryCombo.getItems().add("Complaints");
        categoryCombo.getItems().add("Orders");
        categoryCombo.getItems().add("Malfunctions");

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
    public void changeMode(ActionEvent event) {
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
        App.history.remove(App.history.size() - 1);
        App.setRoot(App.history.get(App.history.size() - 1));
    }

    public StastisticalInformationData FindStastisticalInformationDay(LocalDate date) {
        for (StastisticalInformationData stastisticalInformationData : stastisticalInformationList) {
            if (stastisticalInformationData.getDate().isEqual(date)) {
                System.out.println("true");
                return stastisticalInformationData;
            }
        }
        System.out.println(date.format(DateTimeFormatter.ofPattern("dd/MM/yy")));
        System.out.println("false");
        return null;
    }


    @FXML
    void updateGraph(ActionEvent event) {
        DateTimeFormatter ldf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // ldf - (Local date format)
        reviewChart.getData().clear();


        if (startDate == null && FindStastisticalInformationDay(startDate.getValue()) == null) {
            if (startDate == null) {
                errorLoginMassage.setText("Starting date not selected");
                return;
            }
            errorLoginMassage.setText("Start date not in the system, please pick another date");
            return;
        }
        if (finishDate == null && FindStastisticalInformationDay(finishDate.getValue()) == null) {
            if (finishDate == null) {
                errorLoginMassage.setText("Ending date not selected");
                return;
            }
            errorLoginMassage.setText("End date not in the system, please pick another date");
            return;
        }
        if (finishDate.getValue().isBefore(startDate.getValue())) {
            errorLoginMassage.setText("Start date must be before the end date");
            return;
        }

        //------------Complaints------------
        if (chosenCategory.equals("Complaints")) {
            //need to add tests
            errorLoginMassage.setText("");
            XYChart.Series series = new XYChart.Series();
            reviewChart.setTitle("Complaints report from " + startDate.getValue().format(ldf) + " to "
                    + finishDate.getValue().format(ldf));
            xAxis.setLabel("Date");
            yAxis.setLabel("Quantity");
            series.setName("Number of Complaints");
            for (LocalDate date = startDate.getValue(); date.isBefore(finishDate.getValue().plusDays(1)); date = date.plusDays(1)) {
                StastisticalInformationData stats = FindStastisticalInformationDay(date);
                if (stats != null && stats.getName().equals(chosenID)) {
                    //series.getData().add(new XYChart.Data("Jan", 23));
                }
            }
            reviewChart.getData().add(series);

            //------------Orders------------
        } else if (chosenCategory.equals("Orders")) {

            //need to add tests
            XYChart.Series casualSeries = new XYChart.Series();
            XYChart.Series orderedSeries = new XYChart.Series();


            reviewChart.setTitle("Orders report " + startDate.getValue().format(ldf) + " to "
                    + finishDate.getValue().format(ldf));
            xAxis.setLabel("Date");
            yAxis.setLabel("Quantity");
            casualSeries.setName("Number of casual orders");
            orderedSeries.setName("Number of one-time orders in advance");
            int test = 0;
            for (LocalDate date = startDate.getValue(); date.isBefore(finishDate.getValue().plusDays(1)); date = date.plusDays(1)) {
                StastisticalInformationData stats = FindStastisticalInformationDay(date);
                if (stats != null && stats.getName().equals(chosenID)) {
                    System.out.println(test++);
                    System.out.println(stats.getActualOrders());
                    casualSeries.getData().add(new XYChart.Data(stats.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yy")), stats.getActualOrders()));
                    //orderedSeries.getData().add(new XYChart.Data("Jan", 12));
                    reviewChart.getData().add(casualSeries);
                }
            }
            reviewChart.getData().add(casualSeries);
            reviewChart.getData().add(orderedSeries);

            //------------Malfunctions------------
        } else if (chosenCategory.equals("Malfunctions")) {
            //need to add tests

            XYChart.Series series = new XYChart.Series();
            reviewChart.setTitle("Malfunctions report " + startDate.getValue().format(ldf) + " to "
                    + finishDate.getValue().format(ldf));

            xAxis.setLabel("Date");
            yAxis.setLabel("Quantity");
            series.setName("Number of Malfunctions parking spaces");

            for (LocalDate date = startDate.getValue(); date.isBefore(finishDate.getValue().plusDays(1)); date = date.plusDays(1)) {
                StastisticalInformationData stats = FindStastisticalInformationDay(date);
                if (stats != null && stats.getName().equals(chosenID)) {
                    //series.getData().add(new XYChart.Data("Jan", 25));
                }
            }
            reviewChart.getData().add(series);

        } else {
            //need to add "" case
        }
    }

}