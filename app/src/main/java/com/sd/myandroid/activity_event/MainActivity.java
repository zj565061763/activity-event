package com.sd.myandroid.activity_event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

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
    protected void onCreate(Bundle savedInstanceState)
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
        public void onActivityCreated(Activity activity, Bundle savedInstanceState)
        {
            Log.i(TAG, "onActivityCreated");
        }
    };

    private final ActivityStartedObserver mActivityStartedObserver = new ActivityStartedObserver()
    {
        @Override
        public void onActivityStarted(Activity activity)
        {
            Log.i(TAG, "onActivityStarted");
        }
    };

    private final ActivityResumedObserver mActivityResumedObserver = new ActivityResumedObserver()
    {
        @Override
        public void onActivityResumed(Activity activity)
        {
            Log.i(TAG, "onActivityResumed");
        }
    };

    private final ActivityPausedObserver mActivityPausedObserver = new ActivityPausedObserver()
    {
        @Override
        public void onActivityPaused(Activity activity)
        {
            Log.i(TAG, "onActivityPaused");
        }
    };

    private final ActivityStoppedObserver mActivityStoppedObserver = new ActivityStoppedObserver()
    {
        @Override
        public void onActivityStopped(Activity activity)
        {
            Log.i(TAG, "onActivityStopped");
        }
    };

    private final ActivityDestroyedObserver mActivityDestroyedObserver = new ActivityDestroyedObserver()
    {
        @Override
        public void onActivityDestroyed(Activity activity)
        {
            Log.i(TAG, "onActivityDestroyed");
        }
    };

    private final ActivitySaveInstanceStateObserver mActivitySaveInstanceStateObserver = new ActivitySaveInstanceStateObserver()
    {
        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState)
        {
            Log.i(TAG, "onActivitySaveInstanceState");
        }
    };

    private final ActivityResultObserver mActivityResultObserver = new ActivityResultObserver()
    {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data)
        {
            Log.i(TAG, "onActivityResult:" + requestCode + "," + resultCode + "," + data);
        }
    };

    private final ActivityTouchEventObserver mActivityTouchEventObserver = new ActivityTouchEventObserver()
    {
        @Override
        public boolean onActivityDispatchTouchEvent(Activity activity, MotionEvent event)
        {
            Log.i(TAG, "onActivityDispatchTouchEvent:" + event.getAction());
            return false;
        }
    };

    private final ActivityKeyEventObserver mActivityKeyEventObserver = new ActivityKeyEventObserver()
    {
        @Override
        public boolean onActivityDispatchKeyEvent(Activity activity, KeyEvent event)
        {
            Log.i(TAG, "onActivityDispatchKeyEvent:" + event.getAction());
            return false;
        }
    };
}
