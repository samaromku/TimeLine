package ru.savchenko.andrey.timeline.fragments.mainfragment.presenter;

import java.util.List;

import ru.savchenko.andrey.timeline.entities.Card;
import ru.savchenko.andrey.timeline.fragments.mainfragment.interactor.TimeLineInteractor;
import ru.savchenko.andrey.timeline.fragments.mainfragment.interactor.TimeLineInteractorImpl;
import ru.savchenko.andrey.timeline.fragments.mainfragment.view.TimeLineView;
import ru.savchenko.andrey.timeline.managers.PlayersManager;

/**
 * Created by Andrey on 25.08.2017.
 */

public class TimeLinePresenterImpl implements TimeLinePresenter, TimeLineInteractor.OnChangeCard {
    private TimeLineView view;
    private TimeLineInteractor interactor;

    public TimeLinePresenterImpl(TimeLineView view, int counter) {
        this.view = view;
        this.interactor = new TimeLineInteractorImpl(this, counter);
    }

    @Override
    public PlayersManager getPlayersManager() {
        return interactor.getPlayerManager();
    }

    @Override
    public void initColumns() {
        interactor.initColumns();
    }

    @Override
    public void getPlayerByPosition(int cardCounter, String name) {
        view.upCounterOrVictory(cardCounter, name);
    }

    @Override
    public void addPlayerPositionAndNext() {
        interactor.addPlayerPositionAndNext();
    }

    @Override
    public void addListColumn(List<Card> cardList, boolean hasDate) {
        view.addColumnList(cardList, hasDate);
    }

    @Override
    public void checkWhereDragEnded(int fromColumn, int fromRow, int toColumn, int toRow) {
        if (fromColumn != toColumn || fromRow != toRow) {
            view.adapterCheckDate(toRow);
            interactor.checkDate(fromColumn);
        }
    }

    @Override
    public void changePlayerColor(int color) {
        view.changeToolbarColor(color);
    }

    @Override
    public void showPlayerName(String name) {
        view.showPlayerName(name);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onAddItem(int fromColumn, Card card) {
        view.addItem(fromColumn, card);
    }

    @Override
    public void onChangeImage(int playerCounter) {
        view.setCardImage(playerCounter);
    }
}
