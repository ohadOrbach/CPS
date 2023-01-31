package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.greenrobot.eventbus.EventBus;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
	private Button ComplainEmptBtn;

	@FXML
	private Button statisticsMenuBtn1;

	@FXML
	private AnchorPane parent;

	@FXML
	private TextField timeTF;

	@FXML
	private ImageView imMode;


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
	void openStatisticsMenu(ActionEvent event) {
		try {
			SimpleClient.getClient().sendToServer("#request: stastistical information list");
			System.out.println();
			App.setRoot("StastisticalInformation");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void openKioskMenu(ActionEvent event) {
		try {
			SimpleClient.getClient().sendToServer("#request: parking lots list");
			System.out.println();
			App.setRoot("Kiosk");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@FXML
	void openMakeComplaintScene(ActionEvent event) throws IOException {
		App.setRoot("Complaint");
	}

	@FXML
	void openComplaintsScene(ActionEvent event) throws IOException {
		try {
			SimpleClient.getClient().sendToServer("#request: complaint table");
			App.setRoot("ComplaintsEmployee");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void openOrderMenu(ActionEvent event) throws IOException {
		App.setRoot("MainMenuOrder");
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
		if(!isLightMode){
			setDarkMode(parent, imMode);
		}
		assert ShowParkingLotsListBtn != null : "fx:id=\"ShowParkingLotsListBtn\" was not injected: check your FXML file 'primary.fxml'.";
		assert showPricesBtn != null : "fx:id=\"showPricesBtn\" was not injected: check your FXML file 'primary.fxml'.";
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

	/** To DO - Correct the change mode fo 3-tap */
	static boolean isLightMode = true;

	@FXML
	public void changeMode(ActionEvent event){
		ChangeForAll(parent, imMode);
	}

	public static void ChangeForAll(AnchorPane parent, ImageView imMode){
		if(isLightMode){
			setDarkMode(parent, imMode);
		} else {
			setLightMode(parent, imMode);
		}
		isLightMode = !isLightMode;
	}

	public static void setLightMode(AnchorPane parent, ImageView imMode){
		parent.getStylesheets().remove("src/Styles/DarkMode.css");
		parent.getStylesheets().add("/Styles/LightMode.css");
		Image image = new Image("/Images/LightMode2.png");
		imMode.setImage(image);
	}

	public static void setDarkMode(AnchorPane parent, ImageView imMode) {
		parent.getStylesheets().remove("src/Styles/LightMode.css");
		parent.getStylesheets().add("/Styles/DarkMode.css");
		Image image = new Image("/Images/DarkMode.png");
		imMode.setImage(image);
	}






}
