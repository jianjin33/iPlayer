package com.iplayer.mine;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Toast;

import com.iplayer.basiclib.base.BaseActivity;
import com.iplayer.basiclib.http.HttpCallback;
import com.iplayer.basiclib.http.HttpSubscriber;
import com.iplayer.basiclib.http.Response;
import com.iplayer.basiclib.util.StringUtils;
import com.iplayer.mine.api.HttpMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.mine_tv_account)
    TextInputEditText mineTvAccount;
    @BindView(R.id.mine_tv_pwd)
    TextInputEditText mineTvPwd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.mine_bt_login, R.id.mine_bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_bt_login:
                String account = mineTvAccount.getText().toString().trim();
                String pwd = mineTvPwd.getText().toString().trim();

                if (StringUtils.isSpace(account) || StringUtils.isSpace(pwd))
                    return;

                HttpMethod.getInstance().login(new HttpSubscriber(new HttpCallback<Response>() {
                    @Override
                    public void onNext(Response response) {
                        Toast.makeText(LoginActivity.this, response.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }), account, pwd);


                break;
            case R.id.mine_bt_register:

                String account1 = mineTvAccount.getText().toString().trim();
                String pwd1 = mineTvPwd.getText().toString().trim();

                if (StringUtils.isSpace(account1) || StringUtils.isSpace(pwd1))
                    return;


                HttpMethod.getInstance().register(new HttpSubscriber(new HttpCallback<Response>() {
                    @Override
                    public void onNext(Response response) {
                        Toast.makeText(LoginActivity.this, response.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }), account1, pwd1);

                break;
        }
    }
}
