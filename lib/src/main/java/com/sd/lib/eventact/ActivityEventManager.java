package com.sd.lib.eventact;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sd.lib.eventact.callback.ActivityCreatedCallback;
import com.sd.lib.eventact.callback.ActivityDestroyedCallback;
import com.sd.lib.eventact.callback.ActivityEventCallback;
import com.sd.lib.eventact.callback.ActivityKeyEventCallback;
import com.sd.lib.eventact.callback.ActivityPausedCallback;
import com.sd.lib.eventact.callback.ActivityResultCallback;
import com.sd.lib.eventact.callback.ActivityResumedCallback;
import com.sd.lib.eventact.callback.ActivitySaveInstanceStateCallback;
import com.sd.lib.eventact.callback.ActivityStartedCallback;
import com.sd.lib.eventact.callback.ActivityStoppedCallback;
import com.sd.lib.eventact.callback.ActivityTouchEventCallback;

import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

public class ActivityEventManager
{
    private static ActivityEventManager sInstance;

    public static ActivityEventManager getInstance()
    {
        if (sInstance != null)
            return sInstance;

        synchronized (ActivityEventManager.class)
        {
            if (sInstance == null)
                sInstance = new ActivityEventManager();
            return sInstance;
        }
    }

    private final Map<Activity, CallbackHolder> mCallbackHolder = new WeakHashMap<>();

    private final DefaultActivityEventDispatcher mDispatcher = new DefaultActivityEventDispatcher();
    private SystemActivityEventDispatcher mSystemActivityEventDispatcher;

    private boolean mIsDebug;

    private ActivityEventManager()
    {
    }

    public boolean isDebug()
    {
        return mIsDebug;
    }

    public void setDebug(boolean debug)
    {
        mIsDebug = debug;
    }

    synchronized <T extends ActivityEventCallback> boolean register(@NonNull Activity activity, @NonNull Class<T> clazz, @NonNull T callback)
    {
        if (activity == null)
            throw new IllegalArgumentException("null argument");

        if (activity.isFinishing())
            return false;

        if (mSystemActivityEventDispatcher == null)
        {
            final Application application = activity.getApplication();
            mSystemActivityEventDispatcher = new SystemActivityEventDispatcher(application);
            mSystemActivityEventDispatcher.register();
        }

        CallbackHolder holder = mCallbackHolder.get(activity);
        if (holder == null)
        {
            holder = new CallbackHolder();
            mCallbackHolder.put(activity, holder);
        }

        holder.add(clazz, callback);
        return true;
    }

    synchronized <T extends ActivityEventCallback> void unregister(@NonNull Activity activity, @NonNull Class<T> clazz, @NonNull T callback)
    {
        if (activity == null)
            throw new IllegalArgumentException("null argument");

        final CallbackHolder holder = mCallbackHolder.get(activity);
        if (holder == null)
            return;

        holder.remove(clazz, callback);
        if (holder.isEmpty())
            mCallbackHolder.remove(activity);
    }

    @Nullable
    private synchronized <T extends ActivityEventCallback> Collection<T> getActivityCallbacks(@NonNull Activity activity, @NonNull Class<T> clazz)
    {
        if (activity == null)
            throw new IllegalArgumentException("null argument");

        final CallbackHolder holder = mCallbackHolder.get(activity);
        return holder != null ? holder.get(clazz) : null;
    }

    private synchronized void removeActivityCallback(@NonNull Activity activity)
    {
        if (activity == null)
            throw new IllegalArgumentException("null argument");

        mCallbackHolder.remove(activity);
    }

    @NonNull
    ActivityEventDispatcher newActivityEventDispatcher(@NonNull Activity activity)
    {
        return new CustomActivityEventDispatcher(activity);
    }

    private final class SystemActivityEventDispatcher implements Application.ActivityLifecycleCallbacks
    {
        private final Application mApplication;

        public SystemActivityEventDispatcher(@NonNull Application application)
        {
            if (application == null)
                throw new IllegalArgumentException("application is null");
            mApplication = application;
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
        public void dispatch_onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
        {
            mDispatcher.dispatch_onActivityResult(mActivity, requestCode, resultCode, data);
        }

        @Override
        public boolean dispatch_dispatchTouchEvent(@NonNull MotionEvent event)
        {
            return mDispatcher.dispatch_dispatchTouchEvent(mActivity, event);
        }

        @Override
        public boolean dispatch_dispatchKeyEvent(@NonNull KeyEvent event)
        {
            return mDispatcher.dispatch_dispatchKeyEvent(mActivity, event);
        }
    }

    private final class DefaultActivityEventDispatcher
    {
        public void dispatch_onCreate(@NonNull Activity activity, @NonNull Bundle savedInstanceState)
        {
            final Collection<ActivityCreatedCallback> callbacks = getActivityCallbacks(activity, ActivityCreatedCallback.class);
            if (callbacks == null)
                return;

            if (isDebug())
            {
                Log.i(ActivityEventManager.class.getName(), "dispatch_onCreate " + activity + " size:" + callbacks.size() + "\r\n" +
                        callbacks);
            }

            for (ActivityCreatedCallback item : callbacks)
            {
                item.onActivityCreated(activity, savedInstanceState);
            }
        }

        public void dispatch_onStart(@NonNull Activity activity)
        {
            final Collection<ActivityStartedCallback> callbacks = getActivityCallbacks(activity, ActivityStartedCallback.class);
            if (callbacks == null)
                return;

            if (isDebug())
            {
                Log.i(ActivityEventManager.class.getName(), "dispatch_onStart " + activity + " size:" + callbacks.size() + "\r\n" +
                        callbacks);
            }

            for (ActivityStartedCallback item : callbacks)
            {
                item.onActivityStarted(activity);
            }
        }

        public void dispatch_onResume(@NonNull Activity activity)
        {
            final Collection<ActivityResumedCallback> callbacks = getActivityCallbacks(activity, ActivityResumedCallback.class);
            if (callbacks == null)
                return;

            if (isDebug())
            {
                Log.i(ActivityEventManager.class.getName(), "dispatch_onResume " + activity + " size:" + callbacks.size() + "\r\n" +
                        callbacks);
            }

            for (ActivityResumedCallback item : callbacks)
            {
                item.onActivityResumed(activity);
            }
        }

        public void dispatch_onPause(@NonNull Activity activity)
        {
            final Collection<ActivityPausedCallback> callbacks = getActivityCallbacks(activity, ActivityPausedCallback.class);
            if (callbacks == null)
                return;

            if (isDebug())
            {
                Log.i(ActivityEventManager.class.getName(), "dispatch_onPause " + activity + " size:" + callbacks.size() + "\r\n" +
                        callbacks);
            }

            for (ActivityPausedCallback item : callbacks)
            {
                item.onActivityPaused(activity);
            }
        }

        public void dispatch_onStop(@NonNull Activity activity)
        {
            final Collection<ActivityStoppedCallback> callbacks = getActivityCallbacks(activity, ActivityStoppedCallback.class);
            if (callbacks == null)
                return;

            if (isDebug())
            {
                Log.i(ActivityEventManager.class.getName(), "dispatch_onStop " + activity + " size:" + callbacks.size() + "\r\n" +
                        callbacks);
            }

            for (ActivityStoppedCallback item : callbacks)
            {
                item.onActivityStopped(activity);
            }
        }

        public void dispatch_onDestroy(@NonNull Activity activity)
        {
            final Collection<ActivityDestroyedCallback> callbacks = getActivityCallbacks(activity, ActivityDestroyedCallback.class);
            if (callbacks == null)
                return;

            if (isDebug())
            {
                Log.i(ActivityEventManager.class.getName(), "dispatch_onDestroy " + activity + " size:" + callbacks.size() + "\r\n" +
                        callbacks);
            }

            for (ActivityDestroyedCallback item : callbacks)
            {
                item.onActivityDestroyed(activity);
            }

            removeActivityCallback(activity);
        }

        public void dispatch_onSaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState)
        {
            final Collection<ActivitySaveInstanceStateCallback> callbacks = getActivityCallbacks(activity, ActivitySaveInstanceStateCallback.class);
            if (callbacks == null)
                return;

            if (isDebug())
            {
                Log.i(ActivityEventManager.class.getName(), "dispatch_onSaveInstanceState " + activity + " size:" + callbacks.size() + "\r\n" +
                        callbacks);
            }

            for (ActivitySaveInstanceStateCallback item : callbacks)
            {
                item.onActivitySaveInstanceState(activity, outState);
            }
        }

        public void dispatch_onActivityResult(@NonNull Activity activity, int requestCode, int resultCode, @Nullable Intent data)
        {
            final Collection<ActivityResultCallback> callbacks = getActivityCallbacks(activity, ActivityResultCallback.class);
            if (callbacks == null)
                return;

            if (isDebug())
            {
                Log.i(ActivityEventManager.class.getName(), "dispatch_onActivityResult " + activity + " size:" + callbacks.size() + "\r\n" +
                        callbacks);
            }

            for (ActivityResultCallback item : callbacks)
            {
                item.onActivityResult(activity, requestCode, resultCode, data);
            }
        }

        public boolean dispatch_dispatchTouchEvent(@NonNull Activity activity, @NonNull MotionEvent event)
        {
            final Collection<ActivityTouchEventCallback> callbacks = getActivityCallbacks(activity, ActivityTouchEventCallback.class);
            if (callbacks == null)
                return false;

            if (isDebug())
            {
                Log.i(ActivityEventManager.class.getName(), "dispatch_dispatchTouchEvent " + activity + " size:" + callbacks.size() + " event:" + event.getAction() + "\r\n" +
                        callbacks);
            }

            for (ActivityTouchEventCallback item : callbacks)
            {
                if (item.onActivityDispatchTouchEvent(activity, event))
                    return true;
            }
            return false;
        }

        public boolean dispatch_dispatchKeyEvent(@NonNull Activity activity, @NonNull KeyEvent event)
        {
            final Collection<ActivityKeyEventCallback> callbacks = getActivityCallbacks(activity, ActivityKeyEventCallback.class);
            if (callbacks == null)
                return false;

            if (isDebug())
            {
                Log.i(ActivityEventManager.class.getName(), "dispatch_dispatchKeyEvent " + activity + " size:" + callbacks.size() + " event:" + event.getKeyCode() + "," + event.getAction() + "\r\n" +
                        callbacks);
            }

            for (ActivityKeyEventCallback item : callbacks)
            {
                if (item.onActivityDispatchKeyEvent(activity, event))
                    return true;
            }
            return false;
        }
    }
}
