package ru.savchenko.andrey.timeline.repository;

import io.realm.Realm;

/**
 * Created by Andrey on 23.08.2017.
 */

public class BaseSpec {
    protected Realm realmInstance(){
        return Realm.getDefaultInstance();
    }

    protected void close(Realm realm){
        realm.close();
    }
}
