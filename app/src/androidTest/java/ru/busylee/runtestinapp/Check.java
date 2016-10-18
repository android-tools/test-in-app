package ru.busylee.runtestinapp;

import ru.busylee.runtestinapp.TestCheck;

/**
 * Created by busylee on 18.10.16.
 */

public class Check implements TestCheck {
  public String str = "Test classes installed";

  @Override
  public String checkString() {
    return str;
  }
}
