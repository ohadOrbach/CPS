package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.OrderData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotData;
import il.cshaifasweng.OCSFMediatorExample.entities.ParkingLotListData;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "parkingOrders")
public class ParkingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private int userId;
    private int carNumber;
    private String email;
    private String advance;
    private LocalDate arrivalDate = LocalDate.now();
    private String arrivalTime = (LocalTime.now()).toString();
    private LocalDate leavingDate;
    private String leavingTime;
    private String orderTime = (LocalTime.now()).toString();

    @ManyToOne
    @JoinColumn(name = "parkingLot_id")
    private ParkingLot parkingLot;

    @OneToOne(mappedBy = "parkingOrder")
    private Parking parking;


    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingOrder(int userId, int carNumber,  LocalDate arrivalDate, String arrivalTime,LocalDate leavingDate, String leavingTime, String email, String advance, String parkingLotName){
        this.userId = userId;
        this.carNumber = carNumber;
        this.leavingTime = leavingTime;
        this.email = email;
        this.advance = advance;
        this.parkingLot = findParkingByName(parkingLotName);
        this.arrivalTime = arrivalTime;
        this.leavingDate = leavingDate;
        this.arrivalDate = arrivalDate;
    }

    private ParkingLot findParkingByName(String parkingLotName) {
        List<ParkingLot> parkingLotList = App.parkinglots.getParkingLots();
        for(ParkingLot parkingLot: parkingLotList){
            if(Objects.equals(parkingLot.getName(), parkingLotName)){
                return parkingLot;
            }
        }
        return null;
    }


    public ParkingOrder() {
    }


    public String getEmail(){ return this.email; }
    public String getLeavingTime(){ return this.leavingTime; }
    public int getUserId() { return this.userId; }
    public int getCarNumber() { return this.carNumber; }
    public int getOrderId() { return this.orderId; }
    public ParkingLot getParkingLot() { return this.parkingLot; }

    public String getOrderTime() { return orderTime; }

    public OrderData getOrderData() {
        OrderData orderData = new OrderData(orderId ,Integer.toString(userId),
                Integer.toString(carNumber), arrivalDate, arrivalTime, leavingDate, leavingTime, email, advance, parkingLot.getName());
        return orderData;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }
}