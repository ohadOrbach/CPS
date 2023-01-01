package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;

import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController {

    @FXML // fx:id="parkBtn"
    private Button showPricesBtn; // Value injected by FXMLLoader

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="ShowParkingLotsListBtn"
	private Button ShowParkingLotsListBtn; // Value injected by FXMLLoader
	@FXML
	private Button makeComplaintBtn;

	@FXML
	void sendWarning(ActionEvent event) {
		try {
			SimpleClient.getClient().sendToServer("#warning");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void showParkingLotList(ActionEvent event) {
		try {
			SimpleClient.getClient().sendToServer("#request: parking lots list");
			App.setRoot("ParkingLotList");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void openMakeComplaintScene(ActionEvent event) throws IOException {
		App.setRoot("Complaint");
		/**
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Complaint.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(loader.load()));
		stage.show();
		 */
	}
	@FXML
	void openShowPriceTableScene(ActionEvent event) throws IOException {
		try {
			SimpleClient.getClient().sendToServer("#request: prices table");
			App.setRoot("ParkingLotTable");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert ShowParkingLotsListBtn != null : "fx:id=\"ShowParkingLotsListBtn\" was not injected: check your FXML file 'primary.fxml'.";
		assert showPricesBtn != null : "fx:id=\"showPricesBtn\" was not injected: check your FXML file 'primary.fxml'.";
		assert makeComplaintBtn != null : "fx:id=\"makeComplaintBtn\" was not injected: check your FXML file 'primary.fxml'.";
	}


}
