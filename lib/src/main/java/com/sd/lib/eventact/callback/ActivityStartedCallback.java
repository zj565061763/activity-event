package com.sd.lib.eventact.callback;

import android.app.Activity;

import androidx.annotation.NonNull;

public interface ActivityStartedCallback extends ActivityEventCallback
{
    void onActivityStarted(@NonNull Activity activity);
}
