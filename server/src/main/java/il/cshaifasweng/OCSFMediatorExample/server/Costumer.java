package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.CostumerData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "costumers")
public class Costumer {
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "orders_id")
    public Set<ParkingOrder> parkingOrders = new LinkedHashSet<>();
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "regular_subscriptions_id")
    public List<RegularSubscription> regularSubscriptions = new ArrayList<>();
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "full_subscriptions_id")
    public List<FullSubscription> fullSubscriptions = new ArrayList<>();
    boolean login;
    @Id
    private int id;
    private String email;
    private String password;
    @OneToMany(mappedBy = "issuedBy")
    private List<Complaint> complaints;

    public Costumer(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        login = false;
    }

    public Costumer() {
        login = false;
    }

    public CostumerData getCostumerData() {
        return new CostumerData(password, email);
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<ParkingOrder> getParkingOrders() {
        return parkingOrders;
    }

    public void addParkingOrder(ParkingOrder parkingOrder) {
        parkingOrders.add(parkingOrder);
    }

    public void addRegularSubscriptions(RegularSubscription regularSubscription) {
        regularSubscriptions.add(regularSubscription);
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public void addFullSubscriptions(FullSubscription fullSubscription) {
        fullSubscriptions.add(fullSubscription);
    }

    public boolean subFound(String licencePlate, ParkingLot parkingLot) {
        //System.out.println("hello");
        int i = 0;
        //System.out.println("hello1");
        for (RegularSubscription rs : this.regularSubscriptions) {
            // System.out.println("im looking for: "+licencePlate);
            if (rs.getLicencePlate().equals(licencePlate)) {
                if (parkingLot.getParkingLotId() == rs.getParkingLot().getParkingLotId()) {
                    return true;
                }
            }
        }

        for (FullSubscription rs : this.fullSubscriptions) {
            if (rs.getLicencePlate().equals(licencePlate)) {
                System.out.println(i++);
                return true;
            }
        }

        return false;
    }

    public String subFoundByOrder(String licencePlate, ParkingLot parkingLot) {
        //System.out.println("hello");
        int i = 0;
        //System.out.println("hello1");
        for (RegularSubscription rs : this.regularSubscriptions) {
            // System.out.println("im looking for: "+licencePlate);
            if (rs.getLicencePlate().equals(licencePlate)) {
                if (parkingLot.getParkingLotId() == rs.getParkingLot().getParkingLotId()) {
                    return "regular";
                }
            }
        }

        for (FullSubscription rs : this.fullSubscriptions) {
            if (rs.getLicencePlate().equals(licencePlate)) {
                System.out.println(i++);
                return "fully";
            }
        }

        return "non";
    }
}
