package ru.savchenko.andrey.timeline;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import ru.savchenko.andrey.timeline.entities.Card;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Andrey on 02.09.2017.
 */

public class ReadFromFileTest {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Test
    public void readFromFile() throws IOException {

        File file = new File("C:\\Users\\Andrey\\Desktop\\time_line\\Cinema\\Cinema.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String replaced = line.replace("title: ", "");

                String[] parts = replaced.split("date: ");
                if (parts.length == 2) {
                    String date = parts[1].trim();
                    String event = parts[0].trim();
                    System.out.println("cardList.add(new Card(" + date + ", \"" + event + "\", \"\", \"\"));");
                }
            }
        }
    }


    @Test
    public void testRx() {
        List<Card> cardList = new ArrayList<>();
        cardList.add(new Card(123, "test", "test", "hui"));
        cardList.add(new Card(1, 123, "hello"));
        cardList.add(new Card(1, 123, "deti"));
        cardList.add(new Card(1, 123, "hello"));
        cardList.add(new Card(1, 123, "test"));
        cardList.add(new Card(1, 123, null));
        Observable.fromIterable(cardList)
                .filter(card -> card.getTitle() != null)
                .filter(card -> card.getTitle().equals("test"))
                .flatMap((Function<Card, ObservableSource<?>>) card -> Observable.just(card.getTitle()))
                .subscribe(o -> assertEquals(o, "test"));
    }
}
