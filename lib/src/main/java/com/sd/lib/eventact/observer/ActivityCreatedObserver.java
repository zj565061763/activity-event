package com.sd.lib.eventact.observer;

import android.app.Activity;

import com.sd.lib.eventact.BaseActivityEventObserver;
import com.sd.lib.eventact.callback.ActivityCreatedCallback;

public abstract class ActivityCreatedObserver extends BaseActivityEventObserver<ActivityCreatedCallback> implements ActivityCreatedCallback
{
    public ActivityCreatedObserver(Activity activity)
    {
        super(activity);
    }
}
