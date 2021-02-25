package com.sd.lib.eventact.callback;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;

public interface ActivitySaveInstanceStateCallback extends ActivityEventCallback
{
    void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState);
}
