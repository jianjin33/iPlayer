package com.iplayer.basiclib.arouter;

import android.content.Context;


import com.iplayer.basiclib.arouter.exception.NotRouteException;
import com.iplayer.basiclib.arouter.rule.ActivityRule;
import com.iplayer.basiclib.arouter.rule.ReceiverRule;
import com.iplayer.basiclib.arouter.rule.Rule;
import com.iplayer.basiclib.arouter.rule.ServiceRule;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Administrator on 2017/7/25.
 */

public class ARouterInternal {
    private static ARouterInternal sInstance;

    /** scheme->路由规则 */
    private HashMap<String, Rule> mRules;

    private ARouterInternal() {
        mRules = new HashMap<>();
        initDefaultRouter();
    }

    /**
     * 添加默认的Activity，Service，Receiver路由
     */
    private void initDefaultRouter() {
        addRule(ActivityRule.ACTIVITY_SCHEME, new ActivityRule());
        addRule(ServiceRule.SERVICE_SCHEME, new ServiceRule());
        addRule(ReceiverRule.RECEIVER_SCHEME, new ReceiverRule());
    }

    /*package */ static ARouterInternal get() {
        if (sInstance == null) {
            synchronized (ARouterInternal.class) {
                if (sInstance == null) {
                    sInstance = new ARouterInternal();
                }
            }
        }

        return sInstance;
    }

    /**
     * 添加自定义路由规则
     * @param scheme 路由scheme
     * @param rule 路由规则
     * @return {@code ARouterInternal} Router真实调用类
     */
    public final ARouterInternal addRule(String scheme, Rule rule) {
        mRules.put(scheme, rule);
        return this;
    }

    private <T, V> Rule<T, V> getRule(String pattern) {
        HashMap<String, Rule> rules = mRules;
        Set<String> keySet = rules.keySet();
        Rule<T, V> rule = null;
        for (String scheme : keySet) {
            if (pattern.startsWith(scheme)) {
                rule = rules.get(scheme);
                break;
            }
        }

        return rule;
    }

    /**
     * 添加路由
     * @param pattern 路由uri
     * @param clazz 路由class
     * @return {@code ARouterInternal} Router真实调用类
     */
    public final <T> ARouterInternal router(String pattern, Class<T> clazz) {
        Rule<T, ?> rule = getRule(pattern);
        if (rule == null) {
            throw new NotRouteException("unknown", pattern);
        }

        rule.router(pattern, clazz);
        return this;
    }

    /**
     * 路由调用
     * @param ctx Context
     * @param pattern 路由uri
     * @return {@code V} 返回对应的返回值
     */
     final <V> V invoke(Context ctx, String pattern) {
        Rule<?, V> rule = getRule(pattern);
        if (rule == null) {
            throw new NotRouteException("unknown", pattern);
        }

        return rule.invoke(ctx, pattern);
    }

    /**
     * 是否存在该路由
     * @param pattern
     * @return
     */
    final boolean resolveRouter(String pattern) {
        Rule<?, ?> rule = getRule(pattern);
        return rule != null && rule.resolveRule(pattern);
    }
}
