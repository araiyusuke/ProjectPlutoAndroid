package com.projectpluto.projectplutoandroid.ui.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.projectpluto.projectplutoandroid.BuildConfig;
import com.projectpluto.projectplutoandroid.ui.scan.ScanActivity;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowToast;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class BaseViewTest extends TestCase {
    @Mock AppCompatActivity activity;
    @Captor ArgumentCaptor<Intent> intentCaptor;
    BaseView baseView;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        baseView = new BaseView(Robolectric.buildActivity(ScanActivity.class).attach().get());
    }

    @Test
    public void testToast() {
        baseView.toast("testMessage", Toast.LENGTH_LONG);
        assertEquals(ShadowToast.getTextOfLatestToast(), "testMessage");
    }

    @Test
    public void testPopup() {
        baseView.popUp("testTitle", "testMessage");
        assertEquals(1, ShadowDialog.getShownDialogs().size());
    }

    @Test
    public void testStartActivity() {
        baseView = new BaseView(activity);
        baseView.startActivity(BaseViewTest.class);

        verify(activity, times(1)).startActivity(intentCaptor.capture());
        assertEquals(BaseViewTest.class.getCanonicalName(),
                intentCaptor.getValue().getComponent().getClassName());
    }
}
