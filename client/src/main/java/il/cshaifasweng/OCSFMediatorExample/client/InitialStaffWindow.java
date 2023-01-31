package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.entities.ComplaintData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;
import il.cshaifasweng.OCSFMediatorExample.server.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class InitialStaffWindow {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField id;

    @FXML
    private Button login;

    @FXML
    private TextField password;

    @FXML
    private Label errorLoginMassage;


    @FXML
    private Button back;


    @Subscribe
    public void loginAttemptSuccess(EmployeeLoginReceivedEvent event) throws IOException
    {
        if(event.LoginFailed())
        {
            errorLoginMassage.setText("Login Failed");
        }
        else
        {
            errorLoginMassage.setText("Login Success");
            App.employee = event.getEmployee();
            App.history.add("CostumerMainMenu");
            App.setRoot("CostumerMainMenu");

        }
    }

    @FXML
    void loginAttempt(ActionEvent event)
    {
        if (id.getText().isEmpty() || password.getText().isEmpty()) {
            errorLoginMassage.setText("Error: Missing fields");
            return;
        }
        SimpleClient myClient = SimpleClient.getClient();
        myClient.employeeLogin(id.getText(),password.getText());
    }

    @FXML
    void getBack(ActionEvent event) throws IOException {
        App.history.remove(App.history.size()-1);
        App.setRoot(App.history.get(App.history.size()-1));

    }

    @FXML
    void initialize() {
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'InitialStaffWindow.fxml'.";
        assert id != null : "fx:id=\"id\" was not injected: check your FXML file 'InitialStaffWindow.fxml'.";
        assert login != null : "fx:id=\"login\" was not injected: check your FXML file 'InitialStaffWindow.fxml'.";
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'InitialStaffWindow.fxml'.";
        EventBus.getDefault().register(this);

    }

}