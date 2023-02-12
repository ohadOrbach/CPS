package il.cshaifasweng.OCSFMediatorExample.entities;


import java.io.Serializable;

public class EmployeeData implements Serializable {

    private int id;
    private String privateName;
    private String sureName;
    private String password;
    private String Email;
    private String job;
    private String Branch;

    private boolean loggedIn = false;


    public EmployeeData(int id, String privateName, String sureName, String password, String email, String job, String branch) {
        this.id = id;
        this.privateName = privateName;
        this.sureName = sureName;
        this.password = password;
        Email = email;
        this.job = job;
        Branch = branch;
        loggedIn = true;
    }


    public EmployeeData() {
        loggedIn = false;
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
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}



