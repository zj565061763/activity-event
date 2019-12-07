package com.sd.lib.eventact.callback;

import android.app.Activity;
import android.view.KeyEvent;

public interface ActivityKeyEventCallback extends ActivityEventCallback
{
    boolean onActivityDispatchKeyEvent(Activity activity, KeyEvent event);
}
