package shaolizhi.sunshinebox.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;

import shaolizhi.sunshinebox.ui.base.BaseActivity;

/**
 * 由邵励治于2017/12/8创造.
 */

public abstract class FormalActivityManager extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorUtils.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }
}
