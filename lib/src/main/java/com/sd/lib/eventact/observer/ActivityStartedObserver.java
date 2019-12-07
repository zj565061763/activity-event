package com.sd.lib.eventact.observer;

import android.app.Activity;

import com.sd.lib.eventact.BaseEventObserver;
import com.sd.lib.eventact.callback.ActivityStartedCallback;

public abstract class ActivityStartedObserver extends BaseEventObserver<ActivityStartedCallback> implements ActivityStartedCallback
{
    public ActivityStartedObserver(Activity activity)
    {
        super(activity);
    }
}
