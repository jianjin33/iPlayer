package com.iplayer.media;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.iplayer.basiclib.arouter.ARouter;
import com.iplayer.basiclib.arouter.rule.ActivityRule;
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
            intent.putExtra("order","123456");
            startActivity(intent);
        }
    }

    public void start2(View view){
        if (ARouter.resolveRouter(ActivityRule.ACTIVITY_SCHEME + "com.iplayer.main.ui.Test2Activity")) {
            Intent intent = ARouter.invoke(this, ActivityRule.ACTIVITY_SCHEME + "com.iplayer.main.ui.Test2Activity");
            intent.putExtra("order","123456");
            startActivity(intent);
        }
    }

}
