package ru.savchenko.andrey.timeline.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrey on 23.08.2017.
 */

public class PlayerCounter extends RealmObject {
    @PrimaryKey
    private int id;
    private int counter;

    public PlayerCounter() {
    }

    @Override
    public String toString() {
        return "PlayerCounter{" +
                "id=" + id +
                ", counter=" + counter +
                '}';
    }

    public PlayerCounter(int id, int counter) {
        this.id = id;
        this.counter = counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
