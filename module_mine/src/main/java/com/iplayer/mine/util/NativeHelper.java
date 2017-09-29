package com.iplayer.mine.util;

/**
 * Created by Administrator 2017/5/2.
 */

public class NativeHelper {

    static {
        System.loadLibrary("blur_lib");
    }
    public static native void blurBitmap(Object bitmap, int r);
}
