package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;

import java.io.IOException;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg.getClass().equals(Warning.class)) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
		else if(msg.getClass().equals(ParkingLotListData.class)) {
			Platform.runLater(() -> EventBus.getDefault().
					post(new ReceivedParkingLotListEvent((ParkingLotListData) msg)));
//			System.out.println("get it");
		}
		else if(msg.getClass().equals(PricesList.class)) {
			Platform.runLater(() -> EventBus.getDefault().
					post(new ReceivedParkingPricesEvent((PricesList) msg)));
		}
	}
	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

	public void requestParkingLotList(){
		try {
			client.sendToServer("#request:parkingLots");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void requestParkingPrices(){
		try {
			client.sendToServer("#request:parkingPrices");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changePrice(int price, ParkingLotData parkingLot, String priceType) {
		try {
			client.sendToServer("#update:ItemPrice," + parkingLot.getParkingLotId() + "," + priceType + "," + Integer.toString(price)); //"update:price,<item id>,<new price>"
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
