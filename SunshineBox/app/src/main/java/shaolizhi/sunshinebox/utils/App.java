package shaolizhi.sunshinebox.utils;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.tencent.bugly.Bugly;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import shaolizhi.sunshinebox.BuildConfig;
import shaolizhi.sunshinebox.objectbox.courses.MyObjectBox;

/**
 * 由邵励治于2017/10/23创造.
 */

public class App extends MultiDexApplication {
    public static Context mAppContext;

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        //ObjectBox
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(this);
        }
        Log.e("APP", "Using ObjectBox" + BoxStore.getVersion() + "(" + BoxStore.getVersionNative() + ")");
        //LeanCloud
        AVOSCloud.initialize(this, "CQBviH8f3TNrbRwzHfjTw7yk-gzGzoHsz", "5KnQsMhpWAAXYXvzbGV1YU62");
        AVAnalytics.enableCrashReport(this, true);
        //开启调试日志
        AVOSCloud.setDebugLogEnabled(true);

        Bugly.init(getApplicationContext(), "40483fa79f", false);
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
