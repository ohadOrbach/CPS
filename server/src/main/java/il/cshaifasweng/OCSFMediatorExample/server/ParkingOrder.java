package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.OrderData;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private String arrivalTime = (LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute()).toString();
    private LocalDate leavingDate;
    private String leavingTime;
    private String orderTime = (LocalTime.now()).toString();
    private int status2;     // 0 if we didn't fulfill the order, 1 if we inserted the car, -1 if its completed (we also removed it)

    private double expectedPayment;

    @ManyToOne
    @JoinColumn(name = "parking_id2", referencedColumnName = "id")
    private Parking parking_id2;


    public ParkingOrder(int userId, int carNumber, LocalDate arrivalDate, String arrivalTime, LocalDate leavingDate, String leavingTime, String email, String advance, String parkingLotName) {
        this.userId = userId;
        this.carNumber = carNumber;
        this.leavingTime = leavingTime;
        this.email = email;
        this.advance = advance;
        this.arrivalTime = arrivalTime;
        this.leavingDate = leavingDate;
        this.arrivalDate = arrivalDate;
        this.status2 = 0;   //initilize
        this.expectedPayment = calcExpectedPayment(parkingLotName);
    }


    public ParkingOrder() {
    }

    private ParkingLot findParkingByName(String parkingLotName) {
        List<ParkingLot> parkingLotList = App.parkinglots.getParkingLots();
        for (ParkingLot parkingLot : parkingLotList) {
            if (Objects.equals(parkingLot.getName(), parkingLotName)) {
                return parkingLot;
            }
        }
        return null;
    }

    private String findParkingLotName() {
        return parking_id2.getParkingLot().getName();
    }

    public String getArrivalTime() {
        return this.arrivalTime;
    }

    public String getEmail() {
        return this.email;
    }

    public String getLeavingTime() {
        return this.leavingTime;
    }

    public int getUserId() {
        return this.userId;
    }

    public int getCarNumber() {
        return this.carNumber;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public OrderData getOrderData() {
        OrderData orderData = new OrderData(orderId, Integer.toString(userId),
                Integer.toString(carNumber), arrivalDate, arrivalTime, leavingDate, leavingTime, email, advance, findParkingLotName());
        orderData.setStatus(this.status2);
        return orderData;
    }

    public LocalDate getArrivalDate() {
        return this.arrivalDate;
    }

    public LocalDate getLeavingDate() {
        return this.leavingDate;
    }

    public Parking getParking() {
        return parking_id2;
    }

    public void setParking(Parking parking) {
        this.parking_id2 = parking;
    }

    public int getId() {
        return orderId;
    }

    public void setId(int orderId) {
        this.orderId = orderId;
    }


    public int getStatus() {
        return status2;
    }

    public void setStatus(int status2) {
        this.status2 = status2;
    }

    private double calcExpectedPayment(String parkingLotName) {
        ParkingLot Pl = findParkingByName(parkingLotName);
        double parkingPrice = Pl.getAllPrices().getOrderedParkingPrice();

        LocalDateTime arrival = LocalDateTime.of(arrivalDate, LocalTime.parse(arrivalTime));
        LocalDateTime leaving = LocalDateTime.of(leavingDate, LocalTime.parse(leavingTime));

        long durationInMinutes =
                (Duration.between(arrival, leaving).toMillis() / 1000) / 60;

        return durationInMinutes * (parkingPrice / 60.0);
    }

    public double getExpectedPayment() {
        return expectedPayment;
    }

    public void setExpectedPayment(double expectedPayment) {
        this.expectedPayment = expectedPayment;
    }
}
