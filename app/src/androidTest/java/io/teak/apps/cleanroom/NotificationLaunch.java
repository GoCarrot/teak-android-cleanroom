package io.teak.apps.cleanroom;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.teak.sdk.Teak;
import io.teak.sdk.Session;
import io.teak.sdk.TeakNotification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class NotificationLaunch {
    @Rule
    public TeakLifecycleTestRule<MainActivity> testRule = new TeakLifecycleTestRule<MainActivity>(MainActivity.class, false, false) {
        @Override
        protected void afterActivityLaunched() {
            super.afterActivityLaunched();
            Session session = Session.getCurrentSessionOrNull();
            try {
                Map<String, Object> attribution = session.launchAttribution.get(1, TimeUnit.SECONDS);
                assertEquals("test_notif_id", attribution.get("teak_notif_id"));
            } catch (Exception e) {
                assertNull(e);
            }
        }
    };

    @Test
    public void pushNotificationLaunchLifecycle() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent launchIntent = new Intent(appContext.getPackageName() + TeakNotification.TEAK_NOTIFICATION_OPENED_INTENT_ACTION_SUFFIX);
        launchIntent.putExtra("teakNotifId", "test_notif_id");
        testRule.launchActivity(launchIntent);
    }
}
