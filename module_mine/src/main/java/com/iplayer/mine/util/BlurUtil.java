package com.iplayer.mine.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.Display;

/**
 * Created by Administrator on 2017/9/29.
 * 高斯模糊处理图片
 */

public class BlurUtil {

    public static Bitmap blurNatively(Bitmap original, int radius, boolean canReuseInBitmap) {
        if (radius < 1) {
            return null;
        }

        Bitmap bitmap = buildBitmap(original, canReuseInBitmap);
        if (radius == 1) {
            return bitmap;
        }
        //Jni
        NativeHelper.blurBitmap(bitmap, radius);

        return (bitmap);
    }


    private static Bitmap buildBitmap(Bitmap original, boolean canReuseInBitmap) {
        // First we should check the original
        if (original == null) {
            return null;
        }

        Bitmap.Config config = original.getConfig();
        if (config != Bitmap.Config.ARGB_8888 && config != Bitmap.Config.RGB_565) {
            throw new RuntimeException("Blur bitmap only supported Bitmap.Config.ARGB_8888 and Bitmap.Config.RGB_565.");
        }
        // If can reuse in bitmap return this or copy
        Bitmap rBitmap;
        if (canReuseInBitmap) {
            rBitmap = original;
        } else {
            rBitmap = original.copy(config, true);
        }
        return (rBitmap);
    }

}
