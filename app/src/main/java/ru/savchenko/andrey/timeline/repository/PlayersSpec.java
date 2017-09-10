package ru.savchenko.andrey.timeline.repository;

import java.util.List;

import ru.savchenko.andrey.timeline.entities.Player;

/**
 * Created by Andrey on 23.08.2017.
 */

public class PlayersSpec extends BaseSpec {
    public void addPlayers(final List<Player>players){
        if (players==null)return;
        realmInstance().executeTransaction(realm -> realm.insertOrUpdate(players));
    realmInstance().close();
    }

    public List<Player>getPlayers(){
        List<Player>players = realmInstance().where(Player.class).findAll().sort("position");
        List<Player>playersCopy = realmInstance().copyFromRealm(players);
        realmInstance().close();
        return playersCopy;
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
