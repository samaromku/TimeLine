package ru.savchenko.andrey.timeline.test.dialog;

import android.app.DialogFragment;
import android.content.DialogInterface;
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
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.savchenko.andrey.timeline.R;
import ru.savchenko.andrey.timeline.adapters.PlayersAdapter;
import ru.savchenko.andrey.timeline.entities.Player;
import ru.savchenko.andrey.timeline.entities.PlayerCounter;
import ru.savchenko.andrey.timeline.helper.OnStartDragListener;
import ru.savchenko.andrey.timeline.helper.SimpleItemTouchHelperCallback;
import ru.savchenko.andrey.timeline.intefaces.ChangePlayersListener;
import ru.savchenko.andrey.timeline.intefaces.ItemClickListenter;
import ru.savchenko.andrey.timeline.intefaces.OnSettingsListener;
import ru.savchenko.andrey.timeline.managers.PlayersManager;
import ru.savchenko.andrey.timeline.repository.CounterSpec;
import ru.savchenko.andrey.timeline.repository.PlayersSpec;
import ru.savchenko.andrey.timeline.test.storage.Utils;
import ru.savchenko.andrey.timeline.views.SeekBarWithText;

import static android.content.ContentValues.TAG;
import static ru.savchenko.andrey.timeline.storage.Const.ACCEPT;
import static ru.savchenko.andrey.timeline.storage.Const.CANCEL;
import static ru.savchenko.andrey.timeline.storage.Const.CHOOSE_COLOR;
import static ru.savchenko.andrey.timeline.storage.Const.SETTINGS;

/**
 * Created by Andrey on 21.08.2017.
 */

public class SettingsDialog extends DialogFragment implements OnStartDragListener, ItemClickListenter, ChangePlayersListener {
    @BindView(R.id.sbPlayers) SeekBarWithText sbPlayers;
    @BindView(R.id.tvPlayerCount) TextView tvPlayerCount;
    @BindView(R.id.rvPlayers)
    RecyclerView rvPlayers;
    private PlayersManager playersManager;
    private int counter = Utils.getCounter();
    private OnSettingsListener onSettingsListener;
    private ItemTouchHelper mItemTouchHelper;
    private PlayersAdapter adapter;
    private List<Player>players = new ArrayList<>();
    private PlayersSpec playersSpec = new PlayersSpec();

    public void setOnSettingsListener(OnSettingsListener onSettingsListener) {
        this.onSettingsListener = onSettingsListener;
    }

    public void setPlayersManager(PlayersManager playersManager) {
        this.playersManager = playersManager;

    }

    @OnClick(R.id.btn_cancel)
    void onCancel() {
        getDialog().dismiss();
    }

    @OnClick(R.id.btn_ok)
    void onOk() {
        getDialog().dismiss();
        onSettingsListener.onAccept(counter);
        CounterSpec counterSpec = new CounterSpec();
        PlayerCounter playerCounter = counterSpec.getCounter();
        if(playerCounter!=null) {
            Log.i(TAG, "onOk: " + playerCounter);
            counterSpec.setCounter(playerCounter, counter);
        }
        playersSpec.addPlayers(players);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(SETTINGS);
        View view = inflater.inflate(R.layout.dialog_settings, container);
//        getCounter();
        ButterKnife.bind(this, view);
        initRvList();
        init();
        return view;
    }

//    private void getCounter() {
//        CounterSpec counterSpec = new CounterSpec();
//        PlayerCounter playerCounter = counterSpec.getCounter();
//        if(playerCounter==null){
//            counterSpec.saveCounter(new PlayerCounter(1,1));
//            counter = 1;
//        }else {
//            counter = playerCounter.getCounter();
//        }
//    }

    private void initRvList(){
        adapter = new PlayersAdapter(this);
        addListToAdapter(counter);
        adapter.setClickListenter(this);
        adapter.setOnEditNameListener(this);
        rvPlayers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        rvPlayers.setHasFixedSize(true);
        rvPlayers.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rvPlayers);
    }

    private void addListToAdapter(int count){
        players.clear();
        for (int i = 0; i < count; i++) {
            players.add(playersManager.getPlayers().get(i));
        }
        adapter.setPlayerList(players);
        adapter.notifyDataSetChanged();

    }

    private void init() {
        tvPlayerCount.setText(getPlayersCount(counter));
        sbPlayers.setProgress(counter*10);
        sbPlayers.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int count = progress / 10;
                tvPlayerCount.setText(getPlayersCount(count));
                addListToAdapter(count);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() < 10) {
                    seekBar.setProgress(10);
                }
                counter = seekBar.getProgress() / 10;
            }
        });
    }

    private String getPlayersCount(int count) {
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
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
//                        Toast.makeText(getActivity(), "selected" + selectedColor, Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(ACCEPT, new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        changeBackgroundColor(selectedColor, player);
                    }
                })
                .setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeBackgroundColor(player.getColor(), player);
                    }
                })
                .build()
                .show();
    }

    private void changeBackgroundColor(int selectedColor, Player player) {
        player.setColor(selectedColor);
//        new PlayersSpec().setPlayerColor(player.getId(), selectedColor);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onEditName(int position) {
        final Player player = players.get(position);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final EditText edittext = new EditText(getActivity());
        edittext.setText(player.getName());
        alert.setTitle("Изменить имя игрока");
        alert.setView(edittext);

        alert.setPositiveButton(ACCEPT, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
                player.setName(edittext.getText().toString());
//                new PlayersSpec().setPlayerName(player.getId(), edittext.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        alert.setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.show();
    }

    @Override
    public void swapPlayers(int fromPosition, int toPosition) {
//        Collections.swap(players, fromPosition, toPosition);
    }
}
