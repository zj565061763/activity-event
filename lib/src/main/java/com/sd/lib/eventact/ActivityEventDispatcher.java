package com.sd.lib.eventact;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.MotionEvent;

public interface ActivityEventDispatcher
{
    void dispatch_onActivityResult(int requestCode, int resultCode, Intent data);

    boolean dispatch_dispatchTouchEvent(MotionEvent event);

    boolean dispatch_dispatchKeyEvent(KeyEvent event);
}
