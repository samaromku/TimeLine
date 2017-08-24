package ru.savchenko.andrey.timeline.test.storage;

import ru.savchenko.andrey.timeline.entities.PlayerCounter;
import ru.savchenko.andrey.timeline.repository.CounterSpec;

/**
 * Created by Andrey on 20.08.2017.
 */

public class Utils {
    public static String getDateString(int date) {
        if (date >= 0) {
            return String.valueOf(date) + " г.";
        } else {
            int positiveDate = date * (-1);
            if (positiveDate % 100 > 0) {
                return String.valueOf(positiveDate / 100) + " в. до н.э.";
            } else
                return String.valueOf(positiveDate) + " г. до н.э.";
        }
    }

    public static int getCounter() {
        CounterSpec counterSpec = new CounterSpec();
        PlayerCounter playerCounter = counterSpec.getCounter();
        if(playerCounter==null){
            counterSpec.saveCounter(new PlayerCounter(1,1));
            return 1;
        }else {
            return playerCounter.getCounter();
        }
    }
}
