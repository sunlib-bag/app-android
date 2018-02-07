package shaolizhi.sunshinebox.ui.phone_number_verify;

import android.os.CountDownTimer;

import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.base.BaseActivity;
import shaolizhi.sunshinebox.ui.base.BaseFragment;
import shaolizhi.sunshinebox.utils.App;
import shaolizhi.sunshinebox.utils.ToastUtils;

/**
 * 由邵励治于2017/11/29创造.
 */

public class PhoneNumberVerifyPresenter implements PhoneNumberVerifyContract.Presenter, PhoneNumberVerifyContract.CallBack {

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
    public void startCountDown() {
        if (!isTimerRunning) {
            timer.start();
        }
    }

    @Override
    public void requestSendCaptchaBeanSuccess() {
        ToastUtils.showToast("验证码发送成功");
//        if (bean.getFlag() != null) {
//            switch (bean.getFlag()) {
//                case "001":
//                    if (Objects.equals(bean.getMessage(), "success")) {
//                        ToastUtils.showToast("验证码发送成功");
//                    } else if (Objects.equals(bean.getMessage(), "failure")) {
//                        ToastUtils.showToast("对不起，该手机号没有激活权限或已经激活");
//                        resumeResendButtonState();
//                    } else {
//                        if (view instanceof BaseFragment) {
//                            ((BaseFragment) view).showToastForRequestResult("401");
//                        }
//                        if (view instanceof BaseActivity) {
//                            ((BaseActivity) view).showToastForRequestResult("401");
//                        }
//                    }
//                    break;
//                default:
//                    if (view instanceof BaseFragment) {
//                        ((BaseFragment) view).showToastForRequestResult(bean.getFlag());
//                    }
//                    if (view instanceof BaseActivity) {
//                        ((BaseActivity) view).showToastForRequestResult(bean.getFlag());
//                    }
//                    break;
//            }
//        }
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
//        if (view instanceof BaseFragment) {
//            ((BaseFragment) view).showToastForRequestResult("403");
//        }
//        if (view instanceof BaseActivity) {
//            ((BaseActivity) view).showToastForRequestResult("403");
//        }
    }

    @Override
    public void requestCheckCaptchaBeanSuccess() {
//        if (bean.getFlag() != null) {
//            switch (bean.getFlag()) {
//                case "001":
//                    if (Objects.equals(bean.getMessage(), "success")) {
//                        if (bean.getContent().getUuid() != null) {
//                            SharedPreferencesUtils.put(view.getContext(), ConstantData.UUID, bean.getContent().getUuid());
//                            view.openVerifyActivationCodeActivity();
//                        }
//                    } else if (Objects.equals(bean.getMessage(), "incorrect")) {
//                        ToastUtils.showToast("您输入的验证码有误");
//                    } else if (Objects.equals(bean.getMessage(), "expired")) {
//                        ToastUtils.showToast("您的验证码已过期");
//                    } else {
//                        if (view instanceof BaseFragment) {
//                            ((BaseFragment) view).showToastForRequestResult("401");
//                        }
//                        if (view instanceof BaseActivity) {
//                            ((BaseActivity) view).showToastForRequestResult("401");
//                        }
//                    }
//                    break;
//                default:
//                    if (view instanceof BaseFragment) {
//                        ((BaseFragment) view).showToastForRequestResult(bean.getFlag());
//                    }
//                    if (view instanceof BaseActivity) {
//                        ((BaseActivity) view).showToastForRequestResult(bean.getFlag());
//                    }
//                    break;
//            }
//        }
    }

    @Override
    public void requestCheckCaptchaBeanFailure() {
        if (view instanceof BaseFragment) {
            ((BaseFragment) view).showToastForRequestResult("403");
        }
        if (view instanceof BaseActivity) {
            ((BaseActivity) view).showToastForRequestResult("403");
        }
    }
}
