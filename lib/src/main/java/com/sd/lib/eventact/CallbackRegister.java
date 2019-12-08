package com.sd.lib.eventact;

import android.util.Log;

import com.sd.lib.eventact.callback.ActivityEventCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.WeakHashMap;

class CallbackRegister<K>
{
    private Map<K, Map<Class<? extends ActivityEventCallback>, Collection<? extends ActivityEventCallback>>> mMapCallback;

    private boolean isDebug()
    {
        return BuildConfig.DEBUG;
    }

    public <T extends ActivityEventCallback> boolean register(K key, Class<T> callbackClass, T callback)
    {
        if (key == null || callbackClass == null || callback == null)
            return false;

        if (mMapCallback == null)
            mMapCallback = new WeakHashMap<>();

        Map<Class<? extends ActivityEventCallback>, Collection<? extends ActivityEventCallback>> mapKeyCallback = mMapCallback.get(key);
        if (mapKeyCallback == null)
        {
            mapKeyCallback = new HashMap<>();
            mMapCallback.put(key, mapKeyCallback);
        }

        Collection callbacks = mapKeyCallback.get(callbackClass);
        if (callbacks == null)
        {
            callbacks = new LinkedHashSet<>();
            mapKeyCallback.put(callbackClass, callbacks);
        }

        final boolean result = callbacks.add(callback);

        if (isDebug())
        {
            final StringBuilder builder = new StringBuilder();
            builder.append("+++++ register ").append("\r\n");
            builder.append("result:").append(result).append("\r\n");
            builder.append("key:").append(key).append("\r\n");
            builder.append("callbackClass:").append(callbackClass.getSimpleName()).append("\r\n");
            builder.append("callback:").append(callback).append("\r\n");
            builder.append("size total:").append(mMapCallback != null ? mMapCallback.size() : 0).append(",").append("\r\n");
            builder.append("size callback type:").append(mapKeyCallback.size()).append("\r\n");
            builder.append("size callback:").append(callbacks.size()).append("\r\n");
            builder.append("holder:").append(mapKeyCallback);
            Log.i(CallbackRegister.class.getName(), builder.toString());
        }

        return result;
    }

    public <T extends ActivityEventCallback> boolean unregister(K key, Class<T> callbackClass, T callback)
    {
        if (key == null || callbackClass == null || callback == null)
            return false;

        if (mMapCallback == null)
            return false;

        final Map<Class<? extends ActivityEventCallback>, Collection<? extends ActivityEventCallback>> mapKeyCallback = mMapCallback.get(key);
        if (mapKeyCallback == null)
            return false;

        final Collection callbacks = mapKeyCallback.get(callbackClass);
        if (callbacks == null)
            return false;

        final boolean result = callbacks.remove(callback);

        if (callbacks.isEmpty())
        {
            mapKeyCallback.remove(callbackClass);
            if (mapKeyCallback.isEmpty())
            {
                mMapCallback.remove(key);
                if (mMapCallback.isEmpty())
                    mMapCallback = null;
            }
        }

        if (isDebug())
        {
            final StringBuilder builder = new StringBuilder();
            builder.append("----- unregister ").append("\r\n");
            builder.append("result:").append(result).append("\r\n");
            builder.append("key:").append(key).append("\r\n");
            builder.append("callbackClass:").append(callbackClass.getSimpleName()).append("\r\n");
            builder.append("callback:").append(callback).append("\r\n");
            builder.append("size total:").append(mMapCallback != null ? mMapCallback.size() : 0).append(",").append("\r\n");
            builder.append("size callback type:").append(mapKeyCallback.size()).append("\r\n");
            builder.append("size callback:").append(callbacks.size()).append("\r\n");
            builder.append("holder:").append(mapKeyCallback);
            Log.i(CallbackRegister.class.getName(), builder.toString());
        }

        return result;
    }

    public <T extends ActivityEventCallback> Collection<T> get(K key, Class<T> callbackClass)
    {
        if (key == null || callbackClass == null)
            return null;

        if (mMapCallback == null)
            return null;

        final Map<Class<? extends ActivityEventCallback>, Collection<? extends ActivityEventCallback>> mapKeyCallback = mMapCallback.get(key);
        if (mapKeyCallback == null)
            return null;

        final Collection callbacks = mapKeyCallback.get(callbackClass);
        if (callbacks == null)
            return null;

        return new ArrayList<>(callbacks);
    }

    public boolean remove(K key)
    {
        if (key == null)
            return false;

        if (mMapCallback == null)
            return false;

        final boolean result = mMapCallback.remove(key) != null;

        if (mMapCallback.isEmpty())
            mMapCallback = null;

        if (isDebug())
        {
            final StringBuilder builder = new StringBuilder();
            builder.append("----- remove ").append("\r\n");
            builder.append("result:").append(result).append("\r\n");
            builder.append("key:").append(key).append("\r\n");
            builder.append("size total:").append(mMapCallback != null ? mMapCallback.size() : 0).append(",").append("\r\n");
            Log.i(CallbackRegister.class.getName(), builder.toString());
        }

        return result;
    }

    public boolean isEmpty()
    {
        return mMapCallback == null || mMapCallback.isEmpty();
    }
}
