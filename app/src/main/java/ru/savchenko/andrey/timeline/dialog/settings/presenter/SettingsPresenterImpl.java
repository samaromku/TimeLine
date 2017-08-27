package ru.savchenko.andrey.timeline.dialog.settings.presenter;

import java.util.List;

import ru.savchenko.andrey.timeline.dialog.settings.interactor.SettingsInteractorImpl;
import ru.savchenko.andrey.timeline.dialog.settings.interactor.SettingsInteractor;
import ru.savchenko.andrey.timeline.dialog.settings.view.SettingsView;
import ru.savchenko.andrey.timeline.entities.Player;

/**
 * Created by Andrey on 25.08.2017.
 */

public class SettingsPresenterImpl implements SettingsPresenter, SettingsInteractor.OnChangesListener{
    private SettingsView view;
    private SettingsInteractor interactor;

    public SettingsPresenterImpl(SettingsView view) {
        this.view = view;
        this.interactor = new SettingsInteractorImpl(this);
    }

    @Override
    public void setPlayerName(String name) {
        interactor.setPlayerName(name);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onAccept(int counter) {
        view.onAccept(counter);
    }

    @Override
    public void onAcceptClick() {
        interactor.onOkPressed();
    }

    @Override
    public void addListInAdapter(int count) {
        interactor.addListInAdapter(count);
    }

    @Override
    public void refreshAdapter(List<Player> players) {
        view.refreshAdapter(players);
    }

    @Override
    public void checkProgress(int progress) {
        interactor.checkProgress(progress);
    }

    @Override
    public void onSetProgress(int progress) {
        view.setSeekBarProgress(progress);
    }

    @Override
    public void changePlayerName(int position) {
        interactor.changePlayerName(position);
    }

    @Override
    public void onChangePlayerName(String name) {
        view.changePlayerName(name);
    }
}
