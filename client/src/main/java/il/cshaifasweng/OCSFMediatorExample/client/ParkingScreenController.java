package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.print.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class ParkingScreenController {
    ObservableList<ParkingLotData> parkingLotList = FXCollections.observableArrayList();
    ObservableList<ParkingData> parkingList = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> parkingLotComboBox;
    @FXML
    private Button updateButton;
    @FXML
    private Button backButton;
    @FXML
    private VBox parkingVbox;
    @FXML
    private HBox hb1;
    @FXML
    private GridPane parkingGridPane;
    @FXML
    private Button goBack;
    @FXML
    private Button printButton;
    @FXML
    private Label ParkingLabal;
    private String selectedParkingLotData;



    @Subscribe
    public void onReceivedParkingList(ReceivedParkingLotListEvent event) throws IOException {
        List<ParkingLotData> eventList = event.getParkingLotDataList();
        for (int i = 0; i < eventList.size(); i++) {
            parkingLotList.add(eventList.get(i));
        }
        buildScreen();
    }

    public void buildScreen() {
        assert parkingLotComboBox != null : "fx:id=\"parkingLotComboBox\" was not injected: check your FXML file 'primary.fxml'.";
        for (ParkingLotData parkingLotData : parkingLotList) {
            parkingLotComboBox.getItems().add(parkingLotData.getParkingLotName());
        }
        updateButton.setOnAction(event -> updateParkingLot());
    }

    @FXML
    void initialize() throws IOException {
        EventBus.getDefault().register(this);
        try {
            parkingGridPane.getChildren().clear();
            parkingList.clear();
            parkingLotList.clear();
            SimpleClient.getClient().sendToServer("#request: parking lots list");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert backButton != null : "fx:id=\"MainMenuButton\" was not injected: check your FXML file 'ParkingScreenController.fxml'.";
        assert updateButton != null : "fx:id=\"SendComplaintButton\" was not injected: check your FXML file 'ParkingScreenController.fxml'.";
        assert parkingLotComboBox != null : "fx:id=\"TextField\" was not injected: check your FXML file 'ParkingScreenController.fxml'.";
        assert parkingVbox != null : "fx:id=\"TextField\" was not injected: check your FXML file 'ParkingScreenController.fxml'.";
        assert hb1 != null : "fx:id=\"TextField\" was not injected: check your FXML file 'ParkingScreenController.fxml'.";
        assert parkingGridPane != null : "fx:id=\"TextField\" was not injected: check your FXML file 'ParkingScreenController.fxml'.";

    }

    @FXML
    void updateParkingLot() {
        selectedParkingLotData = (String) parkingLotComboBox.getSelectionModel().getSelectedItem();
        System.out.println("name is" + selectedParkingLotData + "\n");
        SimpleClient myclient = SimpleClient.getClient();
        myclient.getParkings(selectedParkingLotData);

    }

    @Subscribe
    public void updateScreen(ReceivedParkings event) throws IOException {
        parkingList.clear();
        List<ParkingData> eventList = event.getParkingDataList();
        for (int i = 0; i < eventList.size(); i++) {
            parkingList.add(eventList.get(i));
        }
        parkingGridPane.getChildren().clear();
        for (int i = 0; i < parkingList.size(); i++) {
            ParkingData parkingData = parkingList.get(i);
            Label label = new Label(String.valueOf(parkingData.getId()));
            label.setMinSize(50, 50);
            label.setMaxSize(50, 50);
            if (parkingData.getStatus() == 0) {
                label.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (parkingData.getStatus() == 1) {
                label.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            } else if (parkingData.getStatus() == 2) {
                label.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            } else {
                label.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            int cols = (int) parkingList.size() / 9;
            parkingGridPane.add(label, i % cols, i / cols);
        }
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {

        App.history.remove(App.history.size() - 1);
        App.setRoot(App.history.get(App.history.size() - 1));
    }

    @FXML
    void printStatus(ActionEvent event) throws IOException {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            printButton.setVisible(false);
            updateButton.setVisible(false);
            goBack.setVisible(false);
            parkingLotComboBox.setVisible(false);
            ParkingLabal.setVisible(false);

            AnchorPane temp = new AnchorPane(parkingVbox);
            PageLayout layout = job.getPrinter()
                    .createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
            job.getJobSettings().setPageLayout(layout);
            job.printPage(parkingVbox);
            job.endJob();
            App.setRoot("ParkingScreenController");
        }
    }
}

