package ru.savchenko.andrey.timeline.fragments.mainfragment;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import ru.savchenko.andrey.timeline.BuildConfig;
import ru.savchenko.andrey.timeline.entities.Card;
import ru.savchenko.andrey.timeline.intefaces.ChangeToolbarColor;
import ru.savchenko.andrey.timeline.storage.Utils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

/**
 * Created by Andrey on 26.08.2017.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, packageName = "ru.savchenko.andrey.timeline", sdk = 21)
public class TimeLineFragmentTest {

    private TimeLineFragment timeLineFragment = new TimeLineFragment();
    private Context context;

    @Before
    public void setUp()throws Exception{

        timeLineFragment.setChangeToolbarColor(mock(ChangeToolbarColor.class));


        startFragment(timeLineFragment);
        assertNotNull( timeLineFragment );
        context = RuntimeEnvironment.application;
    }

    @Test
    public void addItem() throws Exception {
        timeLineFragment.addItem(2, new Card(12, 1234, "test"));
    }

    @Test
    public void testSaveCOnter(){
        int counter = 0;
        Utils.saveCounter(counter);
        if(counter!=0) {
            assertEquals(counter, Utils.loadCounter());
        }else assertEquals(Utils.loadCounter(), 1);
    }
}