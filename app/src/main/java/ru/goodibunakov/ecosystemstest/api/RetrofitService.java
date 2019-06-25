package ru.goodibunakov.ecosystemstest.api;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import ru.goodibunakov.ecosystemstest.BuildConfig;

public class RetrofitService {

    private static OkHttpClient getClient(){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(logging);
        }
        clientBuilder.retryOnConnectionFailure(true);
        return clientBuilder.build();
    }

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .callFactory(getClient())
            .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(
                    new Persister(new AnnotationStrategy())))
            .build();


    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
