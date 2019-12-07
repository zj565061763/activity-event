package com.sd.lib.eventact.callback;

import android.app.Activity;

public interface ActivityDestroyedCallback extends ActivityEventCallback
{
    void onActivityDestroyed(Activity activity);
}
