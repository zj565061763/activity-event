package com.sd.myandroid.activity_event;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.eventact.ActivityEventDispatcher;
import com.sd.lib.eventact.ActivityEventDispatcherFactory;

public class BaseActivity extends AppCompatActivity
{
    private final ActivityEventDispatcher mEventDispatcher = ActivityEventDispatcherFactory.create(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mEventDispatcher.dispatch_onCreate(savedInstanceState);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mEventDispatcher.dispatch_onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mEventDispatcher.dispatch_onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mEventDispatcher.dispatch_onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mEventDispatcher.dispatch_onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mEventDispatcher.dispatch_onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mEventDispatcher.dispatch_onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        mEventDispatcher.dispatch_onRestoreInstanceState(savedInstanceState);
    }

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
