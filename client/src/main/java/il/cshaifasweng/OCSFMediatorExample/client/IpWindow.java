package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class IpWindow {

    @FXML
    private TextField ClientIP;

    @FXML
    private TextField ClientPort;

    @FXML
    private Button ProceedBtn;

    @FXML
    private AnchorPane parent;

    @FXML
    void ProceedToApp(ActionEvent event) throws IOException {
        String ip = ClientIP.getText().toString();
        int port = Integer.parseInt(ClientPort.getText().toString());
        App.setClient(ip,port);
        App.history.add("InitialWindow");
        App.setRoot("InitialWindow");
    }

}
