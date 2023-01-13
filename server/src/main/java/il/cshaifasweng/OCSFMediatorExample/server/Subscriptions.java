package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.CostumerData;
import il.cshaifasweng.OCSFMediatorExample.entities.EmployeeData;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.server.App.SafeStartTransaction;
import static il.cshaifasweng.OCSFMediatorExample.server.App.employees;

public class Subscriptions
{
    HashMap<Integer,RegularSubscription> regularSubscriptions;
    HashMap<Integer,FullSubscription> fullSubscriptions;

    Session session = App.session;

    public Subscriptions()
    {
        regularSubscriptions = new HashMap<Integer,RegularSubscription>();
        fullSubscriptions = new HashMap<Integer,FullSubscription>();
    }

    public void pullSubscriptionsFromDB()
    {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<RegularSubscription> query = builder.createQuery(RegularSubscription.class);
        query.from(RegularSubscription.class);
        List<RegularSubscription> data = session.createQuery(query).getResultList();
        regularSubscriptions.clear();
        for(RegularSubscription sub: data)
        {
            regularSubscriptions.put(sub.getSubscriptionId(),sub);
        }


        CriteriaQuery<FullSubscription> anotherQuery = builder.createQuery(FullSubscription.class);
        anotherQuery.from(FullSubscription.class);
        List<FullSubscription> moreData = session.createQuery(anotherQuery).getResultList();
        fullSubscriptions.clear();
        for(FullSubscription sub: moreData)
        {
            fullSubscriptions.put(sub.getSubscriptionId(),sub);
        }
    }

    public void getSubscriptions()
    {
        pullSubscriptionsFromDB();
    }

    public String addNewRegularSubscription(Costumer costumer, String licencePlate, LocalDate
            startingDate, ParkingLot parkingLot, String expectedLeavingTime)
    {
        int subscriptionId =  (int)(Math.random() * 900000) + 100000;
        while((regularSubscriptions.get(subscriptionId))!=null)
        {
            subscriptionId =  (int)(Math.random() * 900000) + 100000;
        }
        App.SafeStartTransaction();
        RegularSubscription subscription = new RegularSubscription(subscriptionId,costumer,licencePlate,startingDate,parkingLot,expectedLeavingTime);
        App.session.save(subscription);
        App.session.flush();
        App.SafeCommit();
        regularSubscriptions.put(subscription.getSubscriptionId(),subscription);
        costumer.addRegularSubscriptions(subscription);
        return "new subscription made";
    }

    public String addNewFullSubscription(Costumer costumer, String licencePlate, LocalDate start)
    {
        int subscriptionId =  (int)(Math.random() * 900000) + 100000;
        while((fullSubscriptions.get(subscriptionId))!=null)
        {
            subscriptionId =  (int)(Math.random() * 900000) + 100000;
        }
        App.SafeStartTransaction();
        FullSubscription subscription = new FullSubscription(subscriptionId,costumer,licencePlate,start);
        App.session.save(subscription);
        App.session.flush();
        App.SafeCommit();
        fullSubscriptions.put(subscription.getSubscriptionId(),subscription);
        costumer.addFullSubscriptions(subscription);
        return "new full subscription made";
    }






}
