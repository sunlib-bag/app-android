package shaolizhi.sunshinebox.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.avos.avoscloud.AVUser;

import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.index.IndexActivity;
import shaolizhi.sunshinebox.ui.welcome.WelcomeActivity;
import shaolizhi.sunshinebox.utils.ActivationActivityManager;

/**
 * 由邵励治于2017/12/7创造.
 */

public class MainActivity extends ActivationActivityManager {

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void created(Bundle bundle) {
        automaticLogin();
    }

    private void automaticLogin() {
        if (AVUser.getCurrentUser() != null) {
            //已登录
            startActivity(IndexActivity.newIntent(this));
            finish();
        } else {
            //未登录
            startActivity(WelcomeActivity.newIntent(this));
            finish();
        }
    }

    @Override
    protected void resumed() {

    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, MainActivity.class);
    }
}
