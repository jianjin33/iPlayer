package com.iplayer.main.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.auto.service.AutoService;
import com.iplayer.annotation.router.StaticRouter;
import com.iplayer.basiclib.arouter.rule.ActivityRule;
import com.iplayer.main.R;

@StaticRouter(ActivityRule.ACTIVITY_SCHEME + "module_main.test")
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
