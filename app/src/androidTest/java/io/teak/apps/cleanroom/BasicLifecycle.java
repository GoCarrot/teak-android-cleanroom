package io.teak.apps.cleanroom;

import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.MockitoAnnotations;

//@RunWith(AndroidJUnit4.class)
public class BasicLifecycle {
    @Rule
    public TeakLifecycleTestRule<MainActivity> testRule = new TeakLifecycleTestRule<>(MainActivity.class, false, false);

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
/*
    @Spy
    Context mMockContext;

    @Spy
    Resources mMockResources;

    void unused() {
        // Context
        when(mMockContext.getResources())
                .thenReturn(mMockResources);

        // Resources
        when(mMockResources.getIdentifier("io_teak_app_id", "string", "io.teak.apps.cleanroom"))
                .thenReturn(0x42000);
        when(mMockContext.getString(0x42000))
                .thenReturn("1136371193060244");
    }*/

    @Test
    public void basicLifecycle() {
        Intent launchIntent = new Intent();
        testRule.launchActivity(launchIntent);
    }
}
