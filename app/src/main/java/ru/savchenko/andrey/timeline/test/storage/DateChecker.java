package ru.savchenko.andrey.timeline.test.storage;

import android.util.Log;

import java.util.List;

import ru.savchenko.andrey.timeline.intefaces.BorderViewListener;
import ru.savchenko.andrey.timeline.managers.CardManager;
import ru.savchenko.andrey.timeline.models.Card;

import static android.content.ContentValues.TAG;
import static ru.savchenko.andrey.timeline.storage.Const.NOT_CORRECT_ANSWER;

/**
 * Created by Andrey on 20.08.2017.
 */

public class DateChecker {
    public void checkDate(Card card, List<Card> customListTarget, BorderViewListener listener) {
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
        CardManager.sortList(customListTarget);
        listener.refreshAdapter(customListTarget.indexOf(card));
    }
}
