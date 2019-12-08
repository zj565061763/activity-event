package com.sd.lib.eventact.observer;

import android.app.Activity;

public interface ActivityEventObserver
{
    /**
     * 注册
     *
     * @param activity 要监听的对象
     * @return true-观注册成功；false-注册失败或者已经注册到该Activity对象
     */
    boolean register(Activity activity);

    /**
     * 取消注册
     */
    void unregister();
}
