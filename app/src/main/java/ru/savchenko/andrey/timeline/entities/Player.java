package ru.savchenko.andrey.timeline.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Andrey on 21.08.2017.
 */

public class Player extends RealmObject{
    @PrimaryKey
    private int id;
    private String name;
    private int color;
    private int position;

    public Player() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Player(int id, String name, int color, int position) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", position=" + position +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
