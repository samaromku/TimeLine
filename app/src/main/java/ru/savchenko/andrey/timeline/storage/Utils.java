package ru.savchenko.andrey.timeline.storage;

import android.util.Log;

import java.util.List;

import ru.savchenko.andrey.timeline.entities.Card;
import ru.savchenko.andrey.timeline.entities.Player;
import ru.savchenko.andrey.timeline.entities.PlayerCounter;
import ru.savchenko.andrey.timeline.repository.CounterSpec;
import ru.savchenko.andrey.timeline.repository.PlayersSpec;

import static android.content.ContentValues.TAG;

/**
 * Created by Andrey on 20.08.2017.
 */

public class Utils {
    public static Card dragCardEntity;

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

    public static void addPlayerCounter(int counter, List<Player>players){
        CounterSpec counterSpec = new CounterSpec();
        PlayerCounter playerCounter = counterSpec.getCounter();
        if(playerCounter!=null) {
            Log.i(TAG, "onOk: " + playerCounter);
            counterSpec.setCounter(playerCounter, counter);
        }
        new PlayersSpec().addPlayers(players);
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
