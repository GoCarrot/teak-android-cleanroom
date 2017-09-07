package io.teak.apps.cleanroom;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import io.teak.sdk.Teak;
import io.teak.sdk.Session;
import io.teak.sdk.TeakInstance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TeakLifecycleTestRule<A extends Activity> extends ActivityTestRule<A> {
    abstract public class AbstractPublicStreamHandler extends URLStreamHandler {
        @Override
        public URLConnection openConnection(URL url) throws IOException {
            return null;
        }
    }

    private static URLStreamHandlerFactory urlStreamHandlerFactory;
    static {
        urlStreamHandlerFactory = Mockito.mock(URLStreamHandlerFactory.class);
        //URL.setURLStreamHandlerFactory(urlStreamHandlerFactory);
    }

    public TeakLifecycleTestRule(Class<A> activityClass, boolean initialTouchMode, boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();

        // Mock URL Connections
        AbstractPublicStreamHandler publicStreamHandler = Mockito.mock(AbstractPublicStreamHandler.class);
        Mockito.doReturn(publicStreamHandler).when(urlStreamHandlerFactory).createURLStreamHandler(ArgumentMatchers.eq("https"));

        URLConnection mockedConnection = Mockito.mock(URLConnection.class);
        try {
            Mockito.doReturn(mockedConnection).when(publicStreamHandler).openConnection(ArgumentMatchers.any(URL.class));

            Mockito.doReturn(new ByteArrayInputStream("hello".getBytes("UTF-8"))).when(mockedConnection).getInputStream();
        } catch (Exception e) {
            assertEquals(null, e);
        }

        // Reset Teak singleton
        try {
            Field field = Teak.class.getDeclaredField("Instance");
            field.setAccessible(true);
            field.set(null, new TeakInstance());
        } catch (Exception e) {
            assertEquals(null, e);
        }

        assertEquals(TeakInstance.State.Allocated, Teak.Instance.getState());
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();
        assertEquals(TeakInstance.State.Active, Teak.Instance.getState());
        assertNotNull(Session.getCurrentSessionOrNull());
    }

    @Override
    protected void afterActivityFinished() {
        super.afterActivityFinished();
        getActivity().finish();
        try {
            Thread.sleep(30);
        } catch (Exception ignored) {
        }
        assertEquals(TeakInstance.State.Paused, Teak.Instance.getState());
    }
}
