package ru.savchenko.andrey.timeline.repository;

import android.util.Log;

import java.util.List;

import ru.savchenko.andrey.timeline.entities.Player;

import static android.content.ContentValues.TAG;

/**
 * Created by Andrey on 23.08.2017.
 */

public class PlayersSpec extends BaseSpec {
    public void addPlayers(final List<Player>players){
        realmInstance().executeTransaction(realm -> realm.insertOrUpdate(players));
    realmInstance().close();
    }

    public List<Player>getPlayers(){
        List<Player>players = realmInstance().where(Player.class).findAll().sort("position");
        List<Player>playersCopy = realmInstance().copyFromRealm(players);
        realmInstance().close();
        return playersCopy;
    }

    public void setPlayerName(final Player player, final String name){
        realmInstance().executeTransaction(realm -> player.setName(name));
        realmInstance().close();
    }

    public void setPlayerColor(final Player player, final int color){
        realmInstance().executeTransaction(realm -> player.setColor(color));
        realmInstance().close();
    }

    public void setPlayerPosition(final Player player, final int position){
        realmInstance().executeTransaction(realm -> {
            player.setPosition(position);
            Log.i(TAG, "execute: " + player);
        });
        realmInstance().close();
    }

    public Player getPlayerByPosition(int position){
        Player player = realmInstance()
                .where(Player.class)
                .equalTo("position", position)
                .findFirst();
        realmInstance().close();
        return player;
    }

}
