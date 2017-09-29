package com.iplayer.media.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.iplayer.basiclib.base.BaseActivity;
import com.iplayer.componentlib.router.Router;
import com.iplayer.componentservice.main.MainService;
import com.iplayer.media.R;
import com.wakehao.bar.BottomNavigationBar;
import com.wakehao.bar.BottomNavigationItemWithDot;

public class MainActivity extends BaseActivity {

    private Fragment fragment;
    private FragmentTransaction ft;
    private BottomNavigationBar bar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setBackgroundDrawableResource(android.R.color.white);


        bar = (BottomNavigationBar) findViewById(R.id.bar);
        //可动态改变item的标题
//        bar.setTitle(0,"home(99)");
        bar.showNum(0,80);
        bar.showNum(1,100);
        bar.showNum(2,-2);
        bar.disMissNum(3);

        bar.setOnNavigationItemSelectedListener(new BottomNavigationBar.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull BottomNavigationItemWithDot item, int selectedPosition) {
                if(selectedPosition==3){

//                    startActivityForResult(new Intent(MainActivity.this,LoginActivity.class),1);
                    //用户切换item
                    bar.setItemSelected(3,true);
                    //返回false表示不响应点击事件
                    return false;
                }
                else return true;
            }

            @Override
            public void onNavigationItemSelectedAgain(@NonNull BottomNavigationItemWithDot item, int reSelectedPosition) {

                //连续点击了同一个reSelectedPosition位置的item
            }
        });


        showFragment();
    }
    private void showFragment() {
//        if (fragment != null) {
//            ft = getSupportFragmentManager().beginTransaction();
//            ft.remove(fragment).commit();
//            fragment = null;
//        }
//        Router router = Router.getInstance();
//        if (router.getService(MainService.class.getSimpleName()) != null) {
//            MainService service = (MainService) router.getService(MainService.class.getSimpleName());
//            fragment = service.getMainFragment();
//            ft = getSupportFragmentManager().beginTransaction();
//            ft.add(R.id.main_container, fragment).commitAllowingStateLoss();
//        }
    }

}
