# About
可以方便的监听某个Activity的事件

# Gradle
[![](https://jitpack.io/v/zj565061763/activity-event.svg)](https://jitpack.io/#zj565061763/activity-event)

# Example
```java
public class MainActivity extends BaseActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivityForResult(intent, 99);
            }
        });

        // 注册观察者
        mActivityDestroyedObserver.register(this);
        mActivityResultObserver.register(this);
        mActivityTouchEventObserver.register(this);

        // activity销毁的时候会移除观察者，也可以手动取消注册
//        mActivityTouchEventObserver.unregister();
    }

    private final ActivityDestroyedObserver mActivityDestroyedObserver = new ActivityDestroyedObserver()
    {
        @Override
        public void onActivityDestroyed(Activity activity)
        {
            Log.i(TAG, "onActivityDestroyed");
        }
    };

    private final ActivityResultObserver mActivityResultObserver = new ActivityResultObserver()
    {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data)
        {
            Log.i(TAG, "onActivityResult:" + requestCode + "," + resultCode + "," + data);
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