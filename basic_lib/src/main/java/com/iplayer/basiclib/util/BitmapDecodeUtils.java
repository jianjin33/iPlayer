package com.iplayer.basiclib.util;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.Display;

/**
 * Created by Administrator on 2017/9/26.
 */

public class BitmapDecodeUtils {
    public static Bitmap decodeBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight ) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // 计算inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // 根据inSampleSize压缩图片
        options.inJustDecodeBounds = false;

        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeResource(res, resId, options);
    }

    //reqWidth和reqHeight是需要显示的Imageview的width和height
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // height和width图片长宽的像素
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}