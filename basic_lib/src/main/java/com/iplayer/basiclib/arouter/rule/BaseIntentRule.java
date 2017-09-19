package com.iplayer.basiclib.arouter.rule;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/25.
 * 返回Intent的路由规则的基类
 */

public abstract class BaseIntentRule <T> implements Rule<T, Intent> {

    private HashMap<String, Class<T>> mIntentRules;

    public BaseIntentRule() {
        mIntentRules = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void router(String pattern, Class<T> klass) {
        mIntentRules.put(pattern, klass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Intent invoke(Context ctx, String pattern) {
        Class<T> clazz = mIntentRules.get(pattern);
        if (clazz == null) { throwException(pattern);}
        return new Intent(ctx, clazz);
    }

    @Override
    public boolean resolveRule(String pattern) {
        return mIntentRules.get(pattern) != null;
    }

    /**
     * 当找不到路由规则时抛出异常
     * @param pattern 路由pattern
     */
    public abstract void throwException(String pattern);
}