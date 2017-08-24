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

package ru.savchenko.andrey.timeline.test.fragments;

import android.animation.ObjectAnimator;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.savchenko.andrey.timeline.R;
import ru.savchenko.andrey.timeline.entities.Card;
import ru.savchenko.andrey.timeline.entities.Player;
import ru.savchenko.andrey.timeline.intefaces.BorderViewListener;
import ru.savchenko.andrey.timeline.intefaces.ChangeToolbarColor;
import ru.savchenko.andrey.timeline.intefaces.OnSettingsListener;
import ru.savchenko.andrey.timeline.lib.BoardView;
import ru.savchenko.andrey.timeline.lib.DragItem;
import ru.savchenko.andrey.timeline.managers.CardManager;
import ru.savchenko.andrey.timeline.managers.PlayersManager;
import ru.savchenko.andrey.timeline.repository.PlayersSpec;
import ru.savchenko.andrey.timeline.test.adapter.ItemAdapter;
import ru.savchenko.andrey.timeline.test.dialog.IntroDialog;
import ru.savchenko.andrey.timeline.test.dialog.SettingsDialog;
import ru.savchenko.andrey.timeline.test.storage.Utils;

import static android.content.ContentValues.TAG;
import static ru.savchenko.andrey.timeline.storage.Const.CORRECT_ANSWER;
import static ru.savchenko.andrey.timeline.storage.Const.VICTORY;

public class TimeLineFragment extends Fragment implements BorderViewListener, OnSettingsListener {

    private BoardView mBoardView;
    private CardManager cardManager = new CardManager();
    private ItemAdapter secondColumnItemAdapter;
    private ImageView ivDeck;
    private Map<Integer, Integer> playerCardsMap;
    //    private int cardCounter;
    private int playersCounter = Utils.getCounter();
    private int currentPlayerPosition;
    private static Card dragCardEntity;
    private PlayersManager playersManager = new PlayersManager();
    TextView tvPlayerMove;
    CardView cvPlayerMove;
    private ChangeToolbarColor changeToolbarColor;

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
        tvPlayerMove = (TextView) getActivity().findViewById(R.id.tvPlayerMove);
        cvPlayerMove = (CardView) getActivity().findViewById(R.id.cvPlayerMove);
        ivDeck = (ImageView) getActivity().findViewById(R.id.deck);
        mBoardView = (BoardView) view.findViewById(R.id.board_view);
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
                if (fromColumn != toColumn || fromRow != toRow) {
                    secondColumnItemAdapter.checkDate(toRow);
                    Card card = cardManager.getUniqueCard();
                    mBoardView.addItem(fromColumn, 0, card, true);
                    dragCardEntity = card;
                    showNextPlayerName();
                    setCardShirtImage();
                }
            }
        });

        return view;
    }

    private void showNextPlayerName() {
        if (currentPlayerPosition < playersCounter - 1) {
            currentPlayerPosition = currentPlayerPosition + 1;
        } else {
            currentPlayerPosition = 0;
        }
        Log.i(TAG, "showNextPlayerName: " + currentPlayerPosition);
        Log.i(TAG, "showNextPlayerName: playersCounter " + playersCounter);
        getPlayerAndSetName();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addFirstColumn();
        addSecondColumn();
        mBoardView.setListDragDisable();
        getPlayerAndSetName();
        initPlayers();
    }

    private void initPlayers() {
        playerCardsMap = new HashMap<>();
        List<Player> players = new PlayersSpec().getPlayers();
        for (int i = 0; i < playersCounter; i++) {
            playerCardsMap.put(players.get(i).getPosition(), 0);
        }
    }

    private void getPlayerAndSetName() {
        Player player = new PlayersSpec().getPlayerByPosition(currentPlayerPosition);
        changeToolbarColor.changeToolbarColor(player.getColor());

        showPlayerName(player.getName());
    }

    private void showPlayerName(String name) {
        tvPlayerMove.setText("Ходит " + name);
        tvPlayerMove.setVisibility(View.VISIBLE);

        AlphaAnimation fade_out = new AlphaAnimation(1.0f, 0.0f);
        fade_out.setDuration(2000);
        fade_out.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation arg0) {
            }

            public void onAnimationRepeat(Animation arg0) {
            }

            public void onAnimationEnd(Animation arg0) {
                cvPlayerMove.setVisibility(View.GONE);
            }
        });
        cvPlayerMove.startAnimation(fade_out);
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

    private void openSettings() {
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.setPlayersManager(playersManager);
        settingsDialog.setOnSettingsListener(this);
        settingsDialog.show(getActivity().getFragmentManager(), "settingsFragment");
    }

    private void openIntro() {
        DialogFragment introDialog = new IntroDialog();
        introDialog.show(getActivity().getFragmentManager(), "introFragment");
    }

    private void addFirstColumn() {
        List<Card> cards = cardManager.getBottomCards();
        addColumnList(cards, false);
        if (cards.size() > 0)
            dragCardEntity = cards.get(0);
    }

    private void addSecondColumn() {
        addColumnList(cardManager.getTopCards(), true);
    }

    private void addColumnList(List<Card> cardList, boolean hasDate) {
        final ItemAdapter listAdapter = new ItemAdapter(cardList, R.layout.column_item, R.id.item_layout, true);
        listAdapter.setBorderViewListener(this);
        listAdapter.setHasDate(hasDate);

        secondColumnItemAdapter = listAdapter;
        mBoardView.addColumnList(listAdapter, false);
    }

    @Override
    public int getResourceByString(String resName) {
        return getResources().getIdentifier(resName, "drawable", getActivity().getPackageName());
    }


    @Override
    public void correctDate() {
        playerCardsMap.put(currentPlayerPosition, playerCardsMap.get(currentPlayerPosition) + 1);
        int cardCounter = playerCardsMap.get(currentPlayerPosition);
        Snackbar.make(mBoardView, CORRECT_ANSWER, Snackbar.LENGTH_SHORT).show();
        if (cardCounter > 4) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(VICTORY + new PlayersSpec().getPlayerByPosition(currentPlayerPosition).getName())
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
    public void errorDate(String text) {
        Snackbar.make(mBoardView, text, Snackbar.LENGTH_SHORT).show();
    }

    private void setCardShirtImage() {
        ivDeck.setImageResource(getResources().getIdentifier("shirt_00" + playerCardsMap.get(currentPlayerPosition), "drawable", getActivity().getPackageName()));
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

    private static class MyDragItem extends DragItem {
        private Context activity;

        MyDragItem(Context context, int layoutId) {
            super(context, layoutId);
            activity = context;
        }

        @Override
        public void onBindDragView(View clickedView, View dragView) {
            dragView.findViewById(R.id.text).setVisibility(View.GONE);
            int res = activity.getResources().getIdentifier(dragCardEntity.getImagePath(), "drawable", activity.getPackageName());
            ImageView ivCardPic = ((ImageView) dragView.findViewById(R.id.iv_card_pic));
            ivCardPic.setImageResource(res);
            RelativeLayout.LayoutParams ivParams = (RelativeLayout.LayoutParams) ivCardPic.getLayoutParams();
            ivParams.setMargins(0, -34, 0, 0);
            TextView title = ((TextView) dragView.findViewById(R.id.title));
            title.setText(dragCardEntity.getTitle());
            title.setTextSize(11);
            LinearLayout.LayoutParams tvParams = (LinearLayout.LayoutParams) title.getLayoutParams();
            tvParams.setMargins(0, -110, 0, 0);
            CardView dragCard = ((CardView) dragView.findViewById(R.id.card));
            CardView clickedCard = ((CardView) clickedView.findViewById(R.id.card));

            dragCard.setMaxCardElevation(40);
            dragCard.setCardElevation(clickedCard.getCardElevation());
            // I know the dragView is a FrameLayout and that is why I can use setForeground below api level 23
            dragCard.setForeground(clickedView.getResources().getDrawable(R.drawable.card_view_drag_foreground));
        }

        @Override
        public void onMeasureDragView(View clickedView, View dragView) {
            CardView dragCard = ((CardView) dragView.findViewById(R.id.card));
            CardView clickedCard = ((CardView) clickedView.findViewById(R.id.card));
            int widthDiff = dragCard.getPaddingLeft() - clickedCard.getPaddingLeft() + dragCard.getPaddingRight() -
                    clickedCard.getPaddingRight();
            int heightDiff = dragCard.getPaddingTop() - clickedCard.getPaddingTop() + dragCard.getPaddingBottom() -
                    clickedCard.getPaddingBottom();
            int width = clickedView.getMeasuredWidth() + widthDiff;
            int height = clickedView.getMeasuredHeight() + heightDiff;
            dragView.setLayoutParams(new FrameLayout.LayoutParams(width, height));

            int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            dragView.measure(widthSpec, heightSpec);
        }

        @Override
        public void onStartDragAnimation(View dragView) {
            CardView dragCard = ((CardView) dragView.findViewById(R.id.card));
            ObjectAnimator anim = ObjectAnimator.ofFloat(dragCard, "CardElevation", dragCard.getCardElevation(), 40);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.setDuration(ANIMATION_DURATION);
            anim.start();
        }

        @Override
        public void onEndDragAnimation(View dragView) {
            CardView dragCard = ((CardView) dragView.findViewById(R.id.card));
            ObjectAnimator anim = ObjectAnimator.ofFloat(dragCard, "CardElevation", dragCard.getCardElevation(), 6);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.setDuration(ANIMATION_DURATION);
            anim.start();
        }
    }
}
