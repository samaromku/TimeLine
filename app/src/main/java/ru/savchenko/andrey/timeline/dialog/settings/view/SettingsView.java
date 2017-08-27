package ru.savchenko.andrey.timeline.dialog.settings.view;

import java.util.List;

import ru.savchenko.andrey.timeline.entities.Player;
import ru.savchenko.andrey.timeline.helper.OnStartDragListener;
import ru.savchenko.andrey.timeline.intefaces.ChangePlayersListener;
import ru.savchenko.andrey.timeline.intefaces.ItemClickListenter;

/**
 * Created by Andrey on 25.08.2017.
 */

public interface SettingsView extends OnStartDragListener, ItemClickListenter, ChangePlayersListener {
    void onAccept(int counter);

    void refreshAdapter(List<Player>players);

    void setSeekBarProgress(int progress);

    void changePlayerName(String name);
}
