package ru.busylee.runtestinapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ru.busylee.testing.MyInstrumentationHelper;
import ru.busylee.testing.ui.TestResultActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.start_test).setOnClickListener(this);
        findViewById(R.id.show_results).setOnClickListener(this);
        check();
    }

    private void check() {
        try {
            ClassLoader classLoader = getClassLoader();
            Class<?> aClass = classLoader.loadClass("ru.busylee.runtestinapp.Check");
            TestCheck tInt = (TestCheck) aClass.newInstance();
            Toast.makeText(this, tInt.checkString(), Toast.LENGTH_LONG).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (InstantiationException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_test:
                MyInstrumentationHelper.startInstrumentation(this);
                break;
            case R.id.show_results: {
                Intent intent = new Intent(this, TestResultActivity.class);
                startActivity(intent);
                break;
            }
        }

    }
}
