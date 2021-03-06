package ru.busylee.runtestinapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ru.busylee.testing.ui.TestResultActivity;
import ru.busylee.testing.ui.TestRunActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
                startActivity(new Intent(this, TestRunActivity.class));
                break;
            case R.id.show_results: {
                startActivity(new Intent(this, TestResultActivity.class));
                break;
            }
        }

    }
}
