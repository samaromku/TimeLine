package ru.savchenko.andrey.timeline.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by Andrey on 21.08.2017.
 */

public class SeekBarWithText extends android.support.v7.widget.AppCompatSeekBar {
    public SeekBarWithText(Context context) {
        super(context);
    }

    public SeekBarWithText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeekBarWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.i(TAG, "onDraw: max " + this.getMax());
//        int onTen = this.getProgress()/10;
//        if(this.getProgress()%10==0){
//            Paint fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//            fontPaint.setTextSize(50);
//            int x = this.getS
//            canvas.drawText(String.valueOf(this.getProgress()/10), this.getX(), this.getY(), fontPaint);
//        }
    }


}
