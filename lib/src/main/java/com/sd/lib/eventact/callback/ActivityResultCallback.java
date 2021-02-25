package com.sd.lib.eventact.callback;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ActivityResultCallback extends ActivityEventCallback
{
    void onActivityResult(@NonNull Activity activity, int requestCode, int resultCode, @Nullable Intent data);
}
