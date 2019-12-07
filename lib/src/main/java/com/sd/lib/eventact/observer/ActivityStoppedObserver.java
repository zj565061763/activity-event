package com.sd.lib.eventact.observer;

import android.app.Activity;

import com.sd.lib.eventact.BaseEventObserver;
import com.sd.lib.eventact.callback.ActivityStoppedCallback;

public abstract class ActivityStoppedObserver extends BaseEventObserver<ActivityStoppedCallback> implements ActivityStoppedCallback
{
    public ActivityStoppedObserver(Activity activity)
    {
        super(activity);
    }
}
