package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.CostumerData;
import il.cshaifasweng.OCSFMediatorExample.entities.OrderData;
import il.cshaifasweng.OCSFMediatorExample.entities.OrdersListData;
import org.hibernate.Session;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

        Costumer handmade = costumers.get(308283886);

        if (theCostumer==null || !(theCostumer.getPassword().equals(password)))
        {
            return new CostumerData();
        }

        System.out.println("costumer exist");
        return (new CostumerData(theCostumer.getId(),theCostumer.getEmail(),theCostumer.getPassword()));

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
