package com.sd.lib.eventact.callback;

import android.app.Activity;
import android.os.Bundle;

public interface ActivityCreatedCallback extends ActivityEventCallback
{
    void onActivityCreated(Activity activity, Bundle savedInstanceState);
}
