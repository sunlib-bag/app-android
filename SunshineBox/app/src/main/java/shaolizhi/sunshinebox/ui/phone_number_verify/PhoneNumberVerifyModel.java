package shaolizhi.sunshinebox.ui.phone_number_verify;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import shaolizhi.sunshinebox.data.ApiService;
import shaolizhi.sunshinebox.utils.ServiceGenerator;

import static shaolizhi.sunshinebox.data.ApiService.BASE_URL_DEVELOPMENT;

/**
 * 由邵励治于2017/11/29创造.
 */

public class PhoneNumberVerifyModel implements PhoneNumberVerifyContract.Model {

    private PhoneNumberVerifyContract.CallBack callBack;

    PhoneNumberVerifyModel(PhoneNumberVerifyContract.CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void requestSendCaptchaBean(@NonNull String phoneNumber) {
        ApiService service = ServiceGenerator.createService(ApiService.class);

        Call<SendCaptchaBean> call = service.sendCaptchaAPI(phoneNumber);

        call.enqueue(new Callback<SendCaptchaBean>() {
            @Override
            public void onResponse(@NonNull Call<SendCaptchaBean> call, @NonNull Response<SendCaptchaBean> response) {
                SendCaptchaBean bean = response.body();
                if (bean != null) {
                    callBack.requestSendCaptchaBeanSuccess(bean);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendCaptchaBean> call, @NonNull Throwable t) {
                callBack.requestSendCaptchaBeanFailure();
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
                    callBack.requestCheckCaptchaBeanSuccess(bean);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CheckCaptchaBean> call, @NonNull Throwable t) {
                callBack.requestCheckCaptchaBeanFailure();
            }
        });
    }
}
