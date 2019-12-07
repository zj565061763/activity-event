package com.sd.lib.eventact.callback;

import android.app.Activity;

public interface ActivityPausedCallback extends ActivityEventCallback
{
    void onActivityPaused(Activity activity);
}
