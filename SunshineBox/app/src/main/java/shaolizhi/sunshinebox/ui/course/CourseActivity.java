package shaolizhi.sunshinebox.ui.course;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.base.BaseActivity;
import shaolizhi.sunshinebox.utils.ToastUtils;

public class CourseActivity extends BaseActivity {

    final private static String RESOURCE_STORAGE_ADDRESS = "resource storage address";

    private String resourceStorageAddress;

    @OnClick(R.id.course_act_imagebutton)
    public void back() {
        finish();
    }

    @BindView(R.id.course_act_textview)
    TextView courseNameTextView;

    @Override
    protected int layoutId() {
        return R.layout.activity_course;
    }

    @Override
    protected void created(Bundle bundle) {
        getResourceStorageAddress();
        ToastUtils.showToast(resourceStorageAddress);
    }

    private void getResourceStorageAddress() {
        resourceStorageAddress = getIntent().getStringExtra(RESOURCE_STORAGE_ADDRESS);
    }

    @Override
    protected void resumed() {

    }

    public static Intent newIntent(Context packageContext, String resourceStorageAddress) {
        Intent intent = new Intent(packageContext, CourseActivity.class);
        intent.putExtra(RESOURCE_STORAGE_ADDRESS, resourceStorageAddress);
        return intent;
    }
}
