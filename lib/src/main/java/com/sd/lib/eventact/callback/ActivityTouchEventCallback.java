package com.sd.lib.eventact.callback;

import android.app.Activity;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

public interface ActivityTouchEventCallback extends ActivityEventCallback
{
    boolean onActivityDispatchTouchEvent(@NonNull Activity activity, @NonNull MotionEvent event);
}
