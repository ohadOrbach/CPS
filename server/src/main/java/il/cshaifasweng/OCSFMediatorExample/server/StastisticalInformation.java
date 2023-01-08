package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "stastisticalInformation")
public class StastisticalInformation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date;
    private int actualOrders;
    private int canceledOrders;
    private int parkingLateNum;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parkinglot", referencedColumnName = "id")
    private ParkingLot parkingLot;



    public StastisticalInformation(int id,LocalDateTime date,int actualOrders, int canceledOrders, int parkingLateNum) {
        this.id = id;
        this.date = date;
        this.actualOrders = actualOrders;
        this.canceledOrders = canceledOrders;
        this.parkingLateNum = parkingLateNum;
    }

    public StastisticalInformation(int id, LocalDateTime date,int actualOrders, int canceledOrders, int parkingLateNum, ParkingLot parkingLot) {
        this.id = id;
        this.date = date;
        this.actualOrders = actualOrders;
        this.canceledOrders = canceledOrders;
        this.parkingLateNum = parkingLateNum;
        this.parkingLot = parkingLot;
    }

    public StastisticalInformation(List<StastisticalInformation> list){
        this.id = id;
        this.date = null;
        this.actualOrders = 0;
        this.canceledOrders = 0;
        this.parkingLateNum = 0;
    }


    public StastisticalInformation(){}


    public int getParkingLotId(){
        return this.id;
    }

    public int getActualOrders() {
        return actualOrders;
    }

    public void setActualOrders(int actualOrders) {
        this.actualOrders = actualOrders;
    }

    public int getCanceledOrders() {
        return canceledOrders;
    }

    public void setCanceledOrders(int canceledOrders) {
        this.canceledOrders = canceledOrders;
    }

    public int getParkingLateNum() {
        return parkingLateNum;
    }

    public void setParkingLateNum(int parkingLateNum) {
        this.parkingLateNum = parkingLateNum;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
