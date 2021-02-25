package com.sd.myandroid.activity_event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sd.lib.eventact.ActivityEventManager;
import com.sd.lib.eventact.observer.ActivityCreatedObserver;
import com.sd.lib.eventact.observer.ActivityDestroyedObserver;
import com.sd.lib.eventact.observer.ActivityKeyEventObserver;
import com.sd.lib.eventact.observer.ActivityPausedObserver;
import com.sd.lib.eventact.observer.ActivityResultObserver;
import com.sd.lib.eventact.observer.ActivityResumedObserver;
import com.sd.lib.eventact.observer.ActivitySaveInstanceStateObserver;
import com.sd.lib.eventact.observer.ActivityStartedObserver;
import com.sd.lib.eventact.observer.ActivityStoppedObserver;
import com.sd.lib.eventact.observer.ActivityTouchEventObserver;

public class MainActivity extends BaseActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        ActivityEventManager.getInstance().setDebug(true);

        mActivityCreatedObserver.register(this);
        mActivityStartedObserver.register(this);
        mActivityResumedObserver.register(this);
        mActivityPausedObserver.register(this);
        mActivityStoppedObserver.register(this);
        mActivityDestroyedObserver.register(this);
        mActivitySaveInstanceStateObserver.register(this);
        mActivityResultObserver.register(this);
        mActivityTouchEventObserver.register(this);
        mActivityKeyEventObserver.register(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivityForResult(intent, 99);
            }
        });
    }

    private final ActivityCreatedObserver mActivityCreatedObserver = new ActivityCreatedObserver()
    {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState)
        {
            Log.i(TAG, "onActivityCreated");
        }
    };

    private final ActivityStartedObserver mActivityStartedObserver = new ActivityStartedObserver()
    {
        @Override
        public void onActivityStarted(@NonNull Activity activity)
        {
            Log.i(TAG, "onActivityStarted");
        }
    };

    private final ActivityResumedObserver mActivityResumedObserver = new ActivityResumedObserver()
    {
        @Override
        public void onActivityResumed(@NonNull Activity activity)
        {
            Log.i(TAG, "onActivityResumed");
        }
    };

    private final ActivityPausedObserver mActivityPausedObserver = new ActivityPausedObserver()
    {
        @Override
        public void onActivityPaused(@NonNull Activity activity)
        {
            Log.i(TAG, "onActivityPaused");
        }
    };

    private final ActivityStoppedObserver mActivityStoppedObserver = new ActivityStoppedObserver()
    {
        @Override
        public void onActivityStopped(@NonNull Activity activity)
        {
            Log.i(TAG, "onActivityStopped");
        }
    };

    private final ActivityDestroyedObserver mActivityDestroyedObserver = new ActivityDestroyedObserver()
    {
        @Override
        public void onActivityDestroyed(@NonNull Activity activity)
        {
            Log.i(TAG, "onActivityDestroyed");
        }
    };

    private final ActivitySaveInstanceStateObserver mActivitySaveInstanceStateObserver = new ActivitySaveInstanceStateObserver()
    {
        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState)
        {
            Log.i(TAG, "onActivitySaveInstanceState");
        }
    };

    private final ActivityResultObserver mActivityResultObserver = new ActivityResultObserver()
    {
        @Override
        public void onActivityResult(@NonNull Activity activity, int requestCode, int resultCode, @Nullable Intent data)
        {
            Log.i(TAG, "onActivityResult:" + requestCode + "," + resultCode + "," + data);
        }
    };

    private final ActivityTouchEventObserver mActivityTouchEventObserver = new ActivityTouchEventObserver()
    {
        @Override
        public boolean onActivityDispatchTouchEvent(@NonNull Activity activity, @NonNull MotionEvent event)
        {
            Log.i(TAG, "onActivityDispatchTouchEvent:" + event.getAction());
            return false;
        }
    };

    private final ActivityKeyEventObserver mActivityKeyEventObserver = new ActivityKeyEventObserver()
    {
        @Override
        public boolean onActivityDispatchKeyEvent(@NonNull Activity activity, @NonNull KeyEvent event)
        {
            Log.i(TAG, "onActivityDispatchKeyEvent:" + event.getAction());
            return false;
        }
    };
}
