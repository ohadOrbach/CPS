package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingListData;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class Parkings {
    public List<Parking> parkingList;

    public Parkings() {
        parkingList = new ArrayList<Parking>();
    }

    public Parkings(List<Parking> p) {
        parkingList = new ArrayList<Parking>();
        for (Parking parking : p) {
            parkingList.add(parking);
        }
    }

    public void pullStastisticalInformationFromDB() {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<Parking> query = builder.createQuery(Parking.class);
        query.from(Parking.class);
        List<Parking> data = App.session.createQuery(query).getResultList();
        parkingList.clear();
        parkingList.addAll(data);
    }

    public ParkingData getparkingData(Parking parking) {
        ArrayList<Integer> ordersId = new ArrayList<>();
        for (ParkingOrder parkingOrder : parking.getParkingOrder()) {
            ordersId.add(parkingOrder.getOrderId());
        }
        return new ParkingData(parking.getId(), parking.getStatus(), parking.getParkingLot().getParkingLotId(), parking.getRow(), parking.getColumn(), parking.getDepth(), ordersId);
    }

    public ParkingListData getParkingList() {
        List<ParkingData> dataList = new ArrayList<>();
        for (Parking parking : parkingList) {
            ParkingData parkingData = getparkingData(parking);
            dataList.add(parkingData);
        }
        return new ParkingListData(dataList);
    }

}
