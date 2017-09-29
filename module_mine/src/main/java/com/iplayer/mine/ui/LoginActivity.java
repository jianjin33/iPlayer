package com.iplayer.mine.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.auto.service.AutoService;
import com.iplayer.annotation.router.AutoRouter;
import com.iplayer.basiclib.base.BaseActivity;
import com.iplayer.basiclib.util.BitmapDecodeUtils;
import com.iplayer.basiclib.util.StringUtils;
import com.iplayer.basiclib.util.UIUtils;
import com.iplayer.basiclib.view.CircularAnim;
import com.iplayer.mine.R;
import com.iplayer.mine.R2;
import com.iplayer.mine.presenter.ILogin;
import com.iplayer.mine.presenter.impl.LoginPresenter;
import com.iplayer.mine.util.BlurUtil;
import com.iplayer.mine.util.NativeHelper;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@AutoRouter()
public class LoginActivity extends BaseActivity implements ILogin.ILoginView {

    @BindView(R2.id.mine_tv_account)
    TextInputEditText mineTvAccount;
    @BindView(R2.id.mine_tv_pwd)
    TextInputEditText mineTvPwd;
    @BindView(R2.id.mine_login)
    ConstraintLayout mineLogin;
    @BindView(R2.id.mine_bt_login)
    Button mineBtLogin;
    @BindView(R2.id.mine_login_progress)
    ProgressBar mProgressBar;
    private ILogin.ILoginPresenter presenter;
    private Bitmap bitmap;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this, this);
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        bitmap = BitmapDecodeUtils.decodeBitmapFromResource(getResources(), R.mipmap.mine_login_bg,
                UIUtils.getScreenWidth(), UIUtils.getScreenHeight());

        mineLogin.setBackground(new BitmapDrawable(bitmap));
    }

    @OnClick({R2.id.mine_bt_login, R2.id.mine_bt_forget_pwd, R2.id.mine_bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R2.id.mine_bt_login:
                final String account = mineTvAccount.getText().toString().trim();
                final String pwd = mineTvPwd.getText().toString().trim();
                if (StringUtils.isSpace(account)
                        && StringUtils.isSpace(pwd)) {
                    showToast(getString(R.string.please_input_account_and_pwd));
                    return;
                }

                // 登录按钮执行水波纹缩小动画
                CircularAnim.hide(mineBtLogin)
                        .endRadius(mProgressBar.getHeight() / 2)
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                mProgressBar.setVisibility(View.VISIBLE);
                                presenter.login(account, pwd);
                            }
                        });

                break;
            case R2.id.mine_bt_forget_pwd:
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        final Bitmap ret = BlurUtil.blurNatively(scaleBitmap(getScreenBitmap()), 20, true);
                        runOnUiThread(new Runnable() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void run() {
                                mineLogin.setBackground(new BitmapDrawable(ret));
                            }
                        });
                    }
                };
                thread.start();


                break;
            case R2.id.mine_bt_register:
                break;
        }
    }

    @Override
    public void showLoginBtn() {
        CircularAnim.show(mineBtLogin).go();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launcherAct(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void finishAct() {
        activityManagerUtils.finishActivity(this);
    }


    /**
     * 截屏
     */
    private Bitmap getScreenBitmap() {
        View viewRoot = getWindow().getDecorView().getRootView();
        viewRoot.setDrawingCacheEnabled(true);
        Bitmap screenShotAsBitmap = Bitmap.createBitmap(viewRoot.getDrawingCache());
        viewRoot.setDrawingCacheEnabled(false);
        return screenShotAsBitmap;
    }


    /**
     * 缩放bitmap
     *
     * @param bitmap
     * @return
     */
    private Bitmap scaleBitmap(Bitmap bitmap) {
        // 获得图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 设置想要的大小
        /*Display display = ((Activity)mContext).getWindowManager().getDefaultDisplay();
        int newWidth = display.getWidth();
        int newHeight = display.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        */
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
//        matrix.postScale(scaleWidth, scaleHeight);
        // 实现模糊效果之前，这里可对bitmap进行更大缩放，减少像素点还可提高性能
        float scaleFactor = 10;
        float scale = 1f / scaleFactor;
        matrix.postScale(scale, scale);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        bitmap.recycle();
        return newbm;
    }

}
