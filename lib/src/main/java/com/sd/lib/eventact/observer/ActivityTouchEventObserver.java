package com.sd.lib.eventact.observer;

import android.app.Activity;

import com.sd.lib.eventact.BaseEventObserver;
import com.sd.lib.eventact.callback.ActivityTouchEventCallback;

public abstract class ActivityTouchEventObserver extends BaseEventObserver<ActivityTouchEventCallback> implements ActivityTouchEventCallback
{
    public ActivityTouchEventObserver(Activity activity)
    {
        super(activity);
    }
}
