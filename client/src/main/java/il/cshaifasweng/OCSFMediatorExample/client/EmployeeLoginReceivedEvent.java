package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.EmployeeData;


public class EmployeeLoginReceivedEvent {
    private EmployeeData employee;
    private boolean loginFailed;

    EmployeeLoginReceivedEvent(EmployeeData employee)
    {
        if(employee.isLoggedIn())
        {
            loginFailed = false;
            this.employee = employee;
        }
        else
        {
            this.loginFailed=true;
            this.employee = null;
        }
    }

    public EmployeeData getEmployee() {
        return employee;
    }

    public boolean LoginFailed()
    {
        return loginFailed;
    }
}
