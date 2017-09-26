package com.iplayer.mine.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iplayer.basiclib.base.BaseActivity;
import com.iplayer.basiclib.util.StringUtils;
import com.iplayer.basiclib.view.CircularAnim;
import com.iplayer.mine.R;
import com.iplayer.mine.R2;
import com.iplayer.mine.presenter.ILogin;
import com.iplayer.mine.presenter.impl.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this, this);
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

//        Bitmap bg = BitmapFactory.decodeResource(getResources(),R.mipmap.mine_login_bg);
//        bg.setConfig(Con);
    }

    @OnClick({R.id.mine_bt_login, R.id.mine_bt_forget_pwd, R.id.mine_bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_bt_login:
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
                                presenter.login(account,pwd);
                            }
                        });

                break;
            case R.id.mine_bt_forget_pwd:
                break;
            case R.id.mine_bt_register:

                break;
        }
    }

    @Override
    public void showLoginBtn() {
        CircularAnim.show(mineBtLogin).go();
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
        this.finish();
    }
}
