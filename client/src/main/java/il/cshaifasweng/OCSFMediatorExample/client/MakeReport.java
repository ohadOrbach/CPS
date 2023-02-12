package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ReportData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.client.PrimaryController.isLightMode;

public class MakeReport {

    ObservableList<ParkingLotData> parkingLotList = FXCollections.observableArrayList();
    @FXML
    private Button BackToMain;
    @FXML
    private Button SubmitBtn;
    @FXML
    private Button btnMode;
    @FXML
    private TextField casualNumber;
    @FXML
    private TextField complaintsNumber;
    @FXML
    private DatePicker endDate;
    @FXML
    private ImageView imMode;
    @FXML
    private AnchorPane parent;
    @FXML
    private TextField inAdvanceNumber;
    @FXML
    private ComboBox<String> parkingLotComboBox;
    @FXML
    private TextField malfunctuinsNumber;
    @FXML
    private DatePicker startDate;
    @FXML
    private TextField timeTF;
    @FXML
    private Label errorLoginMassage;

    @Subscribe
    public void onReceivedParkingList(ReceivedParkingLotListEvent event) throws IOException {
        List<ParkingLotData> eventList = event.getParkingLotDataList();
        for (int i = 0; i < eventList.size(); i++) {
            parkingLotList.add(eventList.get(i));
        }
        assert parkingLotComboBox != null : "fx:id=\"parkingLotComboBox\" was not injected: check your FXML file 'primary.fxml'.";
        for (ParkingLotData parkingLotData : parkingLotList) {
            parkingLotComboBox.getItems().add(parkingLotData.getParkingLotName());
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
    void SubmitReport(ActionEvent event) {
        SimpleClient myclient = SimpleClient.getClient();
        int casual;
        int inAdvance;
        int complaints;
        int malfunctuins;
        if (startDate.getValue() == null) {
            errorLoginMassage.setText("Starting date not selected");
            return;
        }
        if (endDate.getValue() == null) {
            errorLoginMassage.setText("Ending date not selected");
            return;
        }
        if (endDate.getValue().isBefore(startDate.getValue())) {
            errorLoginMassage.setText("Start date must be before the end date");
            return;
        }
        if (parkingLotComboBox.getSelectionModel().getSelectedItem() == null) {
            errorLoginMassage.setText("Parking lot not selected");
            return;
        }
        try {
            complaints = Integer.parseInt(complaintsNumber.getText());
        } catch (NumberFormatException e) {
            if (complaintsNumber.getText().equals("")) {
                errorLoginMassage.setText("Complaints not added");
                return;
            }
            errorLoginMassage.setText("Complaints must be a whole number");
            return;
        }
        try {
            malfunctuins = Integer.parseInt(malfunctuinsNumber.getText());
        } catch (NumberFormatException e) {
            if (malfunctuinsNumber.getText().equals("")) {
                errorLoginMassage.setText("Malfunctuins parking spaces not added");
                return;
            }
            errorLoginMassage.setText("Malfunctuin parking spaces must be a whole number");
            return;
        }
        try {
            casual = Integer.parseInt(casualNumber.getText());
        } catch (NumberFormatException e) {
            if (casualNumber.getText().equals("")) {
                errorLoginMassage.setText("Casual orders not added");
                return;
            }
            errorLoginMassage.setText("Casual orders must be a whole number");
            return;
        }
        try {
            inAdvance = Integer.parseInt(inAdvanceNumber.getText());
        } catch (NumberFormatException e) {
            if (inAdvanceNumber.getText().equals("")) {
                errorLoginMassage.setText("Casual orders in advance not added");
                return;
            }
            errorLoginMassage.setText("Casual orders in advance must be a whole number");
            return;
        }
        System.out.println("submitting report..");
        ReportData report = new ReportData(parkingLotComboBox.getSelectionModel().getSelectedItem(), casual, inAdvance, complaints,
                malfunctuins, startDate.getValue(), endDate.getValue());
        myclient.submitReport(report);
        casualNumber.clear();
        complaintsNumber.clear();
        malfunctuinsNumber.clear();
        inAdvanceNumber.clear();
        errorLoginMassage.setText("");
    }

    /*
    @FXML
    void SubmitReport(ActionEvent event) {
        try{
            System.out.println("submitting report..");
            ReportData reportData =
                    new ReportData(lotID.getText(), casualNumber.getText(), inAdvanceNumber.getText(), complaintsNumber.getText(),
                            malfunctuinsNumber.getText(), startDate.getValue(), endDate.getValue());
            SimpleClient.getClient().sendToServer(reportData);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    @FXML
    public void changeMode(ActionEvent event) {
        PrimaryController.ChangeForAll(parent, imMode);
    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.history.remove(App.history.size() - 1);
        App.setRoot(App.history.get(App.history.size() - 1));
    }


}