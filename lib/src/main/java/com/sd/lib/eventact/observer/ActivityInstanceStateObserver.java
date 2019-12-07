package com.sd.lib.eventact.observer;

import android.app.Activity;

import com.sd.lib.eventact.BaseEventObserver;
import com.sd.lib.eventact.callback.ActivityInstanceStateCallback;

public abstract class ActivityInstanceStateObserver extends BaseEventObserver<ActivityInstanceStateCallback> implements ActivityInstanceStateCallback
{
    public ActivityInstanceStateObserver(Activity activity)
    {
        super(activity);
    }
}
