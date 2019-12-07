package com.sd.lib.eventact;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public interface ActivityEventDispatcher
{
    void dispatch_onCreate(Bundle savedInstanceState);

    void dispatch_onStart();

    void dispatch_onResume();

    void dispatch_onPause();

    void dispatch_onStop();

    void dispatch_onDestroy();

    void dispatch_onSaveInstanceState(Bundle outState);

    void dispatch_onRestoreInstanceState(Bundle savedInstanceState);

    void dispatch_onActivityResult(int requestCode, int resultCode, Intent data);

    boolean dispatch_dispatchTouchEvent(MotionEvent event);

    boolean dispatch_dispatchKeyEvent(KeyEvent event);
}
