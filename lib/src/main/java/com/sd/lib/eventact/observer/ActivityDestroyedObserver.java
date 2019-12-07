package com.sd.lib.eventact.observer;

import android.app.Activity;

import com.sd.lib.eventact.BaseActivityEventObserver;
import com.sd.lib.eventact.callback.ActivityDestroyedCallback;

public abstract class ActivityDestroyedObserver extends BaseActivityEventObserver<ActivityDestroyedCallback> implements ActivityDestroyedCallback
{
    public ActivityDestroyedObserver(Activity activity)
    {
        super(activity, ActivityDestroyedCallback.class);
    }
}
