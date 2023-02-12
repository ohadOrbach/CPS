package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static il.cshaifasweng.OCSFMediatorExample.client.PrimaryController.isLightMode;

public class ReportMenu {

    @FXML
    private Button BackToMain;

    @FXML
    private Button btnMode;

    @FXML
    private ImageView imMode;

    @FXML
    private Button makeReport;

    @FXML
    private AnchorPane parent;

    @FXML
    private TextField timeTF;

    @FXML
    private Button viewReport;

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        if (!isLightMode) {
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

    @FXML
    public void changeMode(ActionEvent event) {
        PrimaryController.ChangeForAll(parent, imMode);
    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        App.history.remove(App.history.size() - 1);
        App.setRoot(App.history.get(App.history.size() - 1));
    }

    @FXML
    void goToMakeReport(ActionEvent event) throws IOException {
        App.history.add("reports");
        App.setRoot("reports");
    }

    @FXML
    void goToViewReports(ActionEvent event) throws IOException {
        App.history.add("ViewReport");
        App.setRoot("ViewReport");
    }

}
