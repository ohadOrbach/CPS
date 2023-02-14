package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    public void loginAttemptSuccess(EmployeeLoginReceivedEvent event) throws IOException {
        if (event.LoginFailed()) {
            errorLoginMassage.setText("Login Failed");
        } else {
            App.employee = event.getEmployee();
            errorLoginMassage.setText("Login Success");
            App.history.add("EmployeesMainWindow");
            App.setRoot("EmployeesMainWindow");

        }
    }

    @FXML
    void loginAttempt(ActionEvent event) {
        if (id.getText().isEmpty() || password.getText().isEmpty()||InputCheck.checkId(id.getText())) {
            errorLoginMassage.setText("Error: Invalid fields");
            return;
        }
        SimpleClient myClient = SimpleClient.getClient();
        myClient.employeeLogin(id.getText(), password.getText());
    }

    @FXML
    void getBack(ActionEvent event) throws IOException {
        App.history.remove(App.history.size() - 1);
        App.setRoot(App.history.get(App.history.size() - 1));

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
