package com.iplayer.mine.model;

import android.content.Context;

import com.iplayer.basiclib.base.BaseModel;
import com.iplayer.basiclib.http.HttpSubscriber;
import com.iplayer.basiclib.http.Response;
import com.iplayer.mine.api.HttpMethod;
import com.iplayer.mine.presenter.ILogin;

/**
 * Created by Administrator on 2017/9/26.
 */

public class LoginModel extends BaseModel implements ILogin.IModel {
    public LoginModel(Context context) {
        super(context);
    }

    public void login(HttpSubscriber subscriber, String account, String pwd) {
        HttpMethod.getInstance().login(subscriber,getActivityLifecycleProvider(), account, pwd);
    }

}
