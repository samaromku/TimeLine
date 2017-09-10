package ru.savchenko.andrey.timeline.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.savchenko.andrey.timeline.R;
import ru.savchenko.andrey.timeline.entities.Card;
import ru.savchenko.andrey.timeline.fragments.mainfragment.interactor.TimeLineInteractor;
import ru.savchenko.andrey.timeline.fragments.mainfragment.interactor.TimeLineInteractorImpl;
import ru.savchenko.andrey.timeline.lib.DragItem;
import ru.savchenko.andrey.timeline.storage.Utils;

import static android.content.ContentValues.TAG;

/**
 * Created by Andrey on 25.08.2017.
 */

public class MyDragItem extends DragItem {
    private Context activity;

    public MyDragItem(Context context, int layoutId) {
        super(context, layoutId);
        activity = context;
    }

    @Override
    public void onBindDragView(View clickedView, View dragView) {
        Card dragCardEntity = Utils.dragCardEntity;
        Log.i(TAG, "onBindDragView: " + dragCardEntity);
        dragView.findViewById(R.id.text).setVisibility(View.GONE);
        // TODO: 08.09.2017 replace if possible
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
