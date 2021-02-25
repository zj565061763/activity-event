package com.sd.lib.eventact.callback;

import android.app.Activity;

import androidx.annotation.NonNull;

public interface ActivityResumedCallback extends ActivityEventCallback
{
    void onActivityResumed(@NonNull Activity activity);
}
