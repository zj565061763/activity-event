package com.sd.myandroid.activity_event;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.eventact.ActivityEventDispatcher;
import com.sd.lib.eventact.ActivityEventDispatcherFactory;

public class BaseActivity extends AppCompatActivity
{
    /**
     * 事件分发对象
     */
    private final ActivityEventDispatcher mEventDispatcher = ActivityEventDispatcherFactory.create(this);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mEventDispatcher.dispatch_onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        if (mEventDispatcher.dispatch_dispatchTouchEvent(ev))
            return true;

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (mEventDispatcher.dispatch_dispatchKeyEvent(event))
            return true;

        return super.dispatchKeyEvent(event);
    }
}
