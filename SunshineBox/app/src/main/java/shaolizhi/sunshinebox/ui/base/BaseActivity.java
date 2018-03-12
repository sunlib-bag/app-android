package shaolizhi.sunshinebox.ui.base;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.avos.avoscloud.AVAnalytics;

import butterknife.ButterKnife;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.utils.ToastUtils;


/**
 * 由邵励治于2017/10/23创造.
 */

public abstract class BaseActivity extends AppCompatActivity {


    protected abstract
    @LayoutRes
    int layoutId();

    protected abstract void created(Bundle bundle);

    protected abstract void resumed();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(layoutId());
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        created(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        AVAnalytics.onResume(this);
        resumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    public void showToastForRequestResult(@NonNull String flag) {
        switch (flag) {
            case "203":
                toast203();
                break;
            case "401":
                toast401();
                break;
            case "402":
                toast402();
                break;
            case "403":
                toast403();
            default:
                break;
        }
    }


    protected void toast401() {
        ToastUtils.showToast(getString(R.string.base_string1));
        Log.i(this.getClass().getSimpleName(), getString(R.string.base_string3));
    }

    protected void toast402() {
        ToastUtils.showToast(getString(R.string.base_string1));
        Log.i(this.getClass().getSimpleName(), getString(R.string.base_string4));
    }

    protected void toast203() {
        ToastUtils.showToast(getString(R.string.base_string1));
        Log.i(this.getClass().getSimpleName(), getString(R.string.base_string5));
    }

    protected void toast403() {
        ToastUtils.showToast(getString(R.string.base_string7));
        Log.i(this.getClass().getSimpleName(), getString(R.string.base_string6));
    }
}
