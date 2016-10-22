package ru.busylee.runtestinapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by busylee on 22.10.16.
 */
// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        MainActivityCaseR.class
})
public class MainSuite {
}
