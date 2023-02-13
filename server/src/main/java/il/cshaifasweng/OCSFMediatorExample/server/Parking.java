package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking")
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int status2;     //0 is free, 1 is occupied, 2 is reserved, -1 is broken
    private int row2;
    private int column2;
    private int depth2;

    @ManyToOne
    @JoinColumn(name = "parkingLot", referencedColumnName = "id")
    private ParkingLot parkingLot;


    @OneToMany(mappedBy = "parking_id2")
    private List<ParkingOrder> parkingOrders2;

    public Parking() {
    }

    public Parking(int id, int status, int row, int column, int depth) {
        this.status2 = status;
        this.row2 = row;
        this.column2 = column;
        this.depth2 = depth;
        this.parkingLot = new ParkingLot();
        this.parkingOrders2 = new ArrayList<>();
    }

    public Parking(int id, int status, int row, int column, int depth, ParkingLot parkingLot, List<ParkingOrder> parkingOrders) {
        this.status2 = status;
        this.row2 = row;
        this.column2 = column;
        this.depth2 = depth;
        this.parkingLot = new ParkingLot();
        this.parkingOrders2 = new ArrayList<>();
        this.parkingLot = parkingLot;
        this.parkingOrders2 = parkingOrders;
    }

    public Parking(int status, int row, int column, int depth, ParkingLot parkingLot) {
        this.status2 = status;
        this.row2 = row;
        this.column2 = column;
        this.depth2 = depth;
        this.parkingLot = new ParkingLot();
        this.parkingOrders2 = new ArrayList<>();
        this.parkingLot = parkingLot;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status2;
    }

    public void setStatus(int status) {
        this.status2 = status;
    }

    public int getRow() {
        return row2;
    }

    public void setRow(int row) {
        this.row2 = row;
    }

    public int getColumn() {
        return column2;
    }

    public void setColumn(int column) {
        this.column2 = column;
    }

    public int getDepth() {
        return depth2;
    }

    public void setDepth(int depth) {
        this.depth2 = depth;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public List<ParkingOrder> getParkingOrder() {
        return parkingOrders2;
    }

    public void setParkingOrder(List<ParkingOrder> parkingOrders) {
        this.parkingOrders2 = parkingOrders;
    }

    public void addParkingOrder(ParkingOrder parkingOrder) {
        this.parkingOrders2.add(parkingOrder);
    }
}
