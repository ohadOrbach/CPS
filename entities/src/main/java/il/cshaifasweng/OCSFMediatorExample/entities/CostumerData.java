package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

    public class CostumerData implements Serializable {

        private int id;
        private String password;
        private String Email;
        private boolean loggedIn = false;


        public CostumerData(int id, String password, String email) {
            this.id = id;
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


    }




