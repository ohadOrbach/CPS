package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.CostumerData;
import il.cshaifasweng.OCSFMediatorExample.entities.EmployeeData;


public class CostumerRegistrationReceivedEvent {
    private CostumerData costumer;
    private boolean registerFailed;

    CostumerRegistrationReceivedEvent(CostumerData costumer)
    {
        if(costumer.isLoggedIn())
        {
            registerFailed = false;
            this.costumer = costumer;
        }
        else
        {
            this.registerFailed=true;
            this.costumer = null;
        }
    }

    public CostumerData getCostumer() {
        return costumer;
    }

    public boolean LoginFailed()
    {
        return registerFailed;
    }
}


