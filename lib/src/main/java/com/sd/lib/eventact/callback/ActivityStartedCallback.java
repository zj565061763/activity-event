package com.sd.lib.eventact.callback;

import android.app.Activity;

public interface ActivityStartedCallback extends ActivityEventCallback
{
    void onActivityStarted(Activity activity);
}
