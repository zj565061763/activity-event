package com.sd.lib.eventact.callback;

import android.app.Activity;
import android.content.Intent;

public interface ActivityResultCallback extends ActivityEventCallback
{
    void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data);
}
