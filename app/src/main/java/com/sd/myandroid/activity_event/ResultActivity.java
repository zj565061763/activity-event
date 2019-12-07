package com.sd.myandroid.activity_event;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity
{
    @Override
    public void onBackPressed()
    {
        setResult(100);
        super.onBackPressed();
    }
}
