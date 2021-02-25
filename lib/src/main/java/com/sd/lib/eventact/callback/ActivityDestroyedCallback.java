package com.sd.lib.eventact.callback;

import android.app.Activity;

import androidx.annotation.NonNull;

public interface ActivityDestroyedCallback extends ActivityEventCallback
{
    void onActivityDestroyed(@NonNull Activity activity);
}
