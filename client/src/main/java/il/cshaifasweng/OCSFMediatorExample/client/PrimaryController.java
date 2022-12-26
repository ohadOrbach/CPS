package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class PrimaryController {

    @FXML // fx:id="parkBtn"
    private Button parkBtn; // Value injected by FXMLLoader

    @FXML
    void showParkingLotList(ActionEvent event) throws IOException {
        App.setRoot("ParkingLotList");
    }

}