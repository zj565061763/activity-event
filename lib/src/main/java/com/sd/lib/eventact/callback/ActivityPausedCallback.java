package com.sd.lib.eventact.callback;

import android.app.Activity;

import androidx.annotation.NonNull;

public interface ActivityPausedCallback extends ActivityEventCallback
{
    void onActivityPaused(@NonNull Activity activity);
}
