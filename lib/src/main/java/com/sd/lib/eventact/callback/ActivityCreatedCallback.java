package com.sd.lib.eventact.callback;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ActivityCreatedCallback extends ActivityEventCallback
{
    void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState);
}
