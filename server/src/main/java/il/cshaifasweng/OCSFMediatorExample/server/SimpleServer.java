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
import java.util.List;
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
				System.out.println("im in update mode \n");
				switch (args[0]) {
					case "parking price" -> { // update item price #update:ItemPrice,itemId,newPrice
						App.parkingPrices.changePrice(Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]));
						App.parkingPrices.pullParkingPrices();
					}
					case "complaint status" -> {
						ConnectionToClient c = App.complaints.changeStatus(Integer.parseInt(args[1]));
						Message arrivalMsg = new Message("Your complain is closed you got" +Integer.parseInt(args[2])+" compensation");
						SafeSendToClient(arrivalMsg, c);
						App.complaints.pullComplaints();
					}
					case "parkings" -> { // update parkings to  client screen
						System.out.println("in update parkings  "+args[1]+"\n");
						ParkingLot p = App.parkinglots.getParkingLotByName(args[1]);
						Parkings plist = new Parkings(p.getParkings());
						ParkingListData returnParkings = plist.getParkingList();
						SafeSendToClient(returnParkings, client);
					}
				}
			} else if (msgString.startsWith("#request")) {
				String[] args = (msgString.split(":")[1]).split(",");
				switch (args[0]) {
					case " parking lots list" -> {
						System.out.format("i am in case request for parking lots list\n");
						ParkingLotListData parkingLotListData = App.parkinglots.getParkingLotList();
						SafeSendToClient(parkingLotListData, client);
					}

					case " prices table" -> {
						System.out.format("i am in case request for prices table\n");
						PricesList parkingPricesList = App.parkinglots.getParkingLotsPrices();
						SafeSendToClient(parkingPricesList, client);
					}
					case " complaint table" -> {
						System.out.format("i am in case request for complaint table\n");
						ComplaintListData complaintsList = App.complaints.getComplaints();
						SafeSendToClient(complaintsList, client);
					}

					case " reports list" -> {
						System.out.format("i am in case request for report list\n");
						ReportListData reportsListData = App.reports.getReportsList();
						SafeSendToClient(reportsListData, client);
					}

					case " stastistical information list" -> {
						System.out.format("i am in case request for stastistical information list\n");
						StastisticalInformationListData StastisticalInformationList = App.sastisticalInformations.getStastisticalInformationList();
						SafeSendToClient(StastisticalInformationList, client);
					}
				}
			}
			else if (msgString.startsWith("employee login"))
			{
				String[] args = (msgString.split(":")[1]).split(",");
				EmployeeData employee = App.employees.employeeLoginCheck(args[0],args[1]);
				if (employee.getJob().equals("costumer service")){
					Employee emp = App.employees.findEmployeeById(employee.getId());
					String receivedMsg = emp.checkReminders();
					Message arrivalMsg = new Message(receivedMsg);
					SafeSendToClient(arrivalMsg, client);
				}
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

			// we got a requset from kiosk to take in client car
			else if (msgString.startsWith("#Take in client car:"))
			{
				String[] args = (msgString.split(":")[1]).split(",");
				String clientId = args[0];
				String carId = args[1];
				String parkingLotName = args[2];
				String answer = App.kiosk.InsertCarIntoParkingLot(clientId,carId,parkingLotName);
				Message arrivalMsg = new Message(answer);
				SafeSendToClient(arrivalMsg, client);
			}

			// we got a requset from kiosk to take out client car
			else if (msgString.startsWith("#Take out client car:"))
			{
				String[] args = (msgString.split(":")[1]).split(",");
				String clientId = args[0];
				String carId = args[1];
				String parkingLotName = args[2];
				String answer = App.kiosk.TakeOutCarInParkingLot(clientId,carId,parkingLotName);
				Message arrivalMsg = new Message(answer);
				SafeSendToClient(arrivalMsg, client);
			}


		} else if (msg.getClass().equals(ComplaintData.class)) { // Make a complaint
			Employee emp = App.employees.getRandomCS();
			String receivedMsg = App.complaints.addComplaint((ComplaintData) msg, (Employee) emp, (ConnectionToClient) client);
			App.complaints.pullComplaints();
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
			App.sastisticalInformations.addStastisticalInformationForCancledOrder(ordersListData);
			//TODO chack if its works
			App.sastisticalInformations.pullStastisticalInformationFromDB();
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

		} else if (msg.getClass().equals(ReportData.class)) { // Make a report
			System.out.format("i got a new report\n");
			String receivedMsg = App.reports.addReport((ReportData) msg);
			Message arrivalMsg = new Message(receivedMsg);
			SafeSendToClient(arrivalMsg, client);
		}


	}


}