package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotListData;
import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationData;
import il.cshaifasweng.OCSFMediatorExample.entities.StastisticalInformationListData;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ReceivedStastisticalInformationEvent {
    private List<StastisticalInformationData> stastisticalInformationDataList;

    public ReceivedStastisticalInformationEvent(StastisticalInformationListData stastisticalInformationList) {
        this.stastisticalInformationDataList = new ArrayList<>();
        List<StastisticalInformationData> dataList = stastisticalInformationList.getParkingLotListData();
        for (StastisticalInformationData stastisticalInformation : dataList) {
            stastisticalInformationDataList.add(stastisticalInformation);
        }
    }

    public List<StastisticalInformationData> getstastisticalInformationDataListDataList() {
        List<StastisticalInformationData> weeklystastisticalInformationList = new ArrayList<>();
        for (StastisticalInformationData stastisticalInformation : stastisticalInformationDataList) {
            //we get all the data from this week
            if(Duration.between(stastisticalInformation.getDate(), java.time.LocalDate.now()).toDays() <= 7)
            stastisticalInformationDataList.add(stastisticalInformation);
        }
        return this.stastisticalInformationDataList;
    }

}
