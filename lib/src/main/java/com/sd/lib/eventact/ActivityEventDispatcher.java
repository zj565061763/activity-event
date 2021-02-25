package com.sd.lib.eventact;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ActivityEventDispatcher
{
    void dispatch_onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

    boolean dispatch_dispatchTouchEvent(@NonNull MotionEvent event);

    boolean dispatch_dispatchKeyEvent(@NonNull KeyEvent event);
}
