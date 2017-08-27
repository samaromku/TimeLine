package ru.savchenko.andrey.timeline.dialog.settings.presenter;

/**
 * Created by Andrey on 25.08.2017.
 */

public interface SettingsPresenter {
    void onDestroy();

    void onAcceptClick();

    void addListInAdapter(int count);

    void checkProgress(int progress);

    void changePlayerName(int position);

    void setPlayerName(String name);
}
