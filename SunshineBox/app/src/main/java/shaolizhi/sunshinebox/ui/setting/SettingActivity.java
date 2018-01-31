package shaolizhi.sunshinebox.ui.setting;

import android.content.DialogInterface;
import android.os.Bundle;

import butterknife.OnClick;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.utils.AlertDialogUtils;
import shaolizhi.sunshinebox.utils.FormalActivityManager;

public class SettingActivity extends FormalActivityManager {

    @OnClick(R.id.setting_act_button1)
    public void close() {
        finish();
    }

    @OnClick(R.id.setting_act_button2)
    public void exitActivated() {
        AlertDialogUtils.showAlertDialog(this, "是否退出激活", "退出激活可以使用于激活本设备的手机号重新进入可激活状态，您可以用该手机号激活其它阳光盒子", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void created(Bundle bundle) {

    }


    @Override
    protected void resumed() {

    }
}
