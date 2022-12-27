package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class ParkingLotData implements Serializable {
    private int id;
    private int rowsNum;
    private int size;

    public ParkingLotData(int id, int rows, int rowNum) {
        this.id = id;
        this.rowsNum = rows;
        this.size = rows*rowNum;
    }

    public int getParkingLotId() {
        return id;
    }

    public void setParkingLotId(int id) {
        this.id = id;
    }

    public int getRowsNum() {
        return rowsNum;
    }

    public void setRowsNum(int rowsNum) {
        this.rowsNum = rowsNum;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
