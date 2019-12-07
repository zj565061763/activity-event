package com.sd.lib.eventact.observer;

import android.app.Activity;

import com.sd.lib.eventact.BaseEventObserver;
import com.sd.lib.eventact.callback.ActivityDestroyedCallback;

public abstract class ActivityDestroyedObserver extends BaseEventObserver<ActivityDestroyedCallback> implements ActivityDestroyedCallback
{
    public ActivityDestroyedObserver(Activity activity)
    {
        super(activity);
    }
}
