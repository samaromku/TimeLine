package ru.savchenko.andrey.timeline;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ru.savchenko.andrey.timeline.storage.Utils;

/**
 * Created by Andrey on 23.08.2017.
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
        Utils.init(this);
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }
}
