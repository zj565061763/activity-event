package com.sd.lib.eventact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.sd.lib.eventact.callback.ActivityCreatedCallback;
import com.sd.lib.eventact.callback.ActivityDestroyedCallback;
import com.sd.lib.eventact.callback.ActivityEventCallback;
import com.sd.lib.eventact.callback.ActivityPausedCallback;
import com.sd.lib.eventact.callback.ActivityResumedCallback;
import com.sd.lib.eventact.callback.ActivityStartedCallback;
import com.sd.lib.eventact.callback.ActivityStoppedCallback;

import java.util.Collection;

class ActivityEventManager
{
    private static ActivityEventManager sInstance = null;

    private ActivityEventManager()
    {
    }

    public static ActivityEventManager getInstance()
    {
        if (sInstance == null)
        {
            synchronized (ActivityEventManager.class)
            {
                if (sInstance == null)
                    sInstance = new ActivityEventManager();
            }
        }
        return sInstance;
    }

    private CallbackRegister mCallbackRegister;

    public <T extends ActivityEventCallback> boolean register(Activity activity, Class<T> clazz, T callback)
    {
        synchronized (ActivityEventManager.this)
        {
            if (mCallbackRegister == null)
                mCallbackRegister = new CallbackRegister();

            return mCallbackRegister.register(activity, clazz, callback);
        }
    }

    public <T extends ActivityEventCallback> void unregister(Activity activity, Class<T> clazz, T callback)
    {
        synchronized (ActivityEventManager.this)
        {
            if (mCallbackRegister != null)
            {
                mCallbackRegister.unregister(activity, clazz, callback);
                if (mCallbackRegister.isEmpty())
                    mCallbackRegister = null;
            }
        }
    }

    public ActivityEventDispatcher newActivityEventDispatcher(Activity activity)
    {
        return new InternalActivityEventDispatcher(activity);
    }

    private final class InternalActivityEventDispatcher implements ActivityEventDispatcher
    {
        private final Activity mActivity;

        public InternalActivityEventDispatcher(Activity activity)
        {
            if (activity == null)
                throw new IllegalArgumentException("activity is null");
            mActivity = activity;
        }

        public <T extends ActivityEventCallback> Collection<T> getCallbacks(Class<T> callbackClass)
        {
            synchronized (ActivityEventManager.this)
            {
                if (mCallbackRegister == null)
                    return null;
                return mCallbackRegister.get(mActivity, callbackClass);
            }
        }

        @Override
        public void dispatch_onCreate(Bundle savedInstanceState)
        {
            final Collection<ActivityCreatedCallback> callbacks = getCallbacks(ActivityCreatedCallback.class);
            if (callbacks == null)
                return;

            for (ActivityCreatedCallback item : callbacks)
            {
                item.onActivityCreated(mActivity, savedInstanceState);
            }
        }

        @Override
        public void dispatch_onStart()
        {
            final Collection<ActivityStartedCallback> callbacks = getCallbacks(ActivityStartedCallback.class);
            if (callbacks == null)
                return;

            for (ActivityStartedCallback item : callbacks)
            {
                item.onActivityStarted(mActivity);
            }
        }

        @Override
        public void dispatch_onResume()
        {
            final Collection<ActivityResumedCallback> callbacks = getCallbacks(ActivityResumedCallback.class);
            if (callbacks == null)
                return;

            for (ActivityResumedCallback item : callbacks)
            {
                item.onActivityResumed(mActivity);
            }
        }

        @Override
        public void dispatch_onPause()
        {
            final Collection<ActivityPausedCallback> callbacks = getCallbacks(ActivityPausedCallback.class);
            if (callbacks == null)
                return;

            for (ActivityPausedCallback item : callbacks)
            {
                item.onActivityPaused(mActivity);
            }
        }

        @Override
        public void dispatch_onStop()
        {
            final Collection<ActivityStoppedCallback> callbacks = getCallbacks(ActivityStoppedCallback.class);
            if (callbacks == null)
                return;

            for (ActivityStoppedCallback item : callbacks)
            {
                item.onActivityStopped(mActivity);
            }
        }

        @Override
        public void dispatch_onDestroy()
        {
            final Collection<ActivityDestroyedCallback> callbacks = getCallbacks(ActivityDestroyedCallback.class);
            if (callbacks == null)
                return;

            for (ActivityDestroyedCallback item : callbacks)
            {
                item.onActivityDestroyed(mActivity);
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
