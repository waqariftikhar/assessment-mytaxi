package com.mytaxi.android_demo;

import android.content.Context;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.net.Credentials;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;

/**
 * Created by Waqar Ahmed on 4/15/2018.
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class mytaxi_SearchDriver {

    private credentials key= new credentials();

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);
    private MainActivity mActivity = null;



    @Before
    public void setActivity() {
        mActivity = mActivityRule.getActivity();
    }



    @Test
    public void useAppContext() throws Exception {

        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mytaxi.android_demo", appContext.getPackageName());

        mytaxi_login(key.username,key.password);
        SystemClock.sleep(5000);
        search_Driver(key.driversearch,key.selectDriver);
    }


    public void mytaxi_login(String username, String password) throws InterruptedException{


        onView(withId(R.id.edt_username))
                .perform(typeText(username)).check(matches(withText(username)));
        onView(withId(R.id.edt_username)).perform(closeSoftKeyboard());

        onView(withId(R.id.edt_password))
                .perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.btn_login))
                .perform(click());



    }

    //Searching for driver and Select the available driver
    public void search_Driver(String driversearch, String selectDriver) throws InterruptedException {

        onView(withId(R.id.textSearch))
                .perform(typeText(driversearch), closeSoftKeyboard());

//check suggestion for 'sa' is displayed
        onView(withText("Sarah Meyer"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        onView(withText("Sarah Friedrich"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withText(selectDriver))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.fab)).perform(click());
    }



}

