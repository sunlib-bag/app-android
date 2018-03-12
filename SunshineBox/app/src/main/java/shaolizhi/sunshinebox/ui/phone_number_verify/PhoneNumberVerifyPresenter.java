package shaolizhi.sunshinebox.ui.phone_number_verify;

import android.os.CountDownTimer;

import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.utils.ActivityCollectorUtils;
import shaolizhi.sunshinebox.utils.App;
import shaolizhi.sunshinebox.utils.ToastUtils;

/**
 * 由邵励治于2017/11/29创造.
 */

public class PhoneNumberVerifyPresenter implements PhoneNumberVerifyContract.Presenter, PhoneNumberVerifyContract.CallBack {
    private final static String PASSWORD_FOR_LEANCLOUD_LOGIN = "123456";

    private PhoneNumberVerifyContract.View view;

    private PhoneNumberVerifyContract.Model model;

    PhoneNumberVerifyPresenter(PhoneNumberVerifyContract.View view) {
        this.view = view;
        model = new PhoneNumberVerifyModel(this);
    }

    private Boolean isTimerRunning = false;

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long l) {
            if (view != null) {
                view.setResendButtonEnable(false);
                view.setResendButtonText(String.valueOf(l / 1000));
                isTimerRunning = true;
            }
        }

        @Override
        public void onFinish() {
            if (view != null) {
                view.setResendButtonEnable(true);
                view.setResendButtonText(App.mAppContext.getString(R.string.phone_number_verify_act_string4));
                isTimerRunning = false;
            }
        }
    };

    @Override
    public void start() {
        view.setUpView();
    }

    @Override
    public void tryToRequestCaptcha() {
        startCountDown();
        model.requestSendCaptchaBean(view.getPhoneNumber());
    }

    @Override
    public void tryToVerifyCaptcha() {
        model.requestCheckCaptchaBean(view.getPhoneNumber(), view.getCaptcha());
    }

    @Override
    public void tryToLogin() {
        model.requestToLogin(view.getPhoneNumber(), PASSWORD_FOR_LEANCLOUD_LOGIN);
    }

    @Override
    public void startCountDown() {
        if (!isTimerRunning) {
            timer.start();
        }
    }

    @Override
    public void requestSendCaptchaBeanSuccess() {
        ToastUtils.showToast("验证码发送成功");
    }

    private void resumeResendButtonState() {
        timer.cancel();
        view.setResendButtonEnable(true);
        view.setResendButtonText(App.mAppContext.getString(R.string.phone_number_verify_act_string4));
        isTimerRunning = false;
    }

    @Override
    public void requestSendCaptchaBeanFailure() {
        resumeResendButtonState();
        ToastUtils.showToast("验证码发送失败");
    }

    @Override
    public void requestCheckCaptchaBeanSuccess() {
        ToastUtils.showToast("验证码验证成功");
        view.openIndexActivity();
        ActivityCollectorUtils.finishAll();
    }

    @Override
    public void requestCheckCaptchaBeanFailure() {
        ToastUtils.showToast("验证码验证失败");
    }

    @Override
    public void requestToLoginSuccess() {
        view.openIndexActivity();
    }

    @Override
    public void requestToLoginFailure() {
        ToastUtils.showToast("登录失败");
    }
}
