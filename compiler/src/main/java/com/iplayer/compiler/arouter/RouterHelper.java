package com.iplayer.compiler.arouter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/7/25.
 */

public class RouterHelper {

    public static void install() {
        try {
            Class<?> klass = Class.forName(RouterConfig.PACKAGE_NAME + "." + RouterConfig.ROUTER_MANAGER);
            Method method = klass.getDeclaredMethod(RouterConfig.ROUTER_MANAGER_METHOD);
            method.invoke(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}