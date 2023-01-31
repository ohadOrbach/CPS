package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

public class CostumerData implements Serializable {

        private int id;
        private String password;
        private String Email;
        private HashMap<LocalDate,SubscriptionData> subscriptions;
        private boolean loggedIn = false;


        public CostumerData(int id, String password, String email) {
            this.id = id;
            this.password = password;
            Email = email;
            subscriptions = new HashMap<LocalDate,SubscriptionData>();

            loggedIn = true;
        }
        public CostumerData(String password, String email) {
            this.password = password;
            Email = email;

            loggedIn = true;
        }


        public CostumerData()
        {
            loggedIn = false;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public boolean isLoggedIn() {
            return loggedIn;
        }

        public void setLoggedIn(boolean loggedIn) {
            this.loggedIn = loggedIn;
        }
        public void addSubscription(SubscriptionData sub)
        {
            subscriptions.put(sub.getEndingDate(),sub);
        }

        public SubscriptionData getSubscription(LocalDate date)
        {
            return (subscriptions.get(date));
        }

        public HashMap<LocalDate, SubscriptionData> getSubscriptions() {
            return subscriptions;
        }

    }




