package com.sd.lib.eventact.callback;

public interface ActivityAllEventCallback extends
        ActivityCreatedCallback,
        ActivityStartedCallback,
        ActivityResumedCallback,
        ActivityPausedCallback,
        ActivityStoppedCallback,
        ActivityDestroyedCallback,
        ActivityResultCallback,
        ActivityInstanceStateCallback,
        ActivityTouchEventCallback,
        ActivityKeyEventCallback
{
}
