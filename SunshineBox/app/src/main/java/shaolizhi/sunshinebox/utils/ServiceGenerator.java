package shaolizhi.sunshinebox.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static shaolizhi.sunshinebox.data.ApiService.BASE_URL_DEVELOPMENT;

/**
 * 由邵励治于2017/12/19创造.
 */

public class ServiceGenerator {

    private static Gson gson = new GsonBuilder().create();

//    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL_DEVELOPMENT)
//                    .callbackExecutor(executorService)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
