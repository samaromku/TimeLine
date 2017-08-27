/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.savchenko.andrey.timeline.adapters;

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.savchenko.andrey.timeline.R;
import ru.savchenko.andrey.timeline.entities.Player;
import ru.savchenko.andrey.timeline.helper.ItemTouchHelperAdapter;
import ru.savchenko.andrey.timeline.helper.ItemTouchHelperViewHolder;
import ru.savchenko.andrey.timeline.helper.OnStartDragListener;
import ru.savchenko.andrey.timeline.intefaces.ItemClickListenter;
import ru.savchenko.andrey.timeline.intefaces.OnClickListener;
import ru.savchenko.andrey.timeline.intefaces.ChangePlayersListener;
import ru.savchenko.andrey.timeline.repository.PlayersSpec;

import static android.content.ContentValues.TAG;

/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchHelperAdapter} to respond to move and
 * dismiss events from a {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Paul Burke (ipaulpro)
 */
public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {
    private ItemClickListenter clickListenter;
    private List<Player> playerList;
    private ChangePlayersListener onEditNameListener;
    private PlayersSpec playersSpec = new PlayersSpec();

    public void setOnEditNameListener(ChangePlayersListener onEditNameListener) {
        this.onEditNameListener = onEditNameListener;
    }

    public void setClickListenter(ItemClickListenter clickListenter) {
        this.clickListenter = clickListenter;
    }

    private final OnStartDragListener mDragStartListener;

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public PlayersAdapter(OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
        return new ItemViewHolder(view);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(int position) {
            clickListenter.onClick(position, playerList.get(position));
        }
    };

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final Player player = playerList.get(position);
        holder.setClickListenter(onClickListener);
        holder.setOnEditNameListener(onEditNameListener);
        holder.textView.setText(player.getName());
        holder.tvPosition.setText(String.valueOf(player.getPosition()+1));

        holder.itemView.setBackgroundColor(player.getColor());
        // Start a drag whenever the handle view it touched
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder, player);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        playerList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Player sourcePlayer = playerList.get(fromPosition);
        Player targetPlayer = playerList.get(toPosition);

        sourcePlayer.setPosition(toPosition);
        targetPlayer.setPosition(fromPosition);

        Collections.swap(playerList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);


        notifyItemChanged(toPosition);
        notifyItemChanged(fromPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        @BindView(R.id.tvPlayerName)
        TextView textView;
        @BindView(R.id.tvPosition)
        TextView tvPosition;
        private OnClickListener clickListenter;
        private ChangePlayersListener onEditNameListener;

        @OnClick(R.id.ivEditName)void onClick(){
            onEditNameListener.onEditName(getAdapterPosition());
            Log.i(TAG, "onClick: viewholder position " + getAdapterPosition());
        }

        void setClickListenter(OnClickListener clickListenter) {
            this.clickListenter = clickListenter;
        }

        void setOnEditNameListener(ChangePlayersListener onEditNameListener) {
            this.onEditNameListener = onEditNameListener;
        }

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListenter.onClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
