package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
		System.out.format("i am in the server\n");
		System.out.format("i got object from %s class\n", msg.getClass().getSimpleName());
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
			} else if (msgString.startsWith("#update")) {
				String[] args = (msgString.split(":", 2)[1]).split(",", -1);
				System.out.println("im in update mode");
				switch (args[0]) {
					case "parking price" -> { // update item price #update:ItemPrice,itemId,newPrice
						App.parkingPrices.changePrice(Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]));
						App.parkingPrices.pullParkingPrices();
					}
				}
			} else if (msgString.startsWith("#request")) {
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
			else if (msgString.startsWith("employee login"))
			{
				String[] args = (msgString.split(":")[1]).split(",");
				EmployeeData employee = App.employees.employeeLoginCheck(args[0],args[1]);
				SafeSendToClient(employee, client);

			}
			else if (msgString.startsWith("costumer login"))
			{
				String[] args = (msgString.split(":")[1]).split(",");
				CostumerData costumer = App.costumers.costumerLoginCheck(args[0],args[1]);
				SafeSendToClient(costumer, client);
			}

			else if (msgString.startsWith("costumer Register:"))
			{
				String[] args = (msgString.split(":")[1]).split(",");

				if(App.costumers.addNewCostumer(args[0],args[1],args[2]).equals("success"))
				{
					SafeSendToClient("registration succeeded", client);
				}
				else
				{
					SafeSendToClient("registration failed", client);
				}
			}
			else if (msgString.startsWith("new subscription"))
			{
				String ret = "";
				String[] args = (msgString.split(":")[1]).split(",");
				Costumer subCostumer = App.costumers.getCostumer(Integer.parseInt(args[0]));
				String dateString = args[2];
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse(dateString, formatter);
				if(msgString.startsWith("new subscription regular"))
				{
					ParkingLot theParkingLot = App.parkinglots.getParkingLotByName(args[3]);
					ret =  App.subscriptions.addNewRegularSubscription(subCostumer,args[1],date,theParkingLot,args[3]);
				}
				else
				{
					ret =  App.subscriptions.addNewFullSubscription(subCostumer,args[1],date);
				}

				SafeSendToClient(ret, client);
			}
			else if (msgString.startsWith("logout costumer"))
			{
				String[] args = (msgString.split(":")[1]).split(",");
				App.costumers.logOutCostumer(args[0]);

			}


		} else if (msg.getClass().equals(ComplaintData.class)) { // Make a complaint
			System.out.format("i got a new complaint\n");
			String receivedMsg = App.complaints.addComplaint((ComplaintData) msg);
			Message arrivalMsg = new Message(receivedMsg);
			SafeSendToClient(arrivalMsg, client);

		} else if (OrderData.class.equals(msg.getClass())) { // Make an order
			System.out.format("i got a new order\n");
			String receivedMsg = App.orders.addOrder((OrderData) msg);
			Message arrivalMsg = new Message(receivedMsg);
			SafeSendToClient(arrivalMsg, client);

		} else if (CancelOrderData.class.equals(msg.getClass())) { // find Cancel order
			System.out.format("i got a new cancel order data\n");
			OrdersListData ordersListData = App.orders.findCancelOrder((CancelOrderData) msg);
			ordersListData.setMode("cancel");
			SafeSendToClient(ordersListData, client);

		} else if (OrdersListData.class.equals(msg.getClass())) { // Delete orders
			System.out.format("i got a new cancel order data for delete\n");
			Object obj = App.orders.deleteOrders((OrdersListData) msg);
			SafeSendToClient(obj, client);


		} else if (TrackingOrderData.class.equals(msg.getClass())) { // Tracking order
			System.out.format("i got a new tracking data");
			OrdersListData ordersListData = App.orders.trackOrder((TrackingOrderData) msg);
			ordersListData.setMode("tracking");
			System.out.format("i got a new tracking data11");
			SafeSendToClient(ordersListData, client);
		}


	}


}