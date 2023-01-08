package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotListData;
import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationData;
import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationListData;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class ReceivedStastisticalInformationEvent {
    private List<StastisticalInformationData> stastisticalInformationDataList;

    public ReceivedStastisticalInformationEvent(StastisticalInformationListData stastisticalInformationList) {
        this.stastisticalInformationDataList = new ArrayList<>();
        List<StastisticalInformationData> dataList = stastisticalInformationList.getStastisticalInformationListData();
        for (StastisticalInformationData stastisticalInformation : dataList) {
            stastisticalInformationDataList.add(stastisticalInformation);
        }
    }

    public List<StastisticalInformationData> getstastisticalInformationDataListDataList() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        List<StastisticalInformationData> weeklystastisticalInformationList = new ArrayList<>();
        for (StastisticalInformationData stastisticalInformation : stastisticalInformationDataList) {
            //we get all the data from this week
            if(DAYS.between(now,stastisticalInformation.getDate()) <= 7) {
                weeklystastisticalInformationList.add(stastisticalInformation);
            }
        }
        return weeklystastisticalInformationList;
    }

}
