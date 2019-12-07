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
            final StringBuilder builder = new StringBuilder();
            builder.append("+++++ register ").append("\r\n");
            builder.append("result:").append(result).append("\r\n");
            builder.append("activity:").append(activity).append("\r\n");
            builder.append("callbackClass:").append(callbackClass.getSimpleName()).append("\r\n");
            builder.append("callback:").append(callback).append("\r\n");
            builder.append("size total:").append(mMapCallback != null ? mMapCallback.size() : 0).append(",").append("\r\n");
            builder.append("size callback type:").append(mapActivityCallback.size()).append("\r\n");
            builder.append("size callback:").append(callbacks.size()).append("\r\n");
            builder.append("holder:").append(mapActivityCallback);
            Log.i(CallbackRegister.class.getName(), builder.toString());
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
            final StringBuilder builder = new StringBuilder();
            builder.append("----- unregister ").append("\r\n");
            builder.append("result:").append(result).append("\r\n");
            builder.append("activity:").append(activity).append("\r\n");
            builder.append("callbackClass:").append(callbackClass.getSimpleName()).append("\r\n");
            builder.append("callback:").append(callback).append("\r\n");
            builder.append("size total:").append(mMapCallback != null ? mMapCallback.size() : 0).append(",").append("\r\n");
            builder.append("size callback type:").append(mapActivityCallback.size()).append("\r\n");
            builder.append("size callback:").append(callbacks.size()).append("\r\n");
            builder.append("holder:").append(mapActivityCallback);
            Log.i(CallbackRegister.class.getName(), builder.toString());
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
