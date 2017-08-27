/*
 * Copyright 2014 Magnus Woxblom
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.savchenko.andrey.timeline.fragments.mainfragment;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import ru.savchenko.andrey.timeline.R;
import ru.savchenko.andrey.timeline.adapters.ItemAdapter;
import ru.savchenko.andrey.timeline.adapters.MyDragItem;
import ru.savchenko.andrey.timeline.dialog.IntroDialog;
import ru.savchenko.andrey.timeline.dialog.settings.SettingsDialog;
import ru.savchenko.andrey.timeline.entities.Card;
import ru.savchenko.andrey.timeline.fragments.mainfragment.presenter.TimeLinePresenter;
import ru.savchenko.andrey.timeline.fragments.mainfragment.presenter.TimeLinePresenterImpl;
import ru.savchenko.andrey.timeline.fragments.mainfragment.view.TimeLineView;
import ru.savchenko.andrey.timeline.intefaces.ChangeToolbarColor;
import ru.savchenko.andrey.timeline.lib.BoardView;

import static android.content.ContentValues.TAG;
import static ru.savchenko.andrey.timeline.storage.Const.CORRECT_ANSWER;
import static ru.savchenko.andrey.timeline.storage.Const.INTRO_FRAGMENT;
import static ru.savchenko.andrey.timeline.storage.Const.MOVE;
import static ru.savchenko.andrey.timeline.storage.Const.SETTINGS_FRAGMENT;
import static ru.savchenko.andrey.timeline.storage.Const.VICTORY;

public class TimeLineFragment extends Fragment implements TimeLineView {

    private BoardView mBoardView;
    private ItemAdapter secondColumnItemAdapter;
    private ImageView ivDeck;
//    TextView tvPlayerMove;
//    CardView cvPlayerMove;
    private ChangeToolbarColor changeToolbarColor;
    private TimeLinePresenter presenter;

    public void setChangeToolbarColor(ChangeToolbarColor changeToolbarColor) {
        this.changeToolbarColor = changeToolbarColor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.board_layout, container, false);
        init(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void init(View view) {
//        tvPlayerMove = (TextView) getActivity().findViewById(R.id.tvPlayerMove);
//        cvPlayerMove = (CardView) getActivity().findViewById(R.id.cvPlayerMove);
        ivDeck = (ImageView) getActivity().findViewById(R.id.deck);
        mBoardView = (BoardView) view.findViewById(R.id.board_view);

        presenter = new TimeLinePresenterImpl(this);

        mBoardView.setSnapToColumnsWhenScrolling(true);
        mBoardView.setSnapToColumnWhenDragging(true);
        mBoardView.setSnapDragItemToTouch(true);



        mBoardView.setCustomDragItem(new MyDragItem(getActivity(), R.layout.column_item));
        mBoardView.setSnapToColumnInLandscape(false);
        mBoardView.setColumnSnapPosition(BoardView.ColumnSnapPosition.CENTER);

        mBoardView.setBoardListener(new BoardView.BoardListener() {
            @Override
            public void onItemDragStarted(int column, int row) {

            }

            @Override
            public void onItemChangedPosition(int oldColumn, int oldRow, int newColumn, int newRow) {

            }

            @Override
            public void onItemChangedColumn(int oldColumn, int newColumn) {

            }

            @Override
            public void onItemDragEnded(int fromColumn, int fromRow, int toColumn, int toRow) {
                presenter.checkWhereDragEnded(fromColumn, fromRow, toColumn, toRow);
            }
        });
    }

    @Override
    public void changeToolbarColor(int color) {
        changeToolbarColor.changeToolbarColor(color);
    }

    @Override
    public void adapterCheckDate(int toRow) {
        secondColumnItemAdapter.checkDate(toRow);
    }

    @Override
    public void addItem(int fromColumn, Card card) {
        mBoardView.addItem(fromColumn, 0, card, true);
    }

    @Override
    public void setCardImage(int playerCounter) {
        ivDeck.setImageResource(getResources().getIdentifier("shirt_00" + playerCounter, "drawable", getActivity().getPackageName()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.initColumns();
        mBoardView.setListDragDisable();
    }



    @Override
    public void addColumnList(List<Card> cardList, boolean hasDate) {
        final ItemAdapter listAdapter = new ItemAdapter(cardList, R.layout.column_item, R.id.item_layout, true);
        listAdapter.setBorderViewListener(this);
        listAdapter.setHasDate(hasDate);
        secondColumnItemAdapter = listAdapter;
        mBoardView.addColumnList(listAdapter, false);
    }

    @Override
    public void showPlayerName(String name) {
//        tvPlayerMove.setText(MOVE + name);
//        tvPlayerMove.setVisibility(View.VISIBLE);
//
//        AlphaAnimation fade_out = new AlphaAnimation(1.0f, 0.0f);
//        fade_out.setDuration(2000);
//        fade_out.setAnimationListener(new Animation.AnimationListener() {
//            public void onAnimationStart(Animation arg0) {
//            }
//
//            public void onAnimationRepeat(Animation arg0) {
//            }
//
//            public void onAnimationEnd(Animation arg0) {
//                cvPlayerMove.setVisibility(View.GONE);
//            }
//        });
//        cvPlayerMove.startAnimation(fade_out);
        changeToolbarColor.changeTitle(MOVE + name);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.time_line_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                openSettings();
                return true;
            case R.id.info:
                openIntro();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openSettings() {
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.setOnSettingsListener(this);
        settingsDialog.show(getActivity().getFragmentManager(), SETTINGS_FRAGMENT);
    }

    @Override
    public void openIntro() {
        DialogFragment introDialog = new IntroDialog();
        introDialog.show(getActivity().getFragmentManager(), INTRO_FRAGMENT);
    }

    @Override
    public int getResourceByString(String resName) {
        return getResources().getIdentifier(resName, "drawable", getActivity().getPackageName());
    }

    @Override
    public void upCounterOrVictory(int cardCounter, String playerName) {
        Snackbar.make(mBoardView, CORRECT_ANSWER, Snackbar.LENGTH_SHORT).show();
        if (cardCounter > 4) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(VICTORY + playerName)
                    .setPositiveButton(R.string.ok, (dialog, id) -> {
                        startActivity(new Intent(getActivity(), getActivity().getClass()));
                        getActivity().finish();
                    })
                    .setNegativeButton(R.string.cancel, (dialog, id) -> getActivity().finish())
                    .create()
                    .show();
        }
    }

    @Override
    public void correctDate() {
        presenter.addPlayerPositionAndNext();

    }

    @Override
    public void errorDate(String text) {
        Snackbar.make(mBoardView, text, Snackbar.LENGTH_SHORT).show();
    }



    @Override
    public void refreshAdapter(int position) {

    }

    @Override
    public void onAccept(int counter) {
        Log.i(TAG, "onAccept: cardCounter: " + counter);
        getActivity().finish();
        startActivity(new Intent(getActivity(), getActivity().getClass()));
    }

    @Override
    public void onCancel() {
        Log.i(TAG, "onCancel: ");
    }
}
