package com.sd.myandroid.activity_event;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.sd.lib.eventact.observer.ActivityDestroyedObserver;

public class MainActivity extends BaseActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mActivityDestroyedObserver.register();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private final ActivityDestroyedObserver mActivityDestroyedObserver = new ActivityDestroyedObserver(this)
    {
        @Override
        public void onActivityDestroyed(Activity activity)
        {
            Log.i(TAG, "onActivityDestroyed");
        }
    };
}
