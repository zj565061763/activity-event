package com.sd.lib.eventact.observer;

import android.app.Activity;

public interface ActivityEventObserver
{
    /**
     * 注册
     *
     * @param activity 要监听的对象
     * @return
     */
    boolean register(Activity activity);

    /**
     * 取消注册
     */
    void unregister();
}
