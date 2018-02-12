package shaolizhi.sunshinebox.ui.index;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.base.BaseActivity;

public class IndexActivity extends BaseActivity {

    @Override
    protected int layoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected void created(Bundle bundle) {

    }

    @Override
    protected void resumed() {

    }

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, IndexActivity.class);
    }
}
