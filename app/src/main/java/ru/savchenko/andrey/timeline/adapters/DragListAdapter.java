package ru.savchenko.andrey.timeline.adapters;

import android.content.ClipData;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import ru.savchenko.andrey.timeline.helper.OnStartDragListener;
import ru.savchenko.andrey.timeline.intefaces.Listener;
import ru.savchenko.andrey.timeline.managers.CardManager;
import ru.savchenko.andrey.timeline.entities.Card;

public class DragListAdapter extends BaseListAdapter implements View.OnTouchListener {
    public DragListAdapter(List<Card> list, Listener listener, CardManager cardManager, OnStartDragListener onStartDragListener) {
        super(list, listener, cardManager, onStartDragListener);
    }

    @Override
    public void onBindViewHolder(BaseListAdapter.ListViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.frameLayout.setOnTouchListener(this);
        holder.date.setVisibility(View.GONE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ClipData data = ClipData.newPlainText("123", "234");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                } else {
                    v.startDrag(data, shadowBuilder, v, 0);
                }
                return true;
        }
        return false;
    }
}
