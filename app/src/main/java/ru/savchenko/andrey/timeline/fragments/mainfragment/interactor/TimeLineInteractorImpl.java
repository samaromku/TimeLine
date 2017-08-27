package ru.savchenko.andrey.timeline.fragments.mainfragment.interactor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private CardManager cardManager = new CardManager();
    private Map<Integer, Integer> playerCardsMap;
    private int playersCounter = Utils.getCounter();
    private int currentPlayerPosition;
    private PlayersManager playersManager = new PlayersManager();
    private TimeLineInteractor.OnChangeCard onChangeCard;

    @Override
    public PlayersManager getPlayerManager() {
        return playersManager;
    }

    @Override
    public void addPlayerPositionAndNext() {
        playerCardsMap.put(currentPlayerPosition, playerCardsMap.get(currentPlayerPosition) + 1);
        onChangeCard.getPlayerByPosition(playerCardsMap.get(currentPlayerPosition), new PlayersSpec().getPlayerByPosition(currentPlayerPosition).getName());
    }

    public TimeLineInteractorImpl(OnChangeCard onChangeCard) {
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
        List<Card> cards = cardManager.getBottomCards();
        onChangeCard.addListColumn(cards, false);
        if (cards.size() > 0) {
            Utils.dragCardEntity = cards.get(0);
        }
    }

    @Override
    public void addSecondColumn() {
        onChangeCard.addListColumn(cardManager.getTopCards(), true);
    }

    private void showNextPlayerName() {
        if (currentPlayerPosition < playersCounter - 1) {
            currentPlayerPosition = currentPlayerPosition + 1;
        } else {
            currentPlayerPosition = 0;
        }
        getPlayerAndSetName();
    }

    private void getPlayerAndSetName() {
        Player player = new PlayersSpec().getPlayerByPosition(currentPlayerPosition);
        if (player != null) {
            onChangeCard.changePlayerColor(player.getColor());
            onChangeCard.showPlayerName(player.getName());
        }
    }

    private void initPlayers() {
        playerCardsMap = new HashMap<>();
        playersManager.getPlayers();
        List<Player> players = new PlayersSpec().getPlayers();
        if (players.size() == 0) return;
        for (int i = 0; i < playersCounter; i++) {
            playerCardsMap.put(players.get(i).getPosition(), 0);
        }
    }

    public void checkDate(int fromColumn) {
        Card card = cardManager.getUniqueCard();
        Utils.dragCardEntity = card;
        showNextPlayerName();
        onChangeCard.onAddItem(fromColumn, card);
        if(playerCardsMap.size()==0){
            playerCardsMap.put(1, 1);
        }
        onChangeCard.onChangeImage(playerCardsMap.get(currentPlayerPosition));
    }

}
