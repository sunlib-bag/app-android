package shaolizhi.sunshinebox.ui.welcome;

import android.content.Intent;
import android.os.Bundle;

import butterknife.OnClick;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.phone_number_verify.PhoneNumberVerifyActivity;
import shaolizhi.sunshinebox.utils.ActivationActivityManager;

public class WelcomeActivity extends ActivationActivityManager {

    @OnClick(R.id.welcome_button)
    public void openActivity() {
        Intent intent = new Intent(WelcomeActivity.this, PhoneNumberVerifyActivity.class);
        startActivity(intent);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void created(Bundle bundle) {

    }

    @Override
    protected void resumed() {

    }


}
