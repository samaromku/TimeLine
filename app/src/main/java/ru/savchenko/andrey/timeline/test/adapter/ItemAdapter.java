/*
 * Copyright 2014 Magnus Woxblom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.savchenko.andrey.timeline.test.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.savchenko.andrey.timeline.R;
import ru.savchenko.andrey.timeline.intefaces.BorderViewListener;
import ru.savchenko.andrey.timeline.lib.DragItemAdapter;
import ru.savchenko.andrey.timeline.entities.Card;
import ru.savchenko.andrey.timeline.test.storage.DateChecker;
import ru.savchenko.andrey.timeline.test.storage.Utils;

public class ItemAdapter extends DragItemAdapter<Card, ItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;
    private BorderViewListener borderViewListener;
    private boolean hasDate;
    private DateChecker dateChecker = new DateChecker();

    public void setBorderViewListener(BorderViewListener borderViewListener) {
        this.borderViewListener = borderViewListener;
    }

    public void setHasDate(boolean hasDate) {
        this.hasDate = hasDate;
    }

    public ItemAdapter(List<Card> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        setHasStableIds(true);
        setItemList(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Card card = mItemList.get(position);
        if(hasDate) {
            holder.date.setVisibility(View.VISIBLE);
            holder.date.setText(Utils.getDateString(card.getYear()));
        }else {
            holder.date.setVisibility(View.GONE);
        }
        holder.title.setText(card.getTitle());
        holder.ivCardImage.setImageResource(borderViewListener.getResourceByString(card.getImagePath()));
        holder.itemView.setTag(mItemList.get(position));

    }



    @Override
    public long getItemId(int position) {
        return mItemList.get(position).getId();
    }

    public Card getCardById(int position){
        return mItemList.get(position);
    }

    public void checkDate(int position){
        Card card = mItemList.get(position);
        dateChecker.checkDate(card, mItemList, borderViewListener);
    }

    class ViewHolder extends DragItemAdapter.ViewHolder {
        @BindView(R.id.text)
        TextView date;
        @BindView(R.id.iv_card_pic)
        ImageView ivCardImage;
        @BindView(R.id.title)
        TextView title;

        ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onItemClicked(View view) {

        }

        @Override
        public boolean onItemLongClicked(View view) {
            return true;
        }
    }
}
