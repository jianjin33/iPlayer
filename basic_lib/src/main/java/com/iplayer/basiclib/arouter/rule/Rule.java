package com.iplayer.basiclib.arouter.rule;

import android.content.Context;

/**
 * Created by Administrator on 2017/7/25.
 * 路由规则接口
 * T是我们注册的路由类型, 例如前面使用的Activity类型
 * V是invoke方法的返回值类型, 例如Intent类型.或自定义的代码
 */

public interface Rule<T,V> {
    /**
     * 添加路由
     * @param  pattern 路由uri
     * @param clazz 路由class
     */
    void router(String pattern, Class<T> clazz);

    /**
     * 路由调用
     * @param context context
     * @param pattern 路由uri
     * @return 返回对应V的返回值
     */
    V invoke(Context context, String pattern);

    /**
     * 查看是否存在pattern对应的路由
     * @param pattern
     * @return
     */
    boolean resolveRule(String pattern);
}
