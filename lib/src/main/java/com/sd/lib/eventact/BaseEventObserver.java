package com.sd.lib.eventact;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.sd.lib.eventact.callback.ActivityEventCallback;
import com.sd.lib.eventact.observer.ActivityEventObserver;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseEventObserver<T extends ActivityEventCallback> implements ActivityEventObserver
{
    private final WeakReference<Activity> mActivity;
    private final Class<T> mCallbackClass;
    private final InternalDestroyedObserver mDestroyedObserver;

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

        mDestroyedObserver = new InternalDestroyedObserver();
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
        mDestroyedObserver.unregister();
    }

    private Activity checkActivity()
    {
        final Activity activity = getActivity();
        if (activity == null)
            unregister();
        return activity;
    }

    private final class InternalDestroyedObserver implements ActivityEventObserver, Application.ActivityLifecycleCallbacks
    {
        private Application mApplication;

        @Override
        public boolean register()
        {
            final Activity activity = checkActivity();
            if (activity == null)
                return false;

            if (activity.isFinishing())
                return false;

            synchronized (InternalDestroyedObserver.this)
            {
                if (mApplication == null)
                {
                    mApplication = activity.getApplication();
                    mApplication.registerActivityLifecycleCallbacks(this);
                }
            }

            return true;
        }

        @Override
        public void unregister()
        {
            synchronized (InternalDestroyedObserver.this)
            {
                if (mApplication != null)
                {
                    mApplication.unregisterActivityLifecycleCallbacks(this);
                    mApplication = null;
                }
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity)
        {
            if (activity == checkActivity())
                BaseEventObserver.this.unregister();
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle)
        {
        }

        @Override
        public void onActivityStarted(Activity activity)
        {
        }

        @Override
        public void onActivityResumed(Activity activity)
        {
        }

        @Override
        public void onActivityPaused(Activity activity)
        {
        }

        @Override
        public void onActivityStopped(Activity activity)
        {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle)
        {
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
            if (clazz.getSuperclass() == BaseEventObserver.class)
                return clazz;

            clazz = clazz.getSuperclass();
        }
    }
}
