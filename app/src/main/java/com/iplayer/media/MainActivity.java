package com.iplayer.media;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;

import com.iplayer.basiclib.arouter.ARouter;
import com.iplayer.basiclib.arouter.rule.ActivityRule;
import com.iplayer.basiclib.util.LogUtils;
import com.iplayer.componentlib.router.Router;
import com.iplayer.componentservice.main.MainService;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment();
    }
    private void showFragment() {
        if (fragment != null) {
            ft = getSupportFragmentManager().beginTransaction();
            ft.remove(fragment).commit();
            fragment = null;
        }
        Router router = Router.getInstance();
        if (router.getService(MainService.class.getSimpleName()) != null) {
            MainService service = (MainService) router.getService(MainService.class.getSimpleName());
            fragment = service.getMainFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.tab_content, fragment).commitAllowingStateLoss();
        }
    }

    public void start(View view){
        if (ARouter.resolveRouter(ActivityRule.ACTIVITY_SCHEME + "module_main.test")) {
            Intent intent = ARouter.invoke(this, ActivityRule.ACTIVITY_SCHEME + "module_main.test");
            intent.putExtra("test","123456");
            startActivity(intent);
        }
    }

    public void start2(View view){
        if (ARouter.resolveRouter(ActivityRule.ACTIVITY_SCHEME + "com.iplayer.main.ui.Test2Activity")) {
            Intent intent = ARouter.invoke(this, ActivityRule.ACTIVITY_SCHEME + "com.iplayer.main.ui.Test2Activity");
            intent.putExtra("test","123456");
            startActivity(intent);
        }
    }

    public void start3(View view){
//        playNetVideo("rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov");
//        playNetVideo("http://forum.ea3w.com/coll_ea3w/attach/2008_10/12237832415.3gp");
        playNetVideo("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
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
        startActivity(intent);

//        Intent it = new Intent(Intent.ACTION_VIEW);
//        it.setDataAndType(Uri.parse("/sdcard/1122.mp4"), "video/mp4");
//        startActivity(it);
    }

    private void playNetVideo(String url){
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
        mediaIntent.setDataAndType(Uri.parse(url), mimeType);
        startActivity(mediaIntent);
    }
    private void playNetVideo1(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String type = "video/*";

//        Uri uri = Uri.parse("http://forum.ea3w.com/coll_ea3w/attach/2008_10/12237832415.3gp");
        Uri uri = Uri.parse(url);
        intent.setDataAndType(uri, type);
        startActivity(intent);
    }

}
