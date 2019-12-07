package com.sd.lib.eventact;

import android.app.Activity;
import android.util.Log;

import com.sd.lib.eventact.callback.ActivityEventCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.WeakHashMap;

class CallbackRegister
{
    private Map<Activity, Map<Class<? extends ActivityEventCallback>, Collection<? extends ActivityEventCallback>>> mMapCallback;

    private boolean isDebug()
    {
        return BuildConfig.DEBUG;
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

        final boolean result = callbacks.add(callback);

        if (isDebug())
        {
            Log.i(CallbackRegister.class.getName(),
                    "+++++ register " + "\r\n" +
                            "result:" + result + "\r\n" +
                            "size:" + mMapCallback.size() + "," + mapActivityCallback.size() + "," + callbacks.size() + "\r\n" +
                            "activity:" + activity + "\r\n" +
                            "callbackClass:" + callbackClass + "\r\n" +
                            "callback:" + callback
            );
        }

        return result;
    }

    public <T extends ActivityEventCallback> boolean unregister(Activity activity, Class<T> callbackClass, T callback)
    {
        if (activity == null || callbackClass == null || callback == null)
            return false;

        if (mMapCallback == null)
            return false;

        final Map<Class<? extends ActivityEventCallback>, Collection<? extends ActivityEventCallback>> mapActivityCallback = mMapCallback.get(activity);
        if (mapActivityCallback == null)
            return false;

        final Collection callbacks = mapActivityCallback.get(callbackClass);
        if (callbacks == null)
            return false;

        final boolean result = callbacks.remove(callback);

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

        if (isDebug())
        {
            Log.i(CallbackRegister.class.getName(),
                    "----- unregister " + "\r\n" +
                            "result:" + result + "\r\n" +
                            "size:" + (mMapCallback != null ? mMapCallback.size() : 0) + "," + mapActivityCallback.size() + "," + callbacks.size() + "\r\n" +
                            "activity:" + activity + "\r\n" +
                            "callbackClass:" + callbackClass + "\r\n" +
                            "callback:" + callback
            );
        }

        return result;
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

        return new ArrayList<>(callbacks);
    }

    public boolean isEmpty()
    {
        return mMapCallback == null || mMapCallback.isEmpty();
    }
}
