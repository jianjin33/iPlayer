package com.iplayer.media;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.webkit.MimeTypeMap;

import com.iplayer.basiclib.arouter.ARouter;
import com.iplayer.basiclib.arouter.rule.ActivityRule;
import com.iplayer.basiclib.view.ExplosionField;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.iplayer.media", appContext.getPackageName());
    }



    public void start(View view){

//        final ExplosionField explosionField = ExplosionField.attach2Window(this);
//        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                explosionField.explode(view);
//            }
//        });
//
//        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                explosionField.explode(view);
//            }
//        });
//


        if (ARouter.resolveRouter(ActivityRule.ACTIVITY_SCHEME + "module_main.test")) {
//            Intent intent = ARouter.invoke(this, ActivityRule.ACTIVITY_SCHEME + "module_main.test");
//            intent.putExtra("test","123456");
//            startActivity(intent);
        }
    }

    public void start2(View view){
//        startActivity(new Intent(this,Main2Activity.class));
//        if (ARouter.resolveRouter(ActivityRule.ACTIVITY_SCHEME + "com.iplayer.main.ui.Test2Activity")) {
//            Intent intent = ARouter.invoke(this, ActivityRule.ACTIVITY_SCHEME + "com.iplayer.main.ui.Test2Activity");
//            intent.putExtra("test","123456");
//            startActivity(intent);
//        }
    }

    public void start3(View view){
//        playNetVideo("rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov");
//        playNetVideo("http://forum.ea3w.com/coll_ea3w/attach/2008_10/12237832415.3gp");
//        playNetVideo("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        playNetVideo("http://olq04epn4.bkt.clouddn.com/RxAndroid%E5%BC%80%E5%8F%91%E8%AE%B2%E8%A7%A3%E7%AC%AC%E4%BA%8C%E9%9B%86.mp4");
//        playNetVideo("http://mov.bn.netease.com/open-movie/nos/flv/2016/03/10/SBGSB5P41_sd.flv");
    }

    private void playVideo(String videoPath){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String strend="";
        if(videoPath.toLowerCase().endsWith(".mp4")){
            strend="mp4";
        }
        else if(videoPath.toLowerCase().endsWith(".3gp")){
            strend="3gp";
        }
        else if(videoPath.toLowerCase().endsWith(".mov")){
            strend="mov";
        }
        else if(videoPath.toLowerCase().endsWith(".wmv")){
            strend="wmv";
        }

        intent.setDataAndType(Uri.parse(videoPath), "video/"+strend);
//        startActivity(intent);

//        Intent it = new Intent(Intent.ACTION_VIEW);
//        it.setDataAndType(Uri.parse("/sdcard/1122.mp4"), "video/mp4");
//        startActivity(it);
    }

    private void playNetVideo(String url){
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
        mediaIntent.setDataAndType(Uri.parse(url), mimeType);
//        startActivity(mediaIntent);
    }
    private void playNetVideo1(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type = "video/*";
//        Uri uri = Uri.parse("http://forum.ea3w.com/coll_ea3w/attach/2008_10/12237832415.3gp");
        Uri uri = Uri.parse(url);
        intent.setDataAndType(uri, type);
//        startActivity(intent);
    }
}
