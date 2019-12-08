package com.sd.myandroid.activity_event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sd.lib.eventact.observer.ActivityDestroyedObserver;
import com.sd.lib.eventact.observer.ActivityResultObserver;

public class TestView extends FrameLayout
{
    public static final String TAG = TestView.class.getSimpleName();

    public TestView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        if (!(context instanceof Activity))
            throw new IllegalArgumentException("context must be instance of " + Activity.class);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        mActivityResultObserver.register((Activity) getContext());
        mActivityDestroyedObserver.register((Activity) getContext());
    }

    private final ActivityResultObserver mActivityResultObserver = new ActivityResultObserver()
    {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data)
        {
            Log.i(TAG, "onActivityResult:" + requestCode + "," + resultCode + "," + data);
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
}
