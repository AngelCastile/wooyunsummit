package com.wooyun.summit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent mIntent = new Intent();
                mIntent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(mIntent);
                finish();
            }
        }, 1000);
    }
}
