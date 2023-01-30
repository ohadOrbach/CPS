package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingListData;
import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationData;
import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationListData;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class Parkings {
    public List<Parking> parkingList;
    public Parkings()
    {
        parkingList=new ArrayList<Parking>();
    }
    public void pullStastisticalInformationFromDB()
    {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<Parking> query = builder.createQuery(Parking.class);
        query.from(Parking.class);
        List<Parking> data = App.session.createQuery(query).getResultList();
        parkingList.clear();
        parkingList.addAll(data);
    }
    public ParkingData getparkingData(Parking parking)
    {
        return new ParkingData(parking.getId(), parking.getStatus() , parking.getParkingLot().getParkingLotId() ,parking.getRow(), parking.getColumn(), parking.getDepth(),parking.getParkingOrder().getOrderId());
    }

    public ParkingListData getParkingList() {
        List<ParkingData> dataList = new ArrayList<>();
        for(Parking parking: parkingList){
            ParkingData parkingData = getparkingData(parking);
            dataList.add(parkingData);
        }
        return new ParkingListData(dataList);
    }

}