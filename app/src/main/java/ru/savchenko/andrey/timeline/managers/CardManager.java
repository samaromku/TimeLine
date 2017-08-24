package ru.savchenko.andrey.timeline.managers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import ru.savchenko.andrey.timeline.entities.Card;

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

    public Card getUniqueCard(){
        boolean isInside = false;
        if(allCardList.size()==0)return null;
        int index = (int) (Math.random() * allCardList.size());
        Card addCard = allCardList.get(index);
        for (Card c : topCards) {
            if (c.getId() == addCard.getId()) {
                isInside = true;
            }
        }
        if (!isInside) {
            Log.i(TAG, "addUniqueCardInLIst: not inside");
//            whereAdd.add(addCard);
            Iterator<Card> it = allCardList.iterator();
            while (it.hasNext()) {
                if (it.next().getId()==addCard.getId()) {
                    it.remove();
                }
            }
            return addCard;
        }
        return null;
    }

    public static void sortList(List<Card>cardList){
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
        allCardList.add(new Card(9, 1973, "Мобильный телефон", "", "mobile"));
        allCardList.add(new Card(10, 1940, "Первые колготки", "", "tights"));
        allCardList.add(new Card(11, 1769, "Изобретение паровоза", "", "locomotive"));
        allCardList.add(new Card(12, 1840, "Лампа накаливания", "В 1840 году англичанин Деларю производит первую лампу накаливания (с платиновой спиралью)", "lamp"));
        allCardList.add(new Card(13, 1928, "Изобретение пенициллина", "", "penicilin"));
        allCardList.add(new Card(14, 1992, "Первый смартфон", "", "smart"));
        allCardList.add(new Card(16, 1783, "Воздушный шар", "", "air_ball"));
        allCardList.add(new Card(17, -1200, "Обработка металла", "", "metal"));
        allCardList.add(new Card(18, 1938, "Растворимый кофе", "", "coffee"));
        allCardList.add(new Card(19, 1896, "Изобретение игры дартс", "", "darts"));
        allCardList.add(new Card(20, 1687, "Первый закон Ньютона", "", "nuton"));
        allCardList.add(new Card(21, 1895, "Первый фильм", "", "film"));
        allCardList.add(new Card(22, 1807, "Двигатель внутреннего сгорания", "", "light_engine"));
        allCardList.add(new Card(23, 1768, "Изобретение автомобиля", "", "auto"));
        allCardList.add(new Card(24, 1818, "Изобретение велосипеда", "", "bycicle"));
        allCardList.add(new Card(25, -700, "Первый акведук", "", "akveduk"));
        allCardList.add(new Card(26, 725, "Механические часы", "", "mehanic_clock"));
        allCardList.add(new Card(27, 1991, "Первый веб-сайт", "", "web"));
        allCardList.add(new Card(28, 2009, "Изобретение лжи(фильм)", "", "lie"));
        allCardList.add(new Card(29, 1872, "Изобретение радио", "", "radio"));
        allCardList.add(new Card(30, 1908, "Изобретение фена", "Происхождение слова «фен» связано с немецкой маркой Fön", "fen"));
        allCardList.add(new Card(31, 1180, "Ветряная мельница", "", "wind_mill"));
        allCardList.add(new Card(32, 1492, "Первые сигареты", "", "sigarets"));
        allCardList.add(new Card(33, 1803, "Изобретение холодильника", "", "fridge"));
        allCardList.add(new Card(34, 988, "Крещение Руси", "", "christ"));
        allCardList.add(new Card(35, 1792, "Зарождение феминизма", "", "feminism"));
        allCardList.add(new Card(36, 1920, "Лак для ногтей", "", "lak"));
        allCardList.add(new Card(37, 1968, "Смарт карта", "", "smart_card"));
        allCardList.add(new Card(38, 1826, "Изобретение фотоаппарата", "", "photo"));
        allCardList.add(new Card(39, -2540, "Пирамида Хеопса", "", "heops"));
        allCardList.add(new Card(40, 1912, "Первый парашют", "", "parashut"));
        allCardList.add(new Card(41, 1869, "Таблица менделеева", "", "table_mendeleev"));
        allCardList.add(new Card(42, 1909, "Открытие ядра атома", "", "atom"));
        allCardList.add(new Card(43, 1624, "Первый микроскоп", "", "microskop"));
        allCardList.add(new Card(44, 1961, "Первый полет в космос", "", "cosmos"));
        allCardList.add(new Card(45, 1889, "Первый бюстгальтер", "", "bust"));
        allCardList.add(new Card(46, 1957, "Полиэтиленовый пакет", "", "paket"));
        allCardList.add(new Card(47, 1762, "Первый сэндвич", "", "sendwich"));
        allCardList.add(new Card(48, 1982, "Первый ноутбук", "", "notebook"));
        allCardList.add(new Card(49, 1901, "Лейкопластырь", "", "leikoplast"));
        allCardList.add(new Card(50, 1853, "Первый шприц", "", "shpric"));
    }
}
