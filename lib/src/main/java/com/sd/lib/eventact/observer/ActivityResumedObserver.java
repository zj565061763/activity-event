package com.sd.lib.eventact.observer;

import android.app.Activity;

import com.sd.lib.eventact.BaseEventObserver;
import com.sd.lib.eventact.callback.ActivityResumedCallback;

public abstract class ActivityResumedObserver extends BaseEventObserver<ActivityResumedCallback> implements ActivityResumedCallback
{
    public ActivityResumedObserver(Activity activity)
    {
        super(activity);
    }
}
