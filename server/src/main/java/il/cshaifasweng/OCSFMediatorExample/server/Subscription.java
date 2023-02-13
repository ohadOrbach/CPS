package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "subscriptions")
public class Subscription {
    @Id
    private int subscriptionId;
    @ManyToOne
    @JoinColumn(name = "costumers")
    private Costumer costumer;
    private String licencePlate;
    private LocalDate start;

    public Subscription(int subscriptionId, Costumer costumer, String licencePlate, LocalDate startingDate) {
        this.subscriptionId = subscriptionId;
        this.costumer = costumer;
        this.licencePlate = licencePlate;
        this.start = startingDate;
    }

    public Subscription() {
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Costumer getCostumer() {
        return costumer;
    }

    public void setCostumer(Costumer costumer) {
        this.costumer = costumer;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }
}

