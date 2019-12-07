package com.sd.lib.eventact;

import android.app.Activity;

import com.sd.lib.eventact.callback.ActivityEventCallback;

import java.lang.ref.WeakReference;

public abstract class BaseActivityEventObserver<T extends ActivityEventCallback>
{
    private final WeakReference<Activity> mActivity;
    private final Class<T> mCallbackClass;

    public BaseActivityEventObserver(Activity activity, Class<T> callbackClass)
    {
        if (activity == null)
            throw new IllegalArgumentException("activity is null");

        if (callbackClass == null)
            throw new IllegalArgumentException("callbackClass is null");

        mActivity = new WeakReference<>(activity);
        mCallbackClass = callbackClass;

        if (!mCallbackClass.isAssignableFrom(getClass()))
            throw new RuntimeException(mCallbackClass + " is not assignable from " + getClass());
    }

    public final Activity getActivity()
    {
        return mActivity.get();
    }

    /**
     * 注册
     *
     * @return
     */
    public final boolean register()
    {
        return ActivityEventManager.getInstance().register(getActivity(), mCallbackClass, (T) this);
    }

    /**
     * 取消注册
     */
    public final void unregister()
    {
        ActivityEventManager.getInstance().unregister(getActivity(), mCallbackClass, (T) this);
    }
}
