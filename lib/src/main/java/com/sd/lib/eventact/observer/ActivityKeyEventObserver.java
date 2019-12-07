package com.sd.lib.eventact.observer;

import android.app.Activity;

import com.sd.lib.eventact.BaseEventObserver;
import com.sd.lib.eventact.callback.ActivityKeyEventCallback;

public abstract class ActivityKeyEventObserver extends BaseEventObserver<ActivityKeyEventCallback> implements ActivityKeyEventCallback
{
    public ActivityKeyEventObserver(Activity activity)
    {
        super(activity);
    }
}
