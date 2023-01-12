/**
 * Sample Skeleton for 'NewCostumerRegister.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class NewCostumerRegister {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="email"
    private TextField email; // Value injected by FXMLLoader

    @FXML // fx:id="id"
    private TextField id; // Value injected by FXMLLoader

    @FXML // fx:id="password"
    private TextField password; // Value injected by FXMLLoader

    @FXML // fx:id="register"
    private Button register; // Value injected by FXMLLoader

    @FXML
    private Button back;

    @FXML
    private Label registerReturn;

    @Subscribe
    public void RegisterAttempt(String event) throws IOException
    {
        if(event.equals("registration succeeded"))
        {
            registerReturn.setText("registration succeeded");

        }
        else
        {
            registerReturn.setText("registration failed");
        }
    }

    @FXML
    void getBack(ActionEvent event) throws IOException {
        App.history.remove(App.history.size()-1);
        App.setRoot(App.history.get(App.history.size()-1));
    }

    @FXML
    void registerAttempt(ActionEvent event) throws IOException {

        SimpleClient myClient = SimpleClient.getClient();

        myClient.sendToServer("costumer Register:"+id.getText()+","+email.getText()+","+password.getText());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'NewCostumerRegister.fxml'.";
        assert email != null : "fx:id=\"email\" was not injected: check your FXML file 'NewCostumerRegister.fxml'.";
        assert id != null : "fx:id=\"id\" was not injected: check your FXML file 'NewCostumerRegister.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'NewCostumerRegister.fxml'.";
        assert register != null : "fx:id=\"register\" was not injected: check your FXML file 'NewCostumerRegister.fxml'.";
        assert registerReturn != null : "fx:id=\"registerReturn\" was not injected: check your FXML file 'NewCostumerRegister.fxml'.";
        EventBus.getDefault().register(this);

    }

}
