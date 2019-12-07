package com.sd.lib.eventact;

import android.app.Activity;

import com.sd.lib.eventact.callback.ActivityEventCallback;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivityEventObserver<T extends ActivityEventCallback>
{
    private final WeakReference<Activity> mActivity;
    private final Class<T> mCallbackClass;

    public BaseActivityEventObserver(Activity activity)
    {
        if (activity == null)
            throw new IllegalArgumentException("activity is null");
        mActivity = new WeakReference<>(activity);
        mCallbackClass = (Class<T>) getGenericType(getClass());

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

    private static Type getGenericType(Class<?> clazz)
    {
        final ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        final Type[] types = parameterizedType.getActualTypeArguments();
        return types[0];
    }
}
