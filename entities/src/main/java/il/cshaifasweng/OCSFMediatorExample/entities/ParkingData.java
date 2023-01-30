package il.cshaifasweng.OCSFMediatorExample.entities;

public class ParkingData {
    private int id;
    private int status; //0 is free, 1 is occupied, 2 is reserved, -1 is broken
    private int parkingLotId;
    private int row;
    private int column;
    private int depth;
    private int orderId;

    public ParkingData(int id, int status, int parkingLotId, int row, int column, int depth, int orderId) {
        this.id = id;
        this.status = status;
        this.parkingLotId = parkingLotId;
        this.row = row;
        this.column = column;
        this.depth = depth;
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(int parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
