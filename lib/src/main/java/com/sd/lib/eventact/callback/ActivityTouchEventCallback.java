package com.sd.lib.eventact.callback;

import android.app.Activity;
import android.view.MotionEvent;

public interface ActivityTouchEventCallback extends ActivityEventCallback
{
    boolean onActivityDispatchTouchEvent(Activity activity, MotionEvent event);
}
