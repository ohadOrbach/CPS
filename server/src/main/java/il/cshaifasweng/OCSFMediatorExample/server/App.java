package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.OrderData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.PricesList;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App
{

    private static SimpleServer server;
    public static Session session;
    public static ParkingLots parkinglots;
    public static ParkingLots parkingPrices;
    public static Complaints complaints;
    public static StastisticalInformations sastisticalInformations;
    public static Orders orders;
    public static Employees employees;
    public static Costumers costumers;
    public static Subscriptions subscriptions;
    public static Parkings parkings;
    public static Kiosk kiosk;

    public static void main( String[] args ) throws IOException
    {
        session = getSessionFactory().openSession();
        parkinglots = new ParkingLots();
        parkingPrices = new ParkingLots();
        parkings = new Parkings();
        parkinglots.generateParkingLots();
        parkinglots.pullParkingLots();
        parkingPrices.pullParkingPrices();
        complaints = new Complaints();
        complaints.pullComplaints();
        sastisticalInformations = new StastisticalInformations();
        sastisticalInformations.pullStastisticalInformationFromDB();
        parkings.pullStastisticalInformationFromDB();
        orders = new Orders();
        employees = new Employees();
        employees.generateEmployees();
        employees.pullEmployeesFromDB();
        costumers = new Costumers();
        costumers.generateCostumers();
        subscriptions = new Subscriptions();
        kiosk = new Kiosk();
        server = new SimpleServer(3000);
        server.listen();
    }

    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(ParkingLot.class);
        configuration.addAnnotatedClass(ParkingLots.class);
        configuration.addAnnotatedClass(Parking.class);
        configuration.addAnnotatedClass(Parkings.class);
        configuration.addAnnotatedClass(Orders.class);
        configuration.addAnnotatedClass(Kiosk.class);
        configuration.addAnnotatedClass(ParkingPrices.class);
        configuration.addAnnotatedClass(Complaint.class);
        configuration.addAnnotatedClass(ParkingOrder.class);
        configuration.addAnnotatedClass(Complaints.class);
        configuration.addAnnotatedClass(StastisticalInformation.class);
        configuration.addAnnotatedClass(StastisticalInformations.class);
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(Employees.class);
        configuration.addAnnotatedClass(Costumer.class);
        configuration.addAnnotatedClass(Costumers.class);
        configuration.addAnnotatedClass(Subscription.class);
        configuration.addAnnotatedClass(Subscriptions.class);
        configuration.addAnnotatedClass(FullSubscription.class);
        configuration.addAnnotatedClass(RegularSubscription.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(serviceRegistry);

    }

    private static int TransactionDepth = 0;
    public static void SafeStartTransaction(){
        if(TransactionDepth++ == 0) {
            session.beginTransaction();
        }
    }
    public static void SafeCommit(){
        if(--TransactionDepth == 0) {
            session.getTransaction().commit();
        }
    }


}