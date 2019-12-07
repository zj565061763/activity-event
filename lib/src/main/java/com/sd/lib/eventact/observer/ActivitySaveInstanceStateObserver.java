package com.sd.lib.eventact.observer;

import android.app.Activity;

import com.sd.lib.eventact.BaseEventObserver;
import com.sd.lib.eventact.callback.ActivitySaveInstanceStateCallback;

public abstract class ActivitySaveInstanceStateObserver extends BaseEventObserver<ActivitySaveInstanceStateCallback> implements ActivitySaveInstanceStateCallback
{
    public ActivitySaveInstanceStateObserver(Activity activity)
    {
        super(activity);
    }
}
