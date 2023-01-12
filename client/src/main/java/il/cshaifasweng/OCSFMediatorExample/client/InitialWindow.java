package il.cshaifasweng.OCSFMediatorExample.client;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
    void initialize() {
        assert costumers != null : "fx:id=\"costumers\" was not injected: check your FXML file 'InitialWindow.fxml'.";
        assert staff != null : "fx:id=\"staff\" was not injected: check your FXML file 'InitialWindow.fxml'.";

    }

}
