package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class ParkingLotData implements Serializable {
    private int id;
    private int rowsNum;
    private int size;
    private String name;

    public ParkingLotData(int id, int rows) {
        this.id = id;
        this.rowsNum = rows;
        this.size = rows*3*3;
    }

    public ParkingLotData(int id, int rows, String name) {
        this.id = id;
        this.rowsNum = rows;
        this.size = rows*3*3;
        this.name = name;
    }
    public int getParkingLotId() {
        return id;
    }

    public String getParkingLotName() {return this.name; }
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
