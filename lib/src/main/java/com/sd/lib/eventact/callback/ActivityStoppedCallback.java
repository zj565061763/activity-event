package com.sd.lib.eventact.callback;

import android.app.Activity;

public interface ActivityStoppedCallback extends ActivityEventCallback
{
    void onActivityStopped(Activity activity);
}
