package com.iplayer.media;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iplayer.basiclib.util.UIUtils;
import com.iplayer.basiclib.view.WrapLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        WrapLayout wrapLayout = findViewById(R.id.wrap_layout);
        TextView itemOne = findViewById(R.id.item_one);
        TextView itemTwo = findViewById(R.id.item_two);
        TextView itemThree = findViewById(R.id.item_three);

        String[] array = new String[]{
                "量：我是123456",
                "量：weasdfsadfasdfasd",
                "量：盛大富翁阿德是打发斯蒂芬",
                "量：多发点",
                "量：大发送的发送"
        };
        int[] colors = new int[]{
                android.R.color.black,
                android.R.color.holo_red_dark,
                android.R.color.holo_green_dark,
        };

        itemOne.setText(1 + array[new Random().nextInt(4)]);
        itemTwo.setText(1 + array[new Random().nextInt(4)]);
        itemThree.setText(1 + array[new Random().nextInt(4)]);

        SpannableStringBuilder builder = new SpannableStringBuilder(itemOne.getText().toString());
        ForegroundColorSpan graySpan = new ForegroundColorSpan(Color.GRAY);
        ForegroundColorSpan randomSpan = new ForegroundColorSpan(getColor(colors[new Random().nextInt(2)]));

        builder.setSpan(graySpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(randomSpan,3,itemOne.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        itemOne.setText(builder);
        itemTwo.setText(builder);
        itemThree.setText(builder);



        /*List<View> list = new ArrayList<>();
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = UIUtils.dp2px(20);
        for (int i = 0; i < 5; i++) {
            TextView textView = new TextView(this);
            textView.setText(i + array[new Random().nextInt(4)]);

            SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText().toString());
            ForegroundColorSpan graySpan = new ForegroundColorSpan(Color.GRAY);
            ForegroundColorSpan randomSpan = new ForegroundColorSpan(getColor(colors[new Random().nextInt(2)]));

            builder.setSpan(graySpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(randomSpan,3,textView.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(builder);

            list.add(textView);
        }

        wrapLayout.setView(list,lp);*/
    }

}
