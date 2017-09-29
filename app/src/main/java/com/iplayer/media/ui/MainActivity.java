package com.iplayer.media.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.iplayer.basiclib.arouter.ARouter;
import com.iplayer.basiclib.arouter.rule.ActivityRule;
import com.iplayer.basiclib.base.BaseActivity;
import com.iplayer.componentlib.router.Router;
import com.iplayer.componentservice.main.MainService;
import com.iplayer.componentservice.main.MineService;
import com.iplayer.media.R;
import com.iplayer.media.R2;
import com.wakehao.bar.BottomNavigationBar;
import com.wakehao.bar.BottomNavigationItemWithDot;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R2.id.main_container)
    FrameLayout mainContainer;
    @BindView(R2.id.bar)
    BottomNavigationBar bar;
    private Fragment fragment;
    private FragmentTransaction ft;

    int[] navigationBarColorRes = new int[]{
            R.color.common_greenDark,
            R.color.common_colorPrimaryDark,
            R.color.common_colorAccentDark,
            R.color.common_blue_lightDark
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getWindow().setBackgroundDrawableResource(android.R.color.white);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setNavigationBarColor(getResources().getColor(navigationBarColorRes[0]));

        // 可动态改变item的标题
        bar.showNum(2, 100);
        bar.showNum(3, -2);

        bar.setOnNavigationItemSelectedListener(new BottomNavigationBar.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull BottomNavigationItemWithDot item, int selectedPosition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    getWindow().setNavigationBarColor(getResources().getColor(navigationBarColorRes[selectedPosition]));
                if (selectedPosition == 3) {
                    if (ARouter.resolveRouter(ActivityRule.ACTIVITY_SCHEME + "com.iplayer.mine.ui.LoginActivity")) {
                        Intent intent = ARouter.invoke(MainActivity.this, ActivityRule.ACTIVITY_SCHEME + "com.iplayer.mine.ui.LoginActivity");
                        startActivityForResult(intent, 1);
                    }


                    //用户切换item
                    bar.setItemSelected(3, true);
                    //返回false表示不响应点击事件
                    return true;
                } else return true;
            }

            @Override
            public void onNavigationItemSelectedAgain(@NonNull BottomNavigationItemWithDot item, int reSelectedPosition) {
                //连续点击了同一个reSelectedPosition位置的item
            }
        });

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
            ft.add(R.id.main_container, fragment).commitAllowingStateLoss();
        }
    }

}
