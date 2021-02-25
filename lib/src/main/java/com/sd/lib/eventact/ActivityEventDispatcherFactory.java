package com.sd.lib.eventact;

import android.app.Activity;

import androidx.annotation.NonNull;

public final class ActivityEventDispatcherFactory
{
    private ActivityEventDispatcherFactory()
    {
    }

    public static ActivityEventDispatcher create(@NonNull Activity activity)
    {
        return ActivityEventManager.getInstance().newActivityEventDispatcher(activity);
    }
}
