package ru.savchenko.andrey.timeline.intefaces;

import android.widget.ImageView;

/**
 * Created by Andrey on 20.08.2017.
 */

public interface BorderViewListener {
    int getResourceByString(String resName);

    void correctDate();

    void errorDate(String text);

    void refreshAdapter(int position);

    void setImage(ImageView image, String res);
}
