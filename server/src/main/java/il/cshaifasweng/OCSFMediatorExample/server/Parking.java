package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.*;

@Entity
@Table(name = "parking")
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int status;     //0 is free, 1 is occupied, 2 is reserved, -1 is broken
    private int row;
    private int column;
    private int depth;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parkinglot", referencedColumnName = "id")
    private ParkingLot parkingLot;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parkingOrder", referencedColumnName = "id")
    private ParkingOrder parkingOrder;

    public Parking(){}

    public Parking(int id,int status, int row, int column, int depth){
        this.status = status;
        this.row = row;
        this.column = column;
        this.depth = depth;
        this.parkingLot = null;
        this.parkingOrder = null;
    }

    public Parking(int id,int status, int row, int column, int depth, ParkingLot parkingLot, ParkingOrder parkingOrder){
        this.status = status;
        this.row = row;
        this.column = column;
        this.depth = depth;
        this.parkingLot = null;
        this.parkingOrder = null;
        this.parkingLot = parkingLot;
        this.parkingOrder = parkingOrder;
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

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingOrder getParkingOrder() {
        return parkingOrder;
    }

    public void setParkingOrder(ParkingOrder parkingOrder) {
        this.parkingOrder = parkingOrder;
    }
}
