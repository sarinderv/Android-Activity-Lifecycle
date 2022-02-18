package com.example.android.testapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_THREAD_COUNT = "threadCount";
    private static final String STATE_RESTART_COUNT = "restartCount";
    private int currThreadCount;
    private int currRestartCount;
    private Runnable r;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText threadCounter = (EditText) findViewById(R.id.threadCounter);
        EditText restartCounter = (EditText) findViewById(R.id.restartCounter);

        if (savedInstanceState == null) {
            currThreadCount = currRestartCount = 0;
            Log.e("onCreate ------ ","MainActivity: creating Runnable");
            handler = new Handler();
            r = new Runnable() {
                public void run() {
                    Log.e("Thread ------ ","MainActivity: Runnable() currThreadCount="+ ++currThreadCount);
                    handler.postDelayed(this, 3000);
                    threadCounter.setText(String.valueOf(currThreadCount));
                    restartCounter.setText(String.valueOf(currRestartCount));
                }
            };
            handler.post(r);
        }
        else {
            currThreadCount = savedInstanceState.getInt(STATE_THREAD_COUNT);
            currRestartCount = savedInstanceState.getInt(STATE_RESTART_COUNT);
        }

        threadCounter.setText(String.valueOf(currThreadCount));
        restartCounter.setText(String.valueOf(currRestartCount));

        Log.e("onCreate ------ ", "MainActivity: onCreate() currThreadCount="+ currThreadCount);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart ------ ","MainActivity: onStart()");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume ------ ","MainActivity: onResume()");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause ------ ","MainActivity: onPause()");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop ------ ","MainActivity: onStop()");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy ------ ","MainActivity: onDestroy()");
        handler.removeCallbacks(r);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        currRestartCount++;
        Log.e("onRestart ------ ","MainActivity: onRestart()");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(STATE_THREAD_COUNT, currThreadCount);
        savedInstanceState.putInt(STATE_RESTART_COUNT, currRestartCount);
        Log.e("onSaveInstanceState -- ","MainActivity: onSaveInstanceState() currThreadCount="+ currThreadCount);
    }

    public void switchActivity(View view){
        Intent intent = new Intent(MainActivity.this, AnotherActivity.class);
        startActivity(intent);
    }

    public void showDialog(View view) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Simple Dialog")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    public void closeApp(View view) {
        finish();
    }
}
