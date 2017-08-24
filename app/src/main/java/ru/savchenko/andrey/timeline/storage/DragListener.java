package ru.savchenko.andrey.timeline.storage;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import java.util.List;

import ru.savchenko.andrey.timeline.R;
import ru.savchenko.andrey.timeline.adapters.BaseListAdapter;
import ru.savchenko.andrey.timeline.adapters.DragListAdapter;
import ru.savchenko.andrey.timeline.helper.OnStartDragListener;
import ru.savchenko.andrey.timeline.intefaces.Listener;
import ru.savchenko.andrey.timeline.managers.CardManager;
import ru.savchenko.andrey.timeline.entities.Card;

import static android.content.ContentValues.TAG;
import static ru.savchenko.andrey.timeline.storage.Const.NOT_CORRECT_ANSWER;

public class DragListener implements View.OnDragListener {

    private boolean isDropped = false;
    private Listener listener;
    private CardManager cardManager;
    private OnStartDragListener onStartDragListener;

    public DragListener(Listener listener, CardManager cardManager, OnStartDragListener onStartDragListener) {
        this.listener = listener;
        this.cardManager = cardManager;
        this.onStartDragListener = onStartDragListener;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_LOCATION:
                if (v.getX() == listener.getRvTopPositionX() && v.getY() == listener.getRvTopPositionY()) {
                    Log.i(TAG, "onDrag: herrrrrrrrr");
                    listener.onViewLocation("center");
                } else {
                    Log.i(TAG, "onDrag: 123");
                    listener.onViewLocation("");
                }
                break;
            case DragEvent.ACTION_DROP:
                isDropped = true;
                int positionTarget = -1;

                View viewSource = (View) event.getLocalState();
                int viewId = v.getId();
                final int flItem = R.id.frame_layout_item;
                final int tvEmptyListTop = R.id.tvEmptyListTop;
                final int tvEmptyListBottom = R.id.tvEmptyListBottom;
                final int rvTop = R.id.rvTop;
                final int rvBottom = R.id.rvBottom;

                switch (viewId) {
                    case flItem:
                    case tvEmptyListTop:
                    case tvEmptyListBottom:
                    case rvTop:
                    case rvBottom:

                        RecyclerView target;
                        switch (viewId) {
                            case tvEmptyListTop:
                            case rvTop:
                                target = (RecyclerView) v.getRootView().findViewById(rvTop);
                                break;
                            case tvEmptyListBottom:
                            case rvBottom:
                                target = (RecyclerView) v.getRootView().findViewById(rvBottom);
                                break;
                            default:
                                target = (RecyclerView) v.getParent();
                                positionTarget = (int) v.getTag();
                        }

                        if (viewSource != null) {
                            RecyclerView source = (RecyclerView) viewSource.getParent();
                            if (source == target) {
                                break;
                            }
                            DragListAdapter adapterSource = (DragListAdapter) source.getAdapter();
                            int positionSource = (int) viewSource.getTag();
                            int sourceId = source.getId();

                            Card card = adapterSource.getList().get(positionSource);
                            List<Card> listSource = adapterSource.getList();

                            listSource.remove(positionSource);
                            adapterSource.notifyItemRemoved(positionSource);
                            adapterSource.updateList(listSource);

                            cardManager.addUniqueCardInLIst(listSource);
                            adapterSource.notifyItemInserted(positionSource);

                            BaseListAdapter adapterTarget = (BaseListAdapter) target.getAdapter();
                            List<Card> customListTarget = adapterTarget.getList();
                            if (positionTarget >= 0) {
                                customListTarget.add(positionTarget, card);
                            } else {
                                customListTarget.add(card);
                            }
                            adapterTarget.updateList(customListTarget);
                            adapterTarget.notifyDataSetChanged();
                            checkDate(card, customListTarget);
                            if (sourceId == rvBottom && adapterSource.getItemCount() < 1) {
                                listener.setEmptyListBottom(true);
                            }
                            if (viewId == tvEmptyListBottom) {
                                listener.setEmptyListBottom(false);
                            }
                            if (sourceId == rvTop && adapterSource.getItemCount() < 1) {
                                listener.setEmptyListTop(true);
                            }
                            if (viewId == tvEmptyListTop) {
                                listener.setEmptyListTop(false);
                            }


                        }
                        break;
                }
                break;
        }

        if (!isDropped && event.getLocalState() != null) {
            ((View) event.getLocalState()).setVisibility(View.VISIBLE);
        }
        return true;
    }

    private void checkDate(Card card, List<Card> customListTarget) {
        Log.i(TAG, "checkDate: " + card);
        int position = 0;
        for (Card c : customListTarget) {
            if (c.getId() == card.getId()) {
                position = customListTarget.indexOf(c);
            }
        }
        Log.i(TAG, "checkDate: " + position + customListTarget.size());
        if (position > 0 && position != customListTarget.size()-1) {
            int beforeYear = customListTarget.get(position - 1).getYear();
            int afterYear = customListTarget.get(position + 1).getYear();
            int middleYear = card.getYear();
            if (middleYear < afterYear && middleYear > beforeYear) {
                listener.correctDate();
            } else {
                listener.errorDate(NOT_CORRECT_ANSWER);
            }
        } else if (position == 0) {
            int afterYear = customListTarget.get(position + 1).getYear();
            int middleYear = card.getYear();
            if (middleYear < afterYear) {
                listener.correctDate();
            } else {
                listener.errorDate(NOT_CORRECT_ANSWER);
            }
        } else if (position == customListTarget.size()-1) {
            int beforeYear = customListTarget.get(position - 1).getYear();
            int middleYear = card.getYear();
            if (middleYear > beforeYear) {
                listener.correctDate();
            } else {
                listener.errorDate(NOT_CORRECT_ANSWER);
            }
        }
        Log.i(TAG, "checkDate: " + position);
        cardManager.sortList(customListTarget);
        listener.refreshAdapter(customListTarget.indexOf(card));
    }


}