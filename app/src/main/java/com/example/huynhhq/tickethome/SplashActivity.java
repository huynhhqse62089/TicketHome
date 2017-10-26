package com.example.huynhhq.tickethome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.example.huynhhq.tickethome.Data.DBManager;
import com.example.huynhhq.tickethome.model.User;

/**
 * Created by HuynhHQ on 10/17/2017.
 */

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final DBManager dbManager = new DBManager(this);
        user  = dbManager.getUser();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.getProgressDrawable().setColorFilter(
                Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);

        final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 100; i++) {
                        progressBar.setProgress(i);
                        sleep(20);
                    }
                } catch (Exception se) {
                    se.printStackTrace();
                } finally {
                    if(user != null){
                        Intent intent = new Intent(getApplicationContext(), ChooseLocationActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };
        thread.start();
    }
}
