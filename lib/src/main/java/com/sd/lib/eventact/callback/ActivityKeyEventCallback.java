package com.sd.lib.eventact.callback;

import android.app.Activity;
import android.view.KeyEvent;

import androidx.annotation.NonNull;

public interface ActivityKeyEventCallback extends ActivityEventCallback
{
    boolean onActivityDispatchKeyEvent(@NonNull Activity activity, @NonNull KeyEvent event);
}
