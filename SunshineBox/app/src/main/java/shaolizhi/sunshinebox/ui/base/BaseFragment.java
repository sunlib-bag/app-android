package shaolizhi.sunshinebox.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVAnalytics;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.utils.ToastUtils;


/**
 * 由邵励治于2017/10/23创造.
 */

public abstract class BaseFragment extends Fragment {

    protected String alias = this.getClass().getSimpleName();

    private Unbinder unbinder;

    protected Context mActivity;

    protected View mFragment;

    protected abstract
    @LayoutRes
    int layoutId();

    protected abstract void created(Bundle bundle);

    protected abstract void resumed();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragment = inflater.inflate(layoutId(), container, false);
        return mFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        created(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        AVAnalytics.onFragmentStart(alias);
        resumed();
    }

    @Override
    public void onPause() {
        super.onPause();
        AVAnalytics.onFragmentEnd(alias);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = context;
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
}
