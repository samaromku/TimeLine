package ru.savchenko.andrey.timeline.managers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import ru.savchenko.andrey.timeline.models.Card;

import static android.content.ContentValues.TAG;

/**
 * Created by Andrey on 12.08.2017.
 */

public class CardManager {
    private List<Card> topCards;
    private List<Card> bottomCards;
    private List<Card> allCardList;

    public CardManager() {
        topCards = new ArrayList<>();
        bottomCards = new ArrayList<>();
        allCardList = new ArrayList<>();
        addAllCards();
        addFirstCards();
    }

    public List<Card> getTopCards() {
        return topCards;
    }

    public List<Card> getBottomCards() {
        return bottomCards;
    }

    private void addFirstCards() {
        for (int i = 0; i < 3; i++) {
            addUniqueCardInLIst(topCards);
        }
        sortList(topCards);
        addBottomFirstCard();
    }

    public void addUniqueCardInLIst(List<Card> whereAdd) {
        boolean isInside = false;
        if(allCardList.size()==0)return;
            int index = (int) (Math.random() * allCardList.size());
            Card addCard = allCardList.get(index);
            for (Card c : topCards) {
                if (c.getId() == addCard.getId()) {
                    isInside = true;
                }
            }
            if (!isInside) {
                Log.i(TAG, "addUniqueCardInLIst: not inside");
                whereAdd.add(addCard);
                Iterator<Card> it = allCardList.iterator();
                while (it.hasNext()) {
                    if (it.next().getId()==addCard.getId()) {
                        it.remove();
                    }
                }
            }
    }

    public void sortList(List<Card>cardList){
        Collections.sort(cardList, new Comparator<Card>(){
            @Override
            public int compare(Card card1, Card card2) {
                if(card1.getYear()==card2.getYear())
                    return 0;
                return card1.getYear()<card2.getYear() ? -1: 1;
            }
    });

    }

    private void addBottomFirstCard() {
        addUniqueCardInLIst(bottomCards);
    }

    private void addAllCards() {
        allCardList.add(new Card(1, -3370, "Изобретение колеса", "", "wheel"));
        allCardList.add(new Card(2, 1945, "Изобретение ядерной бомбы", "", "nuclear"));
        allCardList.add(new Card(3, 1295, "Создание мороженого", "", "ice_cream"));
        allCardList.add(new Card(4, 1715, "Создание складного зонта", "", "umbrella"));
        allCardList.add(new Card(5, 1903, "Первый полет самолета", "17 декабря", "air_plane"));
        allCardList.add(new Card(6, 1929, "Первый телевизор", "", "tv"));
        allCardList.add(new Card(7, 1964, "Компьютерная мышь", "", "mouse"));
        allCardList.add(new Card(9, 1973, "Первый мобильный телефон", "", "mobile"));
        allCardList.add(new Card(10, 1940, "Первые колготки", "", "tights"));
        allCardList.add(new Card(11, 1804, "Изобретение паровоза", "", "locomotive"));
        allCardList.add(new Card(12, 1840, "Первая лампа накаливания", "В 1840 году англичанин Деларю производит первую лампу накаливания (с платиновой спиралью)", "lamp"));
        allCardList.add(new Card(13, 1928, "Изобретение пенициллина", "", "penicilin"));
        allCardList.add(new Card(14, 1992, "Первый смартфон", "", "smart"));
        allCardList.add(new Card(16, 1783, "Первый воздушный шар", "", "air_ball"));
        allCardList.add(new Card(17, -1200, "Обработка металла", "", "metal"));
        allCardList.add(new Card(18, 1938, "Растворимый кофе", "", "coffee"));
        allCardList.add(new Card(19, 1896, "Изобретение игры дартс", "", "darts"));
        allCardList.add(new Card(20, 1687, "Первый закон Ньютона", "", "nuton"));
    }
}
