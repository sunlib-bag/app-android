package shaolizhi.sunshinebox.ui.course;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.base.BaseActivity;

public class CourseActivity extends BaseActivity {

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

    }

    @Override
    protected void resumed() {

    }
}
