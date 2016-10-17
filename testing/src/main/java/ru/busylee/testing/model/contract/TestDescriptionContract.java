package ru.busylee.testing.model.contract;

import android.content.ContentValues;
import android.database.Cursor;

import ru.busylee.testing.model.TestDescription;

/**
 * Created by busylee on 16.10.16.
 */

public class TestDescriptionContract {

  public static final String TABLE_NAME = "test_description";

  public static final String ID = "id";

  public static final String NAME_COLUMN = "name";
  public static final String RESULT_COLUMNT = "result";

  public static final String CREATE = "CREATE TABLE " +  TABLE_NAME + "(" +
          ID + " integer PRIMARY KEY AUTOINCREMENT," +
          NAME_COLUMN + " varchar(512) NOT NULL," +
          RESULT_COLUMNT + " varchar(32) NOT NULL" +
          ");";

  public static final String FIND_ALL = "SELECT * FROM " + TABLE_NAME;

  public static TestDescription fromCursor(Cursor cursor) {
    TestDescription testDescription = new TestDescription();
    testDescription.name = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
    testDescription.result = cursor.getString(cursor.getColumnIndex(RESULT_COLUMNT));
    return testDescription;
  }

  public static ContentValues toContentValues(TestDescription testDescription) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(NAME_COLUMN, testDescription.name);
    contentValues.put(RESULT_COLUMNT, testDescription.result);
    return contentValues;
  }

}
