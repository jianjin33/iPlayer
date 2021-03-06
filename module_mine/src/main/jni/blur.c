/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_iplayer_mine_util_NativeHelper */

#ifndef _Included_com_iplayer_mine_util_NativeHelper
#define _Included_com_iplayer_mine_util_NativeHelper
#ifdef __cplusplus
extern "C" {
#endif

#include <android/log.h>
#include <android/bitmap.h>
#include "stackblur.h"

#define TAG "Native_Blur_Jni"
#define LOG_D(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__)

/*
 * Class:     com_iplayer_mine_util_NativeHelper
 * Method:    blurBitmap
 * Signature: (Ljava/lang/Object;I)V
 */
JNIEXPORT void JNICALL Java_com_iplayer_mine_util_NativeHelper_blurBitmap
        (JNIEnv *env, jclass obj, jobject bitmapIn, jint r) {

    AndroidBitmapInfo infoIn;
    void *pixels;

    // Get image info
    if (AndroidBitmap_getInfo(env, bitmapIn, &infoIn) != ANDROID_BITMAP_RESULT_SUCCESS) {
        LOG_D("AndroidBitmap_getInfo failed!");
        return;
    }

    // Check image
    if (infoIn.format != ANDROID_BITMAP_FORMAT_RGBA_8888 &&
        infoIn.format != ANDROID_BITMAP_FORMAT_RGB_565) {
        LOG_D("Only support ANDROID_BITMAP_FORMAT_RGBA_8888 and ANDROID_BITMAP_FORMAT_RGB_565");
        return;
    }

    // Lock all images
    if (AndroidBitmap_lockPixels(env, bitmapIn, &pixels) != ANDROID_BITMAP_RESULT_SUCCESS) {
        LOG_D("AndroidBitmap_lockPixels failed!");
        return;
    }
    // height width
    int h = infoIn.height;
    int w = infoIn.width;

    // Start
    if (infoIn.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        // 调用stackblur.c中的blur_ARGB_8888()或blur_RGB_565()
        pixels = blur_ARGB_8888((int *) pixels, w, h, r);
    } else if (infoIn.format == ANDROID_BITMAP_FORMAT_RGB_565) {
        pixels = blur_RGB_565((short *) pixels, w, h, r);
    }

    // End
    // Unlocks everything
    AndroidBitmap_unlockPixels(env, bitmapIn);
}

#ifdef __cplusplus
}
#endif
#endif