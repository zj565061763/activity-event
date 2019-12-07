package com.sd.lib.eventact;

import android.app.Activity;

public final class ActivityEventDispatcherFactory
{
    private ActivityEventDispatcherFactory()
    {
    }

    public static ActivityEventDispatcher create(Activity activity)
    {
        return ActivityEventManager.getInstance().newActivityEventDispatcher(activity);
    }
}
