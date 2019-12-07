package com.sd.lib.eventact;

import android.app.Activity;

import com.sd.lib.eventact.callback.ActivityEventCallback;
import com.sd.lib.eventact.observer.ActivityEventObserver;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseEventObserver<T extends ActivityEventCallback> implements ActivityEventObserver
{
    private final WeakReference<Activity> mActivity;
    private final Class<T> mCallbackClass;

    public BaseEventObserver(Activity activity)
    {
        if (activity == null)
            throw new IllegalArgumentException("activity is null");

        mActivity = new WeakReference<>(activity);
        mCallbackClass = (Class<T>) getGenericType();

        if (mCallbackClass == ActivityEventCallback.class)
            throw new RuntimeException("callback class must not be " + ActivityEventCallback.class);

        if (!mCallbackClass.isAssignableFrom(getClass()))
            throw new RuntimeException(mCallbackClass + " is not assignable from " + getClass());
    }

    public final Activity getActivity()
    {
        return mActivity.get();
    }

    @Override
    public final boolean register()
    {
        return ActivityEventManager.getInstance().register(getActivity(), mCallbackClass, (T) this);
    }

    @Override
    public final void unregister()
    {
        ActivityEventManager.getInstance().unregister(getActivity(), mCallbackClass, (T) this);
    }

    private Type getGenericType()
    {
        final Class<?> targetClass = findTargetClass();

        final ParameterizedType parameterizedType = (ParameterizedType) targetClass.getGenericSuperclass();
        final Type[] types = parameterizedType.getActualTypeArguments();
        return types[0];
    }

    private Class<?> findTargetClass()
    {
        Class<?> clazz = getClass();
        while (true)
        {
            if (clazz.getSuperclass() == BaseEventObserver.class)
                return clazz;

            clazz = clazz.getSuperclass();
        }
    }
}
