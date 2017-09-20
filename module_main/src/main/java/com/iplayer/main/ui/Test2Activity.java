package com.iplayer.main.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.iplayer.annotation.router.AutoRouter;
import com.iplayer.main.R;
import com.iplayer.main.R2;

import butterknife.BindView;
import butterknife.ButterKnife;


@AutoRouter()
public class Test2Activity extends AppCompatActivity {

    @BindView(R2.id.test_text)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);
        text.setText("hahah");
    }
}
