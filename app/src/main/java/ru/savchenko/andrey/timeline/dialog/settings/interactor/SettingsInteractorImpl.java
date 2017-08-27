package ru.savchenko.andrey.timeline.dialog.settings.interactor;

import java.util.ArrayList;
import java.util.List;

import ru.savchenko.andrey.timeline.entities.Player;
import ru.savchenko.andrey.timeline.managers.PlayersManager;
import ru.savchenko.andrey.timeline.repository.PlayersSpec;
import ru.savchenko.andrey.timeline.storage.Utils;

/**
 * Created by Andrey on 25.08.2017.
 */

public class SettingsInteractorImpl implements SettingsInteractor{
    private PlayersManager playersManager;
    private int counter = Utils.getCounter();
    private List<Player> players;
    private PlayersSpec playersSpec;
    private OnChangesListener onChangesListener;
    private Player player;

    public SettingsInteractorImpl(OnChangesListener onChangesListener) {
        this.onChangesListener = onChangesListener;
        players = new ArrayList<>();
        playersSpec = new PlayersSpec();
        playersManager = new PlayersManager();
    }

    @Override
    public void onOkPressed() {
        onChangesListener.onAccept(counter);
        Utils.addPlayerCounter(counter, players);
    }

    @Override
    public void addListInAdapter(int count) {
        players.clear();
        for (int i = 0; i < count; i++) {
            players.add(playersManager.getPlayers().get(i));
        }
        onChangesListener.refreshAdapter(players);
    }

    @Override
    public void checkProgress(int progress) {
        if (progress < 10) {
            onChangesListener.onSetProgress(10);
        }
        counter = progress / 10;
    }

    @Override
    public void changePlayerName(int position) {
        player = players.get(position);
        onChangesListener.onChangePlayerName(player.getName());
    }

    @Override
    public void setPlayerName(String name) {
        player.setName(name);
    }
}
