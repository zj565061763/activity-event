package com.sd.lib.eventact.callback;

import android.app.Activity;
import android.os.Bundle;

public interface ActivitySaveInstanceStateCallback extends ActivityEventCallback
{
    void onActivitySaveInstanceState(Activity activity, Bundle outState);
}
