package shaolizhi.sunshinebox.ui.phone_number_verify;

import android.support.annotation.NonNull;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVSMS;
import com.avos.avoscloud.AVSMSOption;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;

/**
 * 由邵励治于2017/11/29创造.
 */

public class PhoneNumberVerifyModel implements PhoneNumberVerifyContract.Model {

    private PhoneNumberVerifyContract.CallBack callBack;

    PhoneNumberVerifyModel(PhoneNumberVerifyContract.CallBack callBack) {
        this.callBack = callBack;
    }

    //Call API1:发送验证码
    @Override
    public void requestSendCaptchaBean(@NonNull String phoneNumber) {
        AVSMSOption option = new AVSMSOption();
        option.setTtl(10);
        option.setApplicationName("阳光盒子");
        option.setOperation("短信验证码");
        AVSMS.requestSMSCodeInBackground(phoneNumber, option, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    //请求成功
                    callBack.requestSendCaptchaBeanSuccess();
                } else {
                    //请求失败
                    callBack.requestSendCaptchaBeanFailure();
                }
            }
        });
    }

    //Call API2：验证验证码
    @Override
    public void requestCheckCaptchaBean(@NonNull String phoneNumber, @NonNull String captcha) {
        AVSMS.verifySMSCodeInBackground(captcha, phoneNumber, new AVMobilePhoneVerifyCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    //验证成功
                    callBack.requestCheckCaptchaBeanSuccess();
                } else {
                    //验证失败
                    callBack.requestCheckCaptchaBeanFailure();
                }
            }
        });
    }

    //Call API3: 验证手机号
    @Override
    public void requestToLogin(@NonNull String phoneNumber, @NonNull String password) {
        AVUser.logInInBackground(phoneNumber, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    //登录成功
                    callBack.requestToLoginSuccess();
                } else {
                    //登录失败
                    callBack.requestToLoginFailure();
                }
            }
        });
    }
}
