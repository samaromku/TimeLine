package ru.savchenko.andrey.timeline.intefaces;

/**
 * Created by Andrey on 22.08.2017.
 */

public interface ChangePlayersListener {
    void onEditName(int position);

    void swapPlayers(int fromPosition, int toPosition);
}
