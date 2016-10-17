package ru.busylee.runtestinapp;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by busylee on 06.10.16.
 */
public class MainActivityCase {

  @Rule
  public ActivityTestRule<MainActivity> intentsTestRule = new ActivityTestRule<>(MainActivity.class);

  @Test
  public void test() {
    Log.d("MainActivityCase", "TEST:TEST");
    onView(withText("Hello World!")).check(matches(isDisplayed()));
  }

}
