package com.iplayer.mine.ui;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by MarioStudio on 2016/3/13.
 */

public class ProgressTextUtils {

    public static String getProgressText(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        @SuppressLint("WrongConstant") double minute = calendar.get(Calendar.MINUTE);
        @SuppressLint("WrongConstant") double second = calendar.get(Calendar.SECOND);

        DecimalFormat format = new DecimalFormat("00");
        return format.format(minute) + ":" + format.format(second);
    }
}
