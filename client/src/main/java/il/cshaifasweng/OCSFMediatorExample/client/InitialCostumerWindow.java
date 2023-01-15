/**
 * Sample Skeleton for 'InitialCostumerWindow.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class InitialCostumerWindow {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Button back;

    @FXML // fx:id="errorLoginMassage"
    private Label errorLoginMassage; // Value injected by FXMLLoader

    @FXML // fx:id="id"
    private TextField id; // Value injected by FXMLLoader

    @FXML
    private Hyperlink newCostumer;

    @FXML // fx:id="password"
    private TextField password; // Value injected by FXMLLoader

    @FXML // fx:id="tryToLogIn"
    private Button tryToLogIn; // Value injected by FXMLLoader

    @FXML
    void loginAttempt(ActionEvent event) {
        if (id.getText().isEmpty() || password.getText().isEmpty()) {
            errorLoginMassage.setText("Error: Missing fields");
            return;
        }
        SimpleClient myClient = SimpleClient.getClient();
        myClient.costumerLogin(id.getText(),password.getText());
    }

    @FXML
    void newUser(ActionEvent event) throws IOException {
        App.history.add("NewCostumerRegister");
        App.setRoot("NewCostumerRegister");
    }

    @Subscribe
    public void loginAttemptSuccess(CostumerLoginReceivedEvent event) throws IOException
    {
        if(event.LoginFailed())
        {
            errorLoginMassage.setText("Login Failed");
        }
        else
        {
            errorLoginMassage.setText("Login Success");
            App.costumer = event.getCostumer();
            App.history.add("Primary");
            App.setRoot("Primary");
        }
    }

    @FXML
    void getBack(ActionEvent event) throws IOException {
        App.history.remove(App.history.size()-1);
        App.setRoot(App.history.get(App.history.size()-1));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert errorLoginMassage != null : "fx:id=\"errorLoginMassage\" was not injected: check your FXML file 'InitialCostumerWindow.fxml'.";
        assert id != null : "fx:id=\"id\" was not injected: check your FXML file 'InitialCostumerWindow.fxml'.";
        assert newCostumer != null : "fx:id=\"newCostumer\" was not injected: check your FXML file 'InitialCostumerWindow.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'InitialCostumerWindow.fxml'.";
        assert tryToLogIn != null : "fx:id=\"tryToLogIn\" was not injected: check your FXML file 'InitialCostumerWindow.fxml'.";
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'InitialCostumerWindow.fxml'.";
        EventBus.getDefault().register(this);
    }

}
