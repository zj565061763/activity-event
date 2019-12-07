package com.sd.lib.eventact;

import android.app.Activity;

import com.sd.lib.eventact.callback.ActivityEventCallback;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.WeakHashMap;

class CallbackRegister
{
    private Map<Activity, Map<Class<? extends ActivityEventCallback>, Collection<? extends ActivityEventCallback>>> mMapCallback;

    public <T extends ActivityEventCallback> boolean register(Activity activity, Class<T> callbackClass, T callback)
    {
        if (activity == null || callbackClass == null || callback == null)
            return false;

        if (activity.isFinishing())
            return false;

        if (mMapCallback == null)
            mMapCallback = new WeakHashMap<>();

        Map<Class<? extends ActivityEventCallback>, Collection<? extends ActivityEventCallback>> mapActivityCallback = mMapCallback.get(activity);
        if (mapActivityCallback == null)
        {
            mapActivityCallback = new HashMap<>();
            mMapCallback.put(activity, mapActivityCallback);
        }

        Collection callbacks = mapActivityCallback.get(callbackClass);
        if (callbacks == null)
        {
            callbacks = new LinkedHashSet<>();
            mapActivityCallback.put(callbackClass, callbacks);
        }

        return callbacks.add(callback);
    }

    public <T extends ActivityEventCallback> void unregister(Activity activity, Class<T> callbackClass, T callback)
    {
        if (activity == null || callbackClass == null || callback == null)
            return;

        if (mMapCallback == null)
            return;

        final Map<Class<? extends ActivityEventCallback>, Collection<? extends ActivityEventCallback>> mapActivityCallback = mMapCallback.get(activity);
        if (mapActivityCallback == null)
            return;

        final Collection callbacks = mapActivityCallback.get(callbackClass);
        if (callbacks == null)
            return;

        callbacks.remove(callback);

        if (callbacks.isEmpty())
        {
            mapActivityCallback.remove(callbackClass);
            if (mapActivityCallback.isEmpty())
            {
                mMapCallback.remove(activity);
                if (mMapCallback.isEmpty())
                    mMapCallback = null;
            }
        }
    }

    public <T extends ActivityEventCallback> Collection<T> get(Activity activity, Class<T> callbackClass)
    {
        if (activity == null || callbackClass == null)
            return null;

        if (mMapCallback == null)
            return null;

        final Map<Class<? extends ActivityEventCallback>, Collection<? extends ActivityEventCallback>> mapActivityCallback = mMapCallback.get(activity);
        if (mapActivityCallback == null)
            return null;

        final Collection callbacks = mapActivityCallback.get(callbackClass);
        if (callbacks == null)
            return null;

        return Collections.unmodifiableCollection(callbacks);
    }

    public boolean isEmpty()
    {
        return mMapCallback == null || mMapCallback.isEmpty();
    }
}
