package com.sd.lib.eventact;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.sd.lib.eventact.callback.ActivityCreatedCallback;
import com.sd.lib.eventact.callback.ActivityDestroyedCallback;
import com.sd.lib.eventact.callback.ActivityEventCallback;
import com.sd.lib.eventact.callback.ActivityInstanceStateCallback;
import com.sd.lib.eventact.callback.ActivityKeyEventCallback;
import com.sd.lib.eventact.callback.ActivityPausedCallback;
import com.sd.lib.eventact.callback.ActivityResultCallback;
import com.sd.lib.eventact.callback.ActivityResumedCallback;
import com.sd.lib.eventact.callback.ActivityStartedCallback;
import com.sd.lib.eventact.callback.ActivityStoppedCallback;
import com.sd.lib.eventact.callback.ActivityTouchEventCallback;

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

    private final DefaultActivityEventDispatcher mDispatcher = new DefaultActivityEventDispatcher();
    private SystemActivityEventDispatcher mSystemActivityEventDispatcher;

    public synchronized <T extends ActivityEventCallback> boolean register(Activity activity, Class<T> clazz, T callback)
    {
        initSystemActivityEventDispatcher(activity);

        if (mCallbackRegister == null)
            mCallbackRegister = new CallbackRegister();

        return mCallbackRegister.register(activity, clazz, callback);
    }

    public synchronized <T extends ActivityEventCallback> void unregister(Activity activity, Class<T> clazz, T callback)
    {
        if (mCallbackRegister != null)
        {
            mCallbackRegister.unregister(activity, clazz, callback);
            if (mCallbackRegister.isEmpty())
                mCallbackRegister = null;
        }
    }

    private synchronized <T extends ActivityEventCallback> Collection<T> getCallbacks(Activity activity, Class<T> callbackClass)
    {
        if (mCallbackRegister == null)
            return null;
        return mCallbackRegister.get(activity, callbackClass);
    }

    private synchronized void initSystemActivityEventDispatcher(Activity activity)
    {
        if (activity == null)
            return;

        if (mSystemActivityEventDispatcher == null)
        {
            mSystemActivityEventDispatcher = new SystemActivityEventDispatcher(activity);
            mSystemActivityEventDispatcher.register();
        }
    }

    public ActivityEventDispatcher newActivityEventDispatcher(Activity activity)
    {
        return new CustomActivityEventDispatcher(activity);
    }

    private final class SystemActivityEventDispatcher implements Application.ActivityLifecycleCallbacks
    {
        private final Application mApplication;

        public SystemActivityEventDispatcher(Context context)
        {
            if (context == null)
                throw new IllegalArgumentException("context is null");
            mApplication = (Application) context.getApplicationContext();
        }

        public void register()
        {
            mApplication.unregisterActivityLifecycleCallbacks(this);
            mApplication.registerActivityLifecycleCallbacks(this);
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle)
        {
            mDispatcher.dispatch_onCreate(activity, bundle);
        }

        @Override
        public void onActivityStarted(Activity activity)
        {
            mDispatcher.dispatch_onStart(activity);
        }

        @Override
        public void onActivityResumed(Activity activity)
        {
            mDispatcher.dispatch_onResume(activity);
        }

        @Override
        public void onActivityPaused(Activity activity)
        {
            mDispatcher.dispatch_onPause(activity);
        }

        @Override
        public void onActivityStopped(Activity activity)
        {
            mDispatcher.dispatch_onStop(activity);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle)
        {
            mDispatcher.dispatch_onSaveInstanceState(activity, bundle);
        }

        @Override
        public void onActivityDestroyed(Activity activity)
        {
            mDispatcher.dispatch_onDestroy(activity);
        }
    }

    private final class CustomActivityEventDispatcher implements ActivityEventDispatcher
    {
        private final Activity mActivity;

        public CustomActivityEventDispatcher(Activity activity)
        {
            if (activity == null)
                throw new IllegalArgumentException("activity is null");
            mActivity = activity;
        }

        @Override
        public void dispatch_onActivityResult(int requestCode, int resultCode, Intent data)
        {
            mDispatcher.dispatch_onActivityResult(mActivity, requestCode, resultCode, data);
        }

        @Override
        public boolean dispatch_dispatchTouchEvent(MotionEvent event)
        {
            return mDispatcher.dispatch_dispatchTouchEvent(mActivity, event);
        }

        @Override
        public boolean dispatch_dispatchKeyEvent(KeyEvent event)
        {
            return mDispatcher.dispatch_dispatchKeyEvent(mActivity, event);
        }
    }

    private final class DefaultActivityEventDispatcher
    {
        public void dispatch_onCreate(Activity activity, Bundle savedInstanceState)
        {
            final Collection<ActivityCreatedCallback> callbacks = getCallbacks(activity, ActivityCreatedCallback.class);
            if (callbacks == null)
                return;

            for (ActivityCreatedCallback item : callbacks)
            {
                item.onActivityCreated(activity, savedInstanceState);
            }
        }

        public void dispatch_onStart(Activity activity)
        {
            final Collection<ActivityStartedCallback> callbacks = getCallbacks(activity, ActivityStartedCallback.class);
            if (callbacks == null)
                return;

            for (ActivityStartedCallback item : callbacks)
            {
                item.onActivityStarted(activity);
            }
        }

        public void dispatch_onResume(Activity activity)
        {
            final Collection<ActivityResumedCallback> callbacks = getCallbacks(activity, ActivityResumedCallback.class);
            if (callbacks == null)
                return;

            for (ActivityResumedCallback item : callbacks)
            {
                item.onActivityResumed(activity);
            }
        }

        public void dispatch_onPause(Activity activity)
        {
            final Collection<ActivityPausedCallback> callbacks = getCallbacks(activity, ActivityPausedCallback.class);
            if (callbacks == null)
                return;

            for (ActivityPausedCallback item : callbacks)
            {
                item.onActivityPaused(activity);
            }
        }

        public void dispatch_onStop(Activity activity)
        {
            final Collection<ActivityStoppedCallback> callbacks = getCallbacks(activity, ActivityStoppedCallback.class);
            if (callbacks == null)
                return;

            for (ActivityStoppedCallback item : callbacks)
            {
                item.onActivityStopped(activity);
            }
        }

        public void dispatch_onDestroy(Activity activity)
        {
            final Collection<ActivityDestroyedCallback> callbacks = getCallbacks(activity, ActivityDestroyedCallback.class);
            if (callbacks == null)
                return;

            for (ActivityDestroyedCallback item : callbacks)
            {
                item.onActivityDestroyed(activity);
            }
        }

        public void dispatch_onSaveInstanceState(Activity activity, Bundle outState)
        {
            final Collection<ActivityInstanceStateCallback> callbacks = getCallbacks(activity, ActivityInstanceStateCallback.class);
            if (callbacks == null)
                return;

            for (ActivityInstanceStateCallback item : callbacks)
            {
                item.onActivitySaveInstanceState(activity, outState);
            }
        }

        public void dispatch_onActivityResult(Activity activity, int requestCode, int resultCode, Intent data)
        {
            final Collection<ActivityResultCallback> callbacks = getCallbacks(activity, ActivityResultCallback.class);
            if (callbacks == null)
                return;

            for (ActivityResultCallback item : callbacks)
            {
                item.onActivityResult(activity, requestCode, resultCode, data);
            }
        }

        public boolean dispatch_dispatchTouchEvent(Activity activity, MotionEvent event)
        {
            final Collection<ActivityTouchEventCallback> callbacks = getCallbacks(activity, ActivityTouchEventCallback.class);
            if (callbacks == null)
                return false;

            for (ActivityTouchEventCallback item : callbacks)
            {
                if (item.onActivityDispatchTouchEvent(activity, event))
                    return true;
            }
            return false;
        }

        public boolean dispatch_dispatchKeyEvent(Activity activity, KeyEvent event)
        {
            final Collection<ActivityKeyEventCallback> callbacks = getCallbacks(activity, ActivityKeyEventCallback.class);
            if (callbacks == null)
                return false;

            for (ActivityKeyEventCallback item : callbacks)
            {
                if (item.onActivityDispatchKeyEvent(activity, event))
                    return true;
            }
            return false;
        }
    }
}
