package ru.savchenko.andrey.timeline.adapters;


import android.view.View;

import java.util.List;

import ru.savchenko.andrey.timeline.helper.OnStartDragListener;
import ru.savchenko.andrey.timeline.intefaces.Listener;
import ru.savchenko.andrey.timeline.managers.CardManager;
import ru.savchenko.andrey.timeline.models.Card;

/**
 * Created by Andrey on 12.08.2017.
 */

public class NotDragListAdapter extends BaseListAdapter  {
    public NotDragListAdapter(List<Card> list, Listener listener, CardManager cardManager, OnStartDragListener onStartDragListener) {
        super(list, listener, cardManager, onStartDragListener);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.date.setVisibility(View.VISIBLE);
    }
}