package ru.savchenko.andrey.timeline.intefaces;

public interface Listener {
    void setEmptyListTop(boolean visibility);

    void setEmptyListBottom(boolean visibility);

    int getResourceByString(String resName);

    void onViewLocation(String text);

    int getRvTopPositionY();

    int getRvTopPositionX();

    void correctDate();

    void errorDate(String text);

    void refreshAdapter(int position);

    int visibleCount();
}