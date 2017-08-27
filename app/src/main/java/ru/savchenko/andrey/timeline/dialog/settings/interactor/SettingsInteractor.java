package ru.savchenko.andrey.timeline.dialog.settings.interactor;

import java.util.List;

import ru.savchenko.andrey.timeline.entities.Player;
import ru.savchenko.andrey.timeline.managers.PlayersManager;

/**
 * Created by Andrey on 25.08.2017.
 */

public interface SettingsInteractor {

    void onOkPressed();

    void addListInAdapter(int count);

    void checkProgress(int progress);

    void changePlayerName(int position);

    void setPlayerName(String name);

    interface OnChangesListener{

        void onAccept(int counter);

        void refreshAdapter(List<Player>players);

        void onSetProgress(int progress);

        void onChangePlayerName(String name);
    }

}
