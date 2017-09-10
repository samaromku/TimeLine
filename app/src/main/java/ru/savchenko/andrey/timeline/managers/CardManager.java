package ru.savchenko.andrey.timeline.managers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import ru.savchenko.andrey.timeline.entities.Card;
import ru.savchenko.andrey.timeline.storage.Const;
import ru.savchenko.andrey.timeline.storage.Utils;

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
        initList();
        Log.i(TAG, "CardManager: " + allCardList.size());
        addFirstCards();
    }

    private void initList() {
        int category = Utils.loadCategory();
        switch (category){
            case Const.ALL_CAT:
                allCardList.addAll(getAllCards());
                break;
            case Const.LITRA_CAT:
                allCardList.addAll(litraCards());
                break;
            case Const.RUSSIA_CAT:
                allCardList.addAll(russiaHistory());
                break;
            case Const.CINEMA_CAT:
                allCardList.addAll(cinemaList());
                break;
        }
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

    private List<Card> getAllCards() {
        List<Card>cardList = new ArrayList<>();

        cardList.add(new Card(3370, "Изобретение колеса", "", "wheel"));
        cardList.add(new Card(945, "Изобретение ядерной бомбы", "", "nuclear"));
        cardList.add(new Card(295, "Создание мороженого", "", "ice_cream"));
        cardList.add(new Card(715, "Создание складного зонта", "", "umbrella"));
        cardList.add(new Card(903, "Первый полет самолета", "17 декабря", "air_plane"));
        cardList.add(new Card(929, "Первый телевизор", "", "tv"));
        cardList.add(new Card(970, "Компьютерная мышь", "", "mouse"));
        cardList.add(new Card(973, "Мобильный телефон", "", "mobile"));
        cardList.add(new Card(1940, "Первые колготки", "", "tights"));
        cardList.add(new Card(1769, "Изобретение паровоза", "", "locomotive"));
        cardList.add(new Card(1840, "Лампа накаливания", "В 1840 году англичанин Деларю производит первую лампу накаливания (с платиновой спиралью)", "lamp"));
        cardList.add(new Card(1928, "Изобретение пенициллина", "", "penicilin"));
        cardList.add(new Card(1992, "Первый смартфон", "", "smart"));
        cardList.add(new Card(1783, "Воздушный шар", "", "air_ball"));
        cardList.add(new Card(-1200, "Обработка металла", "", "metal"));
        cardList.add(new Card(1938, "Растворимый кофе", "", "coffee"));
        cardList.add(new Card(1896, "Изобретение игры дартс", "", "darts"));
        cardList.add(new Card(1687, "Первый закон Ньютона", "", "nuton"));
        cardList.add(new Card(1895, "Первый фильм", "", "film"));
        cardList.add(new Card(1807, "Двигатель внутреннего сгорания", "", "light_engine"));
        cardList.add(new Card(1768, "Изобретение автомобиля", "", "auto"));
        cardList.add(new Card(1818, "Изобретение велосипеда", "", "bycicle"));
        cardList.add(new Card(-700, "Первый акведук", "", "akveduk"));
        cardList.add(new Card(725, "Механические часы", "", "mehanic_clock"));
        cardList.add(new Card(1991, "Первый веб-сайт", "", "web"));
        cardList.add(new Card(2009, "Изобретение лжи(фильм)", "", "lie"));
        cardList.add(new Card(1872, "Изобретение радио", "", "radio"));
        cardList.add(new Card(1908, "Изобретение фена", "Происхождение слова «фен» связано с немецкой маркой Fön", "fen"));
        cardList.add(new Card(1180, "Ветряная мельница", "", "wind_mill"));
        cardList.add(new Card(1492, "Первые сигареты", "", "sigarets"));
        cardList.add(new Card(1803, "Изобретение холодильника", "", "fridge"));
        cardList.add(new Card(988, "Крещение Руси", "", "christ"));
        cardList.add(new Card(1792, "Зарождение феминизма", "", "feminism"));
        cardList.add(new Card(1920, "Лак для ногтей", "", "lak"));
        cardList.add(new Card(1968, "Смарт карта", "", "smart_card"));
        cardList.add(new Card(1826, "Изобретение фотоаппарата", "", "photo"));
        cardList.add(new Card(-2540, "Пирамида Хеопса", "", "heops"));
        cardList.add(new Card(1912, "Первый парашют", "", "parashut"));
        cardList.add(new Card(1869, "Таблица менделеева", "", "table_mendeleev"));
        cardList.add(new Card(1909, "Открытие ядра атома", "", "atom"));
        cardList.add(new Card(1624, "Первый микроскоп", "", "microskop"));
        cardList.add(new Card(1961, "Первый полет в космос", "", "cosmos"));
        cardList.add(new Card(1889, "Первый бюстгальтер", "", "bust"));
        cardList.add(new Card(1957, "Полиэтиленовый пакет", "", "paket"));
        cardList.add(new Card(1762, "Первый сэндвич", "", "sendwich"));
        cardList.add(new Card(1982, "Первый ноутбук", "", "notebook"));
        cardList.add(new Card(1901, "Лейкопластырь", "", "leikoplast"));
        cardList.add(new Card(1853, "Первый шприц", "", "shpric"));
        cardList.add(new Card(1969, "Возникновение интернета", "", "inthernet"));
        cardList.add(new Card(1440, "Печатный станок", "", "print"));
        cardList.add(new Card(1637, "Первая газета", "", "newspaper"));
        cardList.add(new Card(1940, "Первый макдональдс", "", "macdonalds"));
        cardList.add(new Card(1949, "Первый телесериал", "", "tele_serial"));
        cardList.add(new Card(1284, "Изобретение очков", "", "glasses"));
        cardList.add(new Card(1939, "Первый комикс марвел", "", "marvel"));
        cardList.add(new Card(1947, "Автомат калашникова", "", "kalash"));
        cardList.add(new Card(1838, "Первый противогаз", "", "protivogaz"));
        cardList.add(new Card(1968, "Первая беговая дорожка", "", "run_road"));
        cardList.add(new Card(1853, "Изобретение чипсов", "", "chips"));
        cardList.add(new Card(1943, "Аэрозольный балончик", "", "air_baloon"));
        cardList.add(new Card(1743, "Изобретение роликов", "", "rolls"));
        cardList.add(new Card(1600, "Понятие электричества", "", "electricity"));
        cardList.add(new Card(1868, "Первый светофор", "", "svetofor"));
        cardList.add(new Card(1828, "Первый трамвай", "", "tram"));
        cardList.add(new Card(1863, "Первая линия метро", "", "metro"));
        cardList.add(new Card(1492, "Открытие Америки", "", "america"));
        cardList.add(new Card(1044, "Открытие пороха", "", "poroh"));
        cardList.add(new Card(1801, "Отмена рабства", "", "rab"));

        for (int i = 0; i < cardList.size(); i++) {
            cardList.get(i).setId(i);
        }

        return cardList;

    }

    private List<Card> litraCards(){
        List<Card>cardList = new ArrayList<>();

        cardList.add(new Card(1943 , "Маленький принц ", "", "little_prince"));
        cardList.add(new Card(1603 , "Гамлет ", "", "hamlet"));
        cardList.add(new Card(1936 , "Унесённые ветром", "", "throw_wind"));
        cardList.add(new Card(1876 , "Приключения Тома Сойера ", "", "tom_soier"));
        cardList.add(new Card(1939 , "Десять негритят ", "", "ten_negros"));
        cardList.add(new Card(1962 , "Пролетая над гнездом кукушки ", "", "kukushka"));
        cardList.add(new Card(1836 , "Капитанская дочка ", "", "capitans_daughter"));
        cardList.add(new Card(1840 , "Герой нашего времени ", "", "hero_our_time"));
        cardList.add(new Card(1966 , "Мастер и Маргарита ", "", "master_margarita"));
        cardList.add(new Card(1880 , "Братья Карамазовы ", "", "brothers_karamaz"));
        cardList.add(new Card(1118 , "Повесть временных лет ", "", "vremennih_let"));
        cardList.add(new Card(1917 , "Принцесса Марса ", "", "princess_marc"));
        cardList.add(new Card(1847 , "Carmina Burana ", "", "carmina_burana"));
        cardList.add(new Card(1932 , "О дивный новый мир ", "", "new_world"));
        cardList.add(new Card(1938 , "Машина времени ", "", "time_machine"));
        cardList.add(new Card(1897 , "Граф Дракула ", "", "dracula"));
        cardList.add(new Card(1931 , "Хребты Безумия ", "", "mad_mountains"));
        cardList.add(new Card(1883 , "Так говорил Заратустра ", "", "zaratustra"));
        cardList.add(new Card(1872 , "Вокруг света за 80 дней ", "", "vokrug_sveta"));
        cardList.add(new Card(1844 , "Граф Монте-Кристо ", "", "monte_cristo"));
        cardList.add(new Card(1516 , "Утопия ", "", "utopia"));
        cardList.add(new Card(1321 , "Божественная комедия ", "", "god_comedy"));
        cardList.add(new Card(1485 , "Смерть Артура ", "", "autur_death"));
        cardList.add(new Card(1542 , "Гаргантюа и Пантагрюэль ", "", "gargantua"));
        cardList.add(new Card(1719 , "Робинзон Крузо ", "", "robinzon_kruzo"));
        cardList.add(new Card(1862 , "Отцы и дети ", "", "fathers_chiildren"));
        cardList.add(new Card(1890 , "Портрет Дориана Грея ", "", "dorian_grey"));
        cardList.add(new Card(1949 , "Герой с тысячью лицами ", "", "hero_thouthend_faces"));
        cardList.add(new Card(1944 , "Сад расходящихся тропок ", "", "garden_borhes"));
        cardList.add(new Card(1952 , "В ожидании Годо ", "", "waiting_godo"));
        cardList.add(new Card(1996 , "Бойцовский клуб ", "", "fight_club"));
        cardList.add(new Card(1985 , "Парфюмер ", "", "parfumer"));
        cardList.add(new Card(1951 , "Над пропастью во ржи ", "", "vo_rzhi"));
        cardList.add(new Card(1967 , "Сто лет одиночества ", "", "sto_let_odinochestva"));
        cardList.add(new Card(1977 , "Сияние ", "", "shining"));
        cardList.add(new Card(1819 , "Айвенго ", "", "aivengo"));
        cardList.add(new Card(1982 , "Охота на овец ", "", "sheeps_hunting"));
        cardList.add(new Card(2003 , "Код да Винчи ", "", "kod_da_vinchi"));
        cardList.add(new Card(2005 , "Сумерки ", "", "sumerki"));
        cardList.add(new Card(2006 , "Ложная слепота ", "", "lie_blind"));
        cardList.add(new Card(2008 , "Голодные игры ", "", ""));
        cardList.add(new Card(1813 , "Гордость и предубеждение ", "", ""));
        cardList.add(new Card(2011 , "50 оттенков серого ", "", ""));
        cardList.add(new Card(75 , "Иудейская война ", "", ""));
        cardList.add(new Card(632 , "Коран ", "", ""));
        cardList.add(new Card(1644 , "Начала философии ", "", ""));
        cardList.add(new Card(1859, "Происхождение видов ", "", ""));
        cardList.add(new Card(1487 , "Молот ведьм ", "", ""));
        cardList.add(new Card(1513 , "Государь (Макиавелли) ", "", ""));
        cardList.add(new Card(1670 , "Мещанин во дворянстве ", "", ""));
        cardList.add(new Card(1608 , "Король Лир ", "", ""));
        cardList.add(new Card(1618 , "Собака на сене ", "", ""));
        cardList.add(new Card(1667 , "Потерянный рай ", "", ""));
        cardList.add(new Card(1808 , "Фауст ", "", ""));
        cardList.add(new Card(1913 , "Пигмалион (Бернард Шоу) ", "", ""));
        cardList.add(new Card(1726 , "Путешествие Гулливера ", "", ""));
        cardList.add(new Card(1826 , "Последний из могикан ", "", ""));
        cardList.add(new Card(1923 , "Алые паруса ", "", ""));
        cardList.add(new Card(1816 , "Щелкунчик и Мышиный король ", "", ""));
        cardList.add(new Card(1827 , "Карлик Нос ", "", ""));
        cardList.add(new Card(1971 , "Белый Бим Чёрное ухо ", "", ""));
        cardList.add(new Card(1906 , "Белый Клык ", "", ""));
        cardList.add(new Card(1937 , "О мышах и людях ", "", ""));
        cardList.add(new Card(1955 , "Малыш и Карлсон", "", ""));
        cardList.add(new Card(1950 , "Мемуары Папы Муми-тролля ", "", ""));
        cardList.add(new Card(1889 , "Трое в лодке, не считая собаки ", "", ""));
        cardList.add(new Card(1837 , "Оливер Твист ", "", ""));
        cardList.add(new Card(1926 , "Винни-Пух ", "", ""));
        cardList.add(new Card(1893 , "Книга джунглей ", "", ""));
        cardList.add(new Card(1900 , "Волшебник из страны Оз ", "", ""));
        cardList.add(new Card(1881 , "Приключения Пиноккио ", "", ""));
        cardList.add(new Card(1852 , "Хижина дяди Тома ", "", ""));
        cardList.add(new Card(1925 , "Великий Гэтсби ", "", ""));
        cardList.add(new Card(1935 , "Три товарища ", "", ""));
        cardList.add(new Card(1969 , "Крёстный отец ", "", ""));
        cardList.add(new Card(1865 , "Всадник без головы ", "", ""));
        cardList.add(new Card(1957 , "Доктор Живаго ", "", ""));
        cardList.add(new Card(1958 , "Завтрак у Тиффани ", "", ""));
        cardList.add(new Card(1979 , "Автостопом по галактике ", "", ""));

        for (int i = 0; i < cardList.size(); i++) {
            cardList.get(i).setId(i);
        }

        return cardList;
    }

    private List<Card>russiaHistory(){
        List<Card>cardList = new ArrayList<>();

        cardList.add(new Card(863, "﻿Кирилл и Мефодий создают письменность", "", ""));
        cardList.add(new Card(879, "Смерть князя Рюрика", "", ""));
        cardList.add(new Card(882, "Киев провозглашается столицей", "", ""));
        cardList.add(new Card(1118, "Повесть временных лет", "", ""));
        cardList.add(new Card(1147, "Первое упоминание о Москве", "", ""));
        cardList.add(new Card(1188, "Слово о полку Игореве", "", ""));
        cardList.add(new Card(1237, "Нашествие хана Батыя", "", ""));
        cardList.add(new Card(1240, "Победа Александра Невского над шведами", "", ""));
        cardList.add(new Card(1242, "Ледовое побоище", "", ""));
        cardList.add(new Card(1367, "Строительство каменного Кремля в Москве", "", ""));
        cardList.add(new Card(1380, "Куликовская битва", "", ""));
        cardList.add(new Card(1395, "Тамерлан разгромил Золотую Орду", "", ""));
        cardList.add(new Card(1480, "Конец татаро-монгольского ига", "", ""));
        cardList.add(new Card(1510, "Присоединение Пскова к Москве", "", ""));
        cardList.add(new Card(1547, "Коронация Ивана Грозного", "", ""));
        cardList.add(new Card(1552, "Взятие Казани", "", ""));
        cardList.add(new Card(1569, "Образование Речи Посполитой", "", ""));
        cardList.add(new Card(1589, "Установление Московского Патриархата", "", ""));
        cardList.add(new Card(1591, "Конец династии Рюриковичей", "", ""));
        cardList.add(new Card(1605, "Воцарение Лжедмитрия I", "", ""));
        cardList.add(new Card(1606, "Убийство Лжедмитрия I", "", ""));
        cardList.add(new Card(1612, "Взятие Кремля Мининым и Пожарским", "", ""));
        cardList.add(new Card(1613, "Воцарение Михаила Федоровича Романова", "", ""));
        cardList.add(new Card(1654, "Воссоединение России и Украины", "", ""));
        cardList.add(new Card(1670, "Восстание Степана Разина", "", ""));
        cardList.add(new Card(1698, "Стрелецкий бунт", "", ""));
        cardList.add(new Card(1699, "Реформа летоисчисления", "", ""));
        cardList.add(new Card(1703, "Основание Санкт-Петербурга", "", ""));
        cardList.add(new Card(1721, "Принятие Петром I императорского титула", "", ""));
        cardList.add(new Card(1725, "Смерть Петра I", "", ""));
        cardList.add(new Card(1762, "Екатерина II становится императрицей", "", ""));
        cardList.add(new Card(1773, "Восстание Емельяна Пугачева", "", ""));
        cardList.add(new Card(1783, "Присоединение Крымского Ханства", "", ""));
        cardList.add(new Card(1799, "Военные походы А. В. Суворова", "", ""));
        cardList.add(new Card(1801, "Убийство Павла I", "", ""));
        cardList.add(new Card(1805, "Битва при Аустерлице", "", ""));
        cardList.add(new Card(1812, "Война с Наполеоном Бонапартом", "", ""));
        cardList.add(new Card(1825, "Восстание декабристов в Петербурге", "", ""));
        cardList.add(new Card(1861, "Отмена крепостного права", "", ""));
        cardList.add(new Card(1881, "Убийство Александра II", "", ""));
        cardList.add(new Card(1902, "Образование партии эсеров", "", ""));
        cardList.add(new Card(1904, "Русско-японская война", "", ""));
        cardList.add(new Card(1906, "Аграрная реформа П. А. Столыпина", "", ""));
        cardList.add(new Card(1914, "Начало Первой мировой войны", "", ""));
        cardList.add(new Card(1916, "Убийство Г. Распутина", "", ""));
        cardList.add(new Card(1917, "Свержение монархии большевиками", "", ""));
        cardList.add(new Card(1918, "Принятие Конституции РСФСР", "", ""));
        cardList.add(new Card(1922, "Образование СССР", "", ""));
        cardList.add(new Card(1924, "Смерть В. И. Ленина", "", ""));
        cardList.add(new Card(1941, "Начало Великой Отечественной Войны", "", ""));
        cardList.add(new Card(1945, "Победа СССР в Великой Отечественной Войне", "", ""));
        cardList.add(new Card(1949, "Первое испытание атомной бомбы", "", ""));
        cardList.add(new Card(1953, "Смерть И. В. Сталина", "", ""));
        cardList.add(new Card(1957, "Запуск первого в мире спутника", "", ""));
        cardList.add(new Card(1961, "Космический полёт Гагарина", "", ""));
        cardList.add(new Card(1979, "Интервенция СССР в Афганистане", "", ""));
        cardList.add(new Card(1980, "Олимпийские игры в Москве", "", ""));
        cardList.add(new Card(1982, "Смерть Л. И. Брежнева", "", ""));
        cardList.add(new Card(1986, "Авария на Чернобыльской АЭС", "", ""));
        cardList.add(new Card(1987, "Начало политики Перестройки", "", ""));
        cardList.add(new Card(1991, "Роспуск СССР и создание СНГ", "", ""));

        for (int i = 0; i < cardList.size(); i++) {
            cardList.get(i).setId(i);
        }

        return cardList;
    }

    private List<Card>cinemaList(){
        List<Card>cardList = new ArrayList<>();

        cardList.add(new Card(1934, "﻿Чапаев", "", ""));
        cardList.add(new Card(1937, "Белоснежка и семь гномов", "", ""));
        cardList.add(new Card(1939, "Унесённые ветром", "", ""));
        cardList.add(new Card(1940, "Филадельфийская история", "", ""));
        cardList.add(new Card(1941, "Гражданин Кейн", "", ""));
        cardList.add(new Card(1942, "Касабланка", "", ""));
        cardList.add(new Card(1948, "Цветик-Семицветик", "", ""));
        cardList.add(new Card(1950, "Всё о Еве", "", ""));
        cardList.add(new Card(1952, "Аленький цветочек (мультфильм)", "", ""));
        cardList.add(new Card(1955, "Леди и бродяга", "", ""));
        cardList.add(new Card(1957, "Летят журавли", "", ""));
        cardList.add(new Card(1959, "В джазе только девушки", "", ""));
        cardList.add(new Card(1960, "Психо", "", ""));
        cardList.add(new Card(1961, "Вестсайдская история", "", ""));
        cardList.add(new Card(1962, "Лоуренс Аравийский", "", ""));
        cardList.add(new Card(1963, "Доктор Стрейнджлав", "", ""));
        cardList.add(new Card(1964, "Голдфингер", "", ""));
        cardList.add(new Card(1965, "Вовка в Тридевятом царстве", "", ""));
        cardList.add(new Card(1966, "Кавказская пленница", "", ""));
        cardList.add(new Card(1967, "Бонни и Клайд", "", ""));
        cardList.add(new Card(1968, "Космическая одиссея 2001", "", ""));
        cardList.add(new Card(1969, "Бременские музыканты (СССР)", "", ""));
        cardList.add(new Card(1970, "Коты-аристократы", "", ""));
        cardList.add(new Card(1971, "Джентльмены удачи", "", ""));
        cardList.add(new Card(1972, "Крестный отец", "", ""));
        cardList.add(new Card(1973, "Американские граффити", "", ""));
        cardList.add(new Card(1975, "Челюсти", "", ""));
        cardList.add(new Card(1976, "Белый Бим Черное ухо", "", ""));
        cardList.add(new Card(1977, "Служебный роман", "", ""));
        cardList.add(new Card(1978, "Хэллоуин", "", ""));
        cardList.add(new Card(1979, "Апокалипсис сегодня", "", ""));
        cardList.add(new Card(1980, "Вам и не снилось", "", ""));
        cardList.add(new Card(1981, "Тайна третьей планеты", "", ""));
        cardList.add(new Card(1982, "Инопланетянин", "", ""));
        cardList.add(new Card(1984, "Терминатор", "", ""));
        cardList.add(new Card(1985, "Назад в будущее", "", ""));
        cardList.add(new Card(1986, "Муха (Д. Кроненберг)", "", ""));
        cardList.add(new Card(1987, "Робокоп", "", ""));
        cardList.add(new Card(1988, "Крепкий орешек", "", ""));
        cardList.add(new Card(1989, "Русалочка (Disney)", "", ""));
        cardList.add(new Card(1990, "Один дома", "", ""));
        cardList.add(new Card(1991, "Бешеные псы", "", ""));
        cardList.add(new Card(1992, "Аладдин", "", ""));
        cardList.add(new Card(1993, "Кошмар перед Рождеством", "", ""));
        cardList.add(new Card(1994, "Криминальное чтиво", "", ""));
        cardList.add(new Card(1995, "Фарго (фильм)", "", ""));
        cardList.add(new Card(1997, "Брат", "", ""));
        cardList.add(new Card(1998, "Большой Лебовски", "", ""));
        cardList.add(new Card(1999, "Бойцовский клуб", "", ""));
        cardList.add(new Card(2000, "Гладиатор", "", ""));
        cardList.add(new Card(2001, "Унесённые призраками", "", ""));
        cardList.add(new Card(2002, "Пианист", "", ""));
        cardList.add(new Card(2003, "В поисках Немо", "", ""));
        cardList.add(new Card(2004, "Вечное сияние чистого разума", "", ""));
        cardList.add(new Card(2005, "Мадагаскар", "", ""));
        cardList.add(new Card(2007, "Старикам тут не место", "", ""));
        cardList.add(new Card(2008, "Темный рыцарь", "", ""));
        cardList.add(new Card(2009, "Аватар (Дж. Кэмерон)", "", ""));
        cardList.add(new Card(2010, "Как приручить дракона", "", ""));
        cardList.add(new Card(2012, "Ральф", "", ""));
        cardList.add(new Card(2013, "Риддик", "", ""));

        for (int i = 0; i < cardList.size(); i++) {
            cardList.get(i).setId(i);
        }

        return cardList;
    }
}
