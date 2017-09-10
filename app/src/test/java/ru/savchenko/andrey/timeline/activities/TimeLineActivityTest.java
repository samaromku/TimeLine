package ru.savchenko.andrey.timeline.activities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ru.savchenko.andrey.timeline.BuildConfig;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Andrey on 02.09.2017.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, packageName = "ru.savchenko.andrey.timeline", sdk = 21)
public class TimeLineActivityTest {
    TimeLineActivity activity;

    @Before
    public void init(){
        activity = Robolectric.buildActivity(TimeLineActivity.class).create().get();
    }

    @Test
    public void changeToolbarColor() throws Exception {
        activity.changeToolbarColor(-123123);
    }

    @Test
    public void changeTitle() throws Exception {
        String title = null;
        activity.changeTitle(title);
        assertEquals(activity.getSupportActionBar().getTitle(), title);
    }

}