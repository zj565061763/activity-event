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

    private boolean setActivity(@NonNull Activity activity)
    {
        if (activity == null)
            throw new IllegalArgumentException("activity is null");

        final Activity old = getActivity();
        if (old != activity)
        {
            if (old != null)
                unregister();

            mActivity = new WeakReference<>(activity);
            return true;
        }
        return false;
    }


    @Override
    public final boolean register(@Nullable Activity activity)
    {
        if (activity == null)
            return false;

        if (setActivity(activity))
            return registerInternal();

        return false;
    }

    @Override
    public final void unregister()
    {
        unregisterInternal();
        mActivity = null;
    }

    private boolean registerInternal()
    {
        return ActivityEventManager.getInstance().register(getActivity(), mCallbackClass, (T) this);
    }

    private boolean unregisterInternal()
    {
        return ActivityEventManager.getInstance().unregister(getActivity(), mCallbackClass, (T) this);
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
