package com.sd.lib.eventact;

import android.util.Log;

import androidx.annotation.NonNull;

import com.sd.lib.eventact.callback.ActivityEventCallback;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class CallbackHolder
{
    private final Map<Class<? extends ActivityEventCallback>, Map<ActivityEventCallback, String>> mCallbackHolder = new ConcurrentHashMap<>();

    private boolean isDebug()
    {
        return ActivityEventManager.getInstance().isDebug();
    }

    public boolean isEmpty()
    {
        return mCallbackHolder.isEmpty();
    }

    public <T extends ActivityEventCallback> void add(@NonNull Class<T> clazz, @NonNull T callback)
    {
        if (clazz == null || callback == null)
            throw new IllegalArgumentException("null argument");

        Map<ActivityEventCallback, String> holder = mCallbackHolder.get(clazz);
        if (holder == null)
        {
            holder = new ConcurrentHashMap<>();
            mCallbackHolder.put(clazz, holder);
        }

        final String put = holder.put(callback, "");
        if (put == null)
        {
            if (isDebug())
            {
                final StringBuilder builder = new StringBuilder();
                builder.append("+++++ add ").append(clazz.getSimpleName()).append(" -> ").append(callback)
                        .append(" size:").append(holder.size())
                        .append(" totalSize:").append(mCallbackHolder.size());
                Log.i(CallbackHolder.class.getName(), builder.toString());
            }
        }
    }

    public <T extends ActivityEventCallback> void remove(@NonNull Class<T> clazz, @NonNull T callback)
    {
        if (clazz == null || callback == null)
            throw new IllegalArgumentException("null argument");

        final Map<ActivityEventCallback, String> holder = mCallbackHolder.get(clazz);
        if (holder == null)
            return;

        final String remove = holder.remove(callback);
        if (remove != null)
        {
            if (holder.isEmpty())
                mCallbackHolder.remove(clazz);

            if (isDebug())
            {
                final StringBuilder builder = new StringBuilder();
                builder.append("----- remove ").append(clazz.getSimpleName()).append(" -> ").append(callback)
                        .append(" size:").append(holder.size())
                        .append(" totalSize:").append(mCallbackHolder.size());
                Log.i(CallbackHolder.class.getName(), builder.toString());
            }
        }
    }

    public <T extends ActivityEventCallback> Collection<T> get(@NonNull Class<T> clazz)
    {
        if (clazz == null)
            throw new IllegalArgumentException("null argument");

        final Map holder = mCallbackHolder.get(clazz);
        if (holder == null)
            return null;

        return holder.keySet();
    }
}
