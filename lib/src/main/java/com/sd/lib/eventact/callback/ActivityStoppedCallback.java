package com.sd.lib.eventact.callback;

import android.app.Activity;

import androidx.annotation.NonNull;

public interface ActivityStoppedCallback extends ActivityEventCallback
{
    void onActivityStopped(@NonNull Activity activity);
}
