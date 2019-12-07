package com.sd.lib.eventact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.sd.lib.eventact.callback.ActivityCreatedCallback;
import com.sd.lib.eventact.callback.ActivityDestroyedCallback;
import com.sd.lib.eventact.callback.ActivityEventCallback;

import java.lang.ref.WeakReference;
import java.util.Collection;

class ActivityEventManager
{
    private static final ActivityEventManager INSTANCE = new ActivityEventManager();

    private ActivityEventManager()
    {
    }

    public static ActivityEventManager getInstance()
    {
        return INSTANCE;
    }

    private CallbackRegister mCallbackRegister;

    public synchronized <T extends ActivityEventCallback> boolean register(Activity activity, Class<T> clazz, T callback)
    {
        if (mCallbackRegister == null)
            mCallbackRegister = new CallbackRegister();
        return mCallbackRegister.register(activity, clazz, callback);
    }

    public synchronized <T extends ActivityEventCallback> void unregister(Activity activity, Class<T> clazz, T callback)
    {
        if (mCallbackRegister != null)
            mCallbackRegister.unregister(activity, clazz, callback);
    }

    public ActivityEventDispatcher newActivityEventDispatcher(Activity activity)
    {
        return new InternalActivityEventDispatcher(activity);
    }

    private final class InternalActivityEventDispatcher implements ActivityEventDispatcher
    {
        private final WeakReference<Activity> mActivity;

        public InternalActivityEventDispatcher(Activity activity)
        {
            if (activity == null)
                throw new IllegalArgumentException("activity is null");
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void dispatch_onCreate(Bundle savedInstanceState)
        {
            synchronized (ActivityEventManager.this)
            {
                final Activity activity = mActivity.get();
                if (activity == null)
                    return;
                if (mCallbackRegister == null)
                    return;

                final Collection<ActivityCreatedCallback> callbacks = mCallbackRegister.get(activity, ActivityCreatedCallback.class);
                if (callbacks == null)
                    return;

                for (ActivityCreatedCallback item : callbacks)
                {
                    item.onActivityCreated(activity, savedInstanceState);
                }
            }
        }

        @Override
        public void dispatch_onStart()
        {

        }

        @Override
        public void dispatch_onResume()
        {

        }

        @Override
        public void dispatch_onPause()
        {

        }

        @Override
        public void dispatch_onStop()
        {

        }

        @Override
        public void dispatch_onDestroy()
        {
            synchronized (ActivityEventManager.this)
            {
                final Activity activity = mActivity.get();
                if (activity == null)
                    return;
                if (mCallbackRegister == null)
                    return;

                final Collection<ActivityDestroyedCallback> callbacks = mCallbackRegister.get(activity, ActivityDestroyedCallback.class);
                if (callbacks == null)
                    return;

                for (ActivityDestroyedCallback item : callbacks)
                {
                    item.onActivityDestroyed(activity);
                }
            }
        }

        @Override
        public void dispatch_onSaveInstanceState(Bundle outState)
        {

        }

        @Override
        public void dispatch_onRestoreInstanceState(Bundle savedInstanceState)
        {

        }

        @Override
        public void dispatch_onActivityResult(int requestCode, int resultCode, Intent data)
        {

        }

        @Override
        public boolean dispatch_dispatchTouchEvent(MotionEvent event)
        {
            return false;
        }

        @Override
        public boolean dispatch_dispatchKeyEvent(KeyEvent event)
        {
            return false;
        }
    }
}
