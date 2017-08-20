package ru.savchenko.andrey.timeline.test.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.savchenko.andrey.timeline.R;

/**
 * Created by Andrey on 20.08.2017.
 */

public class IntroDialog extends DialogFragment {
    String introText =
            "Добро пожаловать в TimeSpot! \n" +
            "Здесь ты узнаешь, что было раньше, изобретение пеницилина или атомной бомбы," +
            "сможешь увидеть как рассыпаются цивилизации и создаются гениальный открытия изменившие мир.\n" +
            "Все что нужно знать о правилах:\n" +
            "У тебя есть шесть карт, тебе нужно правильно положить каждую на линию времени, " +
            "если место будет правильным, откроется следующая из колоды.\n" +
            "Если ответишь неверно, карта ложится на линию времени " +
            "в нужном месте, а у тебя появляется новая случайная карта.\n" +
            "Удачи!";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Приветствие");
        View view = inflater.inflate(R.layout.intro_dialog, container);
        TextView tvIntro = (TextView) view.findViewById(R.id.tvDialog);
        tvIntro.setText(introText);
        return view;
    }

}
