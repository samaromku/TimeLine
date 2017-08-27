package ru.savchenko.andrey.timeline.fragments.mainfragment.interactor;

import java.util.List;

import ru.savchenko.andrey.timeline.entities.Card;
import ru.savchenko.andrey.timeline.managers.PlayersManager;

/**
 * Created by Andrey on 25.08.2017.
 */

public interface TimeLineInteractor {
    void checkDate(int fromColumn);

    void initColumns();

    void addFirstColumn();

    void addSecondColumn();

    PlayersManager getPlayerManager();

    void addPlayerPositionAndNext();

    interface OnChangeCard{
        void onAddItem(int fromColumn, Card card);

        void onChangeImage(int playerCounter);

        void changePlayerColor(int color);

        void showPlayerName(String name);

        void addListColumn(List<Card> cardList, boolean hasDate);

        void getPlayerByPosition(int cardCounter, String name);
    }
}
