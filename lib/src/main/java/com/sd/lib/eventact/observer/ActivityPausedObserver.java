package com.sd.lib.eventact.observer;

import android.app.Activity;

import com.sd.lib.eventact.BaseEventObserver;
import com.sd.lib.eventact.callback.ActivityPausedCallback;

public abstract class ActivityPausedObserver extends BaseEventObserver<ActivityPausedCallback> implements ActivityPausedCallback
{
    public ActivityPausedObserver(Activity activity)
    {
        super(activity);
    }
}
