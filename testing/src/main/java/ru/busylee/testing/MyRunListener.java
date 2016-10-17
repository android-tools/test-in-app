package ru.busylee.testing;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import ru.busylee.testing.model.TestDescription;
import ru.busylee.testing.storage.TestStorage;
import ru.busylee.testing.storage.TestStorageImpl;

/**
 * Created by busylee on 15.10.16.
 */

public class MyRunListener extends RunListener {

  private static final String TAG = "MyRunListener";
  private TestStorage testStorage;
  //ugly but ok for now
  private boolean testSuccess;


  @Override
  public void testStarted(Description description) throws Exception {
    super.testStarted(description);
    Log.d(TAG, "testStarted");
    testSuccess = true;
  }

  @Override
  public void testFinished(Description description) throws Exception {
    super.testFinished(description);
    Log.d(TAG, "testFinished");
    testStorage.store(fromDescription(description, testSuccess));
  }

  @Override
  public void testFailure(Failure failure) throws Exception {
    super.testFailure(failure);
    Log.d(TAG, "testFailure");
    testSuccess = false;
  }

  @Override
  public void testAssumptionFailure(Failure failure) {
    super.testAssumptionFailure(failure);
    Log.d(TAG, "testAssumptionFailure");
  }

  @Override
  public void testIgnored(Description description) throws Exception {
    super.testIgnored(description);
    Log.d(TAG, "testIgnored");
  }

  @Override
  public void testRunStarted(Description description) throws Exception {
    super.testRunStarted(description);
    Log.d(TAG, "testRunStarted");
    testStorage = TestStorageImpl.getInstance(InstrumentationRegistry.getTargetContext());
    testStorage.clear();
  }

  @Override
  public void testRunFinished(Result result) throws Exception {
    super.testRunFinished(result);
    Log.d(TAG, "testRunFinished");
    startTestResultsActivity();
  }

  private TestDescription fromDescription(Description description, boolean success) {
    TestDescription testDescription = new TestDescription();
    testDescription.name = description.getDisplayName();
    testDescription.result = success ? "sucess" : "failure";
    return testDescription;
  }

  private void startTestResultsActivity() {
    Intent intent = new Intent();
    Uri.Builder uri = new Uri.Builder()
            .scheme("testing")
            .authority("test.results");
    intent.setData(uri.build());
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Context context = InstrumentationRegistry.getContext();

    //just
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
    AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 2500, pendingIntent);
  }

}
