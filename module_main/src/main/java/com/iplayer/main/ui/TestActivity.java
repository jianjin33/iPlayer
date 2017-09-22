package com.iplayer.main.ui;

import android.os.Bundle;

import com.iplayer.annotation.router.StaticRouter;
import com.iplayer.basiclib.arouter.rule.ActivityRule;
import com.iplayer.basiclib.base.BaseActivity;
import com.iplayer.main.R;

@StaticRouter(ActivityRule.ACTIVITY_SCHEME + "module_main.test")
public class TestActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
