package ru.savchenko.andrey.timeline.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import ru.savchenko.andrey.timeline.entities.Card;

/**
 * Created by Andrey on 20.08.2017.
 */

public class Utils {
    public static Card dragCardEntity;
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public static String getDateString(int date) {
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

    public static void init(Context context){
        sp = context.getSharedPreferences("prefs", Activity.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static void saveCounter(int counter){
        editor.putInt("counter", counter);
        editor.apply();
    }

    public static int loadCounter(){
        int counter = sp.getInt("counter",0);
        if(counter==0){
            return 1;
        }else {
            return counter;
        }
    }

    public static void saveCategory(int category){
        editor.putInt("category", category);
        editor.apply();
    }

    public static int loadCategory(){
        return sp.getInt("category",0);
    }

}
