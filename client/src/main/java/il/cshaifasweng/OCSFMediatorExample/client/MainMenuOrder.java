package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static il.cshaifasweng.OCSFMediatorExample.client.PrimaryController.isLightMode;

public class MainMenuOrder {

    @FXML
    private Button BackToMain;

    @FXML
    private Button CancelOrder;

    @FXML
    private Button CasualOrder;

    @FXML
    private Button CasualOrderAdvance;

    @FXML
    private Button TrackingOrder;

    @FXML
    private Button btnMode;

    @FXML
    private ImageView imMode;

    @FXML
    private AnchorPane parent;

    @FXML
    private TextField timeTF;

    @FXML
    void makeCasualOrderInAdvance(ActionEvent event) throws IOException {
        App.history.add("CasualOrderInAdvance");
        App.setRoot("CasualOrderInAdvance");
    }

    @FXML
    void makeCasualOrder(ActionEvent event) throws IOException {
        App.history.add("CasualOrder");
        App.setRoot("CasualOrder");
    }

    @FXML
    void cancelOrder(ActionEvent event) throws IOException {
        App.history.add("CancelOrder");
        App.setRoot("CancelOrder");
    }

    @FXML
    void TrackingOrder(ActionEvent event) throws IOException {
        App.history.add("TrackingOrder");
        App.setRoot("TrackingOrder");
    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.history.remove(App.history.size()-1);
        App.setRoot(App.history.get(App.history.size()-1));
    }

    @FXML
    public void changeMode(ActionEvent event){
        PrimaryController.ChangeForAll(parent, imMode);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        if(!isLightMode){
            PrimaryController.setDarkMode(parent, imMode);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            timeTF.setText(currentTime.format(dtf));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

}
