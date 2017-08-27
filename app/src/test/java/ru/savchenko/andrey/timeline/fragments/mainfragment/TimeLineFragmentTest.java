package ru.savchenko.andrey.timeline.fragments.mainfragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.savchenko.andrey.timeline.BuildConfig;
import ru.savchenko.andrey.timeline.R;
import ru.savchenko.andrey.timeline.activities.TimeLineActivity;
import ru.savchenko.andrey.timeline.fragments.mainfragment.view.TimeLineView;

import static org.junit.Assert.assertEquals;

/**
 * Created by Andrey on 26.08.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TimeLineFragmentTest {
    private TimeLineActivity activity;
    private TimeLineFragment timeLineFragment = new TimeLineFragment();
    private TimeLineView view = new TimeLineFragment();

    @Before
    public void setUp()throws Exception{
        TimeLineActivity activity = Robolectric.buildActivity( TimeLineActivity.class )
                .create()
                .start()
                .resume()
                .get();

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( timeLineFragment, null );
        fragmentTransaction.commit();

//        activity = Robolectric.setupActivity(TimeLineActivity.class);
//        view = new TimeLineFragment();
    }

    private void startFragment(TimeLineFragment timeLineFragment) {

    }


    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void onCreateView() throws Exception {

    }

    @Test
    public void onDestroy() throws Exception {

    }

    @Test
    public void changeToolbarColor() throws Exception {

    }

    @Test
    public void adapterCheckDate() throws Exception {

    }

    @Test
    public void addItem() throws Exception {

    }

    @Test
    public void setCardImage() throws Exception {
        view.setCardImage(1);
        ImageView ivDeck = (ImageView) activity.findViewById(R.id.iv_card_pic);
        assertEquals(ivDeck.getResources(), activity.getResources().getIdentifier("shirt_00" + 1, "drawable", activity.getPackageName()));
    }

    @Test
    public void onActivityCreated() throws Exception {

    }

    @Test
    public void addColumnList() throws Exception {

    }

    @Test
    public void showPlayerName() throws Exception {

    }



    @Test
    public void onCreateOptionsMenu() throws Exception {

    }

    @Test
    public void onPrepareOptionsMenu() throws Exception {

    }

    @Test
    public void onOptionsItemSelected() throws Exception {

    }

    @Test
    public void openSettings() throws Exception {

    }

    @Test
    public void openIntro() throws Exception {

    }

    @Test
    public void getResourceByString() throws Exception {

    }

    @Test
    public void upCounterOrVictory() throws Exception {

    }

    @Test
    public void correctDate() throws Exception {

    }

    @Test
    public void errorDate() throws Exception {

    }

    @Test
    public void refreshAdapter() throws Exception {

    }

    @Test
    public void onAccept() throws Exception {

    }

    @Test
    public void onCancel() throws Exception {

    }

}