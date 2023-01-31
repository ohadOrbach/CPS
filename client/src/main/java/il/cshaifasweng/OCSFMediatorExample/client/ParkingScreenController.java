package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotListData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ParkingScreenController {
    @FXML
    private ComboBox<ParkingLotData> parkingLotComboBox;

    @FXML
    private Button updateButton;

    @FXML
    private GridPane parkingGridPane;

    private ParkingLotListData parkingLotListData;

    /**
    public void initialize() {
        parkingLotListData = new ParkingLotListData();
        parkingLotComboBox.setItems(parkingLotListData.getParkingLotDataList());
        parkingLotComboBox.setOnAction(event -> updateParkingLot());
        updateButton.setOnAction(event -> updateParkingLot());
    }

    private void updateParkingLot() {
        ParkingLotData selectedParkingLotData = parkingLotComboBox.getSelectionModel().getSelectedItem();
        parkingGridPane.getChildren().clear();
        for (int i = 0; i < selectedParkingLotData.getParkingDataList().size(); i++) {
            ParkingData parkingData = selectedParkingLotData.getParkingDataList().get(i);
            Label label = new Label(String.valueOf(parkingData.getId()));
            label.setMinSize(50, 50);
            label.setMaxSize(50, 50);
            if (parkingData.getStatus() == 0) {
                label.setTextFill(Color.GREEN);
            } else if (parkingData.getStatus() == 1) {
                label.setTextFill(Color.YELLOW);
            } else if (parkingData.getStatus() == 2) {
                label.setTextFill(Color.RED);
            } else {
                label.setTextFill(Color.GREY);
            }
            parkingGridPane.add(label, i % selectedParkingLotData.getNumOfColumns(), i / selectedParkingLotData.getNumOfColumns());
        }
    }*/
}

