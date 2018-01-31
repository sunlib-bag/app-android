package shaolizhi.sunshinebox.ui.main;

import android.content.Intent;
import android.os.Bundle;

import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.index.IndexActivity;
import shaolizhi.sunshinebox.ui.welcome.WelcomeActivity;
import shaolizhi.sunshinebox.utils.ActivationActivityManager;
import shaolizhi.sunshinebox.utils.SharedPreferencesUtils;

import static shaolizhi.sunshinebox.data.ConstantData.UUID;

/**
 * 由邵励治于2017/12/7创造.
 */

public class MainActivity extends ActivationActivityManager {

    Intent intent;

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void created(Bundle bundle) {
        String uuid = SharedPreferencesUtils.get(this, UUID);
        if (uuid == null) {
            intent = new Intent(MainActivity.this, WelcomeActivity.class);
        } else {
            intent = new Intent(MainActivity.this, IndexActivity.class);
        }
        startActivity(intent);
    }

    @Override
    protected void resumed() {

    }
}
