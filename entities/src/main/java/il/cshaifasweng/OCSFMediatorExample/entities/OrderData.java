package il.cshaifasweng.OCSFMediatorExample.entities;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class OrderData implements Serializable {
    private int id;
    private int carNumber;
    private String email;
    private String leavingTime;
    private String arrivalTime = LocalTime.now().toString();
    private String advanceOrder;
    private int orderId;
    private String parkingLotName;
    private LocalDate leavingDate;
    private LocalDate arrivalDate = LocalDate.now();

    public OrderData(String id, String carNumber, String leavingTime, String email, String advanceOrder){
        this.id = Integer.parseInt(id);
        this.carNumber = Integer.parseInt(carNumber);
        this.leavingTime = leavingTime;
        this.email = email;
        this.advanceOrder = advanceOrder;
    }

    public OrderData(int orderId, String id, String carNumber,LocalDate arrivalDate, String arrivalTime,LocalDate leavingDate, String leavingTime, String email, String advanceOrder, String parkingLotName){
        this.id = Integer.parseInt(id);
        this.carNumber = Integer.parseInt(carNumber);
        this.email = email;
        this.advanceOrder = advanceOrder;
        this.orderId = orderId;
        this.parkingLotName = parkingLotName;
        this.arrivalTime = arrivalTime;
        this.arrivalDate = arrivalDate;
        this.leavingTime = leavingTime;
        this.leavingDate = leavingDate;
    }

    public OrderData(String id, String carNumber,LocalDate leavingDate, String leavingTime, String email, String advanceOrder, String parkingLotName){
        this.id = Integer.parseInt(id);
        this.carNumber = Integer.parseInt(carNumber);
        this.leavingTime = leavingTime;
        this.email = email;
        this.advanceOrder = advanceOrder;
        this.parkingLotName = parkingLotName;
        this.leavingDate = leavingDate;
    }

    public OrderData(String id, String carNumber, LocalDate arrivalDate, String arrivalTime,LocalDate leavingDate, String leavingTime, String email, String advanceOrder, String parkingLotName){
        this.id = Integer.parseInt(id);
        this.carNumber = Integer.parseInt(carNumber);
        this.leavingTime = leavingTime;
        this.email = email;
        this.advanceOrder = advanceOrder;
        this.parkingLotName = parkingLotName;
        this.leavingDate = leavingDate;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
    }

    public String getEmail(){ return this.email; }
    public String getLeavingTime(){ return this.leavingTime; }
    public int getId() { return this.id; }
    public int getCarNumber() { return this.carNumber; }
    public String getAdv() { return this.advanceOrder; }
    public int getOrderId() {return this.orderId; }
    public String getParkingName() {
        return this.parkingLotName;
    }

    public LocalDate getArrivalDate() {
        return this.arrivalDate;
    }

    public String getArrivalTime() {
        return this.arrivalTime;
    }
    public LocalDate getLeavingDate() {
        return this.leavingDate;
    }
}
