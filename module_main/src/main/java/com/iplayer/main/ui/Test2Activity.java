package com.iplayer.main.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.iplayer.annotation.router.AutoRouter;
import com.iplayer.main.R;

@AutoRouter()
public class Test2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
