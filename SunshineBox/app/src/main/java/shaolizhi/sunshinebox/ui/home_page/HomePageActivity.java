package shaolizhi.sunshinebox.ui.home_page;

import android.content.Intent;
import android.os.Bundle;

import butterknife.OnClick;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.index.IndexActivity;
import shaolizhi.sunshinebox.ui.setting.SettingActivity;
import shaolizhi.sunshinebox.utils.ActivityCollectorUtils;
import shaolizhi.sunshinebox.utils.FormalActivityManager;

public class HomePageActivity extends FormalActivityManager {

    @OnClick(R.id.home_page_act_button)
    public void openIndexActivity() {
        Intent intent = new Intent(HomePageActivity.this, IndexActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.home_page_act_imagebutton)
    public void openSettingActivity() {
        Intent intent = new Intent(HomePageActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    protected void created(Bundle bundle) {
        ActivityCollectorUtils.finishAll();
//        SharedPreferencesUtils.clear(this);
    }

    @Override
    protected void resumed() {

    }

//    @Override
//    public void onBackPressed() {
//        UIUtils.ifBackOut(this, getString(R.string.home_page_act_string1));
//    }
}
