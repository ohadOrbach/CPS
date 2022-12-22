package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotId;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPrices;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParkingLots {
    public List<ParkingLot> parkingLots;
    public ParkingLots()
    {
        parkingLots = new ArrayList<ParkingLot>();
    }

    public void generateParkingLots()
    {
        App.SafeStartTransaction();
        Random random = new Random();
        ParkingLot namal1 = new ParkingLot("namal1",1);
        ParkingLot namal2 = new ParkingLot("namal2",2);
        ParkingLot namal3= new ParkingLot("namal3",3);
        ParkingLot namal4 =new ParkingLot("namal4",4);
        ParkingLot namal5 = new ParkingLot("namal5",5);
        App.session.save(namal1);
        App.session.save(namal2);
        App.session.save(namal3);
        App.session.save(namal4);
        App.session.save(namal5);
        App.session.flush();
        App.SafeCommit();
    }

    public void pullParkingLots()
    {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<ParkingLot> query = builder.createQuery(ParkingLot.class);
        query.from(ParkingLot.class);
        List<ParkingLot> data = App.session.createQuery(query).getResultList();
        parkingLots.clear();
        parkingLots.addAll(data);
    }

    public void changePrice(int id,double newPrice)
    {
        App.SafeStartTransaction();
        ParkingLot temp=new ParkingLot();
        for (ParkingLot pl :parkingLots)
        {
            if(pl.getParkingLotId()==id)
            {
                temp=pl;
            }
        }
        temp.setParkingPrice(newPrice);
        App.session.save(temp);
        App.session.flush();
        App.SafeCommit();
    }
}
