package il.cshaifasweng.OCSFMediatorExample.server;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    private int id;
    private String privateName;
    private String sureName;
    private String password;
    private String Email;
    private String job;
    private String branch;
    private boolean login;

    public Employee(int id, String privateName, String sureName, String password, String email, String job, String branch) {
        this.id = id;
        this.privateName = privateName;
        this.sureName = sureName;
        this.password = password;
        Email = email;
        this.job = job;
        this.branch = branch;
        this.login = false;
    }

    public Employee(Employee other) {
        this.id = other.getId();
        this.privateName = other.getPrivateName();
        this.sureName = other.getSureName();
        this.password = other.getPassword();
        Email = other.getEmail();
        this.job = other.getJob();
        this.branch = other.getBranch();
    }

    public Employee()
    {
        this.login = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrivateName() {
        return privateName;
    }

    public void setPrivateName(String privateName) {
        this.privateName = privateName;
    }

    public String getSureName() {
        return sureName;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }


    public String getBranch() {
        return branch;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void compensateCustomer() {
        // leave empty for now
    }

    public void parkingLotPicture() {
        // leave empty for now
    }

    public void ordersReport()
    {
        // leave empty for now
    }

    public void complaintsReport()
    {
        // leave empty for now
    }

    public void disableParking() {
        // leave empty for now
    }

    public void updateParkingPrices() {
        // leave empty for now
    }

}

