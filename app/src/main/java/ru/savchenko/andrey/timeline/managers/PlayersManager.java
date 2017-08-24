package ru.savchenko.andrey.timeline.managers;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.savchenko.andrey.timeline.entities.Player;
import ru.savchenko.andrey.timeline.repository.PlayersSpec;

import static android.content.ContentValues.TAG;

/**
 * Created by Andrey on 21.08.2017.
 */

public class PlayersManager {
    private List<Player> players;
    private int count;
    private PlayersSpec playersSpec = new PlayersSpec();

    public List<Player> getPlayers() {
        return getFromRealmOrNot();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    private List<Player> getFromRealmOrNot(){
        if(playersSpec.getPlayers().size()==0){
            Log.i(TAG, "getFromRealmOrNot: size = 0");
            playersSpec.addPlayers(addFirstPlayers());
        }
        return playersSpec.getPlayers();
    }

    private List<Player> addFirstPlayers() {
        Random rnd = new Random();
        List<Player>playersList = new ArrayList<>();
        if (playersList.size() == 0) {
            for (int i = 0; i < 8; i++) {
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                int b = i + 1;
                playersList.add(new Player(i, "Игрок №" + b, color, i));
            }
        }
        return playersList;
    }
}

