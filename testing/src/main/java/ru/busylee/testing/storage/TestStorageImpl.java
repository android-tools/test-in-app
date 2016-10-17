package ru.busylee.testing.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ru.busylee.testing.model.TestDescription;
import ru.busylee.testing.model.contract.TestDescriptionContract;

/**
 * Created by busylee on 16.10.16.
 */
public class TestStorageImpl implements TestStorage {

  private static TestStorageImpl INSTANCE;

  public static TestStorageImpl getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = new TestStorageImpl(context);
    }

    return INSTANCE;
  }

  private final TesSQLiteHelper liteHelper;

  private TestStorageImpl(Context context) {
    liteHelper = new TesSQLiteHelper(context);
  }

  @Override
  public void clear() {
    liteHelper.getWritableDatabase().delete(TestDescriptionContract.TABLE_NAME, null, null);
  }

  @Override
  public void store(TestDescription testDescription) {
    liteHelper.getWritableDatabase().insert(TestDescriptionContract.TABLE_NAME, null,
            TestDescriptionContract.toContentValues(testDescription));
  }

  @Override
  public List<TestDescription> getLastTestResults() {
    List<TestDescription> testDescriptionList = new ArrayList<>();
    Cursor cursor = liteHelper.getReadableDatabase().rawQuery(TestDescriptionContract.FIND_ALL, null);
    while (cursor.moveToNext()) {
      testDescriptionList.add(TestDescriptionContract.fromCursor(cursor));
    }
    return testDescriptionList;
  }

  private class TesSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "test_cases_results";
    private static final int DB_VERSION = 1;

    TesSQLiteHelper(Context context) {
      super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
      sqLiteDatabase.execSQL(TestDescriptionContract.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
      //nothing yet
    }

  }

}
