package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

import java.io.IOException;
import java.util.ArrayList;

public class SimpleServer extends AbstractServer {

	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();

	public SimpleServer(int port) {
		super(port);

	}

	private void SafeSendToClient(Object obj, ConnectionToClient client){
		try {
			client.sendToClient(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if (String.class.equals(msg.getClass())) {
			String msgString = msg.toString();
			System.out.format("    data: " + msgString + "\n");
			if (msgString.startsWith("#warning")) {
				Warning warning = new Warning("Warning from server!");
				try {
					client.sendToClient(warning);
					System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if (msgString.startsWith("#update")) {
				String[] args = (msgString.split(":", 2)[1]).split(",", -1);
				switch (args[0]) {
					case " parking price" -> { // update item price #update:ItemPrice,itemId,newPrice
						App.parkinglots.changePrice(Integer.parseInt(args[1]), Double.parseDouble(args[2]), args[3]);
					}
				}
			}else if (msgString.startsWith("#request")) {
				String[] args = (msgString.split(":")[1]).split(",");
				switch (args[0]) {
					case " parking lots list" -> {
						ParkingLotListData parkingLotListData = App.parkinglots.getParkingLotList();
						SafeSendToClient(parkingLotListData, client);
					}

					case " prices table" -> {
						PricesList parkingPricesList = App.parkinglots.getParkingLotsPrices();
						SafeSendToClient(parkingPricesList, client);
					}

				}
			}

		}
	}

}
