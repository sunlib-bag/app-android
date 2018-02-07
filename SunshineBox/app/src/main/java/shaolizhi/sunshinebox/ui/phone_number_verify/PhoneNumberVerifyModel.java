package shaolizhi.sunshinebox.ui.phone_number_verify;

import android.support.annotation.NonNull;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVSMS;
import com.avos.avoscloud.AVSMSOption;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import shaolizhi.sunshinebox.data.ApiService;

import static shaolizhi.sunshinebox.data.ApiService.BASE_URL_DEVELOPMENT;

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

    @Override
    public void requestCheckCaptchaBean(@NonNull String phoneNumber, @NonNull String captcha) {
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_DEVELOPMENT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<CheckCaptchaBean> call = service.checkCaptchaAPI(phoneNumber, captcha);

        call.enqueue(new Callback<CheckCaptchaBean>() {
            @Override
            public void onResponse(@NonNull Call<CheckCaptchaBean> call, @NonNull Response<CheckCaptchaBean> response) {
                CheckCaptchaBean bean = response.body();
                if (bean != null) {
                    callBack.requestCheckCaptchaBeanSuccess();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CheckCaptchaBean> call, @NonNull Throwable t) {
                callBack.requestCheckCaptchaBeanFailure();
            }
        });
    }
}
