package com.sd.lib.eventact.observer;

import android.app.Activity;

import com.sd.lib.eventact.BaseEventObserver;
import com.sd.lib.eventact.callback.ActivityResultCallback;

public abstract class ActivityResultObserver extends BaseEventObserver<ActivityResultCallback> implements ActivityResultCallback
{
    public ActivityResultObserver(Activity activity)
    {
        super(activity);
    }
}
