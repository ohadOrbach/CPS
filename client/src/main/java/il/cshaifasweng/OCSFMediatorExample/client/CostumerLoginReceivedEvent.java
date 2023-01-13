package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.CostumerData;
import il.cshaifasweng.OCSFMediatorExample.entities.EmployeeData;


public class CostumerLoginReceivedEvent {
    private CostumerData costumer;
    private boolean loginFailed;

    CostumerLoginReceivedEvent(CostumerData costumer)
    {
        if(costumer.isLoggedIn())
        {
            loginFailed = false;
            this.costumer = costumer;
        }
        else
        {
            this.loginFailed=true;
            this.costumer = null;
        }
    }

    public CostumerData getCostumer() {
        return costumer;
    }

    public boolean LoginFailed()
    {
        return loginFailed;
    }
}

