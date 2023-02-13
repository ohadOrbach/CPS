package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StastisticalInformationListData implements Serializable {
    public List<StastisticalInformationData> stastisticalInformationList;

    public StastisticalInformationListData() {
        this.stastisticalInformationList = new ArrayList<>();
    }

    public StastisticalInformationListData(List<StastisticalInformationData> dataList) {
        this.stastisticalInformationList = new ArrayList<>();
        for (StastisticalInformationData stastisticalInformationData : dataList) {
            stastisticalInformationList.add(stastisticalInformationData);
        }

    }

    public List<StastisticalInformationData> getStastisticalInformationListData() {
        return this.stastisticalInformationList;
    }

}
