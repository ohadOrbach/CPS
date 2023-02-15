package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.CostumerData;
import il.cshaifasweng.OCSFMediatorExample.entities.SubscriptionData;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static il.cshaifasweng.OCSFMediatorExample.server.App.SafeStartTransaction;


public class Costumers {
    public HashMap<Integer, Costumer> costumers;
    Session session = App.session;

    public Costumers() {
        costumers = new HashMap<Integer, Costumer>();
    }

    public void generateCostumers() {
        SafeStartTransaction();
        Costumer or = new Costumer(308283886, "ormeir93@gmail.com", "123456789");
        Costumer amit = new Costumer(318883584, "amit@gmail.com", "1");
        Costumer ronit = new Costumer(308432525, "ronitkolka@gmail.com", "123456789");
        costumers.put(amit.getId(), amit);
        costumers.put(or.getId(), or);
        costumers.put(ronit.getId(), ronit);
        App.session.save(or);
        App.session.save(amit);
        App.session.save(ronit);
        App.session.flush();
        App.SafeCommit();
        pullCostumersFromDB();
    }

    public void pullCostumersFromDB() {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<Costumer> query = builder.createQuery(Costumer.class);
        query.from(Costumer.class);
        List<Costumer> data = App.session.createQuery(query).getResultList();
        costumers.clear();
        for (Costumer costumer : data) {
            costumers.put(costumer.getId(), costumer);
        }
    }

    public HashMap<Integer, Costumer> getCostumers() {
        pullCostumersFromDB();
        return costumers;
    }

    public CostumerData costumerLoginCheck(String id, String password) {
        Costumer theCostumer = costumers.get(Integer.valueOf(id));

        if (theCostumer == null || !(theCostumer.getPassword().equals(password)) || theCostumer.isLogin()) {
            System.out.println("costumer failed");
            return new CostumerData();
        }

        App.SafeStartTransaction();
        theCostumer.setLogin(true);
        session.update(theCostumer);
        App.session.save(theCostumer);
        App.session.flush();
        App.SafeCommit();

        CostumerData costumer = new CostumerData(theCostumer.getId(), theCostumer.getEmail(), theCostumer.getPassword());

        System.out.println("\n subscriptions Check When Login");

        for (FullSubscription fs : theCostumer.fullSubscriptions) {
            SubscriptionData sd = new SubscriptionData("full", fs.getLicencePlate(), fs.getEnd(), Integer.toString(fs.getCostumer().getId()),"");
            sd.setSubscriptionId(String.valueOf(fs.getSubscriptionId()));
            costumer.addSubscription(sd);
            System.out.println(fs.getSubscriptionId()+", "+fs.getEnd());
        }
        for (RegularSubscription fs : theCostumer.regularSubscriptions) {
            SubscriptionData sd = new SubscriptionData("regular", fs.getLicencePlate(), fs.getEnd(), Integer.toString(fs.getCostumer().getId()),fs.getParkingLot().getName());
            sd.setSubscriptionId(String.valueOf(fs.getSubscriptionId()));
            costumer.addSubscription(sd);
            System.out.println(fs.getSubscriptionId()+", "+fs.getEnd());
        }

        //System.out.println("and now, the subscriptions in the CostumerData (the size is "+costumer.getSubscriptions().values().size()+"): ");
        //int i =1;
        for(HashMap<String, SubscriptionData> subMap : costumer.getSubscriptions().values())
        {
            for(SubscriptionData sub : subMap.values())
            {
                System.out.println(sub.getSubscriptionId());
            }
        }
        return costumer;
    }

    public String addNewCostumer(String id, String password,String email)
    {
        System.out.println("inside costumer Register method");
        Costumer costumer = costumers.get(Integer.valueOf(id));
        if(costumer != null)
        {
            System.out.println("inside costumer Register method1");
            return "failure";
        }
        App.SafeStartTransaction();
        Costumer newCostumer = new Costumer(Integer.parseInt(id),email,password);
        App.session.save(newCostumer);
        System.out.println("inside costumer Register method2");
        App.session.flush();
        App.SafeCommit();
        System.out.println("inside costumer Register method3");
        costumers.put(Integer.parseInt(id),newCostumer);
        return "success";
    }

    Costumer getCostumer(int id) {
        return costumers.get(id);
    }

    void logOutCostumer(String id) {
        Costumer costumer = costumers.get(Integer.valueOf(id));
        App.SafeStartTransaction();
        costumer.setLogin(false);
        session.update(costumer);
        App.session.save(costumer);
        App.session.flush();
        App.SafeCommit();
    }
}
