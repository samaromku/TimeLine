package ru.savchenko.andrey.timeline.repository;

import io.realm.Realm;
import ru.savchenko.andrey.timeline.entities.PlayerCounter;

/**
 * Created by Andrey on 23.08.2017.
 */

public class CounterSpec extends BaseSpec{
    public void saveCounter(final PlayerCounter counter){
        realmInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(counter);
            }
        });
        realmInstance().close();
    }

    public PlayerCounter getCounter(){
        PlayerCounter playerCounter = realmInstance().where(PlayerCounter.class).findFirst();
        realmInstance().close();
        return playerCounter;
    }

    public void setCounter(final PlayerCounter playerCounter, final int counter){
        realmInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                playerCounter.setCounter(counter);
            }
        });
        realmInstance().close();
    }

}
