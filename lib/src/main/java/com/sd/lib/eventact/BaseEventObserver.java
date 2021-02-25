package com.sd.lib.eventact;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sd.lib.eventact.callback.ActivityEventCallback;
import com.sd.lib.eventact.observer.ActivityEventObserver;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseEventObserver<T extends ActivityEventCallback> implements ActivityEventObserver
{
    private WeakReference<Activity> mActivity;
    private final Class<T> mCallbackClass;

    public BaseEventObserver()
    {
        mCallbackClass = (Class<T>) getGenericType();

        if (mCallbackClass == ActivityEventCallback.class)
            throw new RuntimeException("callback class must not be " + ActivityEventCallback.class);

        if (!mCallbackClass.isAssignableFrom(getClass()))
            throw new RuntimeException(mCallbackClass + " is not assignable from " + getClass());
    }

    @Nullable
    public final Activity getActivity()
    {
        return mActivity == null ? null : mActivity.get();
    }

    @Override
    public final synchronized boolean register(@Nullable Activity activity)
    {
        if (activity == null)
            return false;

        final Activity oldActivity = getActivity();
        if (oldActivity != null)
        {
            if (oldActivity == activity)
            {
                // 以进注册过了
                return true;
            } else
            {
                // Activity对象发生变化，先取消注册
                unregister();
            }
        }

        final boolean register = ActivityEventManager.getInstance().register(activity, mCallbackClass, (T) this);
        if (register)
            mActivity = new WeakReference<>(activity);

        return register;
    }

    @Override
    public final synchronized void unregister()
    {
        final Activity activity = getActivity();
        if (activity != null)
            ActivityEventManager.getInstance().unregister(activity, mCallbackClass, (T) this);

        mActivity = null;
    }

    @NonNull
    private Type getGenericType()
    {
        final Class<?> targetClass = findTargetClass();

        final ParameterizedType parameterizedType = (ParameterizedType) targetClass.getGenericSuperclass();
        final Type[] types = parameterizedType.getActualTypeArguments();
        return types[0];
    }

    @NonNull
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
