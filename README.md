# About
可以方便的监听某个Activity的事件

# Gradle
[![](https://jitpack.io/v/zj565061763/activity-event.svg)](https://jitpack.io/#zj565061763/activity-event)

# Example
```java
public class TestView extends FrameLayout
{
    public static final String TAG = TestView.class.getSimpleName();

    private final Activity mActivity;

    public TestView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);

        if (!(context instanceof Activity))
            throw new IllegalArgumentException("context must be instance of " + Activity.class);

        mActivity = (Activity) context;
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        /**
         * 注册观察者，监听Activity的事件。
         * 观察者会在Activity销毁的时候被移除，如果需要提前取消，可以调用{@link ActivityEventObserver#unregister()}
         */
        mActivityResultObserver.register(mActivity);
        mActivityDestroyedObserver.register(mActivity);
        mActivityTouchEventObserver.register(mActivity);
    }

    private final ActivityResultObserver mActivityResultObserver = new ActivityResultObserver()
    {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data)
        {
            Log.i(TAG, "onActivityResult:" + requestCode + "," + resultCode + "," + data);
        }
    };

    private final ActivityDestroyedObserver mActivityDestroyedObserver = new ActivityDestroyedObserver()
    {
        @Override
        public void onActivityDestroyed(Activity activity)
        {
            Log.i(TAG, "onActivityDestroyed");
        }
    };

    private final ActivityTouchEventObserver mActivityTouchEventObserver = new ActivityTouchEventObserver()
    {
        @Override
        public boolean onActivityDispatchTouchEvent(Activity activity, MotionEvent event)
        {
            Log.i(TAG, "onActivityDispatchTouchEvent:" + event.getAction());
            return false;
        }
    };
}
```