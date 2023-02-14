package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.CostumerData;
import il.cshaifasweng.OCSFMediatorExample.entities.EmployeeData;
import il.cshaifasweng.OCSFMediatorExample.entities.SubscriptionData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */

public class App extends Application {

    public static List<String> history = new ArrayList<String>();
    static CostumerData costumer = null;
    static EmployeeData employee = null;
    static boolean dontShow = false;
    static SubscriptionData currentSub = null;
    private static Scene scene;
    boolean isCostumer = false;
    boolean isEmployee = false;
    private SimpleClient client;

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        EventBus.getDefault().register(this);
        client = SimpleClient.getClient();
        client.openConnection();
        scene = new Scene(loadFXML("InitialWindow"), 800, 600);
        history.add("InitialWindow");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        // when clicking on "x" you logout.
        SimpleClient myClient = SimpleClient.getClient();
        if (!(costumer == null)) {
            myClient.sendToServer("logout costumer:" + App.costumer.getId());
        } else if (!(employee == null)) {
            myClient.sendToServer("logout employee:" + App.employee.getId());

        }
        EventBus.getDefault().unregister(this);
        super.stop();
    }

    @Subscribe
    public void onWarningEvent(WarningEvent event) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.WARNING,
                    String.format("Message: %s\nTimestamp: %s\n",
                            event.getWarning().getMessage(),
                            event.getWarning().getTime().toString())
            );
            alert.show();
        });
    }

    // TODO: check way timestamp not added to msg
    @Subscribe
    public void onMessageEvent(MessageEvent message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION,
                    String.format("%s\nTimestamp: %s\n",
                            message.getMessage().getMessage(),
                            message.getMessage().getTimeStamp().toString())
            );
            alert.setTitle("new message");
            alert.setHeaderText("New Message:");
            alert.show();
        });
    }




}