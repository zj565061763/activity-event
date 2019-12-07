package com.sd.lib.eventact.callback;

import android.app.Activity;

public interface ActivityResumedCallback extends ActivityEventCallback
{
    void onActivityResumed(Activity activity);
}
