package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ComplaintData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;


public class Complaint {
    String updateType = "";
    int updateID = -1;
    int newPrice = 0;

    @FXML // fx:id="updateBtn"
    private Button sendBtn; // Text injected by FXMLLoader
    @FXML // fx:id="newPriceTxt"
    private TextArea newComplaintTxt; // Text injected by FXMLLoader
    @FXML
    private Button MainMenuButton;


    @FXML
    void sendComplaint(ActionEvent event) {
        SimpleClient myclient = SimpleClient.getClient();
        ComplaintData cmp = new ComplaintData(newComplaintTxt.getText(), 0, App.costumer);
        myclient.sendComplaint(cmp);
        newComplaintTxt.setText("");
    }

    @FXML
    void initialize() {
        assert MainMenuButton != null : "fx:id=\"MainMenuButton\" was not injected: check your FXML file 'Complaints.fxml'.";
        assert sendBtn != null : "fx:id=\"SendComplaintButton\" was not injected: check your FXML file 'Complaints.fxml'.";
        assert newComplaintTxt != null : "fx:id=\"TextField\" was not injected: check your FXML file 'Complaints.fxml'.";

    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {

        App.history.remove(App.history.size() - 1);
        App.setRoot(App.history.get(App.history.size() - 1));
    }
}
