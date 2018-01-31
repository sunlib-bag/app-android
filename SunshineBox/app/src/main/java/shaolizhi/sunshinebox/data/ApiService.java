package shaolizhi.sunshinebox.data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import shaolizhi.sunshinebox.ui.index.IndexBean;
import shaolizhi.sunshinebox.ui.phone_number_verify.CheckCaptchaBean;
import shaolizhi.sunshinebox.ui.phone_number_verify.SendCaptchaBean;

/**
 * 由邵励治于2017/11/29创造.
 */

public interface ApiService {
    String BASE_URL_DEVELOPMENT = "http://111.231.71.150/";
    String BASE_URL_PRODUCTION = "http://39.104.55.82";

    @FormUrlEncoded
    @POST("sunshinebox/activation_system/SendCaptcha.php")
    Call<SendCaptchaBean> sendCaptchaAPI(@Field("phone_number") String phoneNumber);

    @FormUrlEncoded
    @POST("sunshinebox/activation_system/CheckCaptcha.php")
    Call<CheckCaptchaBean> checkCaptchaAPI(@Field("phone_number") String phoneNumber, @Field("captcha") String captcha);

    @FormUrlEncoded
    @POST("sunshinebox/home_page/GetIndexData.php")
    Call<IndexBean> getIndexDataAPI(@Field("course_type") String courseType, @Field("max_last_modification_time") String maxLastModificationTime);

    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrl(@Url String url);
}
