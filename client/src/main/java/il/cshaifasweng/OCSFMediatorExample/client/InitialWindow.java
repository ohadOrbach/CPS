package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InitialWindow {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button costumers;

    @FXML
    private Button staff;

    @FXML
    private Button kiosk;

    @FXML
    void switchToCostumersLogin(ActionEvent event) throws IOException {
        App.history.add("InitialCostumerWindow");
        App.setRoot("InitialCostumerWindow");
    }

    @FXML
    void switchToStaffLogin(ActionEvent event) throws IOException {
        App.history.add("InitialStaffWindow");
        App.setRoot("InitialStaffWindow");
    }

    @FXML
    void goToKiosk(ActionEvent event) throws IOException {
        App.history.add("kiosk");
        App.setRoot("kiosk");
    }

    @FXML
    void initialize() {
        assert costumers != null : "fx:id=\"costumers\" was not injected: check your FXML file 'InitialWindow.fxml'.";
        assert staff != null : "fx:id=\"staff\" was not injected: check your FXML file 'InitialWindow.fxml'.";
        assert kiosk != null : "fx:id=\"kiosk\" was not injected: check your FXML file 'InitialWindow.fxml'.";

    }

}
