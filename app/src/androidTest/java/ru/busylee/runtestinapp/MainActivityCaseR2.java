package ru.busylee.runtestinapp;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ru.busylee.runtestinapp.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by busylee on 06.10.16.
 */

public class MainActivityCaseR2 {

  private static final String TAG = "MainActivityCaseR2";

  @Rule
  public ActivityTestRule<MainActivity> intentsTestRule = new ActivityTestRule<>(MainActivity.class);

  @Before
  public void before() {
    Log.d(TAG, "before");
  }

  @After
  public void after() {
    Log.d(TAG, "after");
  }

  @Test
  public void test() {
    Log.d(TAG, "test");
    onView(withText("Hello World")).check(matches(isDisplayed()));
  }

}
