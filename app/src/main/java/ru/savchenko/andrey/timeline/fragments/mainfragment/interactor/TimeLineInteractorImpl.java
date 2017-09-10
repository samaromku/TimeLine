package ru.savchenko.andrey.timeline.fragments.mainfragment.interactor;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import ru.savchenko.andrey.timeline.entities.Card;
import ru.savchenko.andrey.timeline.entities.Player;
import ru.savchenko.andrey.timeline.managers.CardManager;
import ru.savchenko.andrey.timeline.managers.PlayersManager;
import ru.savchenko.andrey.timeline.repository.PlayersSpec;
import ru.savchenko.andrey.timeline.storage.Utils;

/**
 * Created by Andrey on 25.08.2017.
 */

public class TimeLineInteractorImpl implements TimeLineInteractor {
    private static final String TAG = "TimeLineInteractorImpl";
    private CardManager cardManager = new CardManager();
    private Map<Integer, Integer> playerCardsMap;
    private int playersCounter;
    private int currentPlayerPosition;
    private PlayersManager playersManager = new PlayersManager();
    private TimeLineInteractor.OnChangeCard onChangeCard;
    private PlayersSpec playersSpec = new PlayersSpec();

    @Override
    public PlayersManager getPlayerManager() {
        return playersManager;
    }

    @Override
    public void addPlayerPositionAndNext() {
        playerCardsMap.put(currentPlayerPosition, playerCardsMap.get(currentPlayerPosition) + 1);
        onChangeCard.getPlayerByPosition(playerCardsMap.get(currentPlayerPosition), playersSpec.getPlayerByPosition(currentPlayerPosition).getName());
    }

    public TimeLineInteractorImpl(OnChangeCard onChangeCard, int counter) {
        this.playersCounter = counter;
        this.onChangeCard = onChangeCard;

    }

    @Override
    public void initColumns() {
        addFirstColumn();
        addSecondColumn();
        getPlayerAndSetName();
        initPlayers();
    }

    @Override
    public void addFirstColumn() {
        Single.just(cardManager.getBottomCards())
                .map(cards1 -> {
                    onChangeCard.addListColumn(cards1, false);
                    return cards1;
                })
                .filter(cards1 -> cards1.size() > 0)
                .subscribe(cards1 -> Utils.dragCardEntity = cards1.get(0), Throwable::printStackTrace);
    }

    @Override
    public void addSecondColumn() {
        onChangeCard.addListColumn(cardManager.getTopCards(), true);
    }


    private void initPlayers() {
        playerCardsMap = new HashMap<>();
        playersManager.getPlayers();
        Observable.just(playersSpec.getPlayers())
                .filter(players1 -> players1.size() != 0)
                .flatMap(Observable::fromIterable)
                .subscribe(o -> playerCardsMap.put(o.getPosition(), 0));
    }

    public void checkDate(int fromColumn) {
        Card card = cardManager.getUniqueCard();
        Utils.dragCardEntity = card;
        showNextPlayerName();
        onChangeCard.onAddItem(fromColumn, card);
        if (playerCardsMap.size() == 0) {
            playerCardsMap.put(1, 1);
        }
        onChangeCard.onChangeImage(playerCardsMap.get(currentPlayerPosition));
    }

    private void showNextPlayerName() {
        Single.just(currentPlayerPosition)
                .map(integer -> integer < playersCounter - 1)
                .subscribe(aBoolean ->
                {
                    if (aBoolean) {
                        currentPlayerPosition = currentPlayerPosition + 1;
                    } else {
                        currentPlayerPosition = 0;
                    }
                }, Throwable::printStackTrace);
        getPlayerAndSetName();
    }

    private void getPlayerAndSetName() {
        Player player = playersSpec.getPlayerByPosition(currentPlayerPosition);
        if (player != null) {
            onChangeCard.changePlayerColor(player.getColor());
            onChangeCard.showPlayerName(player.getName());
        }
    }

}
