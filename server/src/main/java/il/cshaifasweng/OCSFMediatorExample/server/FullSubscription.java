package il.cshaifasweng.OCSFMediatorExample.server;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;


@Entity
@Table(name = "full_subscriptions")
public class FullSubscription extends Subscription {
    public FullSubscription(int subscriptionId, Costumer costumer, String licencePlate, LocalDate startingDate) {
        super(subscriptionId, costumer, licencePlate, startingDate);
    }

    public FullSubscription() {

    }
}