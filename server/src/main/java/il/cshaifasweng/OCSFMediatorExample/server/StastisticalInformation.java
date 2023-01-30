package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "stastisticalInformation")
public class StastisticalInformation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int parkingLotId;
    private String name;
    private LocalDate date;
    private int actualOrders;
    private int canceledOrders;
    private int parkingLateNum;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parkinglot", referencedColumnName = "id")
    private ParkingLot parkingLot;



    public StastisticalInformation(int parkingLotId ,String name ,LocalDate date,int actualOrders, int canceledOrders, int parkingLateNum) {
        this.parkingLotId = parkingLotId;
        this.name = name;
        this.date = date;
        this.actualOrders = actualOrders;
        this.canceledOrders = canceledOrders;
        this.parkingLateNum = parkingLateNum;
    }

    public StastisticalInformation(int parkingLotId , LocalDate date,int actualOrders, int canceledOrders, int parkingLateNum, ParkingLot parkingLot) {
        this.parkingLotId = parkingLotId;
        this.name = parkingLot.getName();
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


    public int getStatisticId(){
        return this.id;
    }

    public int getParkingLotId(){ return this.parkingLotId;}

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
