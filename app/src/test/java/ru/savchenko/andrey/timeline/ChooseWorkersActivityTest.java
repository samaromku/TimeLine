package ru.savchenko.andrey.timeline;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import ru.savchenko.andrey.timeline.activities.TimeLineActivity;

import static org.junit.Assert.assertEquals;

//import org.powermock.modules.junit4.rule.PowerMockRule;

/**
 * Created by savchenko on 01.09.17.
 */
@Config(constants = BuildConfig.class, sdk = 21, packageName = "ru.savchenko.andrey.timeline")
@RunWith(RobolectricGradleTestRunner.class)
public class ChooseWorkersActivityTest {
    private Context context;

    @Before
    public void initContext(){
        context = RuntimeEnvironment.application;
//        ChooseWorkersActivity chooseWorkersActivity = Robolectric.buildActivity(ChooseWorkersActivity.class).create().get();
        TimeLineActivity timeLineActivity = Robolectric.setupActivity(TimeLineActivity.class);
    }

    @Test
    public void onCreate() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void initToolbarTitle() throws Exception {
//        view.initToolbarTitle(null);
//        assertEquals(view.getToolbarTitle(), context.getResources().getString(R.string.app_name));
//
//        view.initToolbarTitle("dickos");
//        assertEquals(view.getToolbarTitle(), "dikos1");
    }

    @Test
    public void onClick() throws Exception {

    }

    @Test
    public void refreshAdapterPosition() throws Exception {

    }

    @Test
    public void callSnackBarChooseOne() throws Exception {

    }

    @Test
    public void onFabClick() throws Exception {

    }

    @Test
    public void startInfoActivityWithTaskId() throws Exception {
        int id = 123;
//        view.startInfoActivityWithTaskId(id);
//        assertEquals(id, Prefs.getCurentTaskId());
    }




}