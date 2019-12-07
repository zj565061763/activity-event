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

    private <T extends ActivityEventCallback> Collection<T> getCallbacks(Activity activity, Class<T> clazz)
    {
        final Map<Class<? extends ActivityEventCallback>, Collection<? extends ActivityEventCallback>> map = mMapCallback.get(activity);
        if (map == null)
            return null;

        final Collection<? extends ActivityEventCallback> callbacks = map.get(clazz);
        if (callbacks == null || callbacks.isEmpty())
            return null;

        return (Collection<T>) callbacks;
    }

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

    public <T extends ActivityEventCallback> void unregister(Activity activity, Class<T> clazz, T callback)
    {
        if (activity == null || clazz == null || callback == null)
            return;

        if (mMapCallback == null || mMapCallback.isEmpty())
            return;

        final Collection<T> callbacks = getCallbacks(activity, clazz);
        if (callbacks != null)
            callbacks.remove(activity);
    }

    public <T extends ActivityEventCallback> Collection<T> get(Activity activity, Class<T> clazz)
    {
        final Collection<T> callbacks = getCallbacks(activity, clazz);
        if (callbacks == null || callbacks.isEmpty())
            return null;

        return Collections.unmodifiableCollection(callbacks);
    }
}
