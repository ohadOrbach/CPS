package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.ComplaintData;
import il.cshaifasweng.OCSFMediatorExample.entities.ComplaintListData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingPricesData;
import il.cshaifasweng.OCSFMediatorExample.entities.PricesListToConfirm;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ParkingPricesForConfirmationList implements Serializable {
    public List<ParkingPricesForConfirmation> parkingPricesForConfirmtion;

    public ParkingPricesForConfirmationList() {
        parkingPricesForConfirmtion = new ArrayList<>();
    }

    public void addParkingPricesForConfirmtion(ParkingPricesData parkingPricesData, ParkingLot parkingLot) {
        App.SafeStartTransaction();
        ParkingPricesForConfirmation p = new ParkingPricesForConfirmation(parkingPricesData, parkingLot);
        App.session.save(p);
        App.session.flush();
        App.SafeCommit();
        parkingPricesForConfirmtion.add(p);
    }

    public void removeParkingPricesForConfirmtion(int id) {
        for (ParkingPricesForConfirmation c : parkingPricesForConfirmtion) {
            if (c.getParkingLotId() == id) {
                App.SafeStartTransaction();
                parkingPricesForConfirmtion.remove(c);
                App.session.delete(c);
                App.session.flush();
                App.SafeCommit();
            }
        }
    }

    public ParkingPricesForConfirmation getParkingPricesForConfirmtion(int id) {
        ParkingPricesForConfirmation temp = new ParkingPricesForConfirmation();
        for (ParkingPricesForConfirmation c : parkingPricesForConfirmtion) {
            if (c.getParkingLotId() == id) {
                temp = new ParkingPricesForConfirmation(c.getParkingLotId(), c.getParkingPrice(), c.getOrderedParkingPrice(), c.getParkingLot());
            }
        }
        return temp;
    }

    public void pullPricesConfirm() {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<ParkingPricesForConfirmation> query = builder.createQuery(ParkingPricesForConfirmation.class);
        query.from(ParkingPricesForConfirmation.class);
        List<ParkingPricesForConfirmation> data = App.session.createQuery(query).getResultList();
        parkingPricesForConfirmtion.clear();
        parkingPricesForConfirmtion.addAll(data);
    }

    public PricesListToConfirm getPdata(){
        List<ParkingPricesData> pData = new ArrayList<>();
        for (ParkingPricesForConfirmation p : parkingPricesForConfirmtion) {
            ParkingPricesData temp = new ParkingPricesData(p.getParkingLotId(), p.getParkingPrice(), p.getOrderedParkingPrice());
            pData.add(temp);
        }
        PricesListToConfirm temp = new PricesListToConfirm(pData);
        return temp;
    }
}
