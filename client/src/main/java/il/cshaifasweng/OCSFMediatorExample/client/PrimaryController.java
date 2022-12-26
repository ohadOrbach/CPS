package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrimaryController {

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
	void openChangePricesScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("changePrice.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(loader.load()));
		stage.show();
	}
	@FXML
	void openShowPriceTableScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("showPriceTable.fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(loader.load()));
		stage.show();
	}


}
