package com.iplayer.basiclib.http;

/**
 * Created by Administrator.
 */


import android.os.Message;
import rx.functions.Func1;


/**
 * 用来统一处理Http的resultCode,并将HttpResult返回给subscriber
 * 应该写在各个module中，由于大部分module都需要处理这种返回值，暂时先写在lib中，后期做相应更改。
 */
public class HttpResultFunc<T> implements Func1<Response, T> {

    @Override
    public T call(Response httpResult) {
        //如果返回的code是1111表示登录超时,更改用户的登录状态，跳转登录界面由于没有context，只能在相应界面跳转，以后再优化

        switch (httpResult.getCode()) {
            case "1111":
                Message msg = Message.obtain();
                msg.obj = httpResult.getMessage();
                break;
            default:

                break;
        }
        if (!httpResult.getCode().equals("0000")) {

        }
        // 应该返回的是httpResult中的getT()的数据类型，由于不知道具体的T是什么。这里这样返回也不出错，有待研究
        return (T) httpResult;
    }
}