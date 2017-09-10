package ru.savchenko.andrey.timeline.dialog.settings;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;
import ru.savchenko.andrey.timeline.R;
import ru.savchenko.andrey.timeline.adapters.PlayersAdapter;
import ru.savchenko.andrey.timeline.dialog.settings.presenter.SettingsPresenter;
import ru.savchenko.andrey.timeline.dialog.settings.presenter.SettingsPresenterImpl;
import ru.savchenko.andrey.timeline.dialog.settings.view.SettingsView;
import ru.savchenko.andrey.timeline.entities.Player;
import ru.savchenko.andrey.timeline.helper.SimpleItemTouchHelperCallback;
import ru.savchenko.andrey.timeline.intefaces.OnSettingsListener;
import ru.savchenko.andrey.timeline.storage.Utils;
import ru.savchenko.andrey.timeline.views.SeekBarWithText;

import static android.content.ContentValues.TAG;
import static ru.savchenko.andrey.timeline.storage.Const.*;

/**
 * Created by Andrey on 21.08.2017.
 */

public class SettingsDialog extends DialogFragment implements SettingsView {
    @BindView(R.id.sbPlayers)
    SeekBarWithText sbPlayers;
    @BindView(R.id.tvPlayerCount)
    TextView tvPlayerCount;
    @BindView(R.id.rvPlayers)
    RecyclerView rvPlayers;
    @BindView(R.id.rbAll)
    RadioRealButton rbAll;
    @BindView(R.id.rbCinema)
    RadioRealButton rbCinema;
    @BindView(R.id.rbLitra)
    RadioRealButton rbLitra;
    @BindView(R.id.rbRussia)
    RadioRealButton rbRussia;
    @BindView(R.id.radioGroup)
    RadioRealButtonGroup group;
    private int category;

    private OnSettingsListener onSettingsListener;
    private ItemTouchHelper mItemTouchHelper;
    private PlayersAdapter adapter;
    private SettingsPresenter presenter;

    public void setOnSettingsListener(OnSettingsListener onSettingsListener) {
        this.onSettingsListener = onSettingsListener;
    }

    @OnClick(R.id.btn_cancel)
    void onCancel() {
        getDialog().dismiss();
    }

    @OnClick(R.id.btn_ok)
    void onOk() {
        getDialog().dismiss();
        presenter.onAcceptClick();
        Utils.saveCategory(category);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onAccept(int counter) {
        Utils.saveCounter(counter);
        onSettingsListener.onAccept(counter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(SETTINGS);
        View view = inflater.inflate(R.layout.dialog_settings, container);
        ButterKnife.bind(this, view);
        presenter = new SettingsPresenterImpl(this, Utils.loadCounter());
        initRvList();
        init();
        initRadioGroup();
        return view;
    }

    private void initRadioGroup() {
        category = Utils.loadCategory();
        Log.i(TAG, "initRadioGroup: " + category);
        group.setOnClickedButtonListener((button, position) -> {
            Log.i(TAG, "initRadioGroup: " + position);
            category = position;
        });
    }

    @Override
    public void refreshAdapter(List<Player> players) {
        adapter.setPlayerList(players);
        adapter.notifyDataSetChanged();
    }

    private void initRvList() {
        adapter = new PlayersAdapter(this);
        presenter.addListInAdapter(Utils.loadCounter());
        adapter.setClickListenter(this);
        adapter.setOnEditNameListener(this);
        rvPlayers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        rvPlayers.setHasFixedSize(true);
        rvPlayers.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rvPlayers);
    }

    private void init() {
        tvPlayerCount.setText(getPlayerNameWithCounter(Utils.loadCounter()));
        sbPlayers.setProgress(Utils.loadCounter() * 10);
        sbPlayers.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int count = progress / 10;
                tvPlayerCount.setText(getPlayerNameWithCounter(count));
                presenter.addListInAdapter(count);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                presenter.checkProgress(seekBar.getProgress());
            }
        });
    }

    @Override
    public void setSeekBarProgress(int progress) {
        sbPlayers.setProgress(progress);
    }

    private String getPlayerNameWithCounter(int count) {
        if (count == 1) {
            return count + " игрок";
        } else if (count == 2 || count == 3 || count == 4) {
            return count + " игрока";
        } else
            return count + " игроков";
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder, Player player) {
        viewHolder.itemView.setBackgroundColor(player.getColor());
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onClick(int position, final Player player) {
        ColorPickerDialogBuilder
                .with(getActivity())
                .setTitle(CHOOSE_COLOR)
                .initialColor(player.getColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(selectedColor -> {
                })
                .setPositiveButton(ACCEPT, (dialog, selectedColor, allColors) -> changeBackgroundColor(selectedColor, player))
                .setNegativeButton(CANCEL, (dialog, which) -> changeBackgroundColor(player.getColor(), player))
                .build()
                .show();
    }

    private void changeBackgroundColor(int selectedColor, Player player) {
        player.setColor(selectedColor);
//        new PlayersSpec().setPlayerColor(player.getId(), selectedColor);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void changePlayerName(String name) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final EditText edittext = new EditText(getActivity());
        edittext.setText(name);
        alert.setTitle(CHANGE_PLAYER_NAME);
        alert.setView(edittext);
        alert.setPositiveButton(ACCEPT, (dialog, whichButton) -> {
            presenter.setPlayerName(edittext.getText().toString());
            adapter.notifyDataSetChanged();
        });

        alert.setNegativeButton(CANCEL, (dialog, whichButton) -> {

        });
        alert.show();
    }

    @Override
    public void onEditName(int position) {
        presenter.changePlayerName(position);
    }
}
