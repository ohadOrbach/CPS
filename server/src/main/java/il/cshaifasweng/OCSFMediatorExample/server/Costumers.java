package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.CostumerData;
import il.cshaifasweng.OCSFMediatorExample.entities.OrderData;
import il.cshaifasweng.OCSFMediatorExample.entities.OrdersListData;
import il.cshaifasweng.OCSFMediatorExample.entities.SubscriptionData;
import org.hibernate.Session;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.server.App.*;


public class Costumers {
    public HashMap<Integer,Costumer> costumers;
    Session session = App.session;

    public Costumers(){
        costumers = new HashMap<Integer,Costumer>();
    }
    public void generateCostumers()
    {
        SafeStartTransaction();
        Costumer or = new Costumer(308283886,"ormeir93@gmail.com","123456789");
        Costumer ronit = new Costumer(308432525,"ronitkolka@gmail.com","123456789");
        App.session.save(or);
        App.session.save(ronit);
        App.session.flush();
        App.SafeCommit();
        pullCostumersFromDB();
    }
    public void pullCostumersFromDB()
    {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<Costumer> query = builder.createQuery(Costumer.class);
        query.from(Costumer.class);
        List<Costumer> data = App.session.createQuery(query).getResultList();
        costumers.clear();
        for(Costumer costumer : data)
        {
            costumers.put(costumer.getId(), costumer);
        }
    }

    public HashMap<Integer,Costumer> getCostumers()
    {
        pullCostumersFromDB();
        return costumers;
    }

    public CostumerData costumerLoginCheck(String id, String password)
    {
        Costumer theCostumer = costumers.get(Integer.valueOf(id));

        if (theCostumer==null || !(theCostumer.getPassword().equals(password)) || theCostumer.isLogin())
        {
            System.out.println("costumer failed");
            return new CostumerData();
        }

        theCostumer.setLogin(true);

        CostumerData costumer = new CostumerData(theCostumer.getId(),theCostumer.getEmail(),theCostumer.getPassword());

        for(FullSubscription fs : theCostumer.fullSubscriptions)
        {
            LocalDate endingDate = fs.getStart().plusMonths(1);
            SubscriptionData sd = new SubscriptionData("full",fs.getLicencePlate(),endingDate,Integer.toString(fs.getCostumer().getId()));
            costumer.addSubscription(sd);
        }
        for(RegularSubscription fs : theCostumer.regularSubscriptions)
        {
            LocalDate endingDate = fs.getStart().plusMonths(1);
            SubscriptionData sd = new SubscriptionData("full",fs.getLicencePlate(),endingDate,Integer.toString(fs.getCostumer().getId()));
            costumer.addSubscription(sd);
        }
        return costumer;
    }

    public String addNewCostumer(String id, String password,String email)
    {
        Costumer costumer = costumers.get(Integer.valueOf(id));
        if(costumer != null)
        {
            return "failure";
        }
        App.SafeStartTransaction();
        Costumer newCostumer = new Costumer(Integer.parseInt(id),email,password);
        App.session.save(newCostumer);
        App.session.flush();
        App.SafeCommit();
        costumers.put(Integer.parseInt(id),newCostumer);
        return "success";
    }

    Costumer getCostumer (int id)
    {
        return costumers.get(id);
    }

    void logOutCostumer(String id)
    {
        Costumer costumer = costumers.get(Integer.valueOf(id));
        costumer.setLogin(false);
    }
}
