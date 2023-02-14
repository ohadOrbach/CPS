package il.cshaifasweng.OCSFMediatorExample.client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class InputCheck {


    static boolean checkId(String id)
    {
        if (!Pattern.matches("[0-9]{9}", id))
        {
            return false;
        }
        return true;
    }


    static boolean checkCarNum(String carNum)
    {
        if (!Pattern.matches("[0-9]{9}", carNum))
        {
            return false;
        }
        return true;
    }

    static boolean checkEmail(String email)
    {
        if ((!Pattern.matches("^(.+)@(\\S+)$", email)))
        {
            return false;
        }
        return true;
    }

    static boolean checkDate(LocalDate date)
    {
        if (date.isBefore(LocalDate.now()))
        {
            return false;
        }
        return true;
    }

}
