package ru.savchenko.andrey.timeline.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.savchenko.andrey.timeline.R;
import ru.savchenko.andrey.timeline.helper.OnStartDragListener;
import ru.savchenko.andrey.timeline.intefaces.Listener;
import ru.savchenko.andrey.timeline.managers.CardManager;
import ru.savchenko.andrey.timeline.entities.Card;
import ru.savchenko.andrey.timeline.storage.DragListener;

/**
 * Created by Andrey on 12.08.2017.
 */

public class BaseListAdapter extends RecyclerView.Adapter<BaseListAdapter.ListViewHolder> {

    private List<Card> list;
    private Listener listener;
    private CardManager cardManager;
    private OnStartDragListener onStartDragListener;

    BaseListAdapter(List<Card> list, Listener listener, CardManager cardManager, OnStartDragListener onStartDragListener) {
        this.list = list;
        this.listener = listener;
        this.cardManager = cardManager;
        this.onStartDragListener = onStartDragListener;
    }

    @Override
    public BaseListAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new BaseListAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseListAdapter.ListViewHolder holder, int position) {
        Card card = list.get(position);
        holder.date.setText(getDateString(card.getYear()));
        holder.title.setText(card.getTitle());
        holder.ivCardImage.setImageResource(listener.getResourceByString(card.getImagePath()));
        holder.frameLayout.setTag(position);
        holder.frameLayout.setOnDragListener(new DragListener(listener, cardManager, onStartDragListener));
    }

    private String getDateString(int date) {
        if (date >= 0) {
            return String.valueOf(date) + " г.";
        } else {
            int positiveDate = date * (-1);
            if (positiveDate % 100 > 0) {
                return String.valueOf(positiveDate / 100) + " в. до н.э.";
            } else
                return String.valueOf(positiveDate) + " г. до н.э.";
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<Card> getList() {
        return list;
    }

    public void updateList(List<Card> list) {
        this.list = list;
    }

    public DragListener getDragInstance() {
        if (listener != null) {
            return new DragListener(listener, cardManager, onStartDragListener);
        } else {
            Log.e("BaseListAdapter", "Listener wasn't initialized!");
            return null;
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView date;
        @BindView(R.id.frame_layout_item)
        CardView frameLayout;
        @BindView(R.id.iv_card_pic)
        ImageView ivCardImage;
        @BindView(R.id.title)
        TextView title;

        ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
