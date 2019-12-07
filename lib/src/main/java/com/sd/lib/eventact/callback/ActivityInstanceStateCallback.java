package com.sd.lib.eventact.callback;

import android.app.Activity;
import android.os.Bundle;

public interface ActivityInstanceStateCallback extends ActivityEventCallback
{
    void onActivitySaveInstanceState(Activity activity, Bundle outState);

    void onActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState);
}
