package com.iplayer.mine.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.Display;

import com.iplayer.basiclib.base.BasePresenter;
import com.iplayer.basiclib.core.Constants;
import com.iplayer.basiclib.http.HttpCallback;
import com.iplayer.basiclib.http.HttpSubscriber;
import com.iplayer.basiclib.http.Response;
import com.iplayer.basiclib.util.StringUtils;
import com.iplayer.mine.api.MineResponse;
import com.iplayer.mine.model.LoginModel;
import com.iplayer.mine.presenter.ILogin;
import com.iplayer.mine.util.NativeHelper;

/**
 * Created by Administrator on 2017/9/26.
 */

public class LoginPresenter extends BasePresenter implements ILogin.ILoginPresenter {

    private final LoginModel loginModel;
    private ILogin.ILoginView  loginView;

    public LoginPresenter(Context context, ILogin.ILoginView loginView) {
        super(context);
        loginModel = new LoginModel(context);
        this.loginView = loginView;
    }

    @Override
    public void login(String account, String pwd) {

        if (StringUtils.isSpace(account) || StringUtils.isSpace(pwd))
            return;


        HttpSubscriber httpSubscriber = new HttpSubscriber(new HttpCallback<MineResponse>() {
            @Override
            public void onNext(MineResponse response) {
                loginView.showToast(response.getMessage());
                if (StringUtils.equals(response.getCode(), Constants.REQUEST_SUCCESS)){
                    loginView.finishAct();
                }else {
                    loginView.showLoginBtn();
                }
            }

            @Override
            public void onError(Throwable e) {
                loginView.showLoginBtn();
            }
        });

        loginModel.login(httpSubscriber, account, pwd);

    }


}
