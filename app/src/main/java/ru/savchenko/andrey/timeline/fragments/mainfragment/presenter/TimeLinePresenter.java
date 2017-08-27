package ru.savchenko.andrey.timeline.fragments.mainfragment.presenter;

import ru.savchenko.andrey.timeline.managers.PlayersManager;

/**
 * Created by Andrey on 25.08.2017.
 */

public interface TimeLinePresenter {
    void checkWhereDragEnded(int fromColumn, int fromRow, int toColumn, int toRow);

    void onDestroy();

    PlayersManager getPlayersManager();

    void addPlayerPositionAndNext();

    void initColumns();
}
