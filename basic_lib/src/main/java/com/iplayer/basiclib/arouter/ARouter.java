package com.iplayer.basiclib.arouter;

import android.content.Context;

import com.iplayer.basiclib.arouter.rule.Rule;


/**
 * Created by Administrator on 2017/7/25.
 * step 1. 调用Router.router方法添加路由
 * step 2. 调用Router.invoke方法根据pattern调用路由
 */

public class ARouter {

    /**
     * 添加自定义路由规则
     * @param scheme 路由scheme
     * @param rule 路由规则
     * @return {@code ARouterInternal} Router真实调用类
     */
    public static ARouterInternal addRule(String scheme, Rule rule) {
        ARouterInternal router = ARouterInternal.get();
        router.addRule(scheme, rule);
        return router;
    }

    /**
     * 添加路由
     * @param pattern 路由uri
     * @param klass 路由class
     * @return {@code ARouterInternal} Router真实调用类
     */
    public static <T> ARouterInternal router(String pattern, Class<T> klass) {
        return ARouterInternal.get().router(pattern, klass);
    }

    /**
     * 路由调用
     * @param ctx Context
     * @param pattern 路由uri
     * @return {@code V} 返回对应的返回值
     */
    public static <V> V invoke(Context ctx, String pattern) {
        return ARouterInternal.get().invoke(ctx, pattern);
    }

    /**
     * 是否存在该路由
     * @param pattern
     * @return
     */
    public static boolean resolveRouter(String pattern) {
        return ARouterInternal.get().resolveRouter(pattern);
    }


}
