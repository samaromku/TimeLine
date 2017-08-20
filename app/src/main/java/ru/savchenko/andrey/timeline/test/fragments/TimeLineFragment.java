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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ru.savchenko.andrey.timeline.R;
import ru.savchenko.andrey.timeline.intefaces.BorderViewListener;
import ru.savchenko.andrey.timeline.lib.BoardView;
import ru.savchenko.andrey.timeline.lib.DragItem;
import ru.savchenko.andrey.timeline.managers.CardManager;
import ru.savchenko.andrey.timeline.models.Card;
import ru.savchenko.andrey.timeline.test.adapter.ItemAdapter;
import ru.savchenko.andrey.timeline.test.dialog.IntroDialog;
import ru.savchenko.andrey.timeline.test.storage.DateChecker;

import static ru.savchenko.andrey.timeline.storage.Const.CORRECT_ANSWER;
import static ru.savchenko.andrey.timeline.storage.Const.VICTORY;

public class TimeLineFragment extends Fragment implements BorderViewListener {

    private DateChecker dateChecker = new DateChecker();
    private static int sCreatedItems = 0;
    private BoardView mBoardView;
    private int mColumns;
    private CardManager cardManager = new CardManager();
    private ItemAdapter secondColumnItemAdapter;
    private ImageView ivDeck;
    private int counter;
    private static Card dragCardEntity;
    public static final int MARGIN_TOP = -30;

    public static TimeLineFragment newInstance() {
        return new TimeLineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.board_layout, container, false);

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
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addFirstColumn();
        addSecondColumn();
        mBoardView.setListDragDisable();
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
//            case R.id.statistics:
//                Toast.makeText(getActivity(), "Открыть статистику", Toast.LENGTH_SHORT).show();
//                return true;
            case R.id.info:
                openIntro();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openIntro(){
        DialogFragment introDialog = new IntroDialog();
        introDialog.show(getActivity().getFragmentManager(), "test");
    }

    private void addFirstColumn(){
        List<Card>cards = cardManager.getBottomCards();
        addColumnList(cards, false);
        if(cards.size()>0)
        dragCardEntity = cards.get(0);
    }

    private void addSecondColumn(){
        addColumnList(cardManager.getTopCards(), true);
    }

    private void addColumnList(List<Card>cardList, boolean hasDate) {
        final ItemAdapter listAdapter = new ItemAdapter(cardList, R.layout.column_item, R.id.item_layout, true);
        listAdapter.setBorderViewListener(this);
        listAdapter.setHasDate(hasDate);

        secondColumnItemAdapter = listAdapter;
        mBoardView.addColumnList(listAdapter, false);
        mColumns++;
    }

    @Override
    public int getResourceByString(String resName) {
        return getResources().getIdentifier(resName, "drawable", getActivity().getPackageName());
    }


    @Override
    public void correctDate() {
        counter += 1;
        Snackbar.make(mBoardView, CORRECT_ANSWER, Snackbar.LENGTH_SHORT).show();
        setCardShirtImage();
        if (counter > 4) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(VICTORY)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(getActivity(), getActivity().getClass()));
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getActivity().finish();
                        }
                    })
                    .create()
                    .show();
        }
    }

    @Override
    public void errorDate(String text) {
        Snackbar.make(mBoardView, text, Snackbar.LENGTH_SHORT).show();
    }

    private void setCardShirtImage() {
        ivDeck.setImageResource(getResources().getIdentifier("shirt_00" + counter, "drawable", getActivity().getPackageName()));
    }

    @Override
    public void refreshAdapter(int position) {

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
            ImageView ivCardPic = ((ImageView)dragView.findViewById(R.id.iv_card_pic));
            ivCardPic.setImageResource(res);
            RelativeLayout.LayoutParams ivParams = (RelativeLayout.LayoutParams)ivCardPic.getLayoutParams();
            ivParams.setMargins(0, -34, 0, 0);
            TextView title = ((TextView) dragView.findViewById(R.id.title));
            title.setText(dragCardEntity.getTitle());
            title.setTextSize(11);
            LinearLayout.LayoutParams tvParams = (LinearLayout.LayoutParams)title.getLayoutParams();
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
