package com.iplayer.media.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.iplayer.basiclib.base.BaseActivity;
import com.iplayer.media.R;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                activityManagerUtils.finishActivity(WelcomeActivity.this);
            }
        },2000);

    }
}
