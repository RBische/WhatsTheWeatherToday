package bischof.raphael.whatstheweathertoday;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.content.pm.ActivityInfo;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by rbischof on 04/02/2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private static final long TIMEOUT_IN_MS = 500;
    private MainActivity activity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);
        activity = getActivity();
    }

    public void testOrientation(){

        Exception exceptionThrown=null;
        try {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            Thread.sleep(400);
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            Thread.sleep(400);
        }catch (Exception e){
            exceptionThrown = e;
        }
        assertNull(exceptionThrown);
    }
}