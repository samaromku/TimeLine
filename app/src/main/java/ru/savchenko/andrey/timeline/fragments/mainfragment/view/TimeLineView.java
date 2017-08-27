package ru.savchenko.andrey.timeline.fragments.mainfragment.view;

import java.util.List;

import ru.savchenko.andrey.timeline.entities.Card;
import ru.savchenko.andrey.timeline.intefaces.BorderViewListener;
import ru.savchenko.andrey.timeline.intefaces.OnSettingsListener;

/**
 * Created by Andrey on 25.08.2017.
 */

public interface TimeLineView extends BorderViewListener, OnSettingsListener {
    void adapterCheckDate(int toRow);

    void addItem(int fromColumn, Card card);

    void setCardImage(int playerCounter);

    void changeToolbarColor(int color);

    void showPlayerName(String name);

    void addColumnList(List<Card> cardList, boolean hasDate);

    void openIntro();

    void openSettings();

    void upCounterOrVictory(int cardCounter, String playerName);
}
