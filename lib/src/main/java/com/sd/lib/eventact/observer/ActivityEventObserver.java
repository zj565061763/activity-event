package com.sd.lib.eventact.observer;

public interface ActivityEventObserver
{
    /**
     * 注册
     *
     * @return
     */
    boolean register();

    /**
     * 取消注册
     */
    void unregister();
}
