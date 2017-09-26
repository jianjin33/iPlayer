package com.iplayer.mine.presenter;

import android.content.Intent;

import com.iplayer.basiclib.http.HttpSubscriber;
import com.iplayer.basiclib.http.Response;

/**
 * Created by Administrator on 2017/9/26.
 */

public interface ILogin {

    interface ILoginPresenter {
        void login(String account, String pwd);
    }

    interface ILoginView {
        /**
         * 显示登录按钮
         */
        void showLoginBtn();

        /***
         * 显示提示信息
         */
        void showToast(String msg);

        /***
         * 启动Activity
         */
        void launcherAct(Intent intent);

        /***
         * 结束
         */
        void finishAct();
    }

    interface IModel {
        void login(HttpSubscriber httpSubscriber, String account, String pwd);
    }

}


