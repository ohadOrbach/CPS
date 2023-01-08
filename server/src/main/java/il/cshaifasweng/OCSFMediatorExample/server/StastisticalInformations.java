package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationData;
import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationListData;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StastisticalInformations {
    public List<StastisticalInformation> stastisticalInformations;
    public StastisticalInformations()
    {
        stastisticalInformations=new ArrayList<StastisticalInformation>();
    }
    public void pullStastisticalInformationFromDB()
    {
        CriteriaBuilder builder = App.session.getCriteriaBuilder();
        CriteriaQuery<StastisticalInformation> query = builder.createQuery(StastisticalInformation.class);
        query.from(StastisticalInformation.class);
        List<StastisticalInformation> data = App.session.createQuery(query).getResultList();
        stastisticalInformations.clear();
        stastisticalInformations.addAll(data);
    }


    public void addStastisticalInformation(int id, LocalDateTime date , int actualOrder , int canceledOrders , int parkingLateNum) {
        App.SafeStartTransaction();
        StastisticalInformation stastisticalInformation = new StastisticalInformation(id,date, actualOrder, canceledOrders, parkingLateNum);
        App.session.save(stastisticalInformation);
        App.session.flush();
        App.SafeCommit();

        stastisticalInformations.add(stastisticalInformation);
    }

    public StastisticalInformationData getStastisticalInformationData(StastisticalInformation stastisticalInformation)
    {
        return new StastisticalInformationData(stastisticalInformation.getParkingLotId(),stastisticalInformation.getDate(), stastisticalInformation.getActualOrders(), stastisticalInformation.getCanceledOrders(),stastisticalInformation.getParkingLateNum());
    }

    public StastisticalInformationListData getgetStastisticalInformationList() {
        List<StastisticalInformationData> dataList = new ArrayList<>();
        for(StastisticalInformation stastisticalInformation: stastisticalInformations){
            StastisticalInformationData stastisticalInformationData = getStastisticalInformationData(stastisticalInformation);
            dataList.add(stastisticalInformationData);
        }
        return new StastisticalInformationListData(dataList);
    }


}
