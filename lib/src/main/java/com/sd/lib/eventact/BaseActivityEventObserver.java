package com.sd.lib.eventact;

import android.app.Activity;

import com.sd.lib.eventact.callback.ActivityDestroyedCallback;
import com.sd.lib.eventact.callback.ActivityEventCallback;
import com.sd.lib.eventact.observer.ActivityEventObserver;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivityEventObserver<T extends ActivityEventCallback> implements ActivityEventObserver
{
    private final WeakReference<Activity> mActivity;
    private final Class<T> mCallbackClass;
    private final InternalDestroyedObserver mDestroyedObserver = new InternalDestroyedObserver();

    public BaseActivityEventObserver(Activity activity)
    {
        if (activity == null)
            throw new IllegalArgumentException("activity is null");

        mActivity = new WeakReference<>(activity);
        mCallbackClass = (Class<T>) getGenericType();

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
        boolean register = ActivityEventManager.getInstance().register(getActivity(), mCallbackClass, (T) this);
        if (register)
        {
            register = mDestroyedObserver.register();
            if (!register)
                unregister();
        }
        return register;
    }

    @Override
    public final void unregister()
    {
        ActivityEventManager.getInstance().unregister(getActivity(), mCallbackClass, (T) this);
    }

    private final class InternalDestroyedObserver implements ActivityEventObserver, ActivityDestroyedCallback
    {
        @Override
        public boolean register()
        {
            return ActivityEventManager.getInstance().register(getActivity(), ActivityDestroyedCallback.class, this);
        }

        @Override
        public void unregister()
        {
            ActivityEventManager.getInstance().unregister(getActivity(), ActivityDestroyedCallback.class, this);
        }

        @Override
        public void onActivityDestroyed(Activity activity)
        {
            unregister();
            BaseActivityEventObserver.this.unregister();
        }
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
            if (clazz.getSuperclass() == BaseActivityEventObserver.class)
                return clazz;

            clazz = clazz.getSuperclass();
        }
    }
}
